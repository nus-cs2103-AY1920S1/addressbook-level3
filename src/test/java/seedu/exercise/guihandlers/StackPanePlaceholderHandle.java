package seedu.exercise.guihandlers;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Handle for placeholder {@code StackPane} that will have more children nodes
 */
public class StackPanePlaceholderHandle extends NodeHandle<StackPane> {

    public StackPanePlaceholderHandle(StackPane rootNode) {
        super(rootNode);
    }

    public ObservableList<Node> getChildren() {
        return getRootNode().getChildren();
    }
}
