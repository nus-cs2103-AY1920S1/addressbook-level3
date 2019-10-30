package seedu.address.ui.uicomponent;

//import static org.testfx.api.FxAssert.verifyThat;
//import static org.testfx.util.NodeQueryUtils.hasText;

//import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.PersonCard;

public class PersonCardTest extends ApplicationTest {
    private static final String NAME_ID = "#name";
    private static final String INITIALS_ID = "#personId .label";
    private Person alice = TypicalPersons.ALICE;

    @Override
    public void start(Stage stage) {
        Parent sceneRoot = new PersonCard(new PersonDisplay(alice)).getRoot();
        Scene scene = new Scene(sceneRoot);
        scene.getStylesheets().add("/view/DarkTheme.css");
        stage.setScene(scene);
        stage.show();
    }

    /* Ignore test cases for now because travis has not been set up for TestFX.
    @Test
    public void nameIsDisplayedCorrectly() {
        verifyThat(NAME_ID, hasText(alice.getName().fullName));
    }

    @Test
    public void initialsDisplayedCorrectly() {
        verifyThat(INITIALS_ID, hasText(PersonCard.getPersonInitials(alice.getName().fullName)));
    }
     */
}
