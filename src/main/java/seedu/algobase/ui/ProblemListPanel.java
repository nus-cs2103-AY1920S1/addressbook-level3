package seedu.algobase.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.model.gui.WriteOnlyTabManager;
import seedu.algobase.model.problem.Problem;

/**
 * Panel containing the list of problems.
 */
public class ProblemListPanel extends UiPart<Region> {
    private static final String FXML = "ProblemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ProblemListPanel.class);

    private final WriteOnlyTabManager writeOnlyTabManager;

    @FXML
    private ListView<Problem> problemListView;

    public ProblemListPanel(ObservableList<Problem> problemList, WriteOnlyTabManager writeOnlyTabManager) {
        super(FXML);
        problemListView.setItems(problemList);
        problemListView.setCellFactory(listView -> new ProblemListViewCell());
        this.writeOnlyTabManager = writeOnlyTabManager;
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
                setGraphic(new ProblemCard(problem, getIndex() + 1, writeOnlyTabManager).getRoot());
            }
        }
    }

}
