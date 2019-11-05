package seedu.algobase.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;

/**
 * Panel containing the list of find rules.
 */
public class FindRuleListPanel extends UiPart<Region> {

    private static final String FXML = "FindRuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FindRuleListPanel.class);
    @FXML
    private ListView<ProblemSearchRule> findRuleListView;

    public FindRuleListPanel(ObservableList<ProblemSearchRule> findRuleList) {
        super(FXML);
        findRuleListView.setItems(findRuleList);
        findRuleListView.setCellFactory(listView -> new FindRuleListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ProblemSearchRule}
     * using a {@code FindRuleCard}.
     */
    static class FindRuleListViewCell extends ListCell<ProblemSearchRule> {
        @Override
        protected void updateItem(ProblemSearchRule item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FindRuleCard(item, getIndex() + 1).getRoot());
            }
        }
    }
}
