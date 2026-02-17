package utillities;
import com.aventstack.extentreports.ExtentTest;

public class TestListener {

    public static void addScreenshot(ExtentTest test, String screenshotPath, String message) {
        try {
            test.addScreenCaptureFromPath(screenshotPath, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addBase64Screenshot(ExtentTest test, String base64Screenshot, String message) {
        try {
            test.addScreenCaptureFromBase64String(base64Screenshot, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
