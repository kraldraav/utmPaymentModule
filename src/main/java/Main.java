import Core.Classes.Helpers.Helper;
import Core.Classes.MysqlConnector;
import Core.Classes.Payment;
import Core.Classes.System1C;
import Core.Classes.Helpers.UtmDbHelper;
import Core.Config;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String urlPaymentSystem = Config.Instance.getSetting("paymentSystemUrl");
        if (Helper.isValidUrl(urlPaymentSystem)) {
            try {
                System.out.println("Initialize payment system component");
                System1C sys1C = System1C.getInstance();

                MysqlConnector mysql = new MysqlConnector();
                UtmDbHelper dbHelper = new UtmDbHelper(mysql);

                String lastTimestamp = dbHelper.getLastTimestamp();
                urlPaymentSystem += lastTimestamp;
                System.out.println("Set request URL");
                sys1C.setUrl(urlPaymentSystem);

                System.out.println("Send request to Payment system...");
                String JsonResponse = sys1C.sendRequest();

                ArrayList<Payment> Payments = new ArrayList<>();

                System.out.println("Parsing response from payment system...");
                JSONArray jsonArr = new JSONArray(JsonResponse);

                for (Object json: jsonArr) {
                    Payment payment = new Payment(((JSONObject) json).toString());
                    String taxNumber = payment.inn;
                    String accountId = dbHelper.getClientAccountIdByTaxNumber(taxNumber);
                    if(!accountId.isEmpty()) {
                        payment.personalAccount = accountId;
                        Payments.add(payment);
                    }
                }

                String lastTimestampPayment = "";
                for(Payment payment: Payments) {
                    LocalDateTime dateTime = LocalDateTime.parse(payment.datetime);
                    Timestamp timestamp = Timestamp.valueOf(dateTime);
                    String prepareExecutionString = String.format("/netup/utm5/bin/utm5_payment_tool -a %s -b %s -t %s -L \"%s\" -e %s \n",
                            payment.personalAccount, payment.amount.toString(), timestamp.getTime(), payment.description, payment.identif);
                    Helper.RunExec(prepareExecutionString);
                    //System.out.println(prepareExecutionString);
                    lastTimestampPayment = payment.datetime;
                }

                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                LocalDateTime dateTime = LocalDateTime.parse(lastTimestampPayment);
                dbHelper.setLastTimestamp(dateTime.format(formatter1));

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
