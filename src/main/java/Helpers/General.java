package Helpers;

import java.util.List;
import java.util.Random;
import org.openqa.selenium.WebElement;

public class General {

    public static final String ADMIN_NAME = "admin";
    public static final String ADMIN_PSW = "a8@973E8!be";
    public static final String USER_1 = "user1";
    public static final String USER_PSW = "user1";
    public static final String MODERATOR_NAME = "moderator";
    public static final String MODERATOR_PSW = "moderator";
    public static final String MSG_ERROR_ID = "//div[@class='messages error']";
    public static final String MSG_STATUS_ID = "//div[@class='messages status']";
    public static final String TITLE_ID = "edit-title";
    public static final String SUBMIT_ID = "//div[@class='qm_buttons_wrapper']//input[@id='edit-submit']";
    public static final String SUBMIT_JS = "$('span.qm_button_on_right_side input#edit-submit')";
    public static final String NEXT_ID = "//div[@class='qm_buttons_wrapper']//input[@id='edit-qm-node-wizard-next']";
    public static final String NEXT_ID_JS = "$('span.qm-wizard-next-btn input#edit-qm-node-wizard-next')";
    public static final String FINISH_ID = "edit-qm-node-wizard-finish";
    public static final String FINISH_ID_JS = "$('span.qm_button_on_right_side input#edit-qm-node-wizard-finish')";
    public static final String FINISH_ID_JS_RESP = "$('div.form-buttons-positive button#edit-qm-node-wizard-finish.btn-primary')";
    public static final String JOHN_WAYNE = "John Wayne";
    public static final String CAMPAIGN_MANAGER_1 = "campaign_manager_1";
    public static final String CAMPAIGN_MANAGER_2 = "campaign_manager_2";
    public static final String USER_2 = "user2";
    public static final String USER_3 = "user3";
    public static final String USER_4 = "user4";
    public static final String USER_5 = "user5";
    public static final String DANIEL_MACK = "Daniel Mack";
    public static final String RAHEL_GRAVES = "Rahel Graves";
    public static final String ANDRE_SCOTT = "Andre Scott";
    public static final String ALINA_MUSIKA = "Alina Musika";
    public static final String JONATHAN_WARRILOW = "Jonathan Warrilow";
    public static final String CLOSE = "$('span.qm_button_submit input#edit-cancel')";

    // error message
    public static final String EMPTY_TITLE_MSG = "Title field is required.";
    public static final String EMPTY_DESCRIPTION_MSG = "Description field is required.";
    public static final String EMPTY_IDEA_CATEGORY_MSG = "The field Idea Category is required";
    public static final String[] arrErrorMessagesForCreateIdea = new String[]{EMPTY_TITLE_MSG, EMPTY_DESCRIPTION_MSG, EMPTY_IDEA_CATEGORY_MSG};

    public static final String INPUT_SEARCH_FIELD_RESP = "$('input.select2-search__field:visible')";
    public static final String EN_LANG = "en-edg";
    public static final String DE_LANG = "de";
    public static final String IT_LANG = "it";
    public static final String FR_LANG = "fr";




    public static String getUserName() {
        Report.verifyValue("getUserName: ", true, Browser.waitForIsVisible("$('span.text-master')", 10, true));
        return Browser.returnRunCommand("$('span.text-master').text().trim()");
    }















    public static String getDivClass() {
        String divClass = "div.web-only";
        if (Config.isMobile()) {
            divClass = "div.mobile-only";
        }
        return divClass;
    }

    public static void Click2Cancel() {
        String cmd = Config.isResponsive() ? "$(\"div.qm_wizard_header_right a[onclick*='#edit-cancel']:visible\")" : "$('div.qm_buttons_wrapper #edit-cancel')";
        ClickByJS(cmd);
    }

    public static void click2SubmitByJS() {
        General.ClickByJS(SUBMIT_JS);
    }

    public static void Click2Back() {
        ClickByJS("$('#qm-node-wizard-build-form #edit-qm-node-wizard-back')");
    }

    public static void CloseNotifyMsg() {
        Browser.runCommand("$('div.ui-pnotify-closer')[0].click()");
        Report.verifyValue("CloseNotifyMsg:", true, Browser.waitForIsVisible("$('div.ui-pnotify-closer')", 3, false));
    }

    public static Boolean VerifySuccessMsg(String... expMsg) {
        String actVal = Browser.returnRunCommand("$('div.alert-success:visible').length + $(\"div[class='messages status']:visible\").length");
        String actMsg = GetSuccessMsg();
        if (expMsg.length == 0) {
            return Report.verifyValue("VerifySuccessMsg: exists", "1", actVal);
        } else {
            return Report.verifyValue("VerifySuccessMsg:", expMsg[0], actMsg);
        }
    }

