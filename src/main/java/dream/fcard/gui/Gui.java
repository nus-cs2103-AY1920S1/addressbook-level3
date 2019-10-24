//@@author nattanyz
package dream.fcard.gui;

import dream.fcard.gui.components.FlashCardDisplay;
import dream.fcard.gui.components.ScrollablePane;
import dream.fcard.model.State;
import dream.fcard.model.cards.FlashCard;
import javafx.scene.Node;

/**
 * Manages the GUI of the application.
 * Handles rendering of objects in GUI. Passes user command input to Responder.
 * Only one instance can exist.
 */
public class Gui {

    // the one and only instance of Gui allowed
    private static Gui gui = new Gui();

    // the instance of the main window of the application, containing all UI components
    private static MainWindow applicationMainWindow = new MainWindow();
    private static State applicationState;

    private Gui() {
        // todo
    }

    // todo: refactor Gui's constructors

    /**
     * Instantiates the GUI object. Will be private when refactoring is complete.
     * @param mainWindow Application window housing all other UI components.
     * @param state The state of the application.
     */
    public Gui(MainWindow mainWindow, State state) {
        applicationMainWindow = mainWindow;
        applicationState = state;
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
