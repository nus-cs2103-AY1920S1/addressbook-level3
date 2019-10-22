package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private VBox resultHolder;

    @FXML
    private TextArea resultDisplay;

    @FXML
    private StackPane resultView;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    public void setResultView(Node view) {
        view.getStyleClass().add("resultView");
        resultView.getChildren().add(view);
    }

    /**
     * Removes previous views.
     */
    public void removeResultView() {
        int size = resultView.getChildren().size();
        for (int i = 0; i < size; i++) {
            resultView.getChildren().remove(i);
        }
    }

}
