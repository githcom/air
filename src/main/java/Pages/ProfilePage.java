package Pages;

import Helpers.Browser;
import Helpers.Report;
import org.openqa.selenium.Keys;

public class ProfilePage {

    public static void click2LoginAsUser() {
        String locator = "$('i.fa-sign-in.arrow-bottom-tool.show-popup.show-center.sign-in-as-partner')";
        Report.verifyValue("click2LoginAsUser: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("click2LoginAsUser: before click ", true, Browser.waitForIsVisible(locator, 10, false));
    }

    public static void back2ManagerInterface() {
        String locator = "$('a.out-partner.pull-left')";
        Report.verifyValue("back2ManagerInterface before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("back2ManagerInterface after click ", true, Browser.waitForIsVisible("$('i.fa-sign-in.arrow-bottom-tool.show-popup.show-center.sign-in-as-partner')", 10, true));
    }

    public static void setName(String name) {
        String locator = "$('#firstName')";
        Report.verifyValue("setName: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, name);
        Report.verifyValue("setName: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + name + "')", 10, true));
    }

    public static void setSurname(String surname) {
        String locator = "$('#lastName')";
        Report.verifyValue("setSurname: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, surname);
        Report.verifyValue("setSurname: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + surname + "')", 10, true));
    }

    public static void setCountry(String country) {
        String locator = "$(\"div[placeholder='Выберите страну'] span.btn-default.form-control.ui-select-toggle\")";
        Report.verifyValue("setCountry: click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
//        Report.verifyValue("setCountry: after send keys ", true, Browser.waitForIsVisible("$(\"input[class='form-control ui-select-search ng-pristine ng-valid ng-touched']\")", 10, true));
        locator = "$(\"input[placeholder='Выберите страну']\")";
        Report.verifyValue("setCountry: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, country);
        Report.verifyValue("setCountry: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + country + "')", 10, true));
        Browser.getElement(locator).sendKeys(Keys.ENTER);
    }

    public static void setCity(String city) {
        String locator = "$(\"div[placeholder='Выберите город'] span.btn-default.form-control.ui-select-toggle\")";
        Report.verifyValue("setCity: click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
//        Report.verifyValue("setCountry: after send keys ", true, Browser.waitForIsVisible("$(\"input[class='form-control ui-select-search ng-pristine ng-valid ng-touched']\")", 10, true));
        locator = "$(\"input[placeholder='Выберите город']\")";
        Report.verifyValue("setCity: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, city);
        Report.verifyValue("setCity: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + city + "')", 10, true));
        Browser.getElement(locator).sendKeys(Keys.ENTER);
    }

    public static void setPostalRequisities(String postalRequisities) {
        String locator = "$('#address')";
        Report.verifyValue("setPostalRequisities: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, postalRequisities);
        Report.verifyValue("setPostalRequisities: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + postalRequisities + "')", 10, true));
    }

    public static void addData(String data) {
        String locator = "$('div.button-add')";
        Report.verifyValue("addData: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("addData: after click ", true, Browser.waitForIsVisible("$(\"div.dropdown-toggle[aria-expanded='true']\")", 10, true));
        locator = "$(\"div.name-list.ng-binding.ng-scope:contains('" + data + "')\")";
        Report.verifyValue("addData: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("setName: after click ", true, Browser.waitForIsVisible(locator, 5, false));
    }

    public static void setPhone(String phone, int numberOfInput) {
        String locator = "$('#contact-phone-" + numberOfInput + "')";
        Report.verifyValue("setPhone: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, phone);
        Report.verifyValue("setPhone: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + phone + "')", 10, true));
    }

    public static void setMail(String mail, int numberOfInput) {
        String locator = "$('#contact-email-" + numberOfInput + "')";
        Report.verifyValue("setMail: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, mail);
        Report.verifyValue("setMail: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + mail + "')", 10, true));
    }

    public static void setDate(String date) {
        String locator = "$('#birthdate')";
        Report.verifyValue("setDate: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
//        Report.verifyValue("setCountry: after send keys ", true, Browser.waitForIsVisible("$(\"input[class='form-control ui-select-search ng-pristine ng-valid ng-touched']\")", 10, true));
//        locator = "$(\"input[placeholder='Выберите город']\")";
        Report.verifyValue("setDate: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, date);
        Report.verifyValue("setDate: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + date + "')", 10, true));
        Browser.getElement(locator).sendKeys(Keys.ENTER);
    }

    public static void setDate(String city, String errorMsg) {
        setDate(city);
        String locator = "$(\"div[ng-messages='formUserProfile.userBirthDateInput.$error'] span\")";
        Report.verifyValue("setDate: ", true, Browser.waitForIsVisible(locator, 10, true));
        Report.verifyValue("setDate: verify error msg ", errorMsg, Browser.returnRunCommand(locator + ".text()"));
    }

    public static void click2SaveChanges(String name, String surname, String country, String city,String postalRequisities, String date) {
        String locator = "$(\"button[ng-click='saveUserProfile()']\")";
        Report.verifyValue("click2SaveChanges: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Browser.refreshPage();
        Report.verifyValue("click2SaveChanges: after save ", true, Browser.waitForIsVisible("$('button.btn-success.but-page-add.pull-right.m-b-15.disabled')", 10, true));
        Report.verifyValue("click2SaveChanges setName: after save ", "1", Browser.returnRunCommand("$('#firstName').val('" + name + "').length"));
        Report.verifyValue("click2SaveChanges setSurname: after save ", "1", Browser.returnRunCommand( "$('#lastName').val('" + surname + "').length"));
        Report.verifyValue("click2SaveChanges setCountry: after save ", "1", Browser.returnRunCommand( "$(\"input[placeholder='Выберите страну']\").val('" + country + "').length"));
        Report.verifyValue("click2SaveChanges setCity: after save ", "1", Browser.returnRunCommand( "$(\"input[placeholder='Выберите город']\").val('" + city + "').length"));
        Report.verifyValue("click2SaveChanges setPostalRequisities: after save ", "1", Browser.returnRunCommand( "$('#address').val('" + postalRequisities + "').length"));
        Report.verifyValue("click2SaveChanges setDate: after save ", "1", Browser.returnRunCommand( "$('#birthdate').val('" + date + "').length"));
    }

    public static void click2SaveChanges(boolean isRussian) {
        String locator = "$(\"button[ng-click='saveUserProfile()']\")";
        Report.verifyValue("click2SaveChanges: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");

        Report.verifyValue("click2SaveChanges: after save ", true, Browser.waitForIsVisible("$('button.btn-success.but-page-add.pull-right.m-b-15.disabled')", 15, true));
//        Browser.refreshPage();
//        Report.verifyValue("click2SaveChanges: after save ", true, Browser.waitForIsVisible("$('button.btn-success.but-page-add.pull-right.m-b-15.disabled')", 10, true));
        String val = isRussian ? "Контактные данные" : "Contact Information";
        Report.verifyValue("click2SaveChanges: after save title", true, Browser.waitForIsVisible("$(\"div.title-profile span:contains('" + val + "')\")", 10, true));
        val = isRussian ? "Адрес проживания" : "Address";
        Report.verifyValue("click2SaveChanges: after save title", true, Browser.waitForIsVisible("$(\"div.residence-block span:contains('" + val + "')\")", 10, true));
    }

    public static void setLanguage(boolean isRussian) {
        String language = isRussian ? "Русский" : "English";
        String locator = "$(\"div[placeholder='Выберите язык'] span.btn-default.form-control.ui-select-toggle\")";
        Report.verifyValue("setLanguage: click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
//        Report.verifyValue("setCountry: after send keys ", true, Browser.waitForIsVisible("$(\"input[class='form-control ui-select-search ng-pristine ng-valid ng-touched']\")", 10, true));
        locator = "$(\"input[placeholder='Выберите язык']\")";
        Report.verifyValue("setLanguage: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, language);
        Browser.sleep(1500);
        Report.verifyValue("setLanguage: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + language + "')", 10, true));
        Browser.getElement(locator).sendKeys(Keys.ENTER);
    }

    public static void click2ChangePassword() {
        String locator = "$('button.btn-icons.m-b-20')";
        Report.verifyValue("click2ChangePassword: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("click2ChangePassword: before send keys ", true, Browser.waitForIsVisible("form-set-password", 10, true));
    }

    public static void setOldPassword(String psw) {
        String locator = "$('#OldPassword')";
        Report.verifyValue("setMail: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, psw);
        Report.verifyValue("setMail: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + psw + "')", 10, true));
    }

    public static void setNewPassword(String psw) {
        String locator = "$('#NewPassword')";
        Report.verifyValue("setMail: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, psw);
        Report.verifyValue("setMail: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + psw + "')", 10, true));
    }

    public static void setConfirmNewPassword(String psw) {
        String locator = "$('#ConfirmPassword')";
        Report.verifyValue("setMail: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, psw);
        Report.verifyValue("setMail: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + psw + "')", 10, true));
    }

    public static void click2ConfirmPassword() {
        String locator = "$('button.btn-primary.btn-cons.m-b-10')";
        Report.verifyValue("click2ChangePassword: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("click2ChangePassword: before send keys ", true, Browser.waitForIsVisible(locator, 10, false));
    }

}