    public static String GetSuccessMsg() {
        String actMsg;
        if (!Config.isResponsive()) {
            actMsg = Browser.returnRunCommand("$(\"div[class='messages status']:visible\").text().trim()");
        } else {
            actMsg = Browser.returnRunCommand("$('div.alert-success:visible div.ui-pnotify-text').text().trim()");
        }
        return actMsg;
    }

    public static Boolean VerifyErrorMsg(String expMsg) {
        Wait2PageLoaded();
        Browser.closeAlert();
        String actMsg = "";
        String actVal = Browser.returnRunCommand("$(\"div[class='messages error']:visible\").length + $('div.alert-danger:visible').length");
        if (!actVal.equals("0")) {
            if (!Browser.returnRunCommand("$(\"div[class='messages error']:visible\").length").equals("0")) {
                actMsg = Browser.returnRunCommand("$(\"div[class='messages error']:visible\").text().trim()");
            } else {
                actMsg = Browser.returnRunCommand("$('div.alert-danger:visible div.ui-pnotify-text').text().trim()");
                actMsg = actMsg + Browser.returnRunCommand("$('div.alert-danger.alert-dismissible:visible').text().trim()");
            }
        }
        if (actVal.equals("")) {
            actMsg = "EXCEPTION";
        }
        return Browser.verifyJSErrosLog() && VerifyPhpErrors() && Report.verifyValue("VerifyErrorMsg:", expMsg, actMsg);
    }

    private static Boolean VerifyPhpErrors() {
        String actMsg = "";
        String pageSource = Browser.getPageSource();
        if (pageSource.contains("Fatal error")) {
            actMsg = "Have Fatal error;";
        }
        if (pageSource.contains("Parse error")) {
            actMsg = actMsg + " Have Parse error;";
        }
        return Report.verifyValue("VerifyPhpErrors:", "", actMsg);
    }

    public static void SetTitle(String title) {
        Browser.sendKeys(TITLE_ID, title);
    }

    public static String getRichTextBody(String fieldName) {
        Browser.waitForIsVisible("$('div#cke_edit-" + fieldName + " iframe.cke_wysiwyg_frame.cke_reset')", 5, true);
        return Browser.returnRunCommand("$('div#cke_edit-" + fieldName + " iframe.cke_wysiwyg_frame.cke_reset').contents().find('body').text();");
    }

    private static void RichTextSetBody(String bodyText, String locator) {
        Browser.switchToFrame(locator);
        WebElement oBody = Browser.getElementByCssSelector("body");
        oBody.clear();
        oBody.sendKeys(bodyText);
        Browser.backToDefaultContent();
    }

    public static String GetRichTextLocator(String field) {
        return "//iframe[contains(@title,'" + field + "')]";
    }

    public static void SetBody(String value, String field) {
        String locator = GetRichTextLocator(field);
        Browser.waitForIsVisible(locator, 10, true);
        Browser.setImplicitlyWait(2);
        if (Browser.isExist(locator)) {
            if (Browser.returnRunCommand("$(\"textarea[id='" + field + "'].qm_placeholder\").length;").equals("1")) {
                General.Click2BulletedListIcon(field);
            }
            Browser.waitForIsVisible(locator, 10, true);
            RichTextSetBody(value, locator);
        } else {
            Browser.waitForIsVisible(locator, 10, true);
            Browser.sendKeys(field, value);
        }
        Browser.setImplicitlyWaitDefault();
    }

    public static void Click2BulletedListIcon(String fieldLocator) {
        String locator = "$(\"div[id$='" + fieldLocator + "'] span.cke_button_icon.cke_button__bulletedlist_icon\")";
        Browser.waitForIsVisible(locator, 10, true);
        Browser.runCommand(locator + "[0].click()");
        Browser.sleep(1000);
    }

    public static void Click2NumberedListIcon(String fieldLocator) {
        Browser.click("//div[contains(@id,'" + fieldLocator + "')]//span[@class='cke_button_icon cke_button__numberedlist_icon']");
        Browser.sleep(1000);
    }

    public static String GetDomain(String campaignName) {
        return campaignName.toLowerCase().replace(" ", "-").replace("_", "-");
    }

    public static void SetEnglishLanguage() {
        Browser.goTo(Config.getProperty("url") + "qm_change_my_language/en_edg?qm_lang=en_edg");
    }

    public static String GetRandomNumber() {
        Random rand = new Random();
        int n = rand.nextInt(99999) + 1;
        return Integer.toString(n);
    }

