package testExecute;

import io.qameta.allure.Allure;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class Allure_Video {
    public static void attachVideo(Path videoPath, String name) {
        try (FileInputStream fis = new FileInputStream(videoPath.toFile())) {
            Allure.addAttachment(name, "video/webm", fis, ".webm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
