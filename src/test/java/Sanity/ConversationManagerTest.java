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

public class ConversationManagerTest extends InitTest {

    private String subject = Config.getSubjectProperty();
//    private final static String author = "Oleksandr Shkinder";
    private final static String author = "Александр Шкиндер";

    @Rule
    public TestName testName = new TestName();

    @Test
    public void a1CreateConversationByManagerTest() {
        start();

        LoginPage.login(LoginPage.MANAGER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        ConversationsPage.createNewConversation();
        ConversationsPage.setPartner(LoginPage.AUTHOR_ID);
        ConversationsPage.setCategory(ConversationsPage.MUSIC_LIBRARY);
        subject = ConversationsPage.getSubject();
        Config.setSubjectProperty(subject);
        ConversationsPage.setSubject(subject);
        ConversationsPage.setDescription(ConversationsPage.MESSAGE_CONVERSATION);
        ConversationsPage.click2CreateConversation(subject, ConversationsPage.MESSAGE_CONVERSATION, ConversationsPage.MUSIC_LIBRARY, author, true, false, false);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void b2CreatePriorityConversationByManagerTest() {
        start();

        LoginPage.login(LoginPage.MANAGER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        ConversationsPage.createNewConversation();
        ConversationsPage.setPartner(LoginPage.AUTHOR_ID);
        ConversationsPage.setCategory(ConversationsPage.MUSIC_LIBRARY);
        subject = ConversationsPage.getSubject();
        Config.setSubjectProperty(subject);
        ConversationsPage.setSubject(subject);
        ConversationsPage.setDescription(ConversationsPage.MESSAGE_CONVERSATION);
        ConversationsPage.click2CreateConversation(subject, ConversationsPage.MESSAGE_CONVERSATION, ConversationsPage.MUSIC_LIBRARY, author, true, true, false);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void c3CreateMessageWithAppointmentManagerResponsible() {
        start();

        LoginPage.login(LoginPage.MANAGER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        ConversationsPage.createNewConversation();
        ConversationsPage.setPartner(LoginPage.AUTHOR_ID);
        ConversationsPage.setCategory(ConversationsPage.MUSIC_LIBRARY);
        subject = ConversationsPage.getSubject();
        Config.setSubjectProperty(subject);
        ConversationsPage.setSubject(subject);
        ConversationsPage.setDescription(ConversationsPage.MESSAGE_CONVERSATION);
        ConversationsPage.click2CreateConversation(subject, ConversationsPage.MESSAGE_CONVERSATION, ConversationsPage.MUSIC_LIBRARY, author, true, true, true);
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void d4AssignmentResponsibleManagerForConversation() {
        start();

        LoginPage.login(LoginPage.MANAGER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        ConversationsPage.createNewConversation();
        ConversationsPage.setPartner(LoginPage.AUTHOR_ID);
        ConversationsPage.setCategory(ConversationsPage.MUSIC_LIBRARY);
        subject = ConversationsPage.getSubject();
        Config.setSubjectProperty(subject);
        ConversationsPage.setSubject(subject);
        ConversationsPage.setDescription(ConversationsPage.MESSAGE_CONVERSATION);
        ConversationsPage.click2CreateConversation(subject, ConversationsPage.MESSAGE_CONVERSATION, ConversationsPage.MUSIC_LIBRARY, author, true, false, false);
        ConversationsPage.setResponsibleAfterCreation("Шкиндер");
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void e5ObserverForConversation() {
        start();

        LoginPage.login(LoginPage.MANAGER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        ConversationsPage.createNewConversation();
        ConversationsPage.setPartner(LoginPage.AUTHOR_ID);
        ConversationsPage.setCategory(ConversationsPage.MUSIC_LIBRARY);
        subject = ConversationsPage.getSubject();
        Config.setSubjectProperty(subject);
        ConversationsPage.setSubject(subject);
        ConversationsPage.setDescription(ConversationsPage.MESSAGE_CONVERSATION);
        ConversationsPage.click2CreateConversation(subject, ConversationsPage.MESSAGE_CONVERSATION, ConversationsPage.MUSIC_LIBRARY, author, true, false, false);
        ConversationsPage.addObserver();
        ConversationsPage.deleteObserver();
        Navigation.logout();

        Report.finishTest();
    }

    @Test
    public void f6TakingConversationToWork() {
        start();

        LoginPage.login(LoginPage.MANAGER_LOGIN, LoginPage.PSW);
        Navigation.nav2MenuItem(Navigation.CONVERSATIONS);
        ConversationsPage.createNewConversation();
        ConversationsPage.setPartner(LoginPage.AUTHOR_ID);
        ConversationsPage.setCategory(ConversationsPage.MUSIC_LIBRARY);
        subject = ConversationsPage.getSubject();
        Config.setSubjectProperty(subject);
        ConversationsPage.setSubject(subject);
        ConversationsPage.setDescription(ConversationsPage.MESSAGE_CONVERSATION);
        ConversationsPage.click2CreateConversation(subject, ConversationsPage.MESSAGE_CONVERSATION, ConversationsPage.MUSIC_LIBRARY, author, true, false, false);
        ConversationsPage.takeToWork();
        Navigation.logout();

        Report.finishTest();
    }





    public void start() {
        Report.startTest(getClass().getName() + "." + testName.getMethodName(), testName.toString(), getClass().getPackage().getName());
    }



}
