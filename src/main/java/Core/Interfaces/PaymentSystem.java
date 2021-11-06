package Core.Interfaces;

import java.net.MalformedURLException;

public interface PaymentSystem {
    public void setUrl(String url) throws MalformedURLException;
    public String sendRequest() throws Exception;
}
