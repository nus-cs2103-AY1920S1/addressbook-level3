package seedu.exercise.guihandlers;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import seedu.exercise.ui.ResultDisplay;

/**
 * Handle for {@link ResultDisplay}.
 */
public class ResultDisplayHandle extends NodeHandle<TextArea> {


    public ResultDisplayHandle(TextArea rootNode) {
        super(rootNode);
    }

    public String getResultDisplayText() {
        return getRootNode().getText();
    }

    /**
     * Attempts to type key specified by {@code keyCode}.
     */
    public void enterLetter(KeyCode keyCode) {
        click();
        guiRobot.type(keyCode);
        guiRobot.pauseForHuman();
    }
}

