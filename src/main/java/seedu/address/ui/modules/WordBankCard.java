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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wordbank.WordBank;
import seedu.address.ui.ModularDisplay;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Card}.
 */
public class WordBankCard extends UiPart<Region> {

    private static final String FXML = "WordBankCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final WordBank wordBank;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    @FXML
    private StackPane resultDisplayPlaceholder;

    private ModularDisplay.ModularDisplayExecuteCallBack commandExecutor;

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
        System.out.println("onDragDetected");

        Dragboard db = cardPane.startDragAndDrop(TransferMode.COPY);
        ClipboardContent content = new ClipboardContent();
        String wordBankPath = "data/wordBanks/" + wordBank.getName() + ".json";
        File tmpFile = new File(wordBankPath);

        System.out.println(tmpFile);

        //        List<String> filePathStringList = new ArrayList<>();
        //        filePathStringList.add(wordBankPath);

        List<File> filePathList = new ArrayList<>();
        filePathList.add(tmpFile);

        content.putFiles(filePathList);
        //        content.putFilesByPath(filePathStringList);

        db.setContent(content);

        event.consume();
        System.out.println("dragDetection done");

        try {
            commandExecutor.execute("export w/dragAndDropInternalExport" + wordBank.getName()
                    + " f/" + System.getProperty("user.home"));
        } catch (CommandException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * Registers a method that will be called by the handleDragDetection to simulate an Export command as though
     * it were a user.
     *
     * @param commandExecutor Method to register.
     */
    void registerDragAndDropCallBack(ModularDisplay.ModularDisplayExecuteCallBack commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

}
