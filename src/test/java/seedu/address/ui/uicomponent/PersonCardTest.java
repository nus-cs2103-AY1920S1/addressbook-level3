package seedu.address.ui.uicomponent;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.PersonCard;


@ExtendWith(ApplicationExtension.class)
public class PersonCardTest {
    private static final String NAME_ID = "#name";
    private static final String INITIALS_ID = "#personId .label";
    private Person alice = TypicalPersons.ALICE;

    @AfterEach
    public void pause(FxRobot robot) {
        robot.sleep(500);
    }

    @Stop
    void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Initialise person card test with a stage.
     * @param stage
     */
    @Start
    public void start(Stage stage) {
        Parent sceneRoot = new PersonCard(new PersonDisplay(alice)).getRoot();
        Scene scene = new Scene(sceneRoot);
        scene.getStylesheets().add("/view/DarkTheme.css");
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void nameIsDisplayedCorrectly() {
        verifyThat(NAME_ID, hasText(alice.getName().fullName));
    }

    @Test
    public void initialsDisplayedCorrectly() {
        verifyThat(INITIALS_ID, hasText(PersonCard.getPersonInitials(alice.getName().fullName)));
    }
}
