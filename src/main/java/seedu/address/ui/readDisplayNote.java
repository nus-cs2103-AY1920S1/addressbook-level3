package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.DictionaryException;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.Description;
import seedu.address.model.note.Note;


/**
 * Controller for a help page
 */
public class readDisplayNote extends UiPart<Region> {


    private static final Logger logger = LogsCenter.getLogger(readDisplayNote.class);
    private static final String FXML = "readDisplayNote.fxml";
    private String content;
    private String title;
    private String description;
    private Logic logic;
    private Index index;
    private Note note;

    @FXML
    private Button saveButton;

    @FXML
    private TextField noteTitle;

    @FXML

    private TextArea noteContent;

    @FXML
    private TextField noteDescription;


    /**
     * Creates a new readDisplayNote.
     *
     */
    public readDisplayNote() {
        super(FXML);

    }
    public void setFeedbackToUser(Note note, Index index) {
        this.note = note;
        this.content = note.getContent().toString();
        this.title = note.getTitle().toString();
        this.index = index;
        System.out.println("title:" + title);
        this.description = note.getDescription().toString();
        setNoteContent();
        setNoteTitle();
        setNoteDescription();
    }
    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    private void setNoteContent() {
        noteContent.setText(content + "\n");
        noteContent.setEditable(true);
        noteContent.setWrapText(true);
    }

    private void setNoteTitle() {
        noteTitle.setText(title + "\n");
        noteTitle.setEditable(true);
    }

    private void setNoteDescription() {
        noteDescription.setText(description + "\n");
        noteDescription.setEditable(true);
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    @FXML
    private void saveNote() throws CommandException, ParseException,
            DictionaryException { //TODO:Dictionary Exception unneeded?
        System.out.println(noteContent.getText());
        logic.execute("edit " + index.getOneBased() + " c/"
                + noteContent.getText()
                + " d/" + noteDescription.getText() + " ti/" + noteTitle.getText()
        );
    }

}
