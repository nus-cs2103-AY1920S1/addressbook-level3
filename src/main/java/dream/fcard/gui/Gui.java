//@@author nattanyz
package dream.fcard.gui;

import dream.fcard.MainApp;
import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.core.commons.util.StringUtil;
import dream.fcard.gui.components.FlashCardDisplay;
import dream.fcard.gui.components.ScrollablePane;
import dream.fcard.model.State;
import dream.fcard.model.cards.FlashCard;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Manages the GUI of the application.
 * Handles rendering of objects in GUI. Passes user command input to Responder.
 * Only one instance can exist.
 */
public class Gui {

    // the one and only instance of Gui allowed
    private static Gui gui = new Gui();

    // the instance of the main window of the application, containing all UI components
    private static MainWindow applicationMainWindow;
    private static State applicationState;

    private static final Logger logger = LogsCenter.getLogger(Gui.class);

    private Gui() {
        // empty constructor
    }

    public static void setApplicationState(State state) {
        applicationState = state;
    }

    public static void setApplicationMainWindow (MainWindow mainWindow) {
        applicationMainWindow = mainWindow;
    }

    static State getApplicationState() {
        return applicationState;
    }
    private static MainWindow getMainWindow() {
        return applicationMainWindow;
    }

    /** Accessor for the Gui instance. */
    public static Gui getInstance() {
        if (gui == null) {
            gui = new Gui();
        }
        return gui;
    }

    /**
     * Starts the application's GUI.
     *
     * @param primaryStage The window of the application.
     */
    public static void start(Stage primaryStage) {
        logger.info("Starting UI...");

        // set the application icon
        primaryStage.getIcons().add(getImage(GuiSettings.getApplicationIcon()));

        try {
            // instantiate MainWindow object and set main window to it
            applicationMainWindow = new MainWindow(primaryStage, applicationState);
            // todo: might make sense to call mainWindow's methods rather than putting everything in its constructor
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            //showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    private static Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
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
        // get primary display area of MainWindow
        ScrollablePane scrollablePane = getMainWindow().getScrollablePane(); // todo: check coding standards?

        // remove anything currently in the display area
        //windowContents.getChildren().clear();

        // show the Node in the display area
        scrollablePane.add(node);
    }

    /** Sets the title in the title bar of the application window. */
    public static void setTitle(String title) {
        // todo: refactor
        //getMainWindow().setTitle(title);
    }
}
