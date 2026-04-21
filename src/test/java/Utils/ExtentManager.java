package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentManager {

    private static ConcurrentHashMap<String, ExtentReports> map = new ConcurrentHashMap<>();

    public static ExtentReports getExtent(String tenant) throws IOException {

        if (!map.containsKey(tenant)) {

            String safeTenant = tenant.replaceAll("[^a-zA-Z0-9]", "_");
            new File("Output/Akku_2.0/Report/Extent").mkdirs();

            String reportPath = "Output/Akku_2.0/Report/Extent/" + safeTenant + ".html";

            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);

            // ✅ Load tenant-wide XML config
            File CONF = new File("Files/extent.xml");
            if (CONF.exists()) {
                reporter.loadXMLConfig(CONF);
            }

            ExtentReports extent = new ExtentReports();
            extent.attachReporter(reporter);

            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Tenant", tenant);

            map.put(tenant, extent);
        }

        return map.get(tenant);
    }


    public static void flushAll() {
        map.values().forEach(ExtentReports::flush);
    }
}
