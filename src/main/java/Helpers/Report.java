package Helpers;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Report {

    public static final ThreadLocal<LogStatus> testStatus = new ThreadLocal<>();
    private static final ThreadLocal<String> testPath = new ThreadLocal<>();
    private static final ThreadLocal<ExtentReports> extentReport = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void addLog(LogStatus logStatus, String stepName, String details) {
        if (test.get() != null) {
            test.get().log(logStatus, stepName, details);
        }
    }

    public static Boolean verifyValue(String msg, Object expVal, Object actVal) {
        Boolean res = false;
        String imagePath = "";
        if (!expVal.equals(actVal)) {
             imagePath = createScreenshot();
            addLog(LogStatus.FAIL, msg, "expVal=" + expVal + "; actVal=" + actVal + "\n" + test.get().addBase64ScreenShot(imagePath));
            endTest();
            if (Config.getProperty("debug").equals("false")) {
                Assert.assertEquals(msg, expVal, actVal);
            } else {
                System.out.println(msg + "; expVal=" + expVal + "; actVal=" + actVal);
            }
        } else {
            if (Config.getProperty("images_pass").equals("true")) {
                imagePath = createScreenshot();
            }
            if (imagePath.equals("")) {
                addLog(LogStatus.PASS, msg, "expVal=" + expVal + "; actVal=" + actVal);
            } else {
                addLog(LogStatus.PASS, msg, "expVal=" + expVal + "; actVal=" + actVal + "\n" + test.get().addBase64ScreenShot(imagePath));
            }
            extentReport.get().flush();
            Assert.assertEquals(msg, expVal, actVal);
            res = true;
        }
        return res;
    }

    public static String createScreenshot() {
        String imagePath = "images/";
        UUID uuid = UUID.randomUUID();
        // generate screenshot as a file object
        File scrFile = ((TakesScreenshot) ThreadWebDriver.get()).getScreenshotAs(OutputType.FILE);
        try {
            // copy file object to designated location
            FileUtils.copyFile(scrFile, new File(testPath.get() + imagePath + uuid + ".png"));
//            if (Config.getProperty("debug").equals("false")) {
//                FileUtils.copyFile(scrFile, new File("z:/" + uuid + ".png"));
//            }
        } catch (IOException e) {
            addLog(LogStatus.WARNING, "Error while generating screenshot:", e.toString());
            System.out.println("Error while generating screenshot:\n" + e.toString());
        }
        return imagePath + uuid + ".png";
    }

    public static void startTest(String testName, String testDescription, String testCategory) {
        testStatus.set(LogStatus.FAIL);
        String[] arrUrl = Config.getProperty("url").split("/");
        String envName = "";
        if (arrUrl.length < 4) {
            envName = arrUrl[2];
        } else {
            envName = arrUrl[arrUrl.length - 1];
        }
        testPath.set("../Reporting/" + envName + "/");
        String path = testPath.get();
        String[] arrTestName = testName.replace(".", "_").split("_");
        for (int i = 0; i < arrTestName.length - 1; i++) {
            path = path + arrTestName[i] + "/";
        }
        testPath.set(path);
        String methodName = arrTestName[arrTestName.length - 1] + ".html";
        extentReport.set(new ExtentReports(testPath.get() + methodName, true, DisplayOrder.NEWEST_FIRST));
        //extentReport = new ExtentReports(testPath + testName.replace(".", "_") + ".html", false, DisplayOrder.NEWEST_FIRST);
//        if (Config.getProperty("debug").equals("false")) {
//            // start x
//            extentReport.x("192.168.10.33", 27017);
//        }
        // if using multiple projects, set project name
        // doing so will allow you to filter by project and view all its reports
        //extentReport.assignProject(envName);
        extentReport.get().addSystemInfo("Debug Mode", Config.getProperty("debug"));
        extentReport.get().addSystemInfo("Browser", Config.getProperty("browser"));
        extentReport.get().addSystemInfo("Url", Config.getProperty("url"));
        extentReport.get().addSystemInfo("Environment", envName);

        test.set(extentReport.get().startTest(testName, testDescription));
        if (testCategory != null && !testCategory.equals("")) {
            test.get().assignCategory(testCategory);
        }
        verifyValue("startTest: " + methodName, true, true);
    }

    public static void finishTest() {
        testStatus.set(Report.test.get().getRunStatus());
    }

    public static void endTest() {
        if (extentReport.get() != null) {
            extentReport.get().endTest(test.get());
            extentReport.get().flush();
        }
    }

    public static void closeTest() {
        if (extentReport.get() != null) {
            extentReport.get().close();
        }
    }

    public static void exitTest(String msg) {
        System.out.println(msg);
        String imagePath = createScreenshot();
        addLog(LogStatus.FATAL, "exitTest: See previous step", "msg=" + msg + "\n" + test.get().addBase64ScreenShot(imagePath));
        endTest();
        if (Config.getProperty("exit_test").equals("true")) {
            Assert.assertEquals("exitTest: " + msg, "true", "false");
        }
    }

    public static void takeScreenshot() {
        Browser.scrollToElement("$(\"h2:contains('Sharing')\")");
        String imagePath = createScreenshot();
        addLog(LogStatus.INFO, "takeScreenshot: See previous step", "See image\n" + test.get().addBase64ScreenShot(imagePath));
        endTest();
    }
}
