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
import seedu.address.model.wordbank.WordBank;
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

    /**
     * Card containing the details of the word bank.
     *
     * @param wordBank       The card representing its corresponding word bank.
     * @param displayedIndex The index of the word bank.
     */
    public WordBankCard(WordBank wordBank, int displayedIndex) {
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
        /* drag was detected, start drag-and-drop gesture*/
        System.out.println("onDragDetected");

        /* allow MOVE transfer mode */
        Dragboard db = cardPane.startDragAndDrop(TransferMode.COPY);

        /* put a string on dragboard */
        ClipboardContent content = new ClipboardContent();
        //        content.putString(wordBank.getName());
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
    }


    //    public void handle(MouseEvent event) {
    //        List<File> vec = new ArrayList<File>();
    //        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
    //        File tmpFile = new File("test.txt");
    //        try {
    //            tmpFile.createNewFile();
    //        } catch (IOException e) {
    //            // TODO Auto-generated catch block
    //            e.printStackTrace();
    //        }
    //        vec.add(tmpFile);
    //        ClipboardContent content = new ClipboardContent();
    //        content.putFiles(vec);
    //        db.setContent(content);
    //        event.consume();
    //    }


    //    @FXML
    //    private void handleDragDropped(DragEvent event) {
    //
    //        /* data dropped */
    //        System.out.println("onDragDropped");
    //        /* if there is a string data on dragboard, read it and use it */
    //        Dragboard db = event.getDragboard();
    //        boolean success = false;
    //        if (db.hasFiles()) {
    //            System.out.println("db has files");
    //            name.setText(db.getString());
    //            success = true;
    //        }
    //        /* let the source know whether the string was successfully
    //         * transferred and used */
    //        event.setDropCompleted(success);
    //
    //        event.consume();
    //    }

    //    @FXML
    //    private void handleDragDone(DragEvent event) {
    //        /* the drag-and-drop gesture ended */
    //        System.out.println("onDragDone");
    //        /* if the data was successfully moved, clear it */
    //        if (event.getTransferMode() == TransferMode.MOVE) {
    //            name.setText("");
    //        }
    //
    //        event.consume();
    //    }
}
