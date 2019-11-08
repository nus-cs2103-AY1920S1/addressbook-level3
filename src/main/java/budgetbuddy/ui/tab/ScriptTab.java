package budgetbuddy.ui.tab;

import budgetbuddy.model.script.Script;
import budgetbuddy.ui.panel.ScriptPanel;
import javafx.collections.ObservableList;

/**
 * Represents a tab component that displays the script panel when selected.
 */
public class ScriptTab extends PanelTab {

    public ScriptTab(ObservableList<Script> scriptList) {
        super(new ScriptPanel(scriptList), "Script");
    }
}