    public static void ClosePopupByX() {
        String locator = "$('button[class$=\"close\"]')";
        Report.verifyValue("ClosePopupByX: visible", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("ClosePopupByX:", true, Browser.waitForIsVisible(locator, 5, false));
    }

    public static void ExpandFieldSet(String text) {
        if (!Browser.waitForIsVisible("//fieldset[contains(@class,'collapsible')]//span[text()='" + text + "']", 3, true)) {
            Browser.runCommand("$(\"fieldset.collapsible.collapsed span:contains('" + text + "')\")[0].click()");
            Report.verifyValue("ExpandFieldSet: " + text, true, Browser.isVisible("//fieldset[contains(@class,'collapsible')]//span[text()='" + text + "']"));
        }
    }

    public static String ReplacedScriptTag(String value) {
        return value.replace("<script>", "").replace("</script>", "");
    }

    public static void QMSelectByOptVal(String elmClass, String optValue) {
        Browser.runCommand("$('div.qm_select_2014_top span.qm_select_2014_arrow')[0].click()");
        if (Browser.waitForIsVisible("//div[@opt_val='" + optValue + "']", 1, true)) {
            Browser.click("//div[@opt_val='" + optValue + "']");
        }
    }

    public static void SetQMSelectValue(String field, String[] arrValues, String[] arrExpValues) {
        String cmd = "$('div.qm_select_2014_selection','div.form-item." + field + "').text();";
        if (Config.isResponsive()) {
            cmd = "$('div.qm_select_2014_selection','div.form-group." + field + "').text();";
        }
        //Open select
        Browser.runCommand("$('span.qm_select_2014_arrow','div." + field + "').click()");
        Browser.waitForIsVisible("$('#qm_select_2014_options')", 5, true);
        //Select values
        for (String value : arrValues) {
            if (!value.equals("")) {
                Browser.runCommand("$(\"div[opt_val='" + value + "']\",'#qm_select_2014_options').click();");
            }
        }
        //close select
        Browser.runCommand("$('span.qm_select_2014_arrow','div." + field + "').click()");
        Browser.waitForIsVisible("$('#qm_select_2014_options')", 5, false);
        General.VerifyReturnRunCommand(cmd, String.join("", arrExpValues).replace("\\", ""));
    }

    public static void VerifyText(String locator, String expVal) {
        Report.verifyValue("VerifyText:", expVal, Browser.getText(locator));
    }

    public static Boolean VerifyReturnRunCommand(String locator, String expVal) {
        return Report.verifyValue("VerifyReturnRunCommand: " + locator, expVal, Browser.returnRunCommand(locator));
    }

    public static Boolean VerifyReturnRunCommand(String fromMethod, String locator, String expVal) {
        return Report.verifyValue(fromMethod + ": " + locator, expVal, Browser.returnRunCommand(locator));
    }

    public static Boolean VerifyReturnRunCommandContains(String locator, String expVal) {
        return Report.verifyValue("VerifyReturnRunCommandContains: " + locator + " Contains '" + expVal + "'", true, Browser.returnRunCommand(locator).contains(expVal));
    }

    public static void Click2SubmitByJS() {
        Browser.runCommand("$('#edit-submit:visible','div.qm_buttons_wrapper').click();");
    }

    public static Boolean VerifyIsTableExist(String tableID, String expVal) {
        return General.VerifyReturnRunCommand("$(\"table[table_id='" + tableID + "']\").length;", expVal);
    }

    public static void ClickByJS(String cmd) {
        if (Browser.returnRunCommand(cmd + ".length").equals("1")) {
            Browser.runCommand(cmd + "[0].click()");
            General.VerifyErrorMsg("");
            General.VerifyReturnRunCommand(cmd + ".length", "0");
        } else {
            Report.exitTest("ClickByJS: " + cmd);
        }
    }

    public static void Wait2PageLoaded() {
        Report.verifyValue("Wait2PageLoaded:", true, Browser.waitForIsVisible("page_loading_msg", 20, false));
    }

    public static String firstUpperCase(String word) {
        StringBuilder stateToUpperCase = new StringBuilder();
        String[] wordsArr = word.split(" ");
        for (String s : wordsArr) {
            stateToUpperCase.append(s.substring(0, 1).toUpperCase() + s.substring(1) + (" "));
        }
        return stateToUpperCase.toString().trim();
    }

    public static String firstUpperCaseMemberOfArray(String[] array, int index) {
        return firstUpperCase(array[index]);
    }

    public static void VerifyMandatoryFieldStyle(String fieldName, String expVal) {
        General.VerifyReturnRunCommand("VerifyMandatoryFieldStyle", "$('div.error-help-block:visible',$('label[for=\"" + fieldName + "\"]').parent()).length", expVal);
        General.VerifyReturnRunCommand("VerifyMandatoryFieldStyle", "$('div.form-group.form-element.elm-" + fieldName + ".has-error.form-error').length", expVal);
    }

    public static void DeleteItem(String title) {
        Browser.click("edit-delete");
        VerifyErrorMsg("");
        Report.verifyValue("DeleteItem:", true, GetSuccessMsg().contains("Node '" + ReplacedScriptTag(title) + "' - has been deleted."));
    }

    public static String generateErrorMsg(String[] messages) {
        String result;
        if (!Config.isResponsive()) {
            result = String.join("\n  ", messages);
        } else {
            result = String.join("", messages);
        }
        return result;
    }

    /**
     * Method verify error messages in the error popup and error field style
     * (only for responsive) of mandatory fields of page before and after click
     * to submit button
     *
     * @param submitButtonLocator
     * @param arrOfErrorMessages can be find in the errors popup
     * @param arrOfFieldsName can be find in "for" attribute of "label" element
     * of checked field name
     */
    public static void verifyMandatoryFields(String submitButtonLocator, String[] arrOfErrorMessages, String[] arrOfFieldsName) {
        if (Config.isResponsive()) {
            for (String fieldName : arrOfFieldsName) {
                VerifyMandatoryFieldStyle(fieldName, "0");
            }
        }
        Report.verifyValue("verifyMandatoryFields: verify visibility of submit button ", true, Browser.waitForIsVisible(submitButtonLocator, 10, true));
        Browser.runCommand(submitButtonLocator + "[0].click()");
        Browser.sleep(3000);
        VerifyErrorMsg(generateErrorMsg(arrOfErrorMessages));
        if (Config.isResponsive()) {
            for (String fieldName : arrOfFieldsName) {
                VerifyMandatoryFieldStyle(fieldName, "1");
            }
            General.CloseNotifyMsg();
        }
    }

    public static void click2ChosenUser(String user) {
        String cmd = Config.isResponsive() ? "$(\"span.select2-results div.search-user:contains('" + user + "')\")" : "$(\"td.userref_user_option_details_td div:contains('" + user + "')\").parent().find('div.userref_user_option_action')";
        Report.verifyValue("click2ChosenUser: before click to user", true, Browser.waitForIsVisible(cmd, 10, true));
        String endingOfCmd = Config.isResponsive() ? ".trigger('mousedown').trigger('mouseup')" : "[0].click()";
        Browser.sleep(1000);
        Browser.runCommand(cmd + endingOfCmd);
    }

    public static boolean isPageContainsIdeaName(String ideaName) {
        return Boolean.parseBoolean(Browser.returnRunCommand("$('a').is(\":contains('" + ideaName + "')\")"));
    }

    public static void click2ViewIcons(int viewNumber) {
        Browser.runCommand("$('div.panel-tabs.pull-right a')[" + viewNumber + "].click()");
        String resultId = Browser.returnRunCommand("$('div.panel-tabs.pull-right').find('ul').attr('id').replace(/s$/,'-')") + viewNumber;
        Report.verifyValue("click2ViewIcons: viewNumber=" + viewNumber, "1", Browser.returnRunCommand("$('div#" + resultId + ".tab-pane.active').length"));
    }

    public static void runCron() {
        ThreadWebDriver.get().navigate().to(Config.getProperty("url") + "cron.php");
        Browser.back();
    }

    public static void verifyDataForm2Lists(List<String> data1, List<String> data2) {
        if (data1.size() != data2.size()) {
            Report.exitTest("verifyDataForm2Lists: Wrong lists size ");
        }
        for (int i = 0; i < data1.size(); i++) {
            Report.verifyValue("verifyDataForm2Lists: " + data1.get(i) + " and " + data2.get(i), data1.get(i), data2.get(i));
        }
    }

    public static String getCurrentUser() {
        String cmd = Config.isResponsive() ? "$('li#user-dropdown-header h3').text()" : "$('div.dropdown_navigation_top.user-nav div.dropdown_navigation_main').text()";
        return Browser.returnRunCommand(cmd);
    }

    public static void setLanguageTo(String language) {
        String langId = Config.isResponsive() ? "ul#qm-desktop-language-dropdown" : "div#lang-nav";
        Browser.runCommand("$('" + langId + " a[href$=" + language.replace("-", "_") + "]')[0].click()");
        Report.verifyValue("setLanguageTo: ", true, language.substring(0, 2).equals(getCurrentLanguage()));
    }

    public static String getCurrentLanguage() {
        String cmd = Config.isResponsive() ? "$('div.language-image').text()" : "$('div#lang-nav div.i18n_language_key').text()";
        return Browser.returnRunCommand(cmd).toLowerCase();
    }

}
