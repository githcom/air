package Pages;

import Helpers.Browser;
import Helpers.Report;
import org.openqa.selenium.Keys;

import javax.print.DocFlavor;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class IncomesPage {

    public static void gotoPaymentsOrRequisites(boolean isPaymentsOrRequisites) {
        String tab = isPaymentsOrRequisites ? "Выплаты" : "Платежные реквизиты";
        String locator = "$(\"button[title='" + tab + "']\")";
        Report.verifyValue("gotoPaymentRequisites: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("gotoPaymentRequisites: after click ", true, Browser.waitForIsVisible(locator + ".parent(\"li.active\")", 10, true));
    }

    public static void addNewPaymentRequisities() {
        String locator = "$(\"button[ng-click='addPaymentRequisite()']\")";
        Report.verifyValue("addNewPaymentRequisities: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("addNewPaymentRequisities: after click ", true, Browser.waitForIsVisible("$(\"form[name='paymentRequisitesForm']\")", 10, true));
    }

    public static void selectCountry(String country) {
        click2selectCountry();
        setCountry(country);
        submitCountry(country);
    }

    private static void click2selectCountry() {
        String locator = "$(\"div[placeholder*='Выберите страну'] span.ui-select-toggle\")";
        Report.verifyValue("selectCountry: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("selectCountry: after click ", true, Browser.waitForIsVisible("$('div.ui-select-container.ui-select-bootstrap.dropdown.open')", 10, true));
        Report.verifyValue("selectCountry: verify dropdown ", true, Browser.waitForIsVisible("ui-select-choices-0", 10, true));
    }

    private static void setCountry(String country) {
        String locator = "$(\"input[placeholder*='Выберите страну']\")";
        Report.verifyValue("setCountry: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, country);
        Report.verifyValue("setCountry: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + country + "')", 10, true));
    }

    private static void submitCountry(String country) {
        Browser.getElement("$(\"input[placeholder*='Выберите страну']\")").sendKeys(Keys.ENTER);
        Report.verifyValue("submitCountry: ", true, Browser.waitForIsVisible("$(\"div[placeholder*='Выберите страну'] span.ng-binding.ng-scope:contains('" + country + "')\")", 10, true));
    }

    public static void selectPaymentSystem(String paymentSystem) {
        click2selectPaymentSystem();
        setPaymentSystem(paymentSystem);
        submitPaymentSystem(paymentSystem);
    }

    private static void click2selectPaymentSystem() {
//        String locator = "$(\"div[name='dropDownPaymentMethods'] span.form-control.ui-select-toggle\")";
        String locator = "//div[@name='dropDownPaymentMethods'] //span[contains(@class,'form-control ui-select-toggle')]";
        Report.verifyValue("click2selectPaymentSystem: before click ", true, Browser.waitForIsVisible(locator, 10, true));
//        Browser.runCommand(locator + "[0].click()");
        Browser.click(locator);
        Report.verifyValue("click2selectPaymentSystem: after click ", true, Browser.waitForIsVisible("$(\"div.ui-select-container.ui-select-bootstrap.dropdown.ng-invalid.open\")", 10, true));
        Report.verifyValue("click2selectPaymentSystem: verify dropdown ", true, Browser.waitForIsVisible("ui-select-choices-1", 10, true));
    }

    private static void setPaymentSystem(String paymentSystem) {
        String locator = "$(\"input[placeholder*='Выберите платежную систему']\")";
        Report.verifyValue("setPaymentSystem: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, paymentSystem);
        Browser.sleep(1500);
        Report.verifyValue("setPaymentSystem: after send keys ", true, Browser.waitForIsVisible(locator + ".val('" + paymentSystem + "')", 10, true));
    }

    private static void submitPaymentSystem(String paymentSystem) {
        Browser.getElement("$(\"input[placeholder*='Выберите платежную систему']\")").sendKeys(Keys.ENTER);
        Report.verifyValue("submitPaymentSystem: ", true, Browser.waitForIsVisible("$(\"div[name='dropDownPaymentMethods'] span.ng-binding.ng-scope:contains('" + paymentSystem + "')\")", 10, true));
        switch (paymentSystem.toLowerCase()) {
            case "VISA/MasterCard/Maestro":
                Report.verifyValue("submitPaymentSystem: VISA/MasterCard/Maestro ", true, Browser.waitForIsVisible("$(\"div[ng-form='requisiteForm']\")", 10, true));
                Report.verifyValue("submitPaymentSystem: VISA/MasterCard/Maestro currency disabled ", true, Browser.waitForIsVisible("$(\"div[placeholder*='Выберите валюту'][disabled='disabled']\")", 10, true));
                break;
        }
        Report.verifyValue("submitPaymentSystem: verify close button disabled", true, Browser.waitForIsVisible("$('button.btn-success.pull-right.disabled')", 10, true));
    }

    public static void enterCardNumber(String number, String... errorMsg) {
        String locator = "$(\"input[name='requisiteValue']\")";
        Report.verifyValue("enterCardNumber: before send keys ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, number);
        Report.verifyValue("enterCardNumber: ", true, Browser.waitForIsVisible(locator + ".val('" + number + "')", 10, true));
        if (errorMsg.length > 0) {
            Report.verifyValue("enterCardNumber: verify error msg " + errorMsg[0], true, Browser.waitForIsVisible("$(\"div.error.ng-binding.ng-scope:contains('" + errorMsg[0] + "')\")", 10, true));
            locator = Browser.waitForIsVisible("$('button.btn-success.pull-right.disabled')", 3, true) ? "$('button.btn-success.pull-right.disabled')" : "$('button.btn-success.but-page-add.disabled')";
            Report.verifyValue("submitPaymentSystem: verify close button disabled", true, Browser.waitForIsVisible(locator, 10, true));
        }
    }

    public static void click2saveRequisites(String paymentMethod, String currency, String cartNumber) {
        Report.verifyValue("click2saveRequisites: before click ", true, Browser.waitForIsVisible("btn-save", 10, true));
//        Browser.click("btn-save");
        Browser.runCommand("$('button#btn-save')[0].click()");
        switch (paymentMethod.toLowerCase()) {
            case "visa/mastercard/maestro":
//                Report.verifyValue("click2saveRequisites: verify payment method ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope']:eq(0) div.name-info:contains('Способ оплаты:')\").siblings(\":contains('" + paymentMethod + "')\")", 10, true));
                Report.verifyValue("click2saveRequisites: verify payment method ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope'] div.name-info:contains('Способ оплаты:')\").siblings(\":contains('" + paymentMethod + "')\")", 10, true));
//                Report.verifyValue("click2saveRequisites: verify currency ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope']:eq(0) div.name-info:contains('Валюта выплат:')\").siblings(\":contains('" + currency + "')\")", 10, true));
                Report.verifyValue("click2saveRequisites: verify currency ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope'] div.name-info:contains('Валюта выплат:')\").siblings(\":contains('" + currency + "')\")", 10, true));
//                Report.verifyValue("click2saveRequisites: verify cart number ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope']:eq(0) div.name-info.ng-binding:contains('Пополнение карты:')\").siblings(\":contains('" + cartNumber + "')\")", 10, true));
                Report.verifyValue("click2saveRequisites: verify cart number ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope'] div.name-info.ng-binding:contains('Пополнение карты:')\").siblings(\":contains('" + cartNumber + "')\")", 10, true));
                break;
            case "btc":
                Report.verifyValue("click2saveRequisites: verify cart number ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope'] div.name-info.ng-binding:contains('Адрес BTC кошелька:')\").siblings(\":contains('" + cartNumber + "')\")", 10, true));
                Report.verifyValue("click2saveRequisites: verify payment method ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope'] div.name-info:contains('Способ оплаты:')\").siblings(\":contains('" + paymentMethod + "')\")", 10, true));
                Report.verifyValue("click2saveRequisites: verify currency ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope'] div.name-info:contains('Валюта выплат:')\").siblings(\":contains('" + currency + "')\")", 10, true));
                break;
            case "payoneer":
                Report.verifyValue("click2saveRequisites: verify cart number ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope'] div.name-info.ng-binding:contains('Payoneer аккаунт:')\").siblings(\":contains('" + cartNumber + "')\")", 10, true));
                Report.verifyValue("click2saveRequisites: verify payment method ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope'] div.name-info:contains('Способ оплаты:')\").siblings(\":contains('" + paymentMethod + "')\")", 10, true));
                Report.verifyValue("click2saveRequisites: verify currency ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope'] div.name-info:contains('Валюта выплат:')\").siblings(\":contains('" + currency + "')\")", 10, true));
                break;
        }

