package testExecute;

import com.microsoft.playwright.*;
import Utilities.DeviceConfig;

public class iphone {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    public void setUp(String deviceName) {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Browser.NewContextOptions options = DeviceConfig.getDeviceOptions(deviceName);
        context = browser.newContext(options);
        page = context.newPage();
    }

    public void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
