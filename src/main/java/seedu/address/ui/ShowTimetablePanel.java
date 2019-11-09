package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.finance.Budget;
import seedu.address.model.timetable.TimetableVisualization;
import seedu.address.model.timetable.Timetable;

import java.util.logging.Logger;



/**
 * Panel containing the list of projects.
 */
public class ShowTimetablePanel extends UiPart<Region> {
    private static final String FXML = "ShowTimetablePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ShowTimetablePanel.class);

    @FXML
    private Label title;

    public ShowTimetablePanel(Timetable timetable) {
        super(FXML);
        title.setText(new TimetableVisualization(timetable).visualize());
        title.setStyle("-fx-font-family: 'monospaced';");
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Budget} using a {@code BudgetPieChart}.
     */
    class BudgetListViewCell extends ListCell<Budget> {
        @Override
        protected void updateItem(Budget budget, boolean empty) {
            super.updateItem(budget, empty);

            if (empty || budget == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BudgetPieChart(budget, getIndex() + 1).getRoot());
            }
        }
    }

}
