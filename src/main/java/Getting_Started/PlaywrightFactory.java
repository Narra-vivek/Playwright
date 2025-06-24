package Getting_Started;

import java.nio.file.Paths;
import java.util.Base64;

import com.microsoft.playwright.Page;

public class PlaywrightFactory {
	private static ThreadLocal<Page> tlPage = new ThreadLocal<>();
	public static Page getPage() {
		return tlPage.get();
	}
	
	public static String takeScreenshot() {
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		//getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		
		byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		String base64Path = Base64.getEncoder().encodeToString(buffer);
		
		return base64Path;
	}
}
