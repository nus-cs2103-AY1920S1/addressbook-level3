package dream.fcard.gui.controllers.displays.createandeditdeck;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.CardCreatingWindow;
import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.model.Deck;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * The pane for adding questions to a deck as well as changing the deck's name.
 */
public class EditDeckDisplay extends VBox {
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
    private VBox cardCreatingPane;

    private int numCards;
    private CardCreatingWindow editingWindow;
    private Deck deck;

    private Consumer<Integer> incrementNumCards = x -> {
        ++numCards;
        deckSize.setText(numCards + " cards");
    };

    public EditDeckDisplay(Deck deck) {
        try {
            Consumers.doTask(ConsumerSchema.CLEAR_MESSAGE, true);
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays/"
                    + "CreateDeckDisplay.fxml")); // same ui component as creating a deck, but different handlers
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            this.deck = deck;
            editingWindow = new CardCreatingWindow(incrementNumCards);
            cardCreatingPane.getChildren().add(editingWindow);
            deckNameInput.setText(deck.getName());
            numCards = deck.getCards().size();
            deckSize.setText(numCards + (numCards == 1 ? " card" : " cards"));
            onSaveDeck.setOnAction(e -> onSaveDeck());
            cancelButton.setOnAction(e -> Consumers.doTask(ConsumerSchema.DISPLAY_DECKS, true));
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
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to give your deck a name!");
                return;
            }
            deck.setDeckName(deckName);
            Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Your changes have been saved.");
        }
        Consumers.doTask(ConsumerSchema.DISPLAY_DECKS, true);
    }
}
