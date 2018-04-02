package Init;

import Helpers.Browser;
import Helpers.Config;
import Helpers.Report;
import com.relevantcodes.extentreports.LogStatus;
import org.junit.*;

public class InitClass {

    @BeforeClass
    public static void setUp() {
        Browser.create(Config.getProperty("browser"));
        Browser.goTo(Config.getProperty("url"));
    }

    @After
    public void tearDown() {
        LogStatus status = Report.testStatus.get();
        if (LogStatus.PASS == status) {
            Report.verifyValue("FinishTest: status=" + status, LogStatus.PASS, status);
        } else {
            Report.addLog(status, "FinishTest: status=" + status, "expVal=pass; actVal=" + status);
        }
        Report.endTest();
        Report.closeTest();
        if (status != null) {
            Assert.assertEquals("Test", LogStatus.PASS, status);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        Browser.close();
    }

}
