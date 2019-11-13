package budgetbuddy.ui.panel;

import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.model.script.Script;
import budgetbuddy.ui.card.ScriptCard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * Panel containing the list of scripts.
 */
public class ScriptPanel extends DisplayPanel {
    private static final String FXML = "ScriptPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ScriptPanel.class);

    @FXML
    private ListView<Script> scriptListView;

    public ScriptPanel(ObservableList<Script> scriptList) {
        super(FXML);
        scriptListView.setItems(scriptList);
        scriptListView.setCellFactory(listView -> new ScriptListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Account} using a {@code AccountCard}.
     */
    static class ScriptListViewCell extends ListCell<Script> {
        @Override
        protected void updateItem(Script script, boolean empty) {
            super.updateItem(script, empty);

            if (empty || script == null) {
                setGraphic(null);
                setText(null);
            } else {
                setMouseTransparent(true);
                setFocusTraversable(false);
                setGraphic(new ScriptCard(script).getRoot());
            }
        }
    }

}
