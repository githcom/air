package Pages;

import Helpers.Browser;
import Helpers.General;
import Helpers.Report;
import Helpers.ThreadWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ConversationsPage {

    public static final String MUSIC_LIBRARY = "Музыкальная библиотека";
    public static final String MESSAGE_CONVERSATION = "Message description";

    public static void createNewConversation() {
        Browser.runCommand("$('button.btn-new.btn-default.pull-right')[0].click()");
        Report.verifyValue("createNewConversation: ", true, Browser.waitForIsVisible("conversation-add-edit-modal-dialog", 10, true));
        Report.verifyValue("createNewConversation: verify create button is disabled", true, Browser.waitForIsVisible("$('button#saveButton.btn-cons.btn-primary.disabled')", 10, true));
    }

    public static void setCategory(String category) {
        Browser.runCommand("$('div.category-block span.btn-default.form-control.ui-select-toggle')[0].click()");
        Browser.sendKeys("$(\"input[placeholder='Выберите категорию']\")", category);
        Browser.getElement("$(\"input[placeholder='Выберите категорию']\")").sendKeys(Keys.ENTER);
        Report.verifyValue("setCategory: input should contains category " + category, "1", Browser.returnRunCommand("$(\"input[placeholder='Выберите категорию']\").val('" + category + "').length"));
        Report.verifyValue("setCategory: verify create button is disabled", true, Browser.waitForIsVisible("$('button#saveButton.btn-cons.btn-primary.disabled')", 10, true));
    }

    public static void setSubject(String subject) {
        Browser.sendKeys("$(\"input[name='subject']\")", subject);
        Report.verifyValue("setSubject: input should contains subject " + subject, true, Browser.waitForIsVisible("$(\"input[name='subject']\").val('" + subject + "')", 10, true));
        Report.verifyValue("setSubject: verify create button is disabled", true, Browser.waitForIsVisible("$('button#saveButton.btn-cons.btn-primary.disabled')", 10, true));
    }

    public static String getSubject() {
        return "new subject_" + (int) (Math.random() * 10000);
    }

    public static void setDescription(String description) {
        Browser.sendKeys("$('div.form-block.ng-scope div.note-editable.panel-body')", description);
        Browser.sleep(2000);
        Report.verifyValue("setDescription: input should contains subject " + description, true, Browser.waitForIsVisible("$('div.form-block.ng-scope div.note-editable.panel-body').val('" + description + "')", 10, true));
        Report.verifyValue("setDescription: verify create button is enabled", true, Browser.waitForIsVisible("$(\"button#saveButton[class='btn btn-cons margin-0 ng-binding btn-primary']\")", 10, true));
    }

    public static void click2CreateConversation(String subject, String description, String category, String author, boolean isManager, boolean isPriority, boolean isResponsible) {
        if (isPriority) {
            Browser.runCommand("$('div.contaner-flag.arrow-bottom-tool.show-popup i.fa.fa-flag')[0].click()");
        }
        if (isResponsible) {
            Browser.runCommand("$(\"label[for='checkbox-take-ownership']\")[0].click()");
        }
//        Report.verifyValue("click2CreateConversation: before click ", true, Browser.waitForIsVisible("$(\"button#saveButton[class='btn btn-cons margin-0 ng-binding btn-primary']\")", 10, true));
        Report.verifyValue("click2CreateConversation: before click ", true, Browser.waitForIsVisible("saveButton", 10, true));
//        Browser.sleep(1000);
//        Browser.runCommand("$(\"button#saveButton[class='btn btn-cons margin-0 ng-binding btn-primary']\")[0].click()");
//        Browser.click("$(\"button#saveButton[class='btn btn-cons margin-0 ng-binding btn-primary']\")");
        Browser.click("saveButton");
        if (isManager) {
            gotoActiveConversation(subject);
            chooseConversationBySubject(subject);
            Report.verifyValue("click2CreateConversations: verify conversation is in Opened ", true, Browser.waitForIsVisible("$(\"span:contains('Активные')\").closest('li.active-menu')", 10, true));
            Report.verifyValue("click2CreateConversation: partner ", true, Browser.waitForIsVisible("$(\"span.name-attribute.ng-binding:contains('" + author + "')\")", 10, true));
        } else {
            gotoOpenedOrClosedConversations(true, subject);
            chooseConversationBySubject(subject);
            Report.verifyValue("click2CreateConversations: verify conversation is in Opened ", true, Browser.waitForIsVisible("$(\"span:contains('Открытые')\").closest('li.active-menu')", 10, true));
        }
        if (isPriority) {
            Report.verifyValue("click2CreateConversation: verify flag 1 ", true, Browser.waitForIsVisible("$('div.contaner-flag.active.def-cursor.arrow-bottom-tool.show-popup i.fa.fa-flag')", 10, true));
            Report.verifyValue("click2CreateConversation: verify flag 2 ", true, Browser.waitForIsVisible("$(\"li.item.message-container.ng-scope.selected i.fa.fa-flag[aria-hidden='true']\")", 10, true));
        }
        if (isResponsible) {
            Report.verifyValue("click2CreateConversation: verify button close is displayed ", true, Browser.waitForIsVisible("$(\"button.btn-complete.btn-cons span.ng-scope:contains('Закрыть')\")", 10, true));
            Report.verifyValue("click2CreateConversation: verify responsible ", true, Browser.waitForIsVisible("$(\"span.name-index.ng-binding:contains('" + author + "')\")", 10, true));
        }
        Report.verifyValue("click2CreateConversation: wait for subject element ", true, Browser.waitForIsVisible("$('div.title-header div.name.ng-binding')", 10, true));
        Report.verifyValue("click2CreateConversations: verify subject " + subject, subject, Browser.returnRunCommand("$('div.title-header div.name.ng-binding').text()"));
        Report.verifyValue("click2CreateConversations: wait for description element ", true, Browser.waitForIsVisible("$('p.conversation-content-text.ng-binding')", 10, true));
        Report.verifyValue("click2CreateConversations: verify description " + description, description, Browser.returnRunCommand("$('p.conversation-content-text.ng-binding').text()"));
        Report.verifyValue("click2CreateConversations: wait for category element ", true, Browser.waitForIsVisible("$('div.сonversation-item-category.last span.ng-binding')", 10, true));
        Report.verifyValue("click2CreateConversations: verify category " + category, true, Browser.returnRunCommand("$('div.сonversation-item-category.last span.ng-binding').text().trim()").contains(category));
        Report.verifyValue("click2CreateConversation: author ", true, Browser.waitForIsVisible("$(\"div.block-attribute.clear_after span.value.ng-binding:contains('" + author + "')\")", 10, true));
    }

    public static void closeCreationOfNewConversation() {
        Browser.runCommand("$(\"div#conversation-add-edit-modal-dialog button.close[aria-hidden='true']\")[0].click()");
        Report.verifyValue("closeCreationOfNewConversation: ", true, Browser.waitForIsVisible("conversation-add-edit-modal-dialog", 5, false));
    }

    public static void gotoOpenedOrClosedConversations(boolean isOpenedOrClosed, String subject) {
//        int res = isOpenedOrClosed ? 0 : 1;
//        String locator = isOpenedOrClosed ? "$(\"span:contains('Открытые')\").parent().parent()" : "$(\"span:contains('Закрытые')\").parent().parent()";
//        String locator = isOpenedOrClosed ? "//span[contains(text(),'Открытые')]" : "//span[contains(text(),'Закрытые')]";
//        String locator = isOpenedOrClosed ? "$(\"li div[ng-click*='showActiveConversations()'] a span.ng-binding.ng-scope\")" : "$(\"li div[ng-click*='showClosedConversations()'] a  span.ng-binding.ng-scope\")";
        String locator = isOpenedOrClosed ? "$(\"li.no-drop.m-b-10.ng-scope div[ng-click*='showActiveConversations()'] a\")" : "$(\"li.no-drop.ng-scope div[ng-click*='showClosedConversations()'] a\")";
//        String locator = isOpenedOrClosed ? "$(\"div[ng-click*='showActiveConversations()'] span.ng-binding.ng-scope\")" : "$(\"div[ng-click*='showClosedConversations()'] span.ng-binding.ng-scope\")";
//        Report.verifyValue("gotoOpenedOrClosedConversations: before click ", true, Browser.waitForIsVisible("$(\"li.no-drop.ng-scope:eq(" + res + ") a\")", 10, true));
        Report.verifyValue("gotoOpenedOrClosedConversations: before click ", true, Browser.waitForIsVisible(locator, 10, true));
//        Browser.runCommand("$(\"li.no-drop.ng-scope:eq(" + res + ") a\")[0].click()");
//        Browser.click(locator);
//        Browser.sleep(1000);

//        Browser.runCommand(locator + "[0].click()");
//        Browser.refreshPage();
//        Browser.sleep(1500);
//        Actions action = new Actions(ThreadWebDriver.get());
//        action.moveToElement(Browser.getElement(locator)).doubleClick().build().perform();

//        Browser.runCommand(locator + "[0].click()");

        while (!Browser.waitForIsVisible("$(\"li.item.message-container.ng-scope:contains('" + subject + "')\")", 5, true)) {
            Browser.runCommand(locator + "[0].click()");
            Browser.sleep(500);
        }
//        Browser.runCommand(locator + "[0].click()");
//        Browser.runCommand(locator + ".trigger('click')");
//        Report.verifyValue("gotoOpenedOrClosedConversations: after click ", true, Browser.waitForIsVisible("$(\"li.no-drop.ng-scope:eq(" + res + ")[class*='active-menu'] a\")", 10, true));

        Report.verifyValue("gotoOpenedOrClosedConversations: after click ", true, Browser.waitForIsVisible(locator + ".closest('li.active-menu')", 10, true));
//        Report.verifyValue("gotoOpenedOrClosedConversations: after click ", true, Browser.waitForIsVisible(locator + ".attr('class', 'active-menu')", 10, true));
    }

    public static void gotoActiveConversation(String subject) {
        String locator = "$(\"span:contains('Активные')\")";
        Report.verifyValue("gotoActiveConversation: before click", true, Browser.waitForIsVisible(locator, 10, true));
//        Browser.runCommand(locator + "[0].click()");
//        Browser.sleep(1000);
//        Browser.runCommand(locator + "[0].click()");
        while (!Browser.waitForIsVisible("$(\"li.item.message-container.ng-scope:contains('" + subject + "')\")", 3, true)) {
            Browser.runCommand(locator + "[0].click()");
            Browser.sleep(500);
        }
        Report.verifyValue("gotoActiveConversation: after click ", true, Browser.waitForIsVisible(locator + ".closest('li.active-menu')", 10, true));
    }

    public static void click2EditMessage(String message) {
        Browser.runCommand("$(\"img[src*='/edit.png']\")[0].click()");
        Report.verifyValue("editMessage: ", true, Browser.waitForIsVisible("$(\"div.note-editable.panel-body:contains('" + message + "')\")", 5, true));
    }

    public static void enterMessage(String message) {
        Report.verifyValue("enterMessage: before send keys ", true, Browser.waitForIsVisible("$(\"form#messageEditor div.note-editable.panel-body\")", 5, true));
        Browser.sendKeys("$(\"form#messageEditor div.note-editable.panel-body\")", message);
        Browser.sleep(2000);
        Report.verifyValue("editMessage: verify value after click button", true, Browser.waitForIsVisible("$(\"form#messageEditor div.note-editable.panel-body\").val('" + message + "')", 10, true));
    }

    public static void click2SubmitMessage(String message) {
//        Report.verifyValue("click2SubmitMessage: before click ", true, Browser.waitForIsVisible("$(\"button#btnSend\")", 5, true));
        Report.verifyValue("click2SubmitMessage: before click ", true, Browser.waitForIsVisible("btnSend", 5, true));
//        Browser.runCommand("$(\"button#btnSend\")[0].click()");
        Browser.click("btnSend");
//        Browser.sleep(2000);
        Report.verifyValue("editMessage: verify value after click button", true, Browser.waitForIsVisible("$(\"p[id*='conversation-content-text-']:contains('" + message + "')\")", 15, true));
    }

    public static void editMessage(String message, String newMessage) {
        click2EditMessage(message);
        enterMessage(newMessage);
        click2SubmitMessage(newMessage);
    }

    public static void submitMessage(String message) {
        enterMessage(message);
        click2SubmitMessage(message);
    }

    public static void chooseConversationBuNumber(int numberOfConversation) {
        Report.verifyValue("chooseConversationBuNumber: ", true, Browser.waitForIsVisible("$(\"li.item.message-container.ng-scope:eq(" + numberOfConversation + ")\")", 10, true));
        Browser.runCommand("$(\"li.item.message-container.ng-scope\")[" + numberOfConversation + "].click()");
//        Browser.sleep(1500);
        Report.verifyValue("chooseConversationBuNumber: ", "item message-container ng-scope selected", Browser.returnRunCommand("$(\"li.item.message-container.ng-scope:eq(" + numberOfConversation + ")\").attr('class')"));
    }

    public static void chooseConversationBySubject(String subject) {
//        Browser.refreshPage();
        Report.verifyValue("chooseConversationBySubject: before click ", true, Browser.waitForIsVisible("$(\"li.item.message-container.ng-scope:contains('" + subject + "')\")", 10, true));
        Browser.runCommand("$(\"li.item.message-container.ng-scope:contains('" + subject + "')\")[0].click()");
//        Browser.click("$(\"li.item.message-container.ng-scope:contains('" + subject + "')\")");
//        Browser.sleep(1500);
        Report.verifyValue("chooseConversationBySubject: after click ", true, Browser.waitForIsVisible("$(\"li.item.message-container.ng-scope.selected:contains('" + subject + "')\")", 10, true));
    }

    public static int getNumberOfConversations(boolean isOpenedOrClosed) {
        int res = isOpenedOrClosed ? 0 : 1;
//        Browser.sleep(1500);
        Report.verifyValue("getNumberOfConversations: before get text ", true, Browser.waitForIsVisible("$(\"li.no-drop.ng-scope:eq(" + res + ") div.number-messeng.ng-binding\")", 10, true));
        return Integer.parseInt(Browser.returnRunCommand("$(\"li.no-drop.ng-scope:eq(" + res + ") div.number-messeng.ng-binding\").text()"));
    }

    public static void verifyNumberOfOpenedConversationsIncreased(int numberOfOpened, int numberOfClosed) {
//        Browser.refreshPage();
        Browser.refreshPage();
//        Browser.sleep(3000);
        Report.verifyValue("verifyNumberOfOpenedConversationsIncreased: opened ", true, Browser.waitForIsVisible("$(\"li.no-drop.ng-scope:eq(0) div.number-messeng.ng-binding:contains('" + (numberOfOpened + 1) + "')\")", 10, true));
        Report.verifyValue("verifyNumberOfOpenedConversationsIncreased: closed ", true, Browser.waitForIsVisible("$(\"li.no-drop.ng-scope:eq(1) div.number-messeng.ng-binding:contains('" + (numberOfClosed - 1) + "')\")", 10, true));
        Report.verifyValue("verifyNumberOfOpenedConversationsIncreased: opened + 1 ", numberOfOpened + 1, ConversationsPage.getNumberOfConversations(true));
        Report.verifyValue("verifyNumberOfOpenedConversationsIncreased: closed - 1 ", numberOfClosed - 1, ConversationsPage.getNumberOfConversations(false));
    }

    public static void takeToWork() {
        Report.verifyValue("takeToWork: before click ", true, Browser.waitForIsVisible("$('button.btn-primary.close-mesage')", 10, true));
        Browser.runCommand("$('button.btn-primary.close-mesage')[0].click()");
        Report.verifyValue("takeToWork: after click ", true, Browser.waitForIsVisible("$('button.btn-primary.close-mesage')", 10, false));
        String userName = General.getUserName();
        Report.verifyValue("takeToWork: verify responsible ", true, Browser.waitForIsVisible("$(\"span.name-index.ng-binding:contains('" + userName + "')\")", 10, true));
        Report.verifyValue("takeToWork: ", true, Browser.waitForIsVisible("messageEditor", 10, true));
    }

    public static void moveConversationToClosed(String subject) {
        String locator = "$('button.btn-complete')";
        Report.verifyValue("moveConversationToClosed: before click ", true, Browser.waitForIsVisible(locator, 10, true));
//        Report.verifyValue("moveConversationToClosed: before click ", true, Browser.waitForIsVisible("//button[contains(@class,'btn-complete')]", 10, true));
//        Browser.runCommand(locator + "[0].click()");
        Browser.click(locator);
//        Browser.click(locator);
//        Browser.click("//button[contains(@class,'btn-complete')]");
        Report.verifyValue("moveConversationToClosed: after click ", true, Browser.waitForIsVisible(locator, 10, false));
//        Report.verifyValue("moveConversationToClosed: after click ", true, Browser.waitForIsVisible("//button[contains(@class,'btn-complete')]", 10, false));
        Report.verifyValue("moveConversationToClosed: verify conversation is not displayed", true, Browser.waitForIsVisible("$(\"li.item.message-container.ng-scope:contains('" + subject + "')\")", 10, false));
    }

    public static void enterSecondMessage(String message) {
        ConversationsPage.enterMessage(message);
        ConversationsPage.click2SubmitMessage(message);
        Report.verifyValue("enterSecondMessage: verify second message", true, Browser.waitForIsVisible("$(\"p[id*='conversation-content-text-']:eq(1):contains('" + message + "')\")", 10, true));
        Report.verifyValue("enterSecondMessage: verify two messages exist", "2", Browser.returnRunCommand("$(\"p[id*='conversation-content-text-']\").length"));
        Report.verifyValue("enterSecondMessage: verify two delete buttons exist ", "2", Browser.returnRunCommand("$(\"[id*='btn-delete-message']\").length"));

    }

    public static void deleteMessage(int number) {
        Report.verifyValue("deleteMessage: before click number " + number, true, Browser.waitForIsVisible("$(\"[id*='btn-delete-message']:eq(" + number + ") img\")", 10, true));
        Browser.runCommand("$(\"[id*='btn-delete-message']:eq(" + number + ") img\")[0].click()");
        Report.verifyValue("deleteMessage: ", true, Browser.waitForIsVisible("$(\"button[ng-click*='delete']\")", 10, true));
        Browser.runCommand("$(\"button[ng-click*='delete']\")[0].click()");
    }

    public static void deleteFirstMessageAndVerify(int messageNumber, String secondMessage) {
        deleteMessage(messageNumber);
        Report.verifyValue("deleteFirstMessage: verify second message become first", true, Browser.waitForIsVisible("$(\"p[id*='conversation-content-text-']:eq(0):contains('" + secondMessage + "')\")", 10, true));
        Report.verifyValue("deleteFirstMessage: verify only one messages exist", "1", Browser.returnRunCommand("$(\"p[id*='conversation-content-text-']\").length"));
        Report.verifyValue("deleteFirstMessage: verify no one buttons exist ", "0", Browser.returnRunCommand("$(\"[id*='btn-delete-message']\").length"));
    }

    public static void setPartner(String partner) {
        Report.verifyValue("setPartner: ", true, Browser.waitForIsVisible("$(\"div.ui-select-match:contains('Укажите партнера') i.caret.pull-right\")", 10, true));
        Browser.runCommand("$(\"div.ui-select-match:contains('Укажите партнера') i.caret.pull-right\")[0].click()");
        Report.verifyValue("setPartner: ", true, Browser.waitForIsVisible("$(\"input[placeholder='Укажите партнера']\")", 10, true));
//        Report.verifyValue("setPartner: ", true, Browser.waitForIsVisible("$(\"div[ng-click='clearSelectedPartner()']\")", 10, true));
        Browser.sendKeys("$(\"input[placeholder='Укажите партнера']\")", partner);
        Browser.sleep(2000);
//        Browser.sendKeys("$(\"div[ng-click='clearSelectedPartner()']\")", partner);
        Browser.getElement("$(\"input[placeholder='Укажите партнера']\")").sendKeys(Keys.ENTER);
        Report.verifyValue("setPartner: input should contains partner " + partner, "1", Browser.returnRunCommand("$(\"input[placeholder='Укажите партнера']\").val('" + partner + "').length"));
//        Report.verifyValue("setPartner: input should contains partner " + partner, "1", Browser.returnRunCommand("$(\"div[ng-click='clearSelectedPartner()']\").val('" + partner + "').length"));
    }

    public static void setResponsibleAfterCreation(String responsible) {
        String locator = "$(\"a.select2-choice.ui-select-match\")";
        Report.verifyValue("setResponsibleAfterCreation: before click ", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.runCommand(locator + "[0].click()");
//        locator = "$(\"input.ui-select-search.select2-input.ng-pristine.ng-valid.ng-touched\")";
//        locator = "$(\"input[aria-owns='ui-select-choices-7']\")";
        locator = "$(\"input.select2-input[aria-owns*='ui-select-choices-']\")";
        Report.verifyValue("setResponsibleAfterCreation", true, Browser.waitForIsVisible(locator, 10, true));
        Browser.sendKeys(locator, responsible);
        Browser.getElement(locator).sendKeys(Keys.ENTER);
        Report.verifyValue("click2CreateConversation: verify responsible ", true, Browser.waitForIsVisible("$(\"span.name-index.ng-binding:contains('" + responsible + "')\")", 10, true));
    }


    public static void findConversation(String conversation) {
        Browser.sendKeys("search-сonversation", conversation);
        Browser.getElement("search-сonversation").sendKeys(Keys.ENTER);
    }

    public static void removeAllFilters() {
        Browser.runCommand("$('#status-closed-link.filters-rmove')[0].click()");
    }

    public static void gotoClosed() {
        Browser.runCommand("$('#status-closed-link')[0].click()");
    }

    public static void addObserver() {
//        String locator = "btn-add-remove-watcher";
        String locator = "$('button#btn-add-remove-watcher')";
        Report.verifyValue("addObserver: before click ", true, Browser.waitForIsVisible(locator, 10, true));
//        Report.verifyValue("addObserver: before click ", true, Browser.waitForIsVisible("$('button#btn-add-remove-watcher')", 10, true));
//        Browser.click("btn-add-remove-watcher");
        Browser.runCommand(locator + "[0].click()");
//        Browser.click(locator);
        Report.verifyValue("addObserver: after click ", true, Browser.waitForIsVisible("$(\"div.dropdown-toggle.ng-binding.add-bg:contains('1')\")", 10, true));
    }

    public static void deleteObserver() {
        Report.verifyValue("deleteObserver: before click ", true, Browser.waitForIsVisible("btn-add-remove-watcher", 10, true));
        Browser.click("btn-add-remove-watcher");
        Report.verifyValue("deleteObserver: after click ", true, Browser.waitForIsVisible("$(\"div.dropdown-toggle.ng-binding:contains('0')\")", 10, true));
    }

}
