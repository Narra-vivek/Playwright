package listeners;

import org.testng.*;

import java.util.*;

public class SuiteSummaryListener implements ISuiteListener, ITestListener {

    private static String suiteName = "";

    // Track final outcome only once per test method
    private final Set<String> passedTestIds = new HashSet<>();
    private final Set<String> failedTestIds = new HashSet<>();
    private final Set<String> skippedTestIds = new HashSet<>();

    private String getTestUniqueId(ITestResult result) {
        return result.getTestClass().getName() + "#" + result.getMethod().getMethodName();
    }

    @Override
    public void onStart(ISuite suite) {
        suiteName = suite.getName();
        System.out.println("\n🚀🚀🚀 Launching Test Suite 🚀🚀🚀");
        System.out.println("📦 Suite Name      : " + suiteName);
        System.out.println("⏳ Status          : Running...");
        System.out.println("🔥 Let the testing begin! 🔥\n");
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println();
        System.out.println("✅✅✅ \u001B[1mTest Suite Execution Completed\u001B[0m ✅✅✅");
        System.out.println("📦 \u001B[1mSuite Name       :\u001B[0m " + suiteName);
        System.out.println("============================================================");

        suite.getResults().forEach((testName, result) -> {
            ITestContext context = result.getTestContext();

            int passed = context.getPassedTests().size();
            int failed = context.getFailedTests().size();
            int skipped = context.getSkippedTests().size();

            System.out.println("📊 \u001B[1mTest Name        :\u001B[0m " + testName);
            System.out.println("    \u001B[32m✅ Passed        : " + passed + "\u001B[0m");
            System.out.println("    \u001B[31m❌ Failed        : " + failed + "\u001B[0m");
            System.out.println("    \u001B[33m⚠️  Skipped       : " + skipped + "\u001B[0m");
            System.out.println("============================================================");
        });

        int totalPassed = passedTestIds.size();
        int totalFailed = failedTestIds.size();
        int totalSkipped = skippedTestIds.size();

        System.out.println("📋 \u001B[1mStatus           :\u001B[0m Completed\n");

        System.out.println("\u001B[1;36m\n🧾==================== FINAL SUITE SUMMARY ====================🧾\u001B[0m");
        System.out.println("\u001B[32m✅ PASSED  : " + totalPassed + "\u001B[0m");
        System.out.println("\u001B[31m❌ FAILED  : " + totalFailed + "\u001B[0m");
        System.out.println("\u001B[33m⚠️ SKIPPED : " + totalSkipped + "\u001B[0m");
        System.out.println("🧪 TOTAL   : " + (totalPassed + totalFailed + totalSkipped));
        System.out.println("📘 SUITE   : " + suiteName);
        System.out.println("🧾============================================================\n");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String id = getTestUniqueId(result);
        passedTestIds.add(id);
        failedTestIds.remove(id);
        skippedTestIds.remove(id);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String id = getTestUniqueId(result);
        failedTestIds.add(id);
        passedTestIds.remove(id);
        skippedTestIds.remove(id);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String id = getTestUniqueId(result);
        skippedTestIds.add(id);
        passedTestIds.remove(id);
        failedTestIds.remove(id);
    }

    // No-op overrides
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestFailedWithTimeout(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
