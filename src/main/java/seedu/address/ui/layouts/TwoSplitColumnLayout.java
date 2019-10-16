package seedu.address.ui.layouts;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import seedu.address.ui.UiPart;

import javax.swing.plaf.synth.Region;

public class TwoSplitColumnLayout extends UiPart<Node> {

    private static final String FXML = "TwoSplitColumnLayout.fxml";

    @FXML
    private StackPane leftPane;

    @FXML
    private StackPane rightPane;

    public TwoSplitColumnLayout() {
        super(FXML);
    }

    public void addToLeftPane(Node uiPartToAdd) {
        leftPane.getChildren().clear();
        leftPane.getChildren().add(uiPartToAdd);
    }

    public void addToRightPane(Node uiPartToAdd) {
        rightPane.getChildren().clear();
        rightPane.getChildren().add(uiPartToAdd);
    }

}
