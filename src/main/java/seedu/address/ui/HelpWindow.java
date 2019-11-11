package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String HELP_COMMAND_GUIDE = "Help : help\n"
        + "Exit: exit\n"
        + "\n"
        + "Adding a student: student name/…\n"
        + "Removing a student: student delete index/…\n"
        + "Editing a student: student [index] name/…\n"
        + "Listing all students: student list\n"
        + "Tagging student: tag index/… tag/…\n"
        + "Marking a student: mark index/…\n"
        + "Unmarking a student: mark unmark index/…\n"
        + "\n"
        + "Creating a group: group manual groupID/… studentNumber/…\n"
        + "Adding student to a group: group groupID/… studentNumber/… groupIndexNumber/…\n"
        + "Removing student from a group: group delete groupID/… groupIndexNumber/…\n"
        + "Showing students from a group: group groupID/…\n"
        + "Exporting a group: group export groupID/…\n"
        + "\n"
        + "Creating a question: question question/… answer/… type/… (MCQ OPTIONS IF APPLICABLE)\n"
        + "Editing a question: question [INDEX] question/… answer/… type/… (MCQ OPTIONS IF APPLICABLE)\n"
        + "Deleting a question: question delete [INDEX]\n"
        + "Listing your saved questions: question list\n"
        + "Searching for a question: question find/…\n"
        + "Starting a slideshow: question slideshow [QUESTIONS INDEX]\n"
        + "\n"
        + "Creating a Quiz Manually : quiz manual quizID/… questionNumber/…\n"
        + "Creating a Quiz Automatically : quiz auto quizID/… numQuestions/… type/… "
            + "[Where type is: mcq, open or all]\n"
        + "Adding a Question to Quiz : quiz add quizID/… questionNumber/… quizQuestionNumber/…\n"
        + "Deleting a Question from Quiz : quiz delete quizID/… quizQuestionNumber/…\n"
        + "Listing a Quiz : quiz list quizID/…\n"
        + "Showing only Questions of a Quiz : quiz showQuestions quizID/…\n"
        + "Showing only Answers of a Quiz : quiz showAnswers quizID/…\n"
        + "Exporting a quiz : quiz export quizID/…\n"
        + "\n"
        + "Adding an Event : event eventName/… startDateTime/… endDateTime/… recur/… color/…\n"
        + "Editing an Event : event [INDEX] eventName/… startDateTime/… endDateTime/… recur/… color/…\n"
        + "Deleting an Event : event delete [INDEX]\n"
        + "Viewing all Event : event view scheduleMode/… targetDate/…\n"
        + "Exporting Events : event export directory/…\n"
        + "Screenshot Calendar : event screenshot directory/…\n"
        + "\n"
        + "Creating a Note : note note/… desc/…\n"
        + "Creating a Note with Priority : note note/… desc/… priority/…\n"
        + "Editing a Note : note [INDEX] note/… desc/… priority/…\n"
        + "Deleting a Note : note delete [INDEX]\n"
        + "Listing Notes : note list\n"
        + "\n"
        + "Generating Statistics : statistics file/…\n"
        + "Generating Statistics and Saving : statistics file/… print/…";

    public static final String USERGUIDE_URL =
        "https://github.com/AY1920S1-CS2103T-W13-2/main/blob/master/docs/UserGuide.adoc";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;
    @FXML
    private ScrollPane helpScrollPane;
    @FXML
    private Label helpGuideLabel;
    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        helpGuideLabel.setText(HELP_COMMAND_GUIDE);
        //helpScrollPane.vvalueProperty().bind(getRoot().heightProperty());
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
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

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
