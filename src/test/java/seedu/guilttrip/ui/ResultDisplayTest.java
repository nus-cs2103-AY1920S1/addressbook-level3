package seedu.guilttrip.ui;

import org.junit.jupiter.api.BeforeEach;

import seedu.guilttrip.ui.gui.guihandles.ResultDisplayHandle;

public class ResultDisplayTest extends GuiUnitTest {

    private ResultDisplay resultDisplay;
    private ResultDisplayHandle resultDisplayHandle;

    @BeforeEach
    public void setUp() {
        resultDisplay = new ResultDisplay();
        uiPartExtension.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle(getChildNode(resultDisplay.getRoot(),
                ResultDisplayHandle.RESULT_DISPLAY_ID));
    }

    /*@Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", resultDisplayHandle.getText());

        // new result received
        guiRobot.interact(() -> resultDisplay.setFeedbackToUser("Dummy feedback to user"));
        guiRobot.pauseForHuman();
        assertEquals("Dummy feedback to user", resultDisplayHandle.getText());
    }*/
}
