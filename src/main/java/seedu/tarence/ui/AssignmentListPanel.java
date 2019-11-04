package seedu.tarence.ui;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.model.tutorial.Assignment;

/**
 * Panel containing the list of assignments.
 */
public class AssignmentListPanel extends UiPart<Region> {
    private static final String FXML = "AssignmentListPanel.fxml";
    private static final String EMPTY_LIST_MESSAGE = "Sorry :( there are no assignments to display";
    private static final String ERROR_LIST_MESSAGE = "An error occured while displaying the list";
    private final Logger logger = LogsCenter.getLogger(AssignmentListPanel.class);

    @FXML
    private ListView<Assignment> assignmentListView;

    @FXML
    private VBox cardPane;

    public AssignmentListPanel() {
        super(FXML);
        cardPane = new VBox();
        cardPane.getChildren().add(getLabel(EMPTY_LIST_MESSAGE));
    }

    /**
     * Generates list based on the existing assignment list and stores into cardpane.
     */
    public void generateList(List<Assignment> assignmentList) {
        requireNonNull(assignmentList);
        cardPane.getChildren().clear();

        if (assignmentList.isEmpty()) {
            cardPane.getChildren().add(getLabel(EMPTY_LIST_MESSAGE));
            return;
        }

        try {
            assignmentListView.setItems(FXCollections.observableArrayList(assignmentList));
            assignmentListView.setCellFactory(listView -> new AssignmentListViewCell());
            cardPane.getChildren().add(assignmentListView);
        } catch (RuntimeException e) {
            cardPane.getChildren().add(getLabel(ERROR_LIST_MESSAGE));
        }
    }

    /**
     * @return Pane with attendance table to display.
     */
    public Pane getPane() {
        return this.cardPane;
    }

    private Label getLabel(String message) {
        return new Label(message);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Assignment} using a {@code AssignmentCard}.
     */
    class AssignmentListViewCell extends ListCell<Assignment> {
        @Override
        protected void updateItem(Assignment assignment, boolean empty) {
            super.updateItem(assignment, empty);

            if (empty || assignment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AssignmentCard(assignment, getIndex() + 1).getRoot());
            }
        }
    }
}
