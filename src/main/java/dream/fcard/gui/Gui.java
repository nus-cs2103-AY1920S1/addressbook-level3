//@@author nattanyz
package dream.fcard.gui;

import java.util.logging.Logger;

import dream.fcard.MainApp;
import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.core.commons.util.StringUtil;
import dream.fcard.gui.components.CommandBar;
import dream.fcard.gui.components.CommandTextField;
import dream.fcard.gui.components.DeckListView;
import dream.fcard.gui.components.FlashCardDisplay;
import dream.fcard.gui.components.ScrollablePane;
import dream.fcard.gui.components.StatusBar;
import dream.fcard.gui.components.TitleBar;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FlashCard;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Manages the GUI of the application.
 * Handles rendering of objects in GUI. Passes user command input to Responder.
 * Only one instance can exist.
 */
public class Gui {

    // the one and only instance of Gui allowed
    private static Gui gui = new Gui(); // no need for getter
    private static State applicationState;

    private static final Logger logger = LogsCenter.getLogger(Gui.class);

    private static Stage applicationPrimaryStage;

    // containers for UI components
    private static VBox window = new VBox();
    private static TitleBar titleBar = new TitleBar();
    private static ScrollablePane scrollablePane = new ScrollablePane();
    private static CommandBar commandBar = new CommandBar();
    private static CommandTextField commandTextField = new CommandTextField();
    private static StatusBar statusBar = new StatusBar();

    private Gui() {
        // empty constructor
    }

    /**
     * Sets the applicationState attribute to the current State of the application.
     * Allows the GUI to pass State to its command text field.
     * @param state The state of the application.
     */
    public static void setApplicationState(State state) {
        applicationState = state;
    }

    /**
     * Starts the application's GUI.
     * @param primaryStage The window of the application.
     */
    public static void start(Stage primaryStage) {
        logger.info("Starting UI...");

        // set the application primary stage
        applicationPrimaryStage = primaryStage;

        // set the application icon
        primaryStage.getIcons().add(getImage(GuiSettings.getApplicationIcon()));

        try {
            Gui.onStartup();
            Gui.testUiComponents();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            //showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    private static Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     * Temporary method for testing display of various UI components.
     */
    private static void testUiComponents() {

    }

    // Methods related to setting up GUI components upon application startup
    /**
     * Initialises components of the main window and shows the main window upon startup.
     */
    private static void onStartup() {
        initializeStage();

        // set up initial UI components
        setupCommandTextField();
        setTitle("Welcome!");
        showStatus("No command entered yet...");

        // add UI components to scene
        setupScene();

        // finally, display main window
        applicationPrimaryStage.show();
    }

    /**
     * Initialises the stage by setting its size and title.
     */
    private static void initializeStage() {
        applicationPrimaryStage.setTitle("FlashCard Pro"); // set title of application window
        applicationPrimaryStage.setMinHeight(GuiSettings.getMinHeight());
        applicationPrimaryStage.setMinWidth(GuiSettings.getMinWidth());
    }

    /**
     * Set up the command text field with the given state and add to its placeholder.
     */
    private static void setupCommandTextField() {
        commandTextField.setState(applicationState);
        commandBar.setCommandTextField(commandTextField);
    }

    /**
     * Add the UI components to main window, and display the scene.
     */
    private static void setupScene() {
        // add children to window
        window.getChildren().addAll(titleBar, scrollablePane, commandBar,
            statusBar);

        // display window
        Scene scene = new Scene(window, 400, 400);
        applicationPrimaryStage.setScene(scene);
    }

    /**
     * Sets the title in the title bar of the application window.
     * Useful when the application changes state, e.g. displaying a different deck.
     * @param title The title to be set in the title bar.
     */
    public static void setTitle(String title) {
        titleBar.setText(title);
    }

    /**
     * Shows a given status in the status bar of the application window.
     * Useful for providing feedback to the user, e.g. when a command is entered.
     * @param status The status to be shown in the status bar.
     */
    public static void showStatus(String status) {
        statusBar.setText(status);
    }

    /**
     * Shows a given error in the status bar of the application window.
     * Useful for informing the user when an error has occurred, e.g. when they enter an
     * unrecognised command.
     * @param errorMessage The error message to be shown in the status bar.
     */
    public static void showError(String errorMessage) {
        statusBar.setErrorMessage(errorMessage);
    }

    /**
     * Renders the front of the given FlashCard in the GUI.
     * @param flashCard The FlashCard to be rendered.
     */
    public static void renderFront(FlashCard flashCard) {
        FlashCardDisplay node = GuiHelper.renderFront(flashCard);

        // display the Node in the Gui
        displayInScrollablePane(node);
    }

    /**
     * Renders the back of the given FlashCard in the GUI.
     * @param flashCard The FlashCard to be rendered.
     */
    public static void renderBack(FlashCard flashCard) {
        FlashCardDisplay node = GuiHelper.renderBack(flashCard);

        // display the Node in the Gui
        displayInScrollablePane(node);
    }

    /**
     * Renders a deck of FlashCards in the GUI.
     * @param deck The deck of FlashCards to be rendered.
     */
    public static void renderDeck(Deck deck) {
        setTitle(deck.getName());
        DeckListView node = GuiHelper.renderDeck(deck);

        // display the Node in the Gui
        replaceInScrollablePane(node);
    }

    /**
     * Renders the statistics of the user in the GUI.
     * Takes in a Statistics object as parameter.
     */
    public static void renderStats() {
        // can only be done when Statistics class is implemented
        setTitle("Your Stats");
    }

    /**
     * Displays a given Node in the scrollable pane of the GUI.
     * @param node The Node to be displayed in the scrollable pane of the GUI.
     */
    public static void displayInScrollablePane(Node node) {
        // show the Node in the display area
        scrollablePane.add(node);
    }

    /**
     * Replaces all Nodes currently in the scrollable pane of the GUI with the current Node.
     * @param node The Node to be displayed in the scrollable pane of the GUI.
     */
    public static void replaceInScrollablePane(Node node) {
        scrollablePane.replace(node);
    }
}
