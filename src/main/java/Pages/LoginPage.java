package Pages;

import Helpers.Browser;
import Helpers.General;
import Helpers.Report;

public class LoginPage {

    public static final String MANAGER_LOGIN = "shkinder.o@gmail.com";
    public static final String UNIY_OBOZREVATEL = "uniyizobretatel@gmail.com";
    public static final String PARTNER_LOGIN = "oshkinder@air.io";
    public static final String PSW = "321654987";
    public final static String AUTHOR_ID = "P-65112";
    public final static String ALEKSANDR_SHKINDER = "Александр Шкиндер";


    public static void setUsername(String username) {
        Browser.sendKeys("username", username);
    }

    private static void setPsw(String username) {
        Browser.sendKeys("password", username);
    }

    private static void click2LoginButton() {
        Browser.runCommand("$('#loginBtn').click()");
    }

    public static void verifySuccessLogin(boolean res, int sec) {
        Report.verifyValue("verifyUserLogin: ", res, Browser.waitForIsVisible("$('div.dashboard-container')", sec, true));
    }

    public static void verifyFailLogin() {
        Report.verifyValue("verifyFailLogin: ", true, Browser.waitForIsVisible("$('div.login-container.bg-white')", 10, true));
    }

    public static void login(String usrName, String psw) {
        setUsername(usrName);
        setPsw(psw);
        click2LoginButton();
        verifySuccessLogin(true, 18);
    }

    public static void login(String usrName, String psw, boolean isSuccessLogin, String... expErrorMsg) {
        setUsername(usrName);
        setPsw(psw);
        click2LoginButton();
        verifySuccessLogin(isSuccessLogin, 4);
        verifyErrorMsg(expErrorMsg.length == 0 ? "" : expErrorMsg[0]);
    }

    public static String getUsrnameErrorMsg() {
        return Browser.returnRunCommand("$(\"label[class='error'][for='username'] span\").text()");
    }

    public static String getPswErrorMsg() {
        String msg = Browser.returnRunCommand("$(\"label[class='error'][for='password'] span\").text()");
        return msg+= Browser.returnRunCommand("$(\"div[ng-show*='$pristine'][class=''] div[class='error']>span\").text()");
    }

    public static void verifyErrorMsgPswEmpty() {
        String actErrorMsg = Browser.returnRunCommand("$(\"label[for='password'] span\").text()");
        Report.verifyValue("verifyPswErrorMsg: ", "Поле обязательное для заполнения.", actErrorMsg);
    }

    public static boolean verifyErrorMsg(String expMsg) {
        String actMsg = getUsrnameErrorMsg();
        actMsg+=getPswErrorMsg();
        return Report.verifyValue("verifyErrorMsg: ", expMsg, actMsg);
    }


}

