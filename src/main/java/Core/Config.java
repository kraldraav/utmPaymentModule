package Core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private Properties prop = new Properties();
    private InputStream input = null;
    public static Config Instance = new Config();

    private Config() {
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public String getSetting(String settingName) {
        return prop.getProperty(settingName);
    }
}
