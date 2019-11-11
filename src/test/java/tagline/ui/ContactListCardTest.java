//@@author tanlk99
package tagline.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.testfx.util.NodeQueryUtils.hasText;
import static tagline.testutil.contact.TypicalContacts.BENSON_WITH_DESCRIPTION;
import static tagline.testutil.contact.TypicalContacts.BENSON_WITH_MISSING_FIELDS;
import static tagline.ui.GuiTestUtil.getChildNode;
import static tagline.ui.GuiTestUtil.hasChildNode;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.stage.Stage;
import tagline.model.contact.Contact;
import tagline.ui.contact.ContactListCard;

@ExtendWith(ApplicationExtension.class)
public class ContactListCardTest {
    private GuiTestController controller = GuiTestController.getInstance();
    private ContactListCard contactListCard;

    @Start
    private void setUp(Stage stage) {
        controller.initStageStyle(stage);
        stage.setWidth(500); //for human-viewable results
    }

    private void setContactDisplayed(Contact contact) throws TimeoutException {
        contactListCard = new ContactListCard(contact);
        controller.initSceneRoot(contactListCard.getRoot());
    }

    @Stop
    private void tearDown() throws TimeoutException {
        controller.doTearDown();
    }

    @AfterEach
    private void pause(FxRobot robot) {
        controller.pause(robot);
    }

    @Test
    public void checkFieldsDisplayedCorrectly_allFieldsPresent(FxRobot robot) throws TimeoutException {
        setContactDisplayed(BENSON_WITH_DESCRIPTION);

        FxAssert.verifyThat(getChildNode(robot, "#name"),
                hasText(BENSON_WITH_DESCRIPTION.getName().fullName));
        FxAssert.verifyThat(getChildNode(robot, "#id"),
                hasText("ID: " + BENSON_WITH_DESCRIPTION.getContactId()));
        FxAssert.verifyThat(getChildNode(robot, "#phone"),
                hasText(BENSON_WITH_DESCRIPTION.getPhone().value));
        FxAssert.verifyThat(getChildNode(robot, "#address"),
                hasText(BENSON_WITH_DESCRIPTION.getAddress().value));
        FxAssert.verifyThat(getChildNode(robot, "#email"),
                hasText(BENSON_WITH_DESCRIPTION.getEmail().value));
        FxAssert.verifyThat(getChildNode(robot, "#description"),
                hasText(BENSON_WITH_DESCRIPTION.getDescription().value));
    }

    @Test
    public void checkFieldsDisplayedCorrectly_someOptionalFieldsMissing(FxRobot robot) throws TimeoutException {
        setContactDisplayed(BENSON_WITH_MISSING_FIELDS);

        FxAssert.verifyThat(getChildNode(robot, "#name"),
                hasText(BENSON_WITH_MISSING_FIELDS.getName().fullName));
        FxAssert.verifyThat(getChildNode(robot, "#id"),
                hasText("ID: " + BENSON_WITH_MISSING_FIELDS.getContactId()));
        FxAssert.verifyThat(getChildNode(robot, "#address"),
                hasText(BENSON_WITH_MISSING_FIELDS.getAddress().value));

        assertFalse(hasChildNode(robot, "#email"));
        assertFalse(hasChildNode(robot, "#phone"));
        assertFalse(hasChildNode(robot, "#description"));
    }
}
