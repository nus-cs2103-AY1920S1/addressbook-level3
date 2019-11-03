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
import dream.fcard.logic.respond.Responder;
import dream.fcard.logic.stats.Stats;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.StateEnum;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The single-page parent component.
 */
public class MainWindow extends VBox {

    @FXML
    private VBox deckScrollPane;
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
    private MenuItem statistics;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField commandLine;

    // Consumers
    private Consumer<Boolean> displayDecks = b -> render();
    private Consumer<Pane> swapDisplays = p -> {
        displayContainer.getChildren().clear();
        displayContainer.getChildren().add(p);
    };
    private Consumer<String> displayMessage = message -> {
        messageLabel.setText(message);
    };
    private Consumer<Boolean> clearMessage = b -> messageLabel.setText("");

    private Consumer<Boolean> create = b -> showCreateNewDeckForm();
    private Consumer<String> createWDeckName = s -> showCreateNewDeckForm(s);
    private Consumer<Integer> seeDeck = i -> displaySpecificDeck(
            State.getState().getDecks().get(i - 1));
    private Consumer<Boolean> exitCreate = b -> exitCreate();
    private Consumer<String> processInputCreate = s -> processInputCreate(s);

    private Consumer<Boolean> quitProgram = b -> quit();

    // Attributes
    private CreateDeckDisplay tempCreateDeckDisplay;
    private State currState;

    /**
     * Binds the vertical height of the scroll panes to the size of the Nodes inside them so that the scrollbar
     * activates when there is a vertical overflow. Also renders the contents of the decks and the display pane.
     */
    @FXML
    public void initialize() {
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
            quit();
        });
        javaEditor.setOnAction(e -> openEditor(true));
        jsEditor.setOnAction(e -> openEditor(false));
        statistics.setOnAction(e -> openStatistics());
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
     * Switches the display pane to an edit pane, used in initialising step/
     */
    @FXML
    private void showCreateNewDeckForm() {
        displayContainer.getChildren().clear();
        this.tempCreateDeckDisplay = new CreateDeckDisplay();
        displayContainer.getChildren().add(tempCreateDeckDisplay);
        State.getState().setCurrState(StateEnum.CREATE);
    }

    /**
     * Switches the display pane to create pane, used to enter StateEnun.CREATE.
     *
     * @param s The name of the deck being created.
     */
    private void showCreateNewDeckForm(String s) {
        displayContainer.getChildren().clear();
        this.tempCreateDeckDisplay = new CreateDeckDisplay(s);
        displayContainer.getChildren().add(tempCreateDeckDisplay);
        State.getState().setCurrState(StateEnum.CREATE);
        // CHANGE STATE SHOULD BE DONE IN RESPONSES
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
        Responder.takeInput(input, State.getState());
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
        State.getState().addConsumer(ConsumerSchema.EXIT_CREATE, exitCreate);


        //ignore the duplicates for now, if dispatcher carries all the consumers then transfer over from state
        Dispatcher.addConsumer(ConsumerSchema.SWAP_DISPLAYS, swapDisplays);
        Dispatcher.addConsumer(ConsumerSchema.DISPLAY_DECKS, displayDecks);
        Dispatcher.addConsumer(ConsumerSchema.DISPLAY_MESSAGE, displayMessage);
        Dispatcher.addConsumer(ConsumerSchema.CLEAR_MESSAGE, clearMessage);
        Dispatcher.addConsumer(ConsumerSchema.CREATE_NEW_DECK, create);
        Dispatcher.addConsumer(ConsumerSchema.CREATE_NEW_DECK_W_NAME, createWDeckName);
        Dispatcher.addConsumer(ConsumerSchema.SEE_SPECIFIC_DECK, seeDeck);
        Dispatcher.addConsumer(ConsumerSchema.QUIT_PROGRAM, quitProgram);
        Dispatcher.addConsumer(ConsumerSchema.EXIT_CREATE, exitCreate);
        Dispatcher.addConsumer(ConsumerSchema.PROCESS_INPUT, processInputCreate);

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

    /**
     * Quits from the entire program. Saves the decks to a file first.
     */
    public void quit() {
        // end the current session
        Stats.endCurrentSession();

        // save all files only on exit
        StorageManager.saveAll(State.getState().getDecks());
        StorageManager.saveStats();
        System.exit(0);
    }

    /**
     * Saves and exits from Create mode.
     */
    public void exitCreate() {
        tempCreateDeckDisplay.onSaveDeck();
        State.getState().setCurrState(StateEnum.DEFAULT);
        // State changes should be done in responses
    }

    /**
     * Handles the input when State is in Create mode.
     *
     * @param input
     */
    public void processInputCreate(String input) {
        tempCreateDeckDisplay.processInput(input);
    }
    /**
     * Quits from the entire program. Saves the decks to a file first.
     * Opens a new window to show the user's statistics.
     */
    @FXML
    public void openStatistics() {
        // when Logger is implemented, log "Opening Statistics window..."

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(new StatisticsWindow());
        stage.setScene(scene);
        stage.setTitle("My Statistics");
        stage.show();
    }
}
