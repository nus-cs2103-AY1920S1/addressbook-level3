package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.file.EncryptedFile;

/**
 * Panel containing the list of persons.
 */
public class FileListPanel extends UiPart<Region> {
    private static final String FXML = "FileListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FileListPanel.class);

    @FXML
    private ListView<EncryptedFile> fileListView;

    public FileListPanel(ObservableList<EncryptedFile> fileList) {
        super(FXML);
        fileListView.setItems(fileList);
        fileListView.setCellFactory(listView -> new FileListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code EncryptedFile} using a {@code FileCard}.
     */
    class FileListViewCell extends ListCell<EncryptedFile> {
        @Override
        protected void updateItem(EncryptedFile file, boolean empty) {
            super.updateItem(file, empty);

            if (empty || file == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FileCard(file, getIndex() + 1).getRoot());
            }
        }
    }

}
