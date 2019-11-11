package guitests.guihandles;

import javafx.scene.control.Label;

/**
 * A handler for the {@code LevelLabel} of the UI
 */
public class LevelLabelHandle extends NodeHandle<Label> {

    public static final String LEVEL_LABEL_ID = "#levelLabel";

    public LevelLabelHandle(Label levelLabelNode) {
        super(levelLabelNode);
    }

    /**
     * Returns the text in the level label.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
