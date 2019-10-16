package seedu.address.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.logging.Logger;

/**
 * Controller for a help page
 */
public class EditObjectWindow extends UiPart<Stage> {


    private static final Logger logger = LogsCenter.getLogger(EditObjectWindow.class);
    private static final String FXML = "EditObjectWindow.fxml";
    private String content;
    private String title;
    private Logic logic;
    private String index;


    @FXML
    private Button saveButton;

    @FXML
    private TextArea noteContent;

    @FXML
    private Label noteTitle;

    /**
     * Creates a new EditObjectWindow.
     *
     * @param root Stage to use as the root of the EditObjectWindow.
     */
    public EditObjectWindow(Stage root) {
        super(FXML, root);
    }
    public EditObjectWindow() {
        this(new Stage());
    }

    public void setIndex(String index) {
        this.index = index;
    }
    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void setContent(String content) {
        this.content = content;
        noteContent.setText(content + "\n");
        noteContent.setEditable(true);
        noteContent.setWrapText(true);
    }

    public void setTitle(String title) {
        this.title = title;
        noteTitle.setText(title + "\n ");
    }
    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Edit your notes");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    @FXML
    private void saveNote() throws CommandException, ParseException {
        logic.execute("edit " + index + " c/" + noteContent.getText());
    }

}
