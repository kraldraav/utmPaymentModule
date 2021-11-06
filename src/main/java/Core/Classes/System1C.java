package Core.Classes;

import Core.Interfaces.PaymentSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public final class System1C implements PaymentSystem {

    private URL url;
    private static System1C instance;
    private String responseSource = "";

    private System1C(){};

    public static System1C getInstance() {
        if(instance == null) {
            instance = new System1C();
        }
        return instance;
    }

    @Override
    public void setUrl(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    @Override
    public String sendRequest() throws Exception {
        if(url.toString().isEmpty()) {
            throw new Exception("Url is empty!");
        }
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        if(status != 200) {
            throw new Exception("Response code: " + status);
        }

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String line = "";
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            this.responseSource = response.toString();

        } catch (Exception e) {

        } finally {
            con.disconnect();
        }

        return this.responseSource;
    }
}
