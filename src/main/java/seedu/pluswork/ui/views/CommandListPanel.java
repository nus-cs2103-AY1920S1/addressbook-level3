package seedu.pluswork.ui.views;

import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.pluswork.commons.Keywords;
import seedu.pluswork.commons.core.LogsCenter;
import seedu.pluswork.ui.HelpCard;
import seedu.pluswork.ui.MainWindow;
import seedu.pluswork.ui.UiPart;

/**
 * Panel containing the list of commands.
 * Called by {@code UserViewUpdate} when user executes {@code help}.
 */
public class CommandListPanel extends UiPart<Region> {
    private static final String FXML = "CommandListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandListPanel.class);

    @FXML
    private ListView<String> commandListView;

    public CommandListPanel(ObservableList<String> commandList) {
        super(FXML);
        commandListView.setItems(commandList);
        commandListView.setCellFactory(listView -> new CommandListViewCell());
        commandListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Your action here
                System.out.println("new value: " + newValue.toString());
                System.out.println("liza: " + getRoot().toString());
                MainWindow.updateCommandBox(Keywords.getParameters(newValue));
                // commandBox.setCommandText(Keywords.getParameters(newValue));
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Command} using a {@code HelpCard}.
     */
    class CommandListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String command, boolean empty) {
            super.updateItem(command, empty);

            if (empty || command == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HelpCard(command, getIndex() + 1).getRoot());
            }
        }
    }

}

