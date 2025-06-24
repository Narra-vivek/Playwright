package Utilities;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import io.qameta.allure.testng.AllureTestNg;
@Listeners({
    listeners.TestMethodLogger.class,
    AllureTestNg.class
})
public class PlaywrightFactory {

    private static final Logger log = LoggerFactory.getLogger(PlaywrightFactory.class);

    private static ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
    private static ThreadLocal<Browser> browserThread = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> contextThread = new ThreadLocal<>();
    private static ThreadLocal<Page> pageThread = new ThreadLocal<>();
    
    public static Page initBrowser() {
        if (pageThread.get() != null) {
            log.info("Browser already initialized for this thread.");
            return pageThread.get();
        }

        log.info("Creating Playwright instance...");
        Playwright playwright = Playwright.create();
        playwrightThread.set(playwright);

        String browserType = ConfigReader.get("browser", "chromium").toLowerCase();
        String baseUrl = ConfigReader.get("base.url", "https://www.hydroflask.com");

        log.info("Selected browser type: " + browserType);
        Browser browser;

        switch (browserType) {
            case "chrome":
                log.info("Launching Chrome browser...");
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setChannel("chrome").setHeadless(false)
                        .setArgs(List.of("--start-maximized")));
                break;
            case "firefox":
                log.info("Launching Firefox browser...");
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(List.of("--start-maximized")));
                break;
            case "webkit":
                log.info("Launching WebKit browser...");
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(List.of("--start-maximized")));
                break;
            default:
                log.warn("Unknown browser type provided. Defaulting to Chromium.");
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(List.of("--start-maximized")));
                break;
        }

        browserThread.set(browser);

        try {
            Files.createDirectories(Paths.get("videos"));
            Files.createDirectories(Paths.get("traces"));
        } catch (IOException e) {
            log.error("Error creating output directories", e);
        }

        log.info("Creating new browser context with full viewport...");
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(null)
                .setRecordVideoDir(Paths.get("videos"))
                .setRecordVideoSize(null));
        contextThread.set(context);

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        Page page = context.newPage();
        pageThread.set(page);

        log.info("Navigating to base URL: " + baseUrl);
        page.navigate(baseUrl);

        log.info("Browser setup complete.");
        return page;
    }

    public Page getPage() {
        log.info("Fetching current Page instance from thread-local.");
        return pageThread.get();
    }

    public BrowserContext getContext() {
        return contextThread.get();
    }

    public Video getVideo() {
        if (pageThread.get() != null) {
            return pageThread.get().video();
        }
        return null;
    }

    public void VideoRecord(Method method) throws IOException, InterruptedException {
        String testName = method.getName();
        Video video = getVideo();
        Thread.sleep(1000);

        if (video != null) {
            Path originalVideoPath = video.path();
            Path renamedPath = Path.of("videos", testName + ".webm");

            int maxWaitTimeSeconds = 10;
            int pollIntervalMillis = 500;
            long startTime = System.currentTimeMillis();
            boolean fileReady = false;

            while (System.currentTimeMillis() - startTime < maxWaitTimeSeconds * 1000) {
                try (InputStream is = Files.newInputStream(originalVideoPath)) {
                    fileReady = true;
                    break;
                } catch (IOException e) {
                    Thread.sleep(pollIntervalMillis);
                }
            }

            if (fileReady) {
                Files.move(originalVideoPath, renamedPath, StandardCopyOption.REPLACE_EXISTING);
                try (InputStream is = Files.newInputStream(renamedPath)) {
                    Allure.addAttachment("Video - " + testName, "video/webm", is, ".webm");
                }
            } else {
                log.error("Timed out waiting for video file to become available: " + originalVideoPath);
            }

        } else {
            log.warn("Video is null. Ensure video recording is enabled.");
        }
    }

    public void recordTrace(Method method) throws IOException {
        String testName = method.getName();
        Path tracePath = Paths.get("traces", testName + ".zip");

        try {
            contextThread.get().tracing().stop(new Tracing.StopOptions().setPath(tracePath));
            if (Files.exists(tracePath)) {
                try (InputStream is = Files.newInputStream(tracePath)) {
                    Allure.addAttachment("Trace - " + testName, "application/zip", is, ".zip");
                }
            } else {
                log.warn("Trace file not found: " + tracePath.toString());
            }
        } catch (Exception e) {
            log.error("Failed to stop tracing or attach trace: " + e.getMessage());
        }
    }

    public void tearDown() {
        log.info("Tearing down Playwright and browser resources...");
        try {
            if (pageThread.get() != null) {
                log.info("Closing Page...");
                pageThread.get().close();
            }
            if (contextThread.get() != null) {
                log.info("Closing BrowserContext...");
                contextThread.get().close();
            }
            if (browserThread.get() != null) {
                log.info("Closing Browser...");
                browserThread.get().close();
            }
            if (playwrightThread.get() != null) {
                log.info("Closing Playwright...");
                playwrightThread.get().close();
            }
            log.info("Resources closed successfully.");
        } catch (Exception e) {
            log.error("Error during teardown: " + e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("Clearing thread-local variables.");
            pageThread.remove();
            contextThread.remove();
            browserThread.remove();
            playwrightThread.remove();
        }
    }
}