//        Report.verifyValue("click2saveRequisites: verify payment method ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope'] div.name-info:eq(0)\").siblings(\":contains('" + paymentMethod + "')\")", 10, true));
//        Report.verifyValue("click2saveRequisites: verify currency ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope'] div.name-info:eq(1)\").siblings(\":contains('" + currency + "')\")", 10, true));
//        Report.verifyValue("click2saveRequisites: verify cart number ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope'] div.name-info.ng-binding:eq(2)\").siblings(\":contains('" + cartNumber + "')\")", 10, true));
    }

    public static void editRequisites(int requisiteNumber) {
//        String locator = "$('div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div.container-payment-req.ng-scope:eq(" + requisiteNumber + ") i.fa-pencil')";
//        String locator = "$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope']:eq(" + requisiteNumber + ") i.fa-pencil\")";
//        String locator = "$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div.desck-info.ng-binding:contains('VISA/MasterCard/Maestro')\").closest(\"div[class='container-payment-req ng-scope']:eq(" + requisiteNumber + ")\").find('i.fa-pencil')";
        String locator = "$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div.desck-info.ng-binding:contains('VISA/MasterCard/Maestro')\").closest(\"div[class='container-payment-req ng-scope']\").find('i.fa-pencil')";
        Report.verifyValue("editRequisites: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[" + requisiteNumber + "].click()");
        Report.verifyValue("editRequisites: after click() ", true, Browser.waitForIsVisible("$(\"form[name='paymentRequisitesForm']\")", 10, true));
        Report.verifyValue("submitPaymentSystem: verify close button disabled", true, Browser.waitForIsVisible("$('button.btn-success.but-page-add.disabled')", 10, true));
    }

    public static void deletingOfRequisite(int numberOfRequisite, boolean isDelete) {
        String account = click2deletingRequisite(numberOfRequisite);
        selectDeleteOrCancel(isDelete, account, numberOfRequisite);
    }

    private static String click2deletingRequisite(int numberOfRequisite) {
        String locator = "$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope']:eq(" + numberOfRequisite + ") i.fa-trash-o\")";
        Report.verifyValue("deletingOfRequisite: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("click2deletingRequisite: after click ", true, Browser.waitForIsVisible("$(\"div.modal.fade.fill-in.black.modal-delete.in form.modal-forms.ng-pristine.ng-valid\")", 10, true));
        return getAccountValue(numberOfRequisite);
    }

    private static String getAccountValue(int numberOfAccount) {
        return Browser.returnRunCommand("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope']:eq(" + numberOfAccount + ") div.desck-info.ng-binding:eq(2)\").text()");
    }

    private static void selectDeleteOrCancel(boolean isDelete, String account, int numberOfRequisite) {
        String locator = isDelete ? "$(\"button[ng-click='deletePaymentRequisite()']\")" : "$(\"div.col-xs-4.p-l-7-5.p-r-7-5 button.btn-default.button-icons-close\")";
        Report.verifyValue("selectDeleteOrCancel: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("selectDeleteOrCancel:: after click ", true, Browser.waitForIsVisible(locator, 4, false));
        Report.verifyValue("selectDeleteOrCancel:: verify account ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope']:eq(" + numberOfRequisite + ") div.desck-info.ng-binding:eq(2):contains('" + account + "')\")", 5, !isDelete));
    }

    public static void verifyRequisitesMoreThanOne() {
//        int numberOfRequisites = Integer.parseInt(Browser.returnRunCommand("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope']\").length"));
        String locator = "$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div.desck-info.ng-binding:contains('VISA/MasterCard/Maestro')\").closest(\"div[class='container-payment-req ng-scope']\")";
        Browser.waitForIsVisible(locator, 5, true);
        int numberOfRequisites = Integer.parseInt(Browser.returnRunCommand(locator + ".length"));
        if (numberOfRequisites < 1) {
            IncomesPage.addNewPaymentRequisities();
            IncomesPage.selectCountry("Украина");
            IncomesPage.selectPaymentSystem("VISA/MasterCard/Maestro");
            String number = "12345678" + ((long) (Math.random() * Long.valueOf(100000000)));
            IncomesPage.enterCardNumber(number);
            IncomesPage.click2saveRequisites("1234567812341234", "US Dollar", "VISA/MasterCard/Maestro");
        }
    }

    public static void setCurrentRequisite(int numberOfRequisite) {
        String locator = "$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope']:eq(" + numberOfRequisite + ") i.fa-check\")";
        Report.verifyValue("setCurrentRequisite: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        String account = getAccountValue(numberOfRequisite);
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("setCurrentRequisite: verify set current ", true, Browser.waitForIsVisible("$(\"div.col-block.col-lg-6.col-xs-12.p-l-0.p-r-7-5 div[class='container-payment-req ng-scope active'] div.desck-info.ng-binding:eq(2):contains('" + account + "')\")", 10, true));
    }

    public static String getRandomeNumber() {
        String addValue = "12345678";
        String number = addValue + ((long) (Math.random() * Long.valueOf(100000000)));
        if (number.length() < 16) {
            int diff = 16 - number.length();
            number += addValue.substring(0, diff + 1);
        }
        return number;
    }

    public static void setCheckboxTemporarilySuspendFormationOfPayments(boolean isTrue) {
        String locator = "$(\"label[for='generateStop']\")";
        Report.verifyValue("setCheckboxTemporarilySuspendFormationOfPayments: before click ", true, Browser.waitForIsVisible(locator, 10, true));
//        if (isTrue) {

            if (Browser.waitForIsVisible("$('div.alert-danger.m-t-30')", 4, true)) {
                Browser.click(locator);
            }
//        }
        if (isTrue) {
//        Browser.runCommand(locator + "[0].click()");
            Report.verifyValue("setCheckboxTemporarilySuspendFormationOfPayments: before click ", true, Browser.waitForIsVisible(locator, 10, true));
            Browser.click(locator);
        }
        Browser.sleep(2000);
        Report.verifyValue("setCheckboxTemporarilySuspendFormationOfPayments: ", true, Browser.waitForIsVisible("$(\"div.alert-danger.m-t-30:contains('У вас приостановлено формирование новых отчетов. Доход продолжает начисляться и вы можете возобновить формирование отчетов в любой момент')\")", 5, isTrue));
    }

    public static void verifySuspendingPaymentsOnCurrentRevenue(boolean isPresent) {
        gotoCurrentRevenue();
        Browser.sleep(1000);
        Report.verifyValue("verifySuspendingPaymentsOnCurrentRevenue: bigger font ", true, Browser.waitForIsVisible("$(\"div.name.ng-binding:contains('У вас приостановлено формирование новых отчетов.')\")", 5, isPresent));
        Report.verifyValue("verifySuspendingPaymentsOnCurrentRevenue: smaller font ", true, Browser.waitForIsVisible("$(\"div.desck.ng-binding:contains('Доход продолжает начисляться и вы можете возобновить формирование отчетов в любой момент в разделе “выплаты”.')\")", 5, isPresent));
    }

    public static void gotoCurrentRevenue() {
        String locator = "$(\"span:contains('Текущий доход')\")";
        Report.verifyValue("gotoCurrentRevenue: before click", true, Browser.waitForIsVisible(locator + ".parent()", 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("gotoCurrentRevenue: after click", true, Browser.waitForIsVisible(locator + ".parent('.active')", 10, true));
    }

    public static void click2PaymentsLink() {
        String locator = "$(\"a.linck-suspended.ng-binding\")";
        Report.verifyValue("click2PaymentsLink: ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("click2PaymentsLink: ", true, Browser.waitForIsVisible(locator, 5, false));
    }

    public static void click2PaymentByNumber(int paymentNumber) {
        String locator = "$(\"tr.ng-scope:eq(" + paymentNumber + ")\")";
        Report.verifyValue("click2PaymentByNumber: ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
        Report.verifyValue("click2PaymentByNumber: ", true, Browser.waitForIsVisible(locator, 5, false));
    }

    public static float getSumFromValue(String value) {
        String locator = "$(\"div.block-details-channel.m-b-40 div.name.ng-binding:contains('" + value + "')\").siblings()";
        Report.verifyValue("getSumFromAdvertisiment: ", true, Browser.waitForIsVisible(locator, 10, true));
        int numberOfItems = Integer.parseInt(Browser.returnRunCommand(locator + ".length"));
        float sum = 0.f;
        for (int i = 0; i < numberOfItems; i++) {
            String res = Browser.returnRunCommand("$(" + locator + "[" + i + "]).text().trim()").replace(" ", "");
            res = res.substring(0, res.indexOf("$")).trim().replace(",", ".");
            float resNumber = Float.parseFloat(res);
            sum+=resNumber;
        }
        sum = (float) Math.rint(100.0 * sum)/100;
        return sum;
    }

    public static float getTotalRevenueFromValue(String value) {
        String locator = "$(\"div.item-block.m-b-10.ng-scope div.name.ng-binding:contains('" + value + "')\").siblings()";
        Report.verifyValue("getTotalRevenueFromValue: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        String input = Browser.returnRunCommand(locator + ".text().trim()").replace(" ", "");
        input = input.substring(0, input.indexOf("$")).replace(",", ".").trim();
        return Float.parseFloat(input);
    }

    public static void verifyEqualityOfSums(float sum1, float sum2) {
        Report.verifyValue("verifyEqualityOfSums: " + sum1 + " and  " + sum2, sum1, sum2);
    }




}
