package Helpers;

import com.relevantcodes.extentreports.LogStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class Browser {

    private static WebDriver Driver;

    public static void create(String browserName) {
        DesiredCapabilities capabilities = null;
        switch (browserName.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("chrome.switches", "--disable-extensions-file-access-check "
                        + "--disable-extensions-http-throttling --disable-infobars --enable-automation");
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("password_manager_enabled", false);
//                prefs.put(LogType.BROWSER, Level.ALL);
                options.setExperimentalOption("prefs", prefs);
                Driver = new ChromeDriver(options);
                break;
            case "chrome1":
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//chromedriver.exe");
                capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                Driver = new ChromeDriver(capabilities);
                break;
            case "chrome2":
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//chromedriver.exe");
                Driver = new ChromeDriver();
                break;
            case "firefox":
                Driver = new FirefoxDriver();
                break;
            case "firefox1":
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//geckodriver.exe");
                Driver = new FirefoxDriver();
                break;
            case "firefox2":
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//geckodriver.exe");
                capabilities = DesiredCapabilities.firefox();
                //capabilities.setCapability("marionette", true);
                // Settings to Accept the SSL Certificate in the Capability object
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                Driver = new FirefoxDriver(capabilities);
                break;
            case "ie":
                //System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "//IEDriverServer_x64.exe");
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "//IEDriverServer_win32.exe");

                // create the DesiredCapability object of InternetExplorer
                capabilities = DesiredCapabilities.internetExplorer();
                // Settings to Accept the SSL Certificate in the Capability object
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

                // Force Windows to launch IE through create Process API and in "private" browsing mode
                //capabilities.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
                capabilities.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
                // If testing serial instances of IE, add IE_ENSURE_CLEAN_SESSION
                capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                capabilities.setCapability(CapabilityType.VERSION, "10");

                //Driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
                //Driver = new InternetExplorerDriver(capabilities);
                break;
            case "ie32":
                close();
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "//IEDriverServer_win32.exe");
                Driver = new InternetExplorerDriver();
                sleep(10000);
                break;
            case "ie64":
                close();
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "//IEDriverServer_x64.exe");
                Driver = new InternetExplorerDriver();
                sleep(10000);
                break;
            case "ie_edge":
                System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "//MicrosoftWebDriver.exe");
                capabilities = DesiredCapabilities.edge();
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                Driver = new EdgeDriver(capabilities);
                break;
            default:
                break;
        }

        ThreadWebDriver.set(Driver);
        setPageLoadTimeout(Long.parseLong(Config.getProperty("page_load")));
        setImplicitlyWaitDefault();
        ThreadWebDriver.get().manage().deleteAllCookies();
        setSize();
    }

    public static void setSize() {
        if (!Config.isResponsive() || Config.getProperty("width") == null || Config.getProperty("width").equals("")
                || Config.getProperty("height") == null || Config.getProperty("height").equals("")) {
            ThreadWebDriver.get().manage().window().maximize();
        } else {
            setSize(Config.getProperty("width"), Config.getProperty("height"));
        }
    }

    // Function to capture JSError log.
    public static Boolean verifyJSErrosLog() {
        String actVal = "";
        // Capture all JSerrors
        LogEntries jserrors = ThreadWebDriver.get().manage().logs().get(LogType.BROWSER);
        for (LogEntry error : jserrors.filter(Level.SEVERE)) {
            String errMsg = error.getMessage();
            if (!errMsg.contains("chrome-extension://") && (!errMsg.contains("https://svc.webspellchecker.net/"))) {
                actVal = actVal + errMsg;
            }
        }
        return Report.verifyValue("verifyJSErrosLog:", "", actVal);
    }

    public static void scrollToElement(String element) {
        Browser.runCommand("$(window).scrollTop(" + element + ".offset().top)");
    }

    private static void setSize(String sWidth, String sHeight) {
        int width = Integer.parseInt(sWidth);
        int height = Integer.parseInt(sHeight);
        ThreadWebDriver.get().manage().window().setSize(new Dimension(width, height));
    }

    public static void setPageLoadTimeout(long sec) {
        ThreadWebDriver.get().manage().timeouts().pageLoadTimeout(sec, TimeUnit.SECONDS);
    }

    public static void close() {
        try {
            ThreadWebDriver.get().manage().deleteAllCookies();
            ThreadWebDriver.get().close();
            ThreadWebDriver.get().quit();
            ThreadWebDriver.remove();
        } catch (Exception e) {
        }
    }

    public static void setImplicitlyWaitDefault() {
        setImplicitlyWait(10);
    }

    public static void setImplicitlyWait(long sec) {
        ThreadWebDriver.get().manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
    }

    public static void goTo(String url) {
        Report.addLog(LogStatus.INFO, "Go to url=" + url, "-");
        ThreadWebDriver.get().navigate().to(url);
        if (Config.getProperty("browser").contains("ie")) {
            if (isExist("overridelink")) {
                runCommand("document.getElementById('overridelink').click();"); //IE
            } else if (isExist("continueLink")) {
                runCommand("document.getElementById('continueLink').click();"); //Edge
            }
        }
        setImplicitlyWait(1);
//        if (!isExist("user-login")) {
//            General.VerifyErrorMsg("");
//        }
        setImplicitlyWaitDefault();
    }

    public static void closeAlert() {
        try {
            // Switching to Alert
            Alert alert = ThreadWebDriver.get().switchTo().alert();
            // Capturing alert message.
            String alertMessage = alert.getText();
            // Accepting alert
            alert.accept();
            Report.addLog(LogStatus.WARNING, "closeAlert:", alertMessage);
            //Report.verifyValue("closeAlert:", "X", alertMessage);
        } catch (Exception e) {
        }
    }

    public static void back() {
        ThreadWebDriver.get().navigate().back();
    }

    public static void switchToFrame(String locator) {
        ThreadWebDriver.get().switchTo().frame(Browser.getElement(locator));
    }

    public static void backToDefaultContent() {
        ThreadWebDriver.get().switchTo().defaultContent();
    }

    public static boolean isUrlContains(String text) {
        return ThreadWebDriver.get().getCurrentUrl().contains(text);
    }

    public static void runCommand(String command) {
        try {
            ((JavascriptExecutor) ThreadWebDriver.get()).executeScript(command);
            if (Config.getProperty("browser").contains("ie")) {
                sleep(1000);
            }
        } catch (Exception e) {
            Report.exitTest("runCommand: command=" + command + "; exception=" + e.getMessage());
        }
    }

    public static String returnRunCommand(String command) {
        String res = "";
        try {
            res = ((Object) ((JavascriptExecutor) ThreadWebDriver.get()).executeScript("return " + command)).toString();
        } catch (Exception e) {
            Report.exitTest("returnRunCommand: command=" + command + "; exception=" + e.getMessage());
        }
        return res;
    }

    public static WebElement getElementByCssSelector(String locator) {
        return ThreadWebDriver.get().findElement(By.cssSelector(locator));
    }

    public static WebElement getElement(String locator) {
        WebElement oElem = null;
        try {
            if (locator.contains("//")) {
                oElem = ThreadWebDriver.get().findElement(By.xpath(locator));
            } else if (locator.contains("$(")) {
                oElem = (WebElement) ((JavascriptExecutor) ThreadWebDriver.get()).executeScript("return " + locator + ".get(0);");
            } else {
                oElem = ThreadWebDriver.get().findElement(By.id(locator));
            }
        } catch (Exception e) {
        }
        return oElem;
    }

    public static WebElement getElementByPositionFromListOfElements(String locator, int position) {
        List<WebElement> list = ThreadWebDriver.get().findElements(By.xpath(locator));
        return list.get(position);
    }

    public static void sendKeys(String locator, String key) {
        WebElement oElem = getElement(locator);
        if (oElem == null) {
            Report.exitTest("sendKeys: locator=" + locator + "; Element not found");
        } else {
            try {
                oElem.click();
                oElem.clear();
            } catch (Exception e) {
                oElem.clear();
            }
            oElem.sendKeys(key);
        }
    }

    public static void click(String locator) {
        if (waitForIsVisible(locator, 8, true)) {
            WebElement oElem = getElement(locator);
            if (oElem != null && oElem.isDisplayed()) {
                oElem.click();
            }
        } else {
            Report.verifyValue("click: locator=" + locator + " not visible", true, false);
        }
    }

    public static boolean isVisible(String locator) {
        WebElement oElem = getElement(locator);
        if (oElem == null) {
            return false;
        } else {
            return oElem.isDisplayed();
        }
    }

    public static boolean waitForIsVisible(String locator, int iSec, Boolean expVal) {
        int count = 1;
        Boolean res = true;
        setImplicitlyWait(1);
        do {
            Boolean isVisible = false;
            try {
                isVisible = isVisible(locator);
            } catch (Exception e) {
            }
            res = isVisible.equals(expVal);
            if (!res) {
                sleep(1000);
            }
            count++;
        } while (!res && count < iSec);
        setImplicitlyWaitDefault();
        return res;
    }

    public static boolean waitForIsClickable(WebElement element, int iSec, Boolean expVal) {
        int count = 1;
        Boolean res = true;
        setImplicitlyWait(0);
        do {
            Boolean isClickable = false;
            try {
                //new WebDriverWait(ThreadWebDriver.get(), iSec).until(elementToBeClickable(element));
                //isClickable = true;
                isClickable = element.isEnabled();
            } catch (Exception e) {
            }
            res = isClickable.equals(expVal);
            if (!res) {
                sleep(1000);
            }
            count++;
        } while (!res && count < iSec);
        setImplicitlyWaitDefault();
        return res;
    }

    public static boolean isExist(String locator) {
        WebElement oElem = getElement(locator);
        if (oElem == null) {
            return false;
        } else {
            return true;
        }
    }

    public static String getText(String locator) {
        String res = "";
        try {
            res = getElement(locator).getText();
        } catch (Exception e) {
            Report.exitTest("getText: " + e.getMessage());
        }
        return res;
    }

    public static void mouseOver(String locator) {
        Actions oActions = new Actions(ThreadWebDriver.get());
        oActions.moveToElement(getElement(locator)).build().perform();
    }

    public static void setCheckBox(String locator, Boolean value) {
        String actVal;
        WebElement oElem = getElement(locator);
        actVal = oElem.getAttribute("checked");
        if ((value && actVal == null) || (Boolean.toString(true).equals(actVal) && !value)) {
            oElem.click();
        }
    }

    public static void setCheckBoxByJS(String locator, Boolean value) {
        String actVal;
        WebElement oElem = getElement(locator);
        actVal = oElem.getAttribute("checked");
        if ((value && actVal == null) || (Boolean.toString(true).equals(actVal) && !value)) {
            runCommand("$('#" + locator + "')[0].click();");
        }
    }

    public static String getAttribute(String locator, String attribute) {
        return getElement(locator).getAttribute(attribute);
    }

    public static void selectByValue(String locator, String value) {
        Select oSelect = new Select(getElement(locator));
        oSelect.selectByValue(value);
    }

    public static void selectByVisibleText(String locator, String value) {
        Select oSelect = new Select(getElement(locator));
        oSelect.selectByVisibleText(value);
    }

    public static void selectByIndex(String locator, int index) {
        Select oSelect = new Select(getElement(locator));
        oSelect.selectByIndex(index);
    }

    public static String getPageSource() {
        return ThreadWebDriver.get().getPageSource();
    }

    public static String getCurrentUrl() {
        return ThreadWebDriver.get().getCurrentUrl();
    }

    public static String getCurrentWindow() {
        return ThreadWebDriver.get().getWindowHandle();
    }

    public static void closeAllWindowsExceptMain(String currentWindowHandle) {
        ArrayList<String> windowHandles = new ArrayList<String>(ThreadWebDriver.get().getWindowHandles());
        boolean isClose = false;
        for (String window : windowHandles) {
            //if it contains the current window we want to eliminate that from switchTo();
            if (!window.equals(currentWindowHandle)) {
                //Now switchTo new Tab.
                ThreadWebDriver.get().switchTo().window(window);
                try {
                    //close the newly opened tab
                    ThreadWebDriver.get().close();
                    isClose = true;
                } catch (Exception e) {
                    System.out.println("closeAllWindowsExceptMain:" + e.getMessage());
                }
            }
        }
        ThreadWebDriver.get().switchTo().window(currentWindowHandle);
        if (isClose) {
            refreshPage();
        }
    }

    public static void deleteCookies() {
        ThreadWebDriver.get().manage().deleteAllCookies();
    }

    public static void refreshPage() {
        ThreadWebDriver.get().navigate().refresh();
    }

    public static int getXPathCount(String locator) {
        List<WebElement> list = ThreadWebDriver.get().findElements(By.xpath(locator));
        int res = list.size();
        return res;
    }

    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);     //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
