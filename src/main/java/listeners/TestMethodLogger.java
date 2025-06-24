package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestMethodLogger implements ITestListener {
    private static final Logger log = LogManager.getLogger(TestMethodLogger.class);

    private String getTestMethodName(ITestResult result) {
        return result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("\n\u001B[1;34m🔹 Launched browser for: " + getTestMethodName(result) + "\u001B[0m");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        int retryCount = RetryAnalyzer.getRetryCount(result);

        if (retryCount > 0) {
            log.info("\u001B[32m✅ Test Passed After Retry (" + retryCount + " attempt(s)): " + getTestMethodName(result) + "\u001B[0m");
        } else {
            log.info("\u001B[32m✅ Test Passed: " + getTestMethodName(result) + "\u001B[0m");
        }

        log.info("\u001B[36m🔻 Closing browser for: " + getTestMethodName(result) + "\u001B[0m\n");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        int retryCount = RetryAnalyzer.getRetryCount(result);
        String methodName = getTestMethodName(result);

        if (retryCount < RetryAnalyzer.MAX_RETRY) {
            log.warn("\u001B[33m⚠️ Test failed but will retry: " + methodName + " | Attempt: " + (retryCount + 1) + "\u001B[0m");
        } else {
            log.error("\u001B[31m❌ Test Failed after retries: " + methodName + "\u001B[0m");
        }

        log.info("\u001B[36m🔻 Closing browser for: " + methodName + "\u001B[0m\n");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("\u001B[33m⚠️ Test Skipped: " + getTestMethodName(result) + "\u001B[0m");
        log.info("\u001B[36m🔻 Closing browser for: " + getTestMethodName(result) + "\u001B[0m\n");
    }

    @Override
    public void onFinish(ITestContext context) {
        RetryAnalyzer.clear(); // Clear retry count between test runs
    }

    @Override public void onStart(ITestContext context) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
}
