package Sanity;

import Helpers.Config;
import Helpers.Report;
import Init.InitTest;
import Pages.ConversationsPage;
import Pages.LoginPage;
import Pages.Navigation;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class ConversationPartnerTest extends InitTest {

    private String subject = Config.getSubjectProperty();
    private final static String authorEng = "Oleksandr Shkinder";

    @Rule
    public TestName testName = new TestName();

    @Test
    public void a3in1CreateConversationByPartnerTest() {
        start();

        //1 test CreateConversationByPartnerTest
        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        ConversationsPage.createNewConversation();
        ConversationsPage.setCategory(ConversationsPage.MUSIC_LIBRARY);
        subject = ConversationsPage.getSubject();
        Config.setSubjectProperty(subject);
        ConversationsPage.setSubject(subject);
        ConversationsPage.setDescription(ConversationsPage.MESSAGE_CONVERSATION);
        ConversationsPage.click2CreateConversation(subject, ConversationsPage.MESSAGE_CONVERSATION, ConversationsPage.MUSIC_LIBRARY, LoginPage.ALEKSANDR_SHKINDER, false, false, false);
        Navigation.logout();

        // 2 test MoveConversationToClosedByManagerTest()
        LoginPage.login(LoginPage.MANAGER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        ConversationsPage.gotoActiveConversation(subject);
        ConversationsPage.chooseConversationBySubject(subject);
        ConversationsPage.takeToWork();
        ConversationsPage.moveConversationToClosed(subject);
        Navigation.logout();

        //3 test ReopeningOfClosedConversationsByPartnerTest
        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        ConversationsPage.gotoOpenedOrClosedConversations(false, subject);
        ConversationsPage.chooseConversationBySubject(subject);
        int numberOfOpened = ConversationsPage.getNumberOfConversations(true);
        int numberOfClosed = ConversationsPage.getNumberOfConversations(false);
        ConversationsPage.submitMessage("new message_" + (int) (Math.random()* 10000));
        ConversationsPage.verifyNumberOfOpenedConversationsIncreased(numberOfOpened, numberOfClosed);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void d4EditingUnreadMessageByPartnerTest() {
        start();

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        ConversationsPage.createNewConversation();
        ConversationsPage.setCategory(ConversationsPage.MUSIC_LIBRARY);
//        String subject = "new subject_" + (int) (Math.random() * 10000);
        subject = ConversationsPage.getSubject();
        Config.setSubjectProperty(subject);
        ConversationsPage.setSubject(subject);
        ConversationsPage.setDescription(ConversationsPage.MESSAGE_CONVERSATION);
        ConversationsPage.click2CreateConversation(subject, ConversationsPage.MESSAGE_CONVERSATION, ConversationsPage.MUSIC_LIBRARY, LoginPage.ALEKSANDR_SHKINDER, false, false, false);
        String newMessage = "Message description new string";
        ConversationsPage.editMessage(ConversationsPage.MESSAGE_CONVERSATION, newMessage);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void e5DeletingOfUnreadMessageByPartnerTest() {
        start();

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        ConversationsPage.createNewConversation();
        ConversationsPage.setCategory(ConversationsPage.MUSIC_LIBRARY);
//        String subject = "new subject_" + (int) (Math.random() * 10000);
        subject = ConversationsPage.getSubject();
        Config.setSubjectProperty(subject);
        ConversationsPage.setSubject(subject);
        ConversationsPage.setDescription(ConversationsPage.MESSAGE_CONVERSATION);
        ConversationsPage.click2CreateConversation(subject, ConversationsPage.MESSAGE_CONVERSATION, ConversationsPage.MUSIC_LIBRARY, LoginPage.ALEKSANDR_SHKINDER, false, false, false);
        String secondMessage = "second message_" + (int) (Math.random() * 10000);
        ConversationsPage.enterSecondMessage(secondMessage);
        ConversationsPage.deleteMessage(0);
        ConversationsPage.deleteFirstMessageAndVerify(0, secondMessage);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void f6CancelCreationOfConversationByPartnerTest() {
        start();

        LoginPage.login(LoginPage.PARTNER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        ConversationsPage.createNewConversation();
        ConversationsPage.closeCreationOfNewConversation();
        Navigation.logout();

        Report.finishTest();
    }

    public void start() {
        Report.startTest(getClass().getName() + "." + testName.getMethodName(), testName.toString(), getClass().getPackage().getName());
    }

}
