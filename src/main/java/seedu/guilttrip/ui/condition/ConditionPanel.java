package seedu.guilttrip.ui.condition;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.ui.UiPart;

/**
 * Side Panel for Conditions
 */
public class ConditionPanel extends UiPart<Region> {
    private static final String FXML = "condition/ConditionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ConditionPanel.class);

    @javafx.fxml.FXML
    private ListView<Condition> conditionListView;

    public ConditionPanel(ObservableList<Condition> conditionsList) {
        super(FXML);
        conditionListView.setItems(conditionsList);
        conditionListView.setCellFactory(listView -> new ConditionPanel.ConditionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Condition} using a {@code ConditionCard}.
     */
    class ConditionListViewCell extends ListCell<Condition> {
        @Override
        protected void updateItem(Condition condition, boolean empty) {
            super.updateItem(condition, empty);

            if (empty || condition == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ConditionCard(condition, getIndex() + 1).getRoot());
            }
        }
    }
}
