//@@author nattanyz
package dream.fcard.gui;

import dream.fcard.gui.components.CommandTextField;
import dream.fcard.logic.respond.Responder;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainWindow {
    Gui gui;
    private Stage primaryStage;
    private Scene scene;
    private State state;

    // containers
    private VBox window;
    private VBox titleBar;
    private GridPane windowContents;
    private VBox commandBoxPlaceholder;

    // ui components
    private Text title;
    private CommandTextField commandTextField;
    ListView<Deck> deckDisplay;

    public MainWindow() {
        // temporary no-arg constructor
    }

    public MainWindow(Stage primaryStage, State state) {
        // todo: refactor linkages between MainWindow, Gui and UiManager
        this.primaryStage = primaryStage;
        this.gui = new Gui(this, state);
        onStartup();
        testUiComponents();
    }

    private void onStartup() {
        initializeStage();

        // set up containers for UI components
        initializeContainers();
        setupContainerSizes();
        setupContainerPadding();
        setupContainerColours();

        // set up initial UI components
        setupCommandBox();
        setTitle("Welcome!");

        // add UI components to scene
        setupScene();

        // finally, display main window
        primaryStage.show();
    }

    // temporary method for testing display of various UI components
    private void testUiComponents() {
        this.gui.renderCard("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
//            + "Pellentesque eu placerat urna, eu tincidunt magna.");
    }

    private void initializeStage() {
        primaryStage.setTitle("FlashCard Pro");
        primaryStage.setMinHeight(GuiSettings.getMinHeight());
        primaryStage.setMinWidth(GuiSettings.getMinWidth());
    }

    private void initializeContainers() {
        window = new VBox();
        titleBar = new VBox(GuiSettings.getSpacing());
        windowContents = new GridPane();
        commandBoxPlaceholder = new VBox (GuiSettings.getSpacing());
    }

    private void setupContainerSizes() {
        titleBar.setPrefHeight(Region.USE_COMPUTED_SIZE);
        commandBoxPlaceholder.setPrefHeight(Region.USE_COMPUTED_SIZE);
        VBox.setVgrow(windowContents, Priority.ALWAYS);
    }

    private void setupContainerPadding() {
        titleBar.setPadding(new Insets(GuiSettings.getPadding()));
        windowContents.setPadding(new Insets(GuiSettings.getPadding())); // vertical spacing between elements
        commandBoxPlaceholder.setPadding(new Insets(GuiSettings.getPadding()));
    }

    private void setupContainerColours() {
        // todo: abstract into UI component setBackgroundColour(String colour) method
        titleBar.setStyle("-fx-background-color:" + GuiSettings.getSecondaryUIColour() + ";");
//        commandBoxPlaceholder.setStyle("-fx-background-color:#FFFFFF;");
        commandBoxPlaceholder.setStyle("-fx-background-color:" + GuiSettings.getTertiaryUIColour() + ";"); // temporary
        windowContents.setStyle("-fx-background-color:#FFFFFF;"); // todo: define another colour
    }

    private void setupCommandBox() {
        commandTextField = new CommandTextField(state);
        commandBoxPlaceholder.getChildren().add(commandTextField);
    }

    private void setupScene() {
        // add children to window
        window.getChildren().addAll(titleBar, windowContents, commandBoxPlaceholder);

        // display window
        scene = new Scene(window, 400, 400);
        primaryStage.setScene(scene);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    void setTitle(String titleText) {
        // create label with appropriate text
        title = new Text(titleText);

        // style label
        title.setFont(GuiSettings.getTitleTextStyle());
        title.setFill(Color.web(GuiSettings.getPrimaryTextColour()));

        // remove any existing title in titleBar
        titleBar.getChildren().clear();

        // add label to titleBar
        titleBar.getChildren().add(title);
    }

    GridPane getWindowContents() {
        return this.windowContents;
    }

    // private void setAccelerators()
    // private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination)
    // private void setWindowDefaultSize(GuiSettings guiSettings)

    // FXML methods
    // public void handleHelp()
    // private void handleExit()
}
