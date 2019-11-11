package seedu.exercise.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.ui.testutil.GuiAssert.assertNodeType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import seedu.exercise.guihandlers.StackPanePlaceholderHandle;
import seedu.exercise.testutil.CommonTestData;
import seedu.exercise.testutil.typicalutil.TypicalExercises;
import seedu.exercise.testutil.typicalutil.TypicalRegime;
import seedu.exercise.testutil.typicalutil.TypicalSchedule;
import seedu.exercise.ui.testutil.GuiUnitTest;

public class InfoDisplayPanelTest extends GuiUnitTest {

    private InfoDisplayPanel infoDisplayPanel;
    private StackPanePlaceholderHandle stackPanePlaceholderHandle;

    @BeforeEach
    private void setUp() {
        infoDisplayPanel = new InfoDisplayPanel();
        stackPanePlaceholderHandle = new StackPanePlaceholderHandle(
                getChildNode(infoDisplayPanel.getRoot(), CommonTestData.INFO_PANEL_STACK_PANE_ID));

        uiPartExtension.setUiPart(infoDisplayPanel);
    }

    @Test
    public void showDefaultMessage_correctMessageShown() {
        guiRobot.interact(() -> infoDisplayPanel.showDefaultMessage());
        assertTopNodeType(Label.class, stackPanePlaceholderHandle);

        assertEquals(InfoDisplayPanel.DEFAULT_MESSAGE, ((Label) getTopNode(stackPanePlaceholderHandle)).getText());
    }

    @Test
    public void update_withExercise_correctlyAdded() {
        guiRobot.interact(() -> infoDisplayPanel.update(TypicalExercises.AEROBICS));
        guiRobot.pauseForHuman();
        assertTopNodeType(AnchorPane.class, stackPanePlaceholderHandle);

        guiRobot.interact(() -> infoDisplayPanel.update(TypicalRegime.VALID_REGIME_CARDIO));
        guiRobot.pauseForHuman();
        assertTopNodeType(AnchorPane.class, stackPanePlaceholderHandle);

        guiRobot.interact(() -> infoDisplayPanel.update(TypicalSchedule.VALID_SCHEDULE_CARDIO_DATE));
        assertTopNodeType(AnchorPane.class, stackPanePlaceholderHandle);
    }


    private void assertTopNodeType(Class clazz, StackPanePlaceholderHandle displayPanel) {
        Node current = getTopNode(displayPanel);
        assertNodeType(clazz, current);
    }

    private Node getTopNode(StackPanePlaceholderHandle displayPanelHandle) {
        return displayPanelHandle.getChildren().get(0);
    }
}
