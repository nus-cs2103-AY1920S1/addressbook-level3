package seedu.address.ui.modules;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wordbank.WordBank;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Card}.
 */
public class WordBankCard extends UiPart<Region> {

    private static final String FXML = "WordBankCard.fxml";
    public final WordBank wordBank;
    private String dataFilePath;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    @FXML
    private StackPane resultDisplayPlaceholder;

    private WordBankCardExecuteCallBack commandExecutor;

    /**
     * Card containing the details of the word bank.
     *
     * @param wordBank       The card representing its corresponding word bank.
     * @param displayedIndex The index of the word bank.
     */
    WordBankCard(WordBank wordBank, int displayedIndex) {
        super(FXML);
        this.wordBank = wordBank;
        id.setText(displayedIndex + ". ");
        name.setText(wordBank.getName());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WordBankCard)) {
            return false;
        }

        // state check
        WordBankCard wordBankCard = (WordBankCard) other;
        return false;
    }

    /**
     * Drags and drop a word bank file outside of the app.
     *
     * @param event mouse event.
     */
    @FXML
    private void handleDragDetection(MouseEvent event) {
        Dragboard db = cardPane.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        String wordBankPath = dataFilePath + File.separator + "wordBanks"
                + File.separator + wordBank.getName() + ".json";
        File tmpFile = new File(wordBankPath);

        List<File> filePathList = new ArrayList<>();
        filePathList.add(tmpFile);

        content.putFiles(filePathList);
        db.setContent(content);

        event.consume();

    }

    void initialiseFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    /**
     * Registers a method that will be called by the handleDragDetection to simulate an Export command as though
     * it were a user.
     *
     * @param commandExecutor Method to register.
     */
    void registerDragAndDropCallBack(WordBankCardExecuteCallBack commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    /**
     * Call-back functional interface from ModularDisplay to MainWindow, represents the ModularDisplay sending
     * a command to the app as though it were another user.
     */
    @FunctionalInterface
    public interface WordBankCardExecuteCallBack {
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
