package seedu.address.itinerary.ui;

import com.sun.source.doctree.SummaryTree;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiPart;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Controller for a help page
 */
public class HelpCommandWindow extends UiPart<Stage> {

    public static final String HELP_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String HELP_MESSAGE = "For more information check out our user guide here: \n" + HELP_URL;
    public static final String GREET_MESSAGE = "greet";
    public static final String SUMMARY_MESSAGE = "summary";
    public static final String HELP = "help";
    public static final String GOTO_MESSAGE = "goto [page]";
    public static final String EXIT_MESSAGE = "exit";
    public static final String ADD_MESSAGE = "add title/[title] date/[date] time/[time] l/[location] d/[description]";
    public static final String DELETE_MESSAGE = "delete [index]";
    public static final String EDIT_MESSAGE = "edit [index] [type]/[details]";
    public static final String DONE_MESSAGE = "done [index]";
    public static final String LIST_MESSAGE = "list";
    public static final String SORT_MESSAGE = "sort by/[alphabetical | chronological | completion | duration]";
    public static final String REDO_MESSAGE = "Press the up arrow key to call back previous commands";
    public static final String UNDO_MESSAGE = "undo";
    public static final String SEARCH_MESSAGE = "search by/[title | date | time | location] [keyword]";
    public static final String WISH_MESSAGE = "wish by/[activity | time] [details]";
    public static final String INSTA_URL = "https://www.instagram.com/zhaoming_boiboi/";
    public static final String GITHUB_URL = "https://github.com/ngzhaoming";
    public static final String WEBSITE_URL = "https://ngzhaoming.github.io/";

    private static final Logger logger = LogsCenter.getLogger(HelpCommandWindow.class);
    private static final String FXML = "HelpCommandWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Button greetCommand;

    @FXML
    private Button summaryCommand;

    @FXML
    private Button helpCommand;

    @FXML
    private Button gotoCommand;

    @FXML
    private Button exitCommand;

    @FXML
    private Button addCommand;

    @FXML
    private Button deleteCommand;

    @FXML
    private Button editCommand;

    @FXML
    private Button doneCommand;

    @FXML
    private Button listCommand;

    @FXML
    private Button sortCommand;

    @FXML
    private Button redoCommand;

    @FXML
    private Button undoCommand;

    @FXML
    private Button searchCommand;

    @FXML
    private Button wishCommand;

    @FXML
    private Button instagramCommand;

    @FXML
    private Button githubCommand;

    @FXML
    private Button websiteCommand;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpCommandWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpCommandWindow() {
        this(new Stage());
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
        url.putString(HELP_URL);
        clipboard.setContent(url);
    }

    /**
     * Copies the greet command template to the clipboard.
     */
    @FXML
    private void copyGreet() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(GREET_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the summary command template to the clipboard.
     */
    @FXML
    private void copySummary() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(SUMMARY_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the summary command template to the clipboard.
     */
    @FXML
    private void copyHelp() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(HELP);
        clipboard.setContent(url);
    }

    /**
     * Copies the goto command template to the clipboard.
     */
    @FXML
    private void copyGoto() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(GOTO_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the exit command template to the clipboard.
     */
    @FXML
    private void copyExit() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(EXIT_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the add command template to the clipboard.
     */
    @FXML
    private void copyAdd() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(ADD_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the delete command template to the clipboard.
     */
    @FXML
    private void copyDelete() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(DELETE_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the edit command template to the clipboard.
     */
    @FXML
    private void copyEdit() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(EDIT_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the done command template to the clipboard.
     */
    @FXML
    private void copyDone() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(DONE_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the list command template to the clipboard.
     */
    @FXML
    private void copyList() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(LIST_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the sort command template to the clipboard.
     */
    @FXML
    private void copySort() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(SORT_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the redo command template to the clipboard.
     */
    @FXML
    private void copyRedo() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(REDO_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the undo command template to the clipboard.
     */
    @FXML
    private void copyUndo() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(UNDO_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the search command template to the clipboard.
     */
    @FXML
    private void copySearch() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(SEARCH_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Copies the wish command template to the clipboard.
     */
    @FXML
    private void copyWish() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(WISH_MESSAGE);
        clipboard.setContent(url);
    }

    /**
     * Opens up user default browser to the instagram URL
     */
    @FXML
    private void gotoInstagram() {
        try {
            Desktop.getDesktop().browse(new URL(INSTA_URL).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens up user default browser to the Github URL
     */
    @FXML
    private void gotoGithub() {
        try {
            Desktop.getDesktop().browse(new URL(GITHUB_URL).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens up user default browser to the Website URL
     */
    @FXML
    private void gotoWebsite() {
        try {
            Desktop.getDesktop().browse(new URL(WEBSITE_URL).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}

