package budgetbuddy.ui;

import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.model.rule.Rule;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of rules.
 */
public class RuleListPanel extends UiPart<Region> {
    private static final String FXML = "RuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RuleListPanel.class);

    @FXML
    private ListView<Rule> ruleListView;

    public RuleListPanel(ObservableList<Rule> ruleList) {
        super(FXML);
        ruleListView.setItems(ruleList);
        ruleListView.setCellFactory(listView -> new RuleListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Rule} using a {@code RuleCard}.
     */
    class RuleListViewCell extends ListCell<Rule> {
        @Override
        protected void updateItem(Rule rule, boolean empty) {
            super.updateItem(rule, empty);

            if (empty || rule == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RuleCard(rule, getIndex() + 1).getRoot());
            }
        }
    }

}
