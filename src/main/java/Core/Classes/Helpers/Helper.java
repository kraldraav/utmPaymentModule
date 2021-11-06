package Core.Classes.Helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Helper {
    public static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return true;
    }

    public static void RunExec(String utmCommand) {
        Runtime run = Runtime.getRuntime();
        try{
            Process proc = run.exec(utmCommand);
            proc.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error executing shell command: " + e.getMessage());
        }
    }
}
