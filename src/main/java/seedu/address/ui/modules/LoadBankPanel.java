package seedu.address.ui.modules;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wordbank.WordBank;
import seedu.address.storage.Storage;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of word banks.
 */
public class LoadBankPanel extends UiPart<Region> {
    private static final String FXML = "LoadBankPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LoadBankPanel.class);
    private Storage storage;

    @FXML
    private ListView<WordBank> loadBankView;

    public LoadBankPanel(Storage storage) {
        super(FXML);
        this.storage = storage;
        loadBankView.setItems(storage.getFilteredWordBankList());
        loadBankView.setCellFactory(listView -> new LoadBankViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code CardCard}.
     */
    class LoadBankViewCell extends ListCell<WordBank> {
        @Override
        protected void updateItem(WordBank wordBank, boolean empty) {
            super.updateItem(wordBank, empty);

            if (empty || wordBank == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WordBankCard(wordBank, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Accepts the file
     *
     * @param event that contains the dragged file.
     */
    @FXML
    public void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    /**
     * Receives the file
     *
     * @param event that contains the dragged file.
     */
    @FXML
    public void handleDragDropped(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        File firstFile = files.get(0);
        if (firstFile.exists() && getExtension(firstFile).equals("json")) {
            Path p = firstFile.toPath();
            String childString = p.getFileName().toString();

            String wordBankName = childString.substring(0, childString.length() - ".json".length());
            try {
                storage.importWordBank(wordBankName, p.getParent());
            } catch (DataConversionException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }
    }

    private String getExtension(File file) {
        String fileName = file.toString();
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) { //if the name is not empty
            return fileName.substring(i + 1).toLowerCase();
        }
        return extension;
    }
}
