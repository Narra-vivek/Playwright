package Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AllureEnvironmentWriter {

    public static void writeEnvironmentProperties() {
        Properties props = new Properties();

        props.setProperty("Browser", ConfigReader.get("Browser", "Chrome"));
//        props.setProperty("Browser.Version", ConfigReader.get("browser.version", "114.0"));
        props.setProperty("Environment", ConfigReader.get("Environment", "Production"));
        props.setProperty("Base.URL", ConfigReader.get("Base.url", "https://www.hydroflask.com/"));

        try {
            File file = new File("allure-results/environment.properties");
            file.getParentFile().mkdirs(); // Ensure directory exists
            FileWriter writer = new FileWriter(file);
            props.store(writer, "Environment Properties");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
