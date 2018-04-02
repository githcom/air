package Sanity;

import Helpers.Report;
import Init.InitTest;
import Pages.LoginPage;
import Pages.Navigation;
import Pages.PartnersPage;
import Pages.ProfilePage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class NavigationTest extends InitTest {

    @Rule
    public TestName testName = new TestName();

    @Test
    public void managerNavigationMenuItemTest() {
        Report.startTest(getClass().getName() + "." + testName.getMethodName(), testName.toString(), getClass().getPackage().getName());

        LoginPage.login(LoginPage.MANAGER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        Navigation.nav2MenuItem(Navigation.DASHBOARD);
        Navigation.nav2MenuItem(Navigation.MANAGER);
        Navigation.nav2MenuItem(Navigation.PARTNERS);
        Navigation.nav2MenuItem(Navigation.CHANNEL);
        Navigation.nav2MenuItem(Navigation.PAYMENTS);
        Navigation.nav2MenuItem(Navigation.ADMINISTARATION);
        Navigation.nav2MenuItem(Navigation.REPORTS);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void partnerNavigationMenuItemTest() {
        Report.startTest(getClass().getName() + "." + testName.getMethodName(), testName.toString(), getClass().getPackage().getName());

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        Navigation.nav2MenuItem(Navigation.DASHBOARD);
        Navigation.nav2MenuItem(Navigation.INCOME);
        Navigation.nav2MenuItem(Navigation.CHANNEL);
        Navigation.nav2MenuItem(Navigation.REFERRAL);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void SignInFromNameOfPartnerTest() {
        Report.startTest(getClass().getName() + "." + testName.getMethodName(), testName.toString(), getClass().getPackage().getName());

        LoginPage.login(LoginPage.MANAGER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.PARTNERS);
        PartnersPage.findPartner(LoginPage.AUTHOR_ID);
        PartnersPage.choosePartnerByName(LoginPage.ALEKSANDR_SHKINDER);
        ProfilePage.click2LoginAsUser();
        Navigation.nav2MenuItem(Navigation.DASHBOARD);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        Navigation.nav2MenuItem(Navigation.INCOME);
        Navigation.nav2MenuItem(Navigation.CHANNEL);
        Navigation.nav2MenuItem(Navigation.REFERRAL);
        Navigation.nav2Profile();
        ProfilePage.back2ManagerInterface();
        Navigation.nav2MenuItem(Navigation.DASHBOARD);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        Navigation.nav2MenuItem(Navigation.MANAGER);
        Navigation.nav2MenuItem(Navigation.PARTNERS);
        Navigation.nav2MenuItem(Navigation.CHANNEL);
        Navigation.nav2MenuItem(Navigation.PAYMENTS);
        Navigation.nav2MenuItem(Navigation.ADMINISTARATION);
        Navigation.nav2MenuItem(Navigation.REPORTS);

        Report.finishTest();
    }

}
