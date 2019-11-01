package budgetbuddy.ui.tab;

import budgetbuddy.model.script.Script;
import budgetbuddy.ui.panel.ScriptListPanel;
import javafx.collections.ObservableList;

/**
 * Represents a tab component that displays the script panel when selected.
 */
public class ScriptTab extends PanelTab {

    public ScriptTab(ObservableList<Script> scriptList) {
        super(new ScriptListPanel(scriptList), "/images/scriptTab.png");
    }
}
