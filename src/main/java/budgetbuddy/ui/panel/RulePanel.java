package budgetbuddy.ui.panel;

import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.model.rule.Rule;
import budgetbuddy.ui.card.RuleCard;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * Panel containing the list of rules.
 */
public class RulePanel extends DisplayPanel {
    private static final String FXML = "RulePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RulePanel.class);

    @FXML
    private ListView<Rule> ruleListView;

    public RulePanel(ObservableList<Rule> ruleList) {
        super(FXML);
        ruleListView.setItems(ruleList);
        ruleListView.setCellFactory(listView -> new RuleListViewCell());

        // check for changes
        ruleList.addListener((ListChangeListener<Rule>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    ruleListView.scrollTo(c.getFrom());
                }
            }
        });
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
                setMouseTransparent(true);
                setFocusTraversable(false);
            }
        }
    }
}
