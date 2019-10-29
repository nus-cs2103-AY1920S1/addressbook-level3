package dream.fcard.gui.controllers.displays;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.CardCreatingWindow;
import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * The pane for adding questions to a deck as well as changing the deck's name.
 */
public class EditDeckDisplay extends AnchorPane {
    @FXML
    private TextField deckNameInput;
    @FXML
    private Button onSaveDeck;
    @FXML
    private Button cancelButton;
    @FXML
    private Label deckName;
    @FXML
    private Label deckSize;
    @FXML
    private ScrollPane cardCreatingPane;

    @SuppressWarnings("unchecked")
    private Consumer<String> displayMessage = State.getState().getConsumer(ConsumerSchema.DISPLAY_MESSAGE);
    @SuppressWarnings("unchecked")
    private Consumer<Boolean> clearMessage = State.getState().getConsumer(ConsumerSchema.CLEAR_MESSAGE);
    @SuppressWarnings("unchecked")
    private Consumer<Pane> swapDisplays = State.getState().getConsumer(ConsumerSchema.SWAP_DISPLAYS);
    @SuppressWarnings("unchecked")
    private Consumer<Boolean> exitEditingMode = State.getState().getConsumer(ConsumerSchema.DISPLAY_DECKS);

    private int numCards;
    private CardCreatingWindow editingWindow;
    private Deck deck;

    private Consumer<Integer> incrementNumCards = x -> {
        ++numCards;
        deckSize.setText(numCards + " cards");
    };

    public EditDeckDisplay(Deck deck) {
        try {
            clearMessage.accept(true);
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays/"
                    + "CreateDeckDisplay.fxml")); // same ui component as creating a deck, but different handlers
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            this.deck = deck;
            editingWindow = new CardCreatingWindow(incrementNumCards);
            cardCreatingPane.setContent(editingWindow);
            clearMessage.accept(true);
            deckNameInput.setText(deck.getName());
            numCards = deck.getCards().size();
            deckSize.setText(numCards + (numCards == 1 ? " card" : " cards"));
            onSaveDeck.setOnAction(e -> onSaveDeck());
            cancelButton.setOnAction(e -> exitEditingMode.accept(true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Note that the temporary deck is inside CardCreatingWindow. This method pulls that Deck object out and saves it
     * to the State.
     */
    void onSaveDeck() {
        if (editingWindow != null) {
            Deck tempDeck = editingWindow.getTempDeck();
            tempDeck.getCards().forEach(card -> deck.addNewCard(card));
            String deckName = deckNameInput.getText();
            if (deckName.isBlank()) { // in case the user accidentally deletes the deck name
                displayMessage.accept("You need to give your deck a name!");
                return;
            }
            deck.setDeckName(deckName);
            displayMessage.accept("Your changes have been saved.");
        }
        exitEditingMode.accept(true);
    }
}
