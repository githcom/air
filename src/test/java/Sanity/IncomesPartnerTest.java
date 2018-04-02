package Sanity;

import Helpers.Report;
import Init.InitTest;
import Pages.IncomesPage;
import Pages.LoginPage;
import Pages.Navigation;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class IncomesPartnerTest extends InitTest {

    private static final String DATA_NOT_MEET_REQUIREMENTS = "Данные не соответствуют требованиям.";
    private static final String VISA_MASTERCARD_MAESTRO = "VISA/MasterCard/Maestro";
    private static final String US_DOLLAR = "US Dollar";
    private static final String REVENUE_FROM_ADVERTISING = "Доход от рекламы";
    private static final String REVENUE_FROM_YOUTUBE_RED = "Доход от YouTube RED";

    @Rule
    public TestName testName = new TestName();

    @Test
    public void creationOfPaymentRequisite_MasterCardVisaMaestro_Test() {
        start();

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.INCOME);
        IncomesPage.gotoPaymentsOrRequisites(false);
        IncomesPage.addNewPaymentRequisities();
        IncomesPage.selectCountry("Украина");
        IncomesPage.selectPaymentSystem(VISA_MASTERCARD_MAESTRO);
        IncomesPage.enterCardNumber("", "Поле должно быть заполнено.");
        IncomesPage.enterCardNumber("123456781234123", DATA_NOT_MEET_REQUIREMENTS);
        IncomesPage.enterCardNumber("1234567812341231111", DATA_NOT_MEET_REQUIREMENTS);
//        String number = "12345678" + ((long)(Math.random() * Long.valueOf(100000000)));
        String number = IncomesPage.getRandomeNumber();
        IncomesPage.enterCardNumber(number);
        IncomesPage.click2saveRequisites( VISA_MASTERCARD_MAESTRO, US_DOLLAR, number);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void creationOfPaymentRequisite_BTC_Test() {
        start();

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.INCOME);
        IncomesPage.gotoPaymentsOrRequisites(false);
        IncomesPage.addNewPaymentRequisities();
        IncomesPage.selectCountry("Украина");
        IncomesPage.selectPaymentSystem("BTC");
        IncomesPage.enterCardNumber("1BQ9qza7fn9snSCyJQB3ZcN46biBtkt4eeд", DATA_NOT_MEET_REQUIREMENTS);
        IncomesPage.enterCardNumber("1BQ9qza7fn9snSCyJQB3ZcN46bi!3Btkt4ee", DATA_NOT_MEET_REQUIREMENTS);
        IncomesPage.enterCardNumber("3BQ9qza7fn9snSCyJQB3ZcN46bdsiBtkt4ee", DATA_NOT_MEET_REQUIREMENTS);
        IncomesPage.enterCardNumber("2OBQ9qza7fn9snSCyJQB3ZcN46biBtkt4ee", DATA_NOT_MEET_REQUIREMENTS);
        IncomesPage.enterCardNumber("2OBQ9qza7fn9!nSCy&QB3ZcN46biBtkt4ee", DATA_NOT_MEET_REQUIREMENTS);
        IncomesPage.enterCardNumber("1BQ9qza7fn9snSCyJQB3ZcN46biBtkt4ee");
        IncomesPage.click2saveRequisites("BTC", "BTC", "1BQ9qza7fn9snSCyJQB3ZcN46biBtkt4ee");
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void creationOfPaymentRequisite_Payoneer_Test() {
        start();

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.INCOME);
        IncomesPage.gotoPaymentsOrRequisites(false);
        IncomesPage.addNewPaymentRequisities();
        IncomesPage.selectCountry("Украина");
        IncomesPage.selectPaymentSystem("Payoneer");
        IncomesPage.enterCardNumber("shkindr@mail", DATA_NOT_MEET_REQUIREMENTS);
        IncomesPage.enterCardNumber("shkindr.mail", DATA_NOT_MEET_REQUIREMENTS);
        IncomesPage.enterCardNumber("shkindr@mail.c", DATA_NOT_MEET_REQUIREMENTS);
        String mail = "shkindr" + ((int) (Math.random() * 10000)) +"@mail.com";
        IncomesPage.enterCardNumber(mail);
        IncomesPage.click2saveRequisites("Payoneer", US_DOLLAR, mail);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void editingOfPaymentRequisitesTest() {
        start();

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.INCOME);
        IncomesPage.gotoPaymentsOrRequisites(false);
        IncomesPage.verifyRequisitesMoreThanOne();
        IncomesPage.editRequisites(0);
        IncomesPage.enterCardNumber("1234567812341231111", DATA_NOT_MEET_REQUIREMENTS);
//        String number = "12345678" + ((long)(Math.random() * Long.valueOf(100000000)));
        String number = IncomesPage.getRandomeNumber();
        IncomesPage.enterCardNumber(number);
        IncomesPage.click2saveRequisites(VISA_MASTERCARD_MAESTRO,US_DOLLAR, number);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void deletingOfPaymentRequisiteTest() {
        start();

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.INCOME);
        IncomesPage.gotoPaymentsOrRequisites(false);
        IncomesPage.verifyRequisitesMoreThanOne();
        IncomesPage.deletingOfRequisite(0, false);
        IncomesPage.deletingOfRequisite(0, true);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void changeOfCurrentRequisitesTest() {
        start();

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.INCOME);
        IncomesPage.gotoPaymentsOrRequisites(false);
        IncomesPage.verifyRequisitesMoreThanOne();
        IncomesPage.setCurrentRequisite(0);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void suspensionOfPaymentGenerationTest() {
        start();

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.INCOME);
        IncomesPage.gotoPaymentsOrRequisites(true);
        IncomesPage.setCheckboxTemporarilySuspendFormationOfPayments(true);
        IncomesPage.verifySuspendingPaymentsOnCurrentRevenue(true);
        IncomesPage.click2PaymentsLink();
        IncomesPage.setCheckboxTemporarilySuspendFormationOfPayments(false);
        IncomesPage.verifySuspendingPaymentsOnCurrentRevenue(false);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void calculationOfTotalRevenueThroughChannelsInPaymentTest() {
        start();

        LoginPage.login(LoginPage.UNIY_OBOZREVATEL, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.INCOME);
        IncomesPage.gotoPaymentsOrRequisites(true);
        IncomesPage.click2PaymentByNumber(0);
        float sumFromAdvertisiment = IncomesPage.getSumFromValue(REVENUE_FROM_ADVERTISING);
        float totalRevenueFromAdvertisiment = IncomesPage.getTotalRevenueFromValue(REVENUE_FROM_ADVERTISING);
        IncomesPage.verifyEqualityOfSums(sumFromAdvertisiment, totalRevenueFromAdvertisiment);
        float sumFromYoutubeRed = IncomesPage.getSumFromValue(REVENUE_FROM_YOUTUBE_RED);
        float totalRevenueFromYoutubeRed = IncomesPage.getTotalRevenueFromValue(REVENUE_FROM_YOUTUBE_RED);
        IncomesPage.verifyEqualityOfSums(sumFromYoutubeRed, totalRevenueFromYoutubeRed);
        Navigation.logout();

        Report.finishTest();
    }


    public void start() {
        Report.startTest(getClass().getName() + "." + testName.getMethodName(), testName.toString(), getClass().getPackage().getName());
    }


}
