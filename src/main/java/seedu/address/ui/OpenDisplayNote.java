package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.Note;


/**
 * Controller for a help page
 */
public class OpenDisplayNote extends UiPart<Region> {


    private static final Logger logger = LogsCenter.getLogger(OpenDisplayNote.class);
    private static final String FXML = "OpenDisplayNote.fxml";
    private String content;
    private String title;
    private String description;
    private Logic logic;
    private Index index;
    private Note note;
    private MainWindow mainWindow;

    @FXML
    private Button saveButton;

    @FXML
    private Label labelTitle = new Label("Title:");

    @FXML
    private Label labelDescription = new Label("Description");

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
    public OpenDisplayNote() {
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

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
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

    /**
     * Saves note by running Edit Command and updating all relevant fields of the note.
     */
    @FXML
    private void saveNote() throws CommandException, ParseException {
        System.out.println("noteTitle:" + noteTitle.getText());

        mainWindow.executeCommand("edit " + index.getOneBased() + " c/"
                + noteContent.getText()
                + " d/" + noteDescription.getText() + " ti/" + noteTitle.getText()
        );
    }

}
