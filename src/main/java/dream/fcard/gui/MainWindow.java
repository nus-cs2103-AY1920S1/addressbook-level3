//@@author nattanyz
package dream.fcard.gui;

import dream.fcard.gui.components.CommandTextField;
import dream.fcard.gui.components.CommandTextFieldPlaceholder;
import dream.fcard.gui.components.ScrollablePane;
import dream.fcard.gui.components.TitleBar;
import dream.fcard.model.State;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main window of the application's GUI. Houses all other UI components.
 */
public class MainWindow {

    private Stage primaryStage;
    private Scene scene;
    private State state;

    // containers
    private VBox window = new VBox();
    private TitleBar titleBar = new TitleBar();
    private ScrollablePane scrollablePane = new ScrollablePane();
    private CommandTextFieldPlaceholder commandTextFieldPlaceholder = new CommandTextFieldPlaceholder();

    /**
     * Creates and displays a main window for the application. Called by Gui when application
     * is started.
     *
     * @param primaryStage Stage representing the window of the application
     * @param state The state of the application.
     */
    public MainWindow(Stage primaryStage, State state) {
        this.primaryStage = primaryStage;
        this.state = state;
        //onStartup();
        //testUiComponents();
    }

    /**
     * Initialises components of the main window and shows the main window upon startup.
     */
    private void onStartup() {
        initializeStage();

        // set up initial UI components
        setupCommandTextField();
        titleBar.setTitle("Welcome!");

        // add UI components to scene
        setupScene();

        // finally, display main window
        primaryStage.show();
    }

    /**
     * Temporary method for testing display of various UI components.
     */
    private void testUiComponents() {
        Gui.renderCard("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        //Gui.renderCard("Pellentesque eu placerat urna, eu tincidunt magna.");
    }

    /**
     * Initialises the stage by setting its size and title.
     */
    private void initializeStage() {
        primaryStage.setTitle("FlashCard Pro"); // set title of application window
        primaryStage.setMinHeight(GuiSettings.getMinHeight());
        primaryStage.setMinWidth(GuiSettings.getMinWidth());
    }

    /**
     * Set up the command text field with the given state and add to its placeholder.
     */
    private void setupCommandTextField() {
        CommandTextField commandTextField = new CommandTextField(state);
        commandTextFieldPlaceholder.add(commandTextField);
    }

    /**
     * Add the UI components to main window, and display the scene.
     */
    private void setupScene() {
        // add children to window
        window.getChildren().addAll(titleBar, scrollablePane, commandTextFieldPlaceholder);

        // display window
        scene = new Scene(window, 400, 400);
        primaryStage.setScene(scene);
    }

    private Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Temporary method (to be refactored) returning the ScrollablePane in this MainWindow.
     */
    ScrollablePane getScrollablePane() {
        return this.scrollablePane;
    }
}
