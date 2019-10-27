//@@author nattanyz
package dream.fcard.gui;

import dream.fcard.MainApp;
import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.core.commons.util.StringUtil;
import dream.fcard.gui.components.CommandTextField;
import dream.fcard.gui.components.CommandTextFieldPlaceholder;
import dream.fcard.gui.components.FlashCardDisplay;
import dream.fcard.gui.components.ScrollablePane;
import dream.fcard.gui.components.TitleBar;
import dream.fcard.model.State;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.FrontBackCard;
import java.util.logging.Logger;
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
    private static CommandTextFieldPlaceholder commandTextFieldPlaceholder = new CommandTextFieldPlaceholder();

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
        String longStringForTesting = "Lorem ipsum dolor sit amet, "
            + "consectetur adipscing elit. Aliquam lacinia, nunc quis dictum consectetur, "
            + "erat nulla molestie turpis, quis finibus justo ipsum a justo. Cras quis ullamcorper "
            + "nulla. Duis nec volutpat nibh. Praesent ut justo vestibulum, lacinia tortor mattis, "
            + "euismod eros. Maecenas viverra erat ac eros consequat feugiat. Praesent vehicula non "
            + "turpis tempor elementum. Suspendisse ac lacus congue, blandit nisl eget, suscipit "
            + "eros. Donec aliquet, tellus eu consectetur ornare, odio ipsum lacinia ipsum, id "
            + "ullamcorper magna nibh quis nulla. Nulla sagittis, quam vel condimentum commodo, "
            + "arcu ligula lobortis erat, non consequat tortor mi non libero. Etiam orci purus, "
            + "maximus sed suscipit ut, consectetur sed arcu.";
        FrontBackCard flashCardForTesting = new FrontBackCard(longStringForTesting,
            "Praesent ut est justo. Cras urna risus, ultricies posuere pharetra in, "
                + "fringilla ac dolor. Donec a porttitor tellus, vitae ullamcorper risus.");
        Gui.renderFront(flashCardForTesting);
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
        CommandTextField commandTextField = new CommandTextField(applicationState);
        commandTextFieldPlaceholder.add(commandTextField);
    }

    /**
     * Add the UI components to main window, and display the scene.
     */
    private static void setupScene() {
        // add children to window
        window.getChildren().addAll(titleBar, scrollablePane, commandTextFieldPlaceholder);

        // display window
        Scene scene = new Scene(window, 400, 400);
        applicationPrimaryStage.setScene(scene);
    }

    /**
     * Renders the front of the given FlashCard in the GUI.
     * @param flashCard The FlashCard to be rendered.
     */
    public static void renderFront(FlashCard flashCard) {
        // todo: use functional interfaces, from renderCard?
        // get text to be rendered
        String cardText = flashCard.getFront();

        // generate Node representing flashCard's contents
        FlashCardDisplay node = new FlashCardDisplay(cardText);

        // display the Node in the Gui
        displayInScrollablePane(node);
    }

    /**
     * Renders the back of the given FlashCard in the GUI.
     * @param flashCard The FlashCard to be rendered.
     */
    public static void renderBack(FlashCard flashCard) {
        // todo: use functional interfaces, from renderCard?
        // get text to be rendered
        String cardText = flashCard.getBack();

        // generate Node representing flashCard's contents
        FlashCardDisplay node = new FlashCardDisplay(cardText);

        // display the Node in the Gui
        displayInScrollablePane(node);
    }

    /** Temporary method to render a FlashCardDisplay object without accessing FlashCard class. */
    static void renderCard(String cardText) {
        FlashCardDisplay node = new FlashCardDisplay(cardText);
        displayInScrollablePane(node);
    }

    /** Displays a given Node in the scrollable pane of the MainWindow. */
    private static void displayInScrollablePane(Node node) {
        // remove anything currently in the display area
        //windowContents.getChildren().clear();

        // show the Node in the display area
        scrollablePane.add(node);
    }

    /** Sets the title in the title bar of the application window. */
    public static void setTitle(String title) {
        titleBar.setTitle(title);
    }
}
