package seedu.address.itinerary.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

import org.controlsfx.control.Notifications;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import javafx.util.Duration;

import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiPart;

/**
 * Controller for a help page
 */
@SuppressWarnings("unused")
public class HelpCommandWindow extends UiPart<Stage> {

    private static final String HELP_URL = "https://ay1920s1-cs2103t-t17-2.github.io/main/UserGuide.html";
    private static final String HELP_MESSAGE = "For more information check out our user guide here: \n" + HELP_URL;
    private static final String GREET_MESSAGE = "greet";
    private static final String SUMMARY_MESSAGE = "clear";
    private static final String HELP = "help";
    private static final String GOTO_MESSAGE = "goto [page]";
    private static final String EXIT_MESSAGE = "exit";
    private static final String ADD_MESSAGE = "add title/TITLE date/DATE time/TIME [l/LOCATION] d/[DESCRIPTION]";
    private static final String DELETE_MESSAGE = "delete INDEX";
    private static final String EDIT_MESSAGE = "edit INDEX [title/TITLE] [date/DATE] [time/TIME] [l/LOCATION]"
            + " [d/DESCRIPTION] [tag/]";
    private static final String DONE_MESSAGE = "done INDEX";
    private static final String LIST_MESSAGE = "list";
    private static final String SORT_MESSAGE = "sort by/[title] [location] [chronological] [completion] [priority]";
    private static final String HISTORY_MESSAGE = "history";
    private static final String UNDO_MESSAGE = "This command box supports auto-completion!";
    private static final String SEARCH_MESSAGE = "search [title/TITLE] [date/DATE] [time/TIME] [l/LOCATION]"
            + " [d/DESCRIPTION] [tag/]";
    //  private static final String WISH_MESSAGE = "wish by/[activity | time] [details]";
    private static final String INSTA_URL = "https://www.instagram.com/zhaoming_boiboi/";
    private static final String GITHUB_URL = "https://github.com/ngzhaoming";
    private static final String WEBSITE_URL = "https://ngzhaoming.github.io/";

    private static final Logger logger = LogsCenter.getLogger(HelpCommandWindow.class);
    private static final String FXML = "HelpCommandWindow.fxml";

    private Notifications notificationBuilder;

    private Node graphic;

    private String wishSuccess = "Yeah, me too... I wish the developers can release "
             + "this wish feature sooner （╯°□°）╯︵( .o.)";

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
    private HelpCommandWindow(Stage root) {
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
        url.putString(HISTORY_MESSAGE);
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

    //  /**
    //   * Copies the wish command template to the clipboard.
    //   */
    //  @FXML
    //  private void copyWish() {
    //      final Clipboard clipboard = Clipboard.getSystemClipboard();
    //      final ClipboardContent url = new ClipboardContent();
    //      url.putString(WISH_MESSAGE);
    //      clipboard.setContent(url);
    //  }

    /**
     * Show the notification when user click on the wish button.
     */
    @FXML
    private void handleWish() {
        getRoot().hide();
        notification(Pos.TOP_RIGHT, graphic, wishSuccess);
        notificationBuilder.showInformation();

    }

    /**
     * Inform the user that the following action has been completed.
     * @param pos specifies the position that the command box appears on the screen.
     * @param graphic the picture accompanying the notification.
     * @param text the message that is shown in the notification.
     */
    private void notification(Pos pos, Node graphic, String text) {
        notificationBuilder = Notifications.create()
                .title("Hold it!")
                .text(text)
                .graphic(graphic)
                .hideAfter(Duration.seconds(5))
                .position(pos)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Notification is clicked");
                    }
                });
    }


    /**
     * Opens up user default browser to the instagram URL
     */
    @FXML
    private void gotoInstagram() {
        try {
            Desktop.getDesktop().browse(new URL(INSTA_URL).toURI());
        } catch (IOException | URISyntaxException e) {
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
        } catch (IOException | URISyntaxException e) {
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
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
