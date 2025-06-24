package Testcomponents;
 
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
 
import java.io.ByteArrayInputStream;
 
public class Allure_Screenshot {
 
	    public static void captureScreenshot(Page page, String name) {
	        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
	        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
	    }
}