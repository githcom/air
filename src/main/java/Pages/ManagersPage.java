package Pages;

import Helpers.Browser;
import Helpers.Report;

public class ManagersPage {


    public void addManager() {
        Browser.runCommand("$('button.btn-new.btn-default.pull-right')[0].click()");
        Report.verifyValue("addManager: ", true, Browser.waitForIsVisible("modal-manager-add", 10, true));
    }

}
