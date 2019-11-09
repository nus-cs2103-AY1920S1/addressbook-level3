package seedu.address.ui.modules;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
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
    private LoadBankPanelDisplayExecuteCallBack importCommandExecutor;
    private WordBankCard.WordBankCardExecuteCallBack exportCommandExecutor;
    private String dataFilePath;

    @FXML
    private ListView<WordBank> loadBankView;

    public LoadBankPanel(ObservableList<WordBank> wbl) {
        super(FXML);
        loadBankView.setItems(wbl);
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
                WordBankCard wbc = new WordBankCard(wordBank, getIndex() + 1);
                setGraphic(wbc.getRoot());
                wbc.registerDragAndDropCallBack(exportCommandExecutor);
                wbc.initialiseFilePath(dataFilePath);
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
        boolean isCompleted = false;

        if (firstFile.exists() && getExtension(firstFile).equals("json")) {
            Path p = firstFile.toPath();
            String childString = p.getFileName().toString();
            String wordBankName = childString.substring(0, childString.length() - ".json".length());

            try {
                importCommandExecutor.execute("import w/" + wordBankName
                        + " f/" + p.getParent().toString());
            } catch (CommandException | ParseException e) {
                e.printStackTrace();
            }
            isCompleted = true;
        }

        event.setDropCompleted(isCompleted);
        event.consume();
    }

    /**
     * Retrieves the extension of the file.
     *
     * @param file
     * @return the extension of the file.
     */
    private String getExtension(File file) {
        String fileName = file.toString();
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) { //if the name is not empty
            return fileName.substring(i + 1).toLowerCase();
        }
        return extension;
    }

    /**
     * Removes the extension.
     *
     * @param file
     * @return the name of the file without the extension.
     */
    private String getFileNameWithoutExtension(File file) {
        String fileName = file.toString();
        String ext = getExtension(file);
        return fileName.substring(0, fileName.length() - ext.length());
    }

    public void initialiseFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    /**
     * Registers a method that will be called by the ModularDisplay to simulate an Import or Export command as though
     * it were a user.
     *
     * @param importCommandExecutor for import.
     * @param exportCommandExecutor for export.
     */
    public void registerDragAndDropCallBack(LoadBankPanel.LoadBankPanelDisplayExecuteCallBack importCommandExecutor,
                                     WordBankCard.WordBankCardExecuteCallBack exportCommandExecutor) {
        requireAllNonNull(importCommandExecutor, exportCommandExecutor);
        this.importCommandExecutor = importCommandExecutor;
        this.exportCommandExecutor = exportCommandExecutor;
    }
    /**
     * Call-back functional interface from ModularDisplay to MainWindow, represents the ModularDisplay sending
     * a command to the app as though it were another user.
     */
    @FunctionalInterface
    public interface LoadBankPanelDisplayExecuteCallBack {
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
