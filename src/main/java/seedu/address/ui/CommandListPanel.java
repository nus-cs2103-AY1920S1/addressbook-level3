package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.FXCollections;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.CommandRecord;

/**
 * Panel containing the list of Commands entered by User.
 */
public class CommandListPanel extends UiPart<Region> {
    private static final String FXML = "ListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandListPanel.class);

    @FXML
    private ListView<CommandRecord> listView;
    @FXML
    private VBox panelContainer;


    public CommandListPanel(ArrayList<CommandRecord> commandRecords) {
        super(FXML);
        listView.setItems(FXCollections.observableArrayList(commandRecords));
        listView.setCellFactory(listView -> new CommandListViewCell());


    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code CommandRecord} using a {@code CommandRecordCard}.
     */
    class CommandListViewCell extends ListCell<CommandRecord> {
        @Override
        protected void updateItem(CommandRecord commandRecord, boolean isEmpty) {
            super.updateItem(commandRecord, isEmpty);

            if (isEmpty || commandRecord == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CommandRecordCard(commandRecord).getRoot());
            }
        }
    }


}



