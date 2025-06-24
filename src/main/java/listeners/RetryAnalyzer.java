package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.concurrent.ConcurrentHashMap;

public class RetryAnalyzer implements IRetryAnalyzer {

    public static final int MAX_RETRY = 1;
    private static final ConcurrentHashMap<String, Integer> retryCounts = new ConcurrentHashMap<>();

    @Override
    public boolean retry(ITestResult result) {
        String key = result.getMethod().getQualifiedName();
        int currentCount = retryCounts.getOrDefault(key, 0);

        if (currentCount < MAX_RETRY) {
            retryCounts.put(key, currentCount + 1);
            System.out.println("🔁 Retrying test: " + key + " | Attempt: " + (currentCount + 1));
            return true;
        }

        System.out.println("❌ Max retry attempts reached for: " + key);
        return false;
    }

    // For external access
    public static int getRetryCount(ITestResult result) {
        return retryCounts.getOrDefault(result.getMethod().getQualifiedName(), 0);
    }

    public static void clear() {
        retryCounts.clear();
    }
}
