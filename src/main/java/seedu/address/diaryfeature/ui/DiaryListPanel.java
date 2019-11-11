package seedu.address.diaryfeature.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class DiaryListPanel extends UiPart<Region> {
    private static final String FXML = "DiaryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DiaryListPanel.class);

    @FXML
    private ListView<DiaryEntry> diaryListView;

    public DiaryListPanel(ObservableList<DiaryEntry> entries) {
        super(FXML);
        diaryListView.setItems(entries);
        diaryListView.setCellFactory(listView -> new DiaryListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class DiaryListViewCell extends ListCell<DiaryEntry> {
        @Override
        protected void updateItem(DiaryEntry entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DiaryEntryCard(entry, getIndex() + 1).getRoot());
            }
        }
    }

}
