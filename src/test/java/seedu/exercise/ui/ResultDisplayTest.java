package seedu.exercise.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.testutil.CommonTestData.RESULT_DISPLAY_FIELD_ID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.input.KeyCode;
import seedu.exercise.guihandlers.ResultDisplayHandle;
import seedu.exercise.ui.testutil.GuiUnitTest;

public class ResultDisplayTest extends GuiUnitTest {

    private static final String FEEDBACK_MESSAGE = "feedback";

    private ResultDisplayHandle displayHandle;
    private ResultDisplay resultDisplay;

    @BeforeEach
    private void setUp() {
        resultDisplay = new ResultDisplay();
        displayHandle = new ResultDisplayHandle(
                getChildNode(resultDisplay.getRoot(), RESULT_DISPLAY_FIELD_ID));
        uiPartExtension.setUiPart(resultDisplay);
    }

    @Test
    public void feedback_feedbackShown() {
        resultDisplay.setFeedbackToUser(FEEDBACK_MESSAGE);
        assertEquals(FEEDBACK_MESSAGE, displayHandle.getResultDisplayText());
    }

    @Test
    public void typeInTextBox_withRobot_nothingChanges() {
        displayHandle.enterLetter(KeyCode.K);
        assertEquals("", displayHandle.getResultDisplayText());
    }

    @Test
    public void clearText_feedbackCleared() {
        resultDisplay.clearText();
        assertEquals("", displayHandle.getResultDisplayText());
    }
}
