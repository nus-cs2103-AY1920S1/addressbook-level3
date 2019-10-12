package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.cheatsheet.CheatSheet;


import java.util.logging.Logger;

public class CheatSheetListPanel extends UiPart<Region> {
    private static final String FXML = "CheatSheetListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CheatSheetListPanel.class);

    @javafx.fxml.FXML
    private ListView<CheatSheet> cheatSheetListView;

    public CheatSheetListPanel(ObservableList<CheatSheet> cheatSheetList) {
        super(FXML);
        cheatSheetListView.setItems(cheatSheetList);
        cheatSheetListView.setCellFactory(listView -> new CheatSheetListPanel.CheatSheetListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class CheatSheetListViewCell extends ListCell<CheatSheet> {
        @Override
        protected void updateItem(CheatSheet cheatSheet, boolean empty) {
            super.updateItem(cheatSheet, empty);

            if (empty || cheatSheet == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CheatSheetCard(cheatSheet, getIndex() + 1).getRoot());
            }
        }
    }

}
