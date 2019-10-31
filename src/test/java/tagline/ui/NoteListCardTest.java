package tagline.ui;

import static org.testfx.util.NodeQueryUtils.hasText;
import static tagline.ui.NoteListCard.UNTITLED_NOTE_STRING;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tagline.model.note.Note;
import tagline.testutil.note.TypicalNotes;

@ExtendWith(ApplicationExtension.class)
public class NoteListCardTest {
    private NoteListCard noteListCard;

    /**
     * Sets up the stage style. Can only be done once per testing session.
     */
    private void initStage(Stage stage) {
        if (stage.getStyle() != StageStyle.DECORATED) {
            stage.initStyle(StageStyle.DECORATED);
        }
    }

    @Start
    void setUp(Stage stage) {
        initStage(stage);
        stage.setWidth(500); //for human-viewable results
    }

    @Stop
    void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    private void setNoteDisplayed(Note note) throws TimeoutException {
        noteListCard = new NoteListCard(note);
        FxToolkit.setupSceneRoot(() -> noteListCard.getRoot());
        FxToolkit.showStage();
    }

    @AfterEach
    void pause(FxRobot robot) {
        String headlessPropertyValue = System.getProperty("testfx.headless");
        if (headlessPropertyValue != null && headlessPropertyValue.equals("true")) {
            return;
        }

        robot.sleep(500);
    }

    private Node getChildNode(FxRobot robot, String id) {
        return robot.lookup(id).query();
    }

    @Test
    void checkFieldsDisplayedCorrectly_titlePresent(FxRobot robot) throws TimeoutException {
        setNoteDisplayed(TypicalNotes.EARTH);

        FxAssert.verifyThat(getChildNode(robot, "#title"), hasText(TypicalNotes.EARTH.getTitle().titleDescription));
        FxAssert.verifyThat(getChildNode(robot, "#time"), hasText(TypicalNotes.EARTH.getTimeCreated().toString()));
        FxAssert.verifyThat(getChildNode(robot, "#content"), hasText(TypicalNotes.EARTH.getContent().value));
        FxAssert.verifyThat(getChildNode(robot, "#id"), hasText("#" + TypicalNotes.EARTH.getNoteId().value));
    }

    @Test
    void checkFieldsDisplayedCorrectly_titleAbsent(FxRobot robot) throws TimeoutException {
        setNoteDisplayed(TypicalNotes.EARTH_NO_TITLE);

        FxAssert.verifyThat(getChildNode(robot, "#title"), hasText(UNTITLED_NOTE_STRING));
        FxAssert.verifyThat(getChildNode(robot, "#time"),
                hasText(TypicalNotes.EARTH_NO_TITLE.getTimeCreated().toString()));
        FxAssert.verifyThat(getChildNode(robot, "#content"),
                hasText(TypicalNotes.EARTH_NO_TITLE.getContent().value));
        FxAssert.verifyThat(getChildNode(robot, "#id"),
                hasText("#" + TypicalNotes.EARTH_NO_TITLE.getNoteId().value));
    }
}
