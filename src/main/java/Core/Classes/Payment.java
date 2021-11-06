package Core.Classes;


import java.math.BigDecimal;

import org.json.JSONObject;

public class Payment {
    public String inn;
    public String description;
    public String datetime;
    public String identif;
    public String personalAccount;
    public BigDecimal amount;

    public Payment(String inn, String description, String identif, String datetime, String amount) {
        this.inn = inn;
        this.description = description;
        this.datetime = datetime;
        this.identif = identif;
        this.amount = new BigDecimal(amount);
    }

    public Payment(String Json) {
        try {
            JSONObject jsonObj = new JSONObject(Json);
            this.inn = (String) jsonObj.get("inn");
            this.identif = (String) jsonObj.get("identif");
            this.description = ((String) jsonObj.get("desc_payment")).replace("'", "").replace("\"", "\"");
            this.datetime = (String) jsonObj.get("datetime_paymen");
            this.amount = new BigDecimal(((Object)jsonObj.get("summ_payment")).toString());
        } catch (Exception e) {

        }

    }
}
