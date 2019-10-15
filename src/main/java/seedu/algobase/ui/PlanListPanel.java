package seedu.algobase.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.model.plan.Plan;


/**
 * Panel containing the list of plans.
 */
public class PlanListPanel extends UiPart<Region> {
    private static final String FXML = "PlanListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PlanListPanel.class);

    @FXML
    private ListView<Plan> planListView;

    public PlanListPanel(ObservableList<Plan> planList) {
        super(FXML);
        planListView.setItems(planList);
        planListView.setCellFactory(listView -> new PlanListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Plan} using a {@code PlanCard}.
     */
    class PlanListViewCell extends ListCell<Plan> {
        @Override
        protected void updateItem(Plan plan, boolean empty) {
            super.updateItem(plan, empty);

            if (empty || plan == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PlanCard(plan, getIndex() + 1).getRoot());
            }
        }
    }

}
