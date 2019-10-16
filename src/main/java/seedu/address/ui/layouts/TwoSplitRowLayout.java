package seedu.address.ui.layouts;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import seedu.address.ui.UiPart;

import javax.swing.plaf.synth.Region;

public class TwoSplitRowLayout extends UiPart<Node> {

    private static final String FXML = "TwoSplitRowLayout.fxml";

    @FXML
    private StackPane topPane;

    @FXML
    private StackPane bottomPane;

    public TwoSplitRowLayout() {
        super(FXML);
    }

    public void addToTopPane(Node uiPartToAdd) {
        topPane.getChildren().clear();
        topPane.getChildren().add(uiPartToAdd);
    }

    public void addToBottomPane(Node uiPartToAdd) {
        bottomPane.getChildren().clear();
        bottomPane.getChildren().add(uiPartToAdd);
    }

}
