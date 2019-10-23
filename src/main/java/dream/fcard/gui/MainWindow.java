package dream.fcard.gui;

import dream.fcard.model.Deck;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainWindow {
    Gui gui;
    Stage primaryStage;
    Scene scene;

    // containers
    VBox window;
    VBox titleBar;
    VBox windowContents;
    VBox commandBoxPlaceholder;

    // ui components
    Text title;
    TextField commandTextField;
    ListView<Deck> deckDisplay;

    public MainWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;

        initializeStage();

        // set up containers for UI components
        initializeContainers(10);
        setupContainerSizes();
        setupContainerPaddings(10);
        setupContainerColours();

        // add children to window
        window.getChildren().addAll(titleBar, windowContents, commandBoxPlaceholder);

        // display window
        scene = new Scene(window, 400, 400);
        primaryStage.setScene(scene);
    }

    void show() {
        primaryStage.show();
    }

    // consider renaming fillInnerParts
    void fillInnerParts() {
        setTitle("Welcome!");

        // add text field to commandBoxPlaceholder
        commandTextField = new TextField("Enter command here...");
        commandBoxPlaceholder.getChildren().add(commandTextField);
    }

    private void initializeStage() {
        primaryStage.setTitle("FlashCard Pro");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);
    }

    private void initializeContainers(double spacing) {
        window = new VBox();
        titleBar = new VBox(spacing);
        windowContents = new VBox(spacing);
        commandBoxPlaceholder = new VBox (spacing);
    }

    private void setupContainerSizes() {
        titleBar.setPrefHeight(Region.USE_COMPUTED_SIZE);
        commandBoxPlaceholder.setPrefHeight(Region.USE_COMPUTED_SIZE);
        VBox.setVgrow(windowContents, Priority.ALWAYS);
    }

    private void setupContainerPaddings(double padding) {
        titleBar.setPadding(new Insets(padding));
        windowContents.setPadding(new Insets(padding));
        commandBoxPlaceholder.setPadding(new Insets(padding));
    }

    private void setupContainerColours() {
        // todo: abstract into UI component setBackgroundColour(String colour) method
        titleBar.setStyle("-fx-background-color:" + GuiSettings.getSecondaryUIColour() + ";");
        commandBoxPlaceholder.setStyle("-fx-background-color:" + GuiSettings.getTertiaryUIColour() + ";");
        windowContents.setStyle("-fx-background-color:#FFFFFF;"); // todo: define another colour
    }

    // methods I can consider omitting or refactoring
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    void setTitle(String titleText) {
        // create label with appropriate text
        title = new Text(titleText);

        // style label
        title.setFont(GuiSettings.getTitleTextStyle());
        title.setFill(Color.web(GuiSettings.getPrimaryTextColour()));

        // add label to titleBar
        titleBar.getChildren().add(title);
    }

    // private void setAccelerators()
    // private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination)
    // private void setWindowDefaultSize(GuiSettings guiSettings)

    // FXML methods
    // public void handleHelp()
    // private void handleExit()
}
