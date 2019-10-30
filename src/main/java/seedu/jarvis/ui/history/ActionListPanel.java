package seedu.jarvis.ui.history;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import seedu.jarvis.logic.Logic;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.model.Model;
import seedu.jarvis.ui.MainWindow;
import seedu.jarvis.ui.template.View;


/**
 * Panel containing the list of actions.
 */
public class ActionListPanel extends View<AnchorPane> {
    private static final String FXML = "ActionListPanel.fxml";

    @FXML
    private ListView<Command> commandListView;

    public ActionListPanel(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    @Override
    public void fillPage() {
        commandListView.setItems(model.getExecutedCommandsList());
        commandListView.setCellFactory(listView -> new CommandListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Command} using a {@code ActionCard}.
     */
    class CommandListViewCell extends ListCell<Command> {
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
