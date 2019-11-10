package seedu.exercise.guihandlers;

import javafx.scene.control.Button;

/**
 * Handle for common {@code Button} type of JavaFX node.
 */
public class ButtonHandle extends NodeHandle<Button> {

    public ButtonHandle(Button rootNode) {
        super(rootNode);
    }

    /**
     * Clicks on the the root button node and pauses.
     */
    public void click() {
        guiRobot.interact(() -> getRootNode().fire());
        guiRobot.pauseForHuman();
    }
}
