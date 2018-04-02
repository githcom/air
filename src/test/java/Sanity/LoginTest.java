package Sanity;

import Helpers.Report;
import Init.InitTest;
import Pages.ConversationsPage;
import Pages.LoginPage;
import Pages.Navigation;
import Pages.PartnersPage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class LoginTest extends InitTest {

    @Rule
    public TestName testName = new TestName();

    @Test
    public void loginNegativeScenarios() {
        Report.startTest(getClass().getName() + "." + testName.getMethodName(), testName.toString(), getClass().getPackage().getName());

        LoginPage.login("", "", false);
        LoginPage.login("wrongLoginFormat", "", false, "Неправильный формат логина.");
        LoginPage.login("", "wronpsw", false, "Неправильный формат логина.");
        LoginPage.login("shkinder.o@gmail.com", "", false, "Поле обязательное для заполнения.");
        LoginPage.login("", "321654987", false, "Неправильный формат логина.");
        LoginPage.login("shkinder.o@gmail.com", "wrongpasw", false, "Неправильный логин или пароль");
        LoginPage.login("wronglogin@gmail.com", "321654987", false, "Неправильный логин или пароль");

        Report.finishTest();
    }



}
