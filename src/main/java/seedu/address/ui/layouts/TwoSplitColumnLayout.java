package seedu.address.ui.layouts;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import seedu.address.ui.UiPart;

/**
 * Layout class to use in {@code ModularDisplay} split vertically.
 */
public class TwoSplitColumnLayout extends UiPart<Node> {

    private static final String FXML = "TwoSplitColumnLayout.fxml";

    @FXML
    private StackPane leftPane;

    @FXML
    private StackPane rightPane;

    public TwoSplitColumnLayout() {
        super(FXML);
    }

    /**
     * Adds node to the left pane of the layout.
     *
     * @param uiPartToAdd The {@code Node} to add.
     */
    public void addToLeftPane(Node uiPartToAdd) {
        leftPane.getChildren().clear();
        leftPane.getChildren().add(uiPartToAdd);
    }

    /**
     * Adds node to the right pane of the layout.
     *
     * @param uiPartToAdd The {@code Node} to add.
     */
    public void addToRightPane(Node uiPartToAdd) {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(uiPartToAdd);
    }

}
