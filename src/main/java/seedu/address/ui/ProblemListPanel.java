package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Problem.Problem;

import java.util.logging.Logger;

/**
 * Panel containing the list of problems.
 */
public class ProblemListPanel extends UiPart<Region> {
    private static final String FXML = "ProblemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ProblemListPanel.class);

    @FXML
    private ListView<Problem> problemListView;

    public ProblemListPanel(ObservableList<Problem> problemList) {
        super(FXML);
        problemListView.setItems(problemList);
        problemListView.setCellFactory(listView -> new ProblemListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Problem} using a {@code ProblemCard}.
     */
    class ProblemListViewCell extends ListCell<Problem> {
        @Override
        protected void updateItem(Problem problem, boolean empty) {
            super.updateItem(problem, empty);

            if (empty || problem == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ProblemCard(problem, getIndex() + 1).getRoot());
            }
        }
    }

}
