package dream.fcard.gui.controllers.windows;

import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.displays.createandeditdeck.CreateDeckDisplay;
import dream.fcard.gui.controllers.displays.displayingdecks.DeckDisplay;
import dream.fcard.gui.controllers.displays.displayingdecks.NoDecksDisplay;
import dream.fcard.gui.controllers.jsjava.JavaEditorApplication;
import dream.fcard.gui.controllers.jsjava.JsEditorApplication;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.logic.respond.Dispatcher;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The single-page parent component.
 */
public class MainWindow extends VBox {

    @FXML
    private ScrollPane deckScrollPane;
    @FXML
    private ListView<Deck> deckList;
    @FXML
    private ScrollPane displayScrollPane;
    @FXML
    private VBox displayContainer;
    @FXML
    private MenuItem onCreateNewDeck;
    @FXML
    private MenuItem jsEditor;
    @FXML
    private MenuItem javaEditor;
    @FXML
    private MenuItem quit;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField commandLine;

    private Consumer<Boolean> displayDecks = b -> render();
    private Consumer<Pane> swapDisplays = p -> {
        displayContainer.getChildren().clear();
        displayContainer.getChildren().add(p);
    };
    private Consumer<String> displayMessage = message -> {
        messageLabel.setText(message);
    };
    private Consumer<Boolean> clearMessage = b -> messageLabel.setText("");

    //Example code
    private Consumer<Boolean> create = b -> showCreateNewDeckForm();
    private Consumer<Integer> seeDeck = i -> displaySpecificDeck(State.getState().getDecks().get(i - 1));


    /**
     * Binds the vertical height of the scroll panes to the size of the Nodes inside them so that the scrollbar
     * activates when there is a vertical overflow. Also renders the contents of the decks and the display pane.
     */
    @FXML
    public void initialize() {
        deckScrollPane.vvalueProperty().bind(deckList.heightProperty());
        displayScrollPane.vvalueProperty().bind(displayContainer.heightProperty());
        onCreateNewDeck.setOnAction(e -> showCreateNewDeckForm());
        registerConsumers();
        displayMessage.accept("Welcome to FlashCard Pro!");
        deckList.setOnMouseClicked(e -> {
            Deck d = deckList.getSelectionModel().getSelectedItem();
            displaySpecificDeck(d);
            //TODO: need to check if currently in a review session. If so, might want to disable re-rendering
            // because that would terminate the review session unexpectedly. or we can just give the use
            // this extra flexibility - whether it's a flexibility or an annoyance depends on us.
        });
        quit.setOnAction(e -> {
            //Save all files only exit
            StorageManager.saveAll(State.getState().getDecks());
            System.exit(0);
        });
        javaEditor.setOnAction(e -> openEditor(true));
        jsEditor.setOnAction(e -> openEditor(false));
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
        ArrayList<Deck> decks = State.getState().getDecks();
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
     * Switches the display pane to an edit pane
     */
    private void showCreateNewDeckForm() {
        displayContainer.getChildren().clear();
        displayContainer.getChildren().add(new CreateDeckDisplay());
    }

    /**
     * When user wants to create/edit deck, put the editing pane.
     * Otherwise when the user clicks on a deck, put the deck display Pane containing info about the deck.
     * If there are no decks, and the user has not created a deck, display a pane asking user to create a
     * new deck.
     */
    private void renderDisplayPane() {
        if (State.getState().isEmpty()) {
            inviteUserToCreateDeckInDisplayPane();
        } else {
            //render the details of the first deck
            DeckDisplay deckDisplay = new DeckDisplay(State.getState().getDecks().get(0));
            displayContainer.getChildren().clear();
            displayContainer.getChildren().add(deckDisplay);
        }
    }

    /**
     * Changes the deck on display in the display pane to the selected one.
     */
    private void displaySpecificDeck(Deck d) {
        DeckDisplay deckDisplay = new DeckDisplay(d);
        displayContainer.getChildren().clear();
        displayContainer.getChildren().add(deckDisplay);
    }

    /**
     * Responsible for clearing text input area after user presses the Enter key.
     * Also responsible for getting the Responder class to parse and execute the entered command.
     */
    @FXML
    private void handleUserInput() {
        String input = commandLine.getText();
        Dispatcher.parseAndDispatch(input);
        commandLine.clear();
    }

    /**
     * Registers consumers in State for global access.
     */
    private void registerConsumers() {
        State.getState().addConsumer(ConsumerSchema.SWAP_DISPLAYS, swapDisplays);
        State.getState().addConsumer(ConsumerSchema.DISPLAY_DECKS, displayDecks);
        State.getState().addConsumer(ConsumerSchema.DISPLAY_MESSAGE, displayMessage);
        State.getState().addConsumer(ConsumerSchema.CLEAR_MESSAGE, clearMessage);

        //ignore the duplicates for now, if dispatcher carries all the consumers then transfer over from state
        Dispatcher.addConsumer(ConsumerSchema.SWAP_DISPLAYS, swapDisplays);
        Dispatcher.addConsumer(ConsumerSchema.DISPLAY_DECKS, displayDecks);
        Dispatcher.addConsumer(ConsumerSchema.DISPLAY_MESSAGE, displayMessage);
        Dispatcher.addConsumer(ConsumerSchema.CLEAR_MESSAGE, clearMessage);
        Dispatcher.addConsumer(ConsumerSchema.CREATE_NEW_DECK, create);
        Dispatcher.addConsumer(ConsumerSchema.SEE_SPECIFIC_DECK, seeDeck);
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
