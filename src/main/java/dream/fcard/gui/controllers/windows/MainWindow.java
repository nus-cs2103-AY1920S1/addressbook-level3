package dream.fcard.gui.controllers.windows;

import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.displays.createandeditdeck.CreateDeckDisplay;
import dream.fcard.gui.controllers.displays.displayingdecks.DeckDisplay;
import dream.fcard.gui.controllers.displays.displayingdecks.NoDecksDisplay;
import dream.fcard.gui.controllers.jsjava.JavaEditorApplication;
import dream.fcard.gui.controllers.jsjava.JsEditorApplication;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.Deck;
import dream.fcard.model.StateHolder;
import dream.fcard.util.stats.StatsDisplayUtil;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The single-page parent component.
 */
public class MainWindow extends VBox {

    @FXML
    private ListView<Deck> deckList;
    @FXML
    private ScrollPane displayScrollPane;
    @FXML
    private VBox displayContainer;
    @FXML
    private MenuItem onCreateNewDeckMenuItem;
    @FXML
    private MenuItem jsEditorMenuItem;
    @FXML
    private MenuItem javaEditorMenuItem;
    @FXML
    private MenuItem quitMenuItem;
    @FXML
    private MenuItem statisticsMenuItem;
    @FXML
    private Label messageLabel;
    @FXML
    private TextArea cli;

    private CliEditor clieditor;

    private Consumer<Boolean> displayDecks = b -> render();
    private Consumer<Boolean> renderList = b -> renderDecks();
    private Consumer<Pane> swapDisplays = p -> {
        displayContainer.getChildren().clear();
        displayContainer.getChildren().add(p);
        //displayScrollPane.setVvalue(0);
    };
    private Consumer<String> displayMessage = message -> {
        //messageLabel.setText(message);
        clieditor.printNewLine(message);
    };
    private Consumer<Boolean> clearMessage = b -> messageLabel.setText("");

    //Example code
    private Consumer<Boolean> create = b -> showCreateNewDeckForm();
    private Consumer<Integer> seeDeck = i -> {
        Deck d = StateHolder.getState().getDecks().get(i - 1);
        deckList.getSelectionModel().select(d);
        DeckDisplay deckDisplay = new DeckDisplay(d);
        displayContainer.getChildren().clear();
        displayContainer.getChildren().add(deckDisplay);
        //displayScrollPane.setVvalue(0);
    };
    //private Consumer<Boolean> exitCreate = b -> exitCreate();

    private Consumer<Boolean> quitProgram = b -> {
        //UserStats.endCurrentSession();

        // save all files only on exit
        StorageManager.saveAll(StateHolder.getState().getDecks());
        //StorageManager.saveStats();
        System.exit(0);
    };

    private CreateDeckDisplay tempCreateDeckDisplay;

    /**
     * Binds the vertical height of the scroll panes to the size of the Nodes inside them so that the scrollbar
     * activates when there is a vertical overflow. Also renders the contents of the decks and the display pane.
     */
    @FXML
    public void initialize() {
        clieditor = new CliEditor(cli);
        displayScrollPane.vvalueProperty().bind(displayContainer.heightProperty());
        onCreateNewDeckMenuItem.setOnAction(e -> showCreateNewDeckForm());
        registerConsumers();
        displayMessage.accept("Welcome to FlashCard Pro!");
        deckList.setOnMouseClicked(e -> {
            Deck d = deckList.getSelectionModel().getSelectedItem();
            seeDeck.accept(StateHolder.getState().getDecks().indexOf(d) + 1);
        });
        quitMenuItem.setOnAction(e -> {
            quitProgram.accept(true);
        });
        javaEditorMenuItem.setOnAction(e -> openEditor(true));
        jsEditorMenuItem.setOnAction(e -> openEditor(false));
        statisticsMenuItem.setOnAction(e -> StatsDisplayUtil.openStatisticsWindow());
        render();
    }

    /**
     * Called when app starts or user has exited editing mode.
     * Purpose is to refresh the whole main window.
     */
    private void render() {
        renderDecks();
        renderDisplayPane();
    }

    /**
     * Pulls updated list of decks from model and re-displays the deck names.
     * Note: can replace with ObservableList if we can figure out the API
     */
    private void renderDecks() {
        ArrayList<Deck> decks = StateHolder.getState().getDecks();
        deckList.setItems(FXCollections.observableArrayList(decks));
        deckList.getSelectionModel().selectFirst();
    }


    /**
     * Creates the display pane telling the user there are no decks, with a button to start creating one.
     * Renders the display pane.
     */
    private void inviteUserToCreateDeckInDisplayPane() {
        displayContainer.getChildren().clear();
        displayContainer.getChildren().add(new NoDecksDisplay(wantsToCreateDeck -> {
            if (wantsToCreateDeck) {
                showCreateNewDeckForm();
            }
        }));
    }

    /**
     * Switches the display pane to an edit pane, used in initialising step/
     */
    @FXML
    private void showCreateNewDeckForm() {
        displayContainer.getChildren().clear();
        this.tempCreateDeckDisplay = new CreateDeckDisplay();
        displayContainer.getChildren().add(tempCreateDeckDisplay);
    }

    /**
     * When user wants to create/edit deck, put the editing pane.
     * Otherwise when the user clicks on a deck, put the deck display Pane containing info about the deck.
     * If there are no decks, and the user has not created a deck, display a pane asking user to create a
     * new deck.
     */
    private void renderDisplayPane() {
        if (StateHolder.getState().isEmpty()) {
            inviteUserToCreateDeckInDisplayPane();
        } else {
            //render the details of the first deck
            DeckDisplay deckDisplay = new DeckDisplay(StateHolder.getState().getDecks().get(0));
            displayContainer.getChildren().clear();
            displayContainer.getChildren().add(deckDisplay);
            //displayScrollPane.setVvalue(0);
        }
    }

    /**
     * Registers consumers in State for global access.
     */
    private void registerConsumers() {
        Consumers.addConsumer(ConsumerSchema.SWAP_DISPLAYS, swapDisplays);
        Consumers.addConsumer(ConsumerSchema.DISPLAY_DECKS, displayDecks);
        Consumers.addConsumer(ConsumerSchema.RENDER_LIST, renderList);
        Consumers.addConsumer(ConsumerSchema.DISPLAY_MESSAGE, displayMessage);
        Consumers.addConsumer(ConsumerSchema.CLEAR_MESSAGE, clearMessage);
        Consumers.addConsumer(ConsumerSchema.CREATE_NEW_DECK, create);
        Consumers.addConsumer(ConsumerSchema.SEE_SPECIFIC_DECK, seeDeck);
        Consumers.addConsumer(ConsumerSchema.QUIT_PROGRAM, quitProgram);
    }

    /**
     * Opens the Code Editor.
     */
    @FXML
    public void openEditor(boolean isJava) {
        Stage stage = new Stage();
        if (isJava) {
            JavaEditorApplication app = new JavaEditorApplication();
            app.start(stage);
        } else {
            JsEditorApplication app = new JsEditorApplication();
            app.start(stage);
        }

    }
}
