package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.tag.Tag;
import seedu.address.ui.exceptions.InvalidResultViewTypeException;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private VBox resultHolder;

    @FXML
    private Label resultDisplay;

    @FXML
    private StackPane resultView;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    /**
     * Handles the result by assigning the appropriate nodes to the result display.
     */
    public <T> void handleResult(ResultViewType resultViewType, ObservableList<T> resultContent) {
        resultView.setManaged(true);
        resultView.setVisible(true);
        switch (resultViewType) {
        case TEXT:
            resultView.setManaged(false);
            resultView.setVisible(false);
            break;
        case TAG:
            ObservableList<Tag> tagContent = (ObservableList<Tag>) resultContent;
            TagListPanel tagListPanel = new TagListPanel(tagContent);
            setResultView(tagListPanel.getRoot());
            break;
        case MODULE:
            ObservableList<Module> moduleContent = (ObservableList<Module>) resultContent;
            ModuleListPanel moduleListPanel = new ModuleListPanel(moduleContent);
            setResultView(moduleListPanel.getRoot());
            break;
        case SEMESTER:
            ObservableList<Semester> studyPlanContent = (ObservableList<Semester>) resultContent;
            SimpleSemesterListPanel simpleSemesterListPanel = new SimpleSemesterListPanel(studyPlanContent);
            setResultView(simpleSemesterListPanel.getRoot());
            break;
        default:
            throw new InvalidResultViewTypeException(resultViewType.name());
        }
    }

    private void setResultView(Node view) {
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
