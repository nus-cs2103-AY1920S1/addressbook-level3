package budgetbuddy.ui.tab;

import budgetbuddy.model.rule.Rule;
import budgetbuddy.ui.panel.RulePanel;
import javafx.collections.ObservableList;

/**
 * Represents a tab component that displays the rule panel when selected.
 */
public class RuleTab extends PanelTab {

    public RuleTab(ObservableList<Rule> ruleList) {
        super(new RulePanel(ruleList), "Rule");
    }
}
