package seedu.jarvis.ui.history;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.ui.UiPart;



/**
 * Panel containing the list of actions.
 */
public class ActionListPanel extends UiPart<Region> {
    private static final String FXML = "ActionListPanel.fxml";

    @FXML
    private ListView<Command> commandListView;

    public ActionListPanel(ObservableList<Command> commands) {
        super(FXML);
        commandListView.setItems(commands);
        commandListView.setCellFactory(listView -> new ActionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Command} using a {@code ActionCard}.
     */
    class ActionListViewCell extends ListCell<Command> {
        @Override
        protected void updateItem(Command command, boolean empty) {
            super.updateItem(command, empty);

            if (empty || command == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ActionCard(command, getIndex() + 1).getRoot());
            }
        }
    }
}
