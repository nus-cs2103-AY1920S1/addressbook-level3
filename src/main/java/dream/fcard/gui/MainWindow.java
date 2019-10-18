package dream.fcard.gui;

import dream.fcard.model.Deck;
import dream.fcard.model.cards.FlashCard;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainWindow {
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
    ListView<FlashCard> cardDisplay;

    // colours
    String primaryTextColour = "#333333";
    String primaryUIColour = "#ABDFF6";
    String secondaryUIColour = "#F0ECEB";
    String tertiaryUIColour = "#6C7476";

    // font styles
    Font titleBarText = Font.font("Montserrat", FontWeight.BOLD, FontPosture.ITALIC, 36);


    public MainWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // setup
        primaryStage.setTitle("FlashCard Pro");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(400);

        // initialise containers
        window = new VBox();
        titleBar = new VBox(10);
        windowContents = new VBox(10);
        commandBoxPlaceholder = new VBox (10);

        // set size of containers
        titleBar.setPrefHeight(Region.USE_COMPUTED_SIZE);
        commandBoxPlaceholder.setPrefHeight(Region.USE_COMPUTED_SIZE);
        VBox.setVgrow(windowContents, Priority.ALWAYS);

        // set padding of containers
        titleBar.setPadding(new Insets(20));
        windowContents.setPadding(new Insets(20));
        commandBoxPlaceholder.setPadding(new Insets(20));

        // set colour of containers
        titleBar.setStyle("-fx-background-color:" + secondaryUIColour + ";");
        commandBoxPlaceholder.setStyle("-fx-background-color:" + tertiaryUIColour + ";");
        windowContents.setStyle("-fx-background-color:#FFFFFF;");

        // add children to window
        window.getChildren().addAll(titleBar, windowContents, commandBoxPlaceholder);

        // display window
        scene = new Scene(window, 400, 400);
        primaryStage.setScene(scene);
//        primaryStage.sizeToScene();
    }

    void show() {
        primaryStage.show();
    }

    // consider renaming fillInnerParts
    void fillInnerParts() {
        // create label with appropriate text
        title = new Text("Welcome!");

        // style label
        title.setFont(titleBarText);
        title.setFill(Color.web(primaryTextColour));

        // add label to titleBar
        titleBar.getChildren().add(title);

        // add text field to commandBoxPlaceholder
        commandTextField = new TextField("Enter command here...");
        commandBoxPlaceholder.getChildren().add(commandTextField);
    }

    // methods I can consider omitting or refactoring
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    // private void setAccelerators()
    // private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination)
    // private void setWindowDefaultSize(GuiSettings guisSettings)

    // FXML methods
    // public void handleHelp()
    // private void handleExit()
}
