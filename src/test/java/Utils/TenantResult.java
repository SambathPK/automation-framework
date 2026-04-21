package Utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TenantResult {

    public static Map<String, Boolean> results = new LinkedHashMap<>();
    public static Map<String, Long> executionTimes = new LinkedHashMap<>();

    public static long suiteStartTime;
    public static long suiteEndTime;

    public static void startSuite() {
        suiteStartTime = System.currentTimeMillis();
    }

    public static void endSuite() {
        suiteEndTime = System.currentTimeMillis();
    }

    // ✅ ACCUMULATE execution time per tenant
    public static synchronized void addExecutionTime(String tenant, long duration) {
        executionTimes.merge(tenant, duration, Long::sum);
    }
}
