package utillities;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
public class ExtentManager {
    private static Logger logger = LogManager.getLogger(ExtentManager.class);
    private static ExtentReports extentReports;
    private static ExtentSparkReporter sparkReporter;
    public static ExtentReports initializeExtentReport() {
        try {
            String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
            String reportPath = System.getProperty("user.dir") + File.separator +
                    "test-output" + File.separator + "ExtentReport_" + timestamp + ".html";
            new File(reportPath).getParentFile().mkdirs();
            sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);
            sparkReporter.config().setDocumentTitle("SauceDemo Login Test Report");
            sparkReporter.config().setReportName("Automation Test Report");
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Application Name", "SauceDemo");
            extentReports.setSystemInfo("Test Environment", "Production");
            extentReports.setSystemInfo("Browser", "Chrome");
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
            logger.info("Extent Report initialized");
        } catch (Exception e) {
            logger.error("Error initializing Extent Report: " + e.getMessage());
            throw new RuntimeException("Failed to initialize Extent Report", e);
        }
        return extentReports; }
    public static ExtentReports getExtentReports() {
        if (extentReports == null) {
            return initializeExtentReport();
        }
        return extentReports;
    }
    public static ExtentTest createTest(String testName, String testDescription) {
        logger.info("Creating test: " + testName);
        return getExtentReports().createTest(testName, testDescription);
    }
    public static void flushExtentReport() {
        if (extentReports != null) {
            extentReports.flush();
            logger.info("Extent Report finalized and flushed");
        }
    }
}
