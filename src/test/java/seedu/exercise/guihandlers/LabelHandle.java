package seedu.exercise.guihandlers;

import javafx.scene.control.Label;

/**
 * Handle for any type of {@link Label} type JavaFX node
 */
public class LabelHandle extends NodeHandle<Label> {


    public LabelHandle(Label rootNode) {
        super(rootNode);
    }

    public String getLabelText() {
        return getRootNode().getText();
    }
}
