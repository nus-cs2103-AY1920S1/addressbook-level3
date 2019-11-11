package seedu.address.ui.uicomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.schedule.PersonInformationDisplay;

@ExtendWith(ApplicationExtension.class)
public class PersonInformationDisplayTest extends ApplicationTest {
    private static final Person ALICE = TypicalPersons.ALICE;
    private static final Person BENSON = TypicalPersons.BENSON;
    private static final String cardId = "#personInformationContainer";
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
        Parent sceneRoot = new PersonInformationDisplay(new PersonDisplay(ALICE)).getRoot();
        Scene scene = new Scene(sceneRoot);
        scene.getStylesheets().add("/view/DarkTheme.css");
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void detailsCorrectlyDisplayed() {
        //Name displayed correctly.
        verifyThat(cardId + " #name", hasText(ALICE.getName().fullName));
        //Phone displayed correctly.
        verifyThat(cardId + " #phone", hasText(ALICE.getPhone().value));
        //Address displayed correctly.
        verifyThat(cardId + " #address", hasText(ALICE.getAddress().value));
        //Email displayed correctly.
        verifyThat(cardId + " #email", hasText(ALICE.getEmail().value));
        //Remark displayed correctly.
        verifyThat(cardId + " #remark", hasText(ALICE.getRemark().value));

    }

    @Test
    public void equalTest() {
        //Same object.
        PersonInformationDisplay aliceCard = new PersonInformationDisplay(new PersonDisplay(ALICE));
        PersonInformationDisplay aliceCardDuplicate = new PersonInformationDisplay(new PersonDisplay(ALICE));
        PersonInformationDisplay bensonCard = new PersonInformationDisplay(new PersonDisplay(BENSON));
        assertEquals(aliceCard, aliceCard);
        //Different object but same field.
        assertEquals(aliceCard, aliceCardDuplicate);
        //Different object and different field.
        assertNotEquals(aliceCard, bensonCard);
    }
}
