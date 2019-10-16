package seedu.address.ui.layouts;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import seedu.address.ui.UiPart;

public class TwoSplitRowLayout extends UiPart<Node> {

    private static final String FXML = "TwoSplitRowLayout.fxml";

    @FXML
    private StackPane topPane;

    @FXML
    private StackPane bottomPane;

    public TwoSplitRowLayout() {
        super(FXML);
    }

    /**
     * Adds node to the top pane of the layout.
     *
     * @param uiPartToAdd The {@code Node} to add.
     */
    public void addToTopPane(Node uiPartToAdd) {
        topPane.getChildren().clear();
        topPane.getChildren().add(uiPartToAdd);
    }

    /**
     * Adds node to the bottom pane of the layout.
     *
     * @param uiPartToAdd The {@code Node} to add.
     */
    public void addToBottomPane(Node uiPartToAdd) {
        bottomPane.getChildren().clear();
        bottomPane.getChildren().add(uiPartToAdd);
    }

}
