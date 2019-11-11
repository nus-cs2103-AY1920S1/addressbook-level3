package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the command list history.
 */
public class CommandListPanel extends UiPart<Region> {
    private static final String FXML = "CommandListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandListPanel.class);

    @FXML
    private ListView<String> commandListView;

    public CommandListPanel(ObservableList<String> commandList) {
        super(FXML);
        commandListView.setItems(commandList);
        commandListView.setCellFactory(listView -> new CommandListPanel.CommandListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code String} using a {@code CommandCard}.
     */
    class CommandListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String string, boolean empty) {
            super.updateItem(string, empty);

            if (empty || string == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CommandCard(string, getIndex() + 1).getRoot());
            }
        }
    }
}

