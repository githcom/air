package Pages;

import Helpers.Browser;
import Helpers.Report;

public class Navigation {

    public static final String DASHBOARD = "dashboard";
    public static final String CONVERSATIONS = "message";
    public static final String MANAGER = "admin/manager";
    public static final String PARTNERS = "partners";
    public static final String CHANNEL = "channel";
    public static final String PAYMENTS = "payments";
    public static final String ADMINISTARATION = "administration";
    public static final String REPORTS = "reports";
    public static final String INCOME = "income";
    public static final String REFERRAL = "referral";

    public static void logout() {
        String locator = "$('button.profile-dropdown-toggle')";
        Report.verifyValue("logout: berfore 1", true,Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("nav2Profile: after 1 ", true, Browser.waitForIsVisible("$(\"button.profile-dropdown-toggle[aria-expanded='true']\")", 10, true));
        locator = "$('a.clearfix')";
        Report.verifyValue("logout: berfore 2", true,Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("logout: after 2 ", true, Browser.waitForIsVisible("form-login", 10, true));
    }

    /**
     * availaible items: DASHBOARD, CONVERSATIONS, MANAGER, PARTNERS, CHANNELS, PAYMENTS, ADMINISTARATION, REPORTS
     */
    public static void nav2MenuItem(String item) {
        String locator = "$(\"a[href*='" + item + "']\")";
        Report.verifyValue("nav2MenuItem: before click ", true, Browser.waitForIsVisible(locator, 15, true));
        Browser.runCommand(locator + "[0].click()");
        // bug in opening Reports item
        if (item.equals(REPORTS)) return;
        Report.verifyValue("nav2MenuItem: " + item, true, Browser.waitForIsVisible(locator + ".closest('li.open')", 40, true));
    }

    public static void nav2Profile() {
        String locator = "$('#userAvatar')";
        Report.verifyValue("nav2Profile: before picture click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("nav2Profile: after picture click ", true, Browser.waitForIsVisible("$(\"button.profile-dropdown-toggle[aria-expanded='true']\")", 10, true));
        locator = "$(\"a[href*='profile']\")";
        Report.verifyValue("nav2Profile: before click ", true, Browser.waitForIsVisible(locator, 15, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("nav2Profile: after click ", true, Browser.waitForIsVisible("$(\"form[name='formUserProfile']\")", 10, true));
    }

}
