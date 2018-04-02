package Pages;

import Helpers.Browser;
import Helpers.Report;
import org.openqa.selenium.Keys;

public class PartnersPage {

    public static void findPartner(String partnerID) {
        Browser.runCommand("$('span.ui-select-match-text.pull-left')[0].click()");
        String locator = "managers-partner-list-input-text";
        Report.verifyValue("findPartner: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, partnerID);
        Browser.sleep(2000);
        Browser.getElement(locator).sendKeys(Keys.ENTER);
//        Report.verifyValue("findPartner: after send keys ", true, Browser.waitForIsVisible("$(\"table#table-partners div.position-desc:contains('" + partnerID + "')\")", 15, true));
        Report.verifyValue("findPartner: after send keys ", true, Browser.waitForIsVisible("$(\"td.sorting_1[data-dt-row='0'][data-dt-column='1'] div.position-desc:contains('" + partnerID + "')\")", 15, true));
    }

    public static void choosePartnerByName(String name) {
        String locaror = "$(\"td.sorting_1[data-dt-row='0'][data-dt-column='1'] a.title-video:contains('" + name + "')\")";
        Report.verifyValue("choosePartnerByName: " + name, true, Browser.waitForIsVisible(locaror, 10, true));
        Browser.runCommand(locaror + "[0].click()");
        Report.verifyValue("choosePartnerByName: " + name, true, Browser.waitForIsVisible("$(\"form[name='formUserProfile']\")", 10, false));
    }
}
