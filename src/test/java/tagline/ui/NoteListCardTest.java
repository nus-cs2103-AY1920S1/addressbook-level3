//@@author tanlk99
package tagline.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.util.NodeQueryUtils.hasText;
import static tagline.testutil.note.TypicalNotes.EARTH;
import static tagline.testutil.note.TypicalNotes.EARTH_NO_TITLE;
import static tagline.ui.GuiTestUtil.getChildNode;
import static tagline.ui.GuiTestUtil.getChildNodes;
import static tagline.ui.note.NoteListCard.UNTITLED_NOTE_STRING;

import java.util.Collection;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import tagline.model.note.Note;
import tagline.model.tag.Tag;
import tagline.ui.note.NoteListCard;

@ExtendWith(ApplicationExtension.class)
public class NoteListCardTest {
    private GuiTestController controller = GuiTestController.getInstance();
    private NoteListCard noteListCard;

    @Start
    private void setUp(Stage stage) {
        GuiTestController.getInstance().initStageStyle(stage);
        stage.setWidth(500); //for human-viewable results
    }

    @Stop
    private void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    private void setNoteDisplayed(Note note) throws TimeoutException {
        noteListCard = new NoteListCard(note);
        FxToolkit.setupSceneRoot(() -> noteListCard.getRoot());
        FxToolkit.showStage();
    }

    @AfterEach
    private void pause(FxRobot robot) {
        controller.pause(robot);
    }

    @Test
    public void checkFieldsDisplayedCorrectly_titlePresent(FxRobot robot) throws TimeoutException {
        setNoteDisplayed(EARTH);

        FxAssert.verifyThat(getChildNode(robot, "#title"), hasText(EARTH.getTitle().value));
        FxAssert.verifyThat(getChildNode(robot, "#time"), hasText(EARTH.getTimeCreated().toString()));
        FxAssert.verifyThat(getChildNode(robot, "#content"), hasText(EARTH.getContent().value));
        FxAssert.verifyThat(getChildNode(robot, "#id"), hasText("#" + EARTH.getNoteId().value));

        assertEquals(EARTH.getTags().size(), getChildNodes(robot, "#tags > .label").size());
        Collection<String> tagStrings = getChildNodes(robot, "#tags > .label").stream()
                .map(label -> ((Label) label).getText()).sorted().collect(Collectors.toList());

        Collection<String> expectedTagStrings = EARTH.getTags().stream()
                .map(Tag::toString).sorted().collect(Collectors.toList());

        assertEquals(expectedTagStrings, tagStrings);
    }

    @Test
    public void checkFieldsDisplayedCorrectly_titleAbsent(FxRobot robot) throws TimeoutException {
        setNoteDisplayed(EARTH_NO_TITLE);

        FxAssert.verifyThat(getChildNode(robot, "#title"), hasText(UNTITLED_NOTE_STRING));
        FxAssert.verifyThat(getChildNode(robot, "#time"),
                hasText(EARTH_NO_TITLE.getTimeCreated().toString()));
        FxAssert.verifyThat(getChildNode(robot, "#content"),
                hasText(EARTH_NO_TITLE.getContent().value));
        FxAssert.verifyThat(getChildNode(robot, "#id"),
                hasText("#" + EARTH_NO_TITLE.getNoteId().value));

        assertEquals(EARTH_NO_TITLE.getTags().size(), getChildNodes(robot, "#tags > .label").size());
        Collection<String> tagStrings = getChildNodes(robot, "#tags > .label").stream()
                .map(label -> ((Label) label).getText()).sorted().collect(Collectors.toList());

        Collection<String> expectedTagStrings = EARTH_NO_TITLE.getTags().stream()
                .map(Tag::toString).sorted().collect(Collectors.toList());

        assertEquals(expectedTagStrings, tagStrings);

    }
}
