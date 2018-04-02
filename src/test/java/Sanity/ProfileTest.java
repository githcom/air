package Sanity;

import Helpers.DateHelper;
import Helpers.Report;
import Init.InitTest;
import Pages.IncomesPage;
import Pages.LoginPage;
import Pages.Navigation;
import Pages.ProfilePage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class ProfileTest extends InitTest {

    @Rule
    public TestName testName = new TestName();

    public void start() {
        Report.startTest(getClass().getName() + "." + testName.getMethodName(), testName.toString(), getClass().getPackage().getName());
    }

    @Test
    public void editingPartnerProfile_Test() {
        start();

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2Profile();
        ProfilePage.setName("OLEKSANDR");
        ProfilePage.setSurname("SHKINDER");
        ProfilePage.setCountry("Голландия");
        ProfilePage.setCity("Амстердам");
        ProfilePage.setPostalRequisities("Украина, г. Киев, ул. Первая, 12, кв.2");
        ProfilePage.addData("Телефон");
        ProfilePage.setPhone("11323223 ", 2);
        ProfilePage.addData("Почта");
        ProfilePage.setMail("example.mail@mail.com", 2);
        ProfilePage.setDate("31.01.1898", "Неправильный формат даты");
        ProfilePage.setDate("12.03.1999");
        ProfilePage.click2SaveChanges("OLEKSANDR", "SHKINDER", "Голландия", "Амстердам", "Украина, г. Киев, ул. Первая, 12, кв.2","12.03.1999");
        ProfilePage.setName("Александр");
        ProfilePage.setSurname("Шкиндер");
        ProfilePage.setCountry("Аргентина");
        ProfilePage.setCity("Гойя");
        ProfilePage.setPostalRequisities("Украина, г. Киев, ул. Вторая, 34, кв.78");
        ProfilePage.setDate("15.08.1990");
        ProfilePage.click2SaveChanges("Александр", "Шкиндер", "Аргентина", "Гойя", "Украина, г. Киев, ул. Вторая, 34, кв.78","15.08.1990");
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void  editingManagerProfile_Test() {
        start();

        LoginPage.login(LoginPage.MANAGER_LOGIN, LoginPage.PSW);
        Navigation.nav2Profile();
        ProfilePage.setName("OLEKSANDR");
        ProfilePage.setSurname("SHKINDER");
        ProfilePage.setCountry("Голландия");
        ProfilePage.setCity("Амстердам");
        ProfilePage.setPostalRequisities("Украина, г. Киев, ул. Первая, 12, кв.2");
        ProfilePage.addData("Телефон");
        ProfilePage.setPhone("11323223 ", 2);
        ProfilePage.addData("Почта");
        ProfilePage.setMail("example.mail@mail.com", 2);
        ProfilePage.setDate("31.01.1898", "Неправильный формат даты");
        ProfilePage.setDate("12.03.1999");
        ProfilePage.click2SaveChanges("OLEKSANDR", "SHKINDER", "Голландия", "Амстердам", "Украина, г. Киев, ул. Первая, 12, кв.2","12.03.1999");
        ProfilePage.setName("Александр");
        ProfilePage.setSurname("Шкиндер");
        ProfilePage.setCountry("Аргентина");
        ProfilePage.setCity("Гойя");
        ProfilePage.setPostalRequisities("Украина, г. Киев, ул. Вторая, 34, кв.78");
        ProfilePage.setDate("15.08.1990");
        ProfilePage.click2SaveChanges("Александр", "Шкиндер", "Аргентина", "Гойя", "Украина, г. Киев, ул. Вторая, 34, кв.78","15.08.1990");
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void  localizationPartner_Test() {
        start();

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2Profile();
        ProfilePage.setLanguage(false);
        ProfilePage.click2SaveChanges(false);
        ProfilePage.setLanguage(true);
        ProfilePage.click2SaveChanges(true);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void  localizationManager_Test() {
        start();

        LoginPage.login(LoginPage.MANAGER_LOGIN, LoginPage.PSW);
        Navigation.nav2Profile();
        ProfilePage.setLanguage(false);
        ProfilePage.click2SaveChanges(false);
        ProfilePage.setLanguage(true);
        ProfilePage.click2SaveChanges(true);
        Navigation.logout();

        Report.finishTest();
    }

//    @Test
//    public void  changePasswordForCabinetManager_Test() {
//        start();
//
//        LoginPage.login(LoginPage.MANAGER_LOGIN, LoginPage.PSW /*"32165498709"*/);
//        Navigation.nav2Profile();
//        ProfilePage.click2ChangePassword();
//        ProfilePage.setOldPassword("321654987");
//        ProfilePage.setNewPassword("32165498709");
//        ProfilePage.setConfirmNewPassword("32165498709");
//        ProfilePage.click2ConfirmPassword();
//        ProfilePage.setOldPassword("32165498709");
//        ProfilePage.setNewPassword("321654987");
//        ProfilePage.setConfirmNewPassword("321654987");
//        ProfilePage.click2ConfirmPassword();
//
//
//
//        ProfilePage.setLanguage(false);
//        ProfilePage.click2SaveChanges(false);
//        ProfilePage.setLanguage(true);
//        ProfilePage.click2SaveChanges(true);
//        Navigation.logout();
//
//        Report.finishTest();
//    }



}
