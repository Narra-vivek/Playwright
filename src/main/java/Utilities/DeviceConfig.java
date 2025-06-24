package Utilities;

import com.microsoft.playwright.Browser;

public class DeviceConfig {
    public static Browser.NewContextOptions getDeviceOptions(String deviceName) {
        Browser.NewContextOptions options = new Browser.NewContextOptions();

        switch (deviceName.toLowerCase()) {
            case "iphone 12":
                return options
                    .setViewportSize(390, 844)
                    .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/604.1")
                    .setDeviceScaleFactor(3)
                    .setIsMobile(true)
                    .setHasTouch(true);

            case "pixel 5":
                return options
                    .setViewportSize(393, 851)
                    .setUserAgent("Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Mobile Safari/537.36")
                    .setDeviceScaleFactor(2.75)
                    .setIsMobile(true)
                    .setHasTouch(true);

            default:
                return options; // desktop fallback
        }
    }
}
