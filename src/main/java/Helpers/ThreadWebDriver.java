package Helpers;

import org.openqa.selenium.WebDriver;

public class ThreadWebDriver {

    private static final ThreadLocal<WebDriver> threadWebDriver = new ThreadLocal<>();

    private ThreadWebDriver() {
    }

    public static WebDriver get() {
        return threadWebDriver.get();
    }

    public static void set(WebDriver driver) {
        threadWebDriver.set(driver);
    }

    public static void remove() {
        threadWebDriver.remove();
    }

}
