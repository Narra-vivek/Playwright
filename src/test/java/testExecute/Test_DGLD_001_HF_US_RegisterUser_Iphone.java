package testExecute;

import testExecute.iphone;
import org.testng.annotations.Test;

public class Test_DGLD_001_HF_US_RegisterUser_Iphone extends iphone {

    @Test
    public void testWithIPhone() throws InterruptedException {
        setUp("pixel 5"); // Set device emulation here

        page.navigate("https://google.com");
        Thread.sleep(8000);
        System.out.println("Running on iPhone - Title: " + page.title());

        tearDown(); // Optional if not using @AfterMethod
    }

    @Test
    public void testWithDesktop() {
        setUp("desktop");

        page.navigate("https://google.com");
        System.out.println("Running on Desktop - Title: " + page.title());

        tearDown();
    }
}
