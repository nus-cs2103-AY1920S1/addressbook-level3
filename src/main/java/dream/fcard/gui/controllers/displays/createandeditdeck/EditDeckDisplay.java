package dream.fcard.gui.controllers.displays.createandeditdeck;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.CardCreatingWindow;
import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.Deck;
import dream.fcard.model.StateHolder;
import dream.fcard.model.cards.FlashCard;
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
    private Button doneEditingButton;
    @FXML
    private Label deckName;
    @FXML
    private Label deckSize;
    @FXML
    private VBox cardCreatingPane;

    private int numCards;

    public EditDeckDisplay(Deck deck) {
        try {
            Consumers.doTask(ConsumerSchema.CLEAR_MESSAGE, true);
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays/"
                    + "EditDeckDisplay.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();

            Consumer<FlashCard> saveToDeck = c -> {
                deck.addNewCard(c);
                StorageManager.writeDeck(deck);
                Consumers.doTask(ConsumerSchema.RENDER_LIST, true);
            };

            Consumer<Integer> incrementNumCards = x -> {
                ++numCards;
                deckSize.setText(numCards + " cards");
            };

            CardCreatingWindow editingWindow = new CardCreatingWindow(incrementNumCards, saveToDeck);
            cardCreatingPane.getChildren().add(editingWindow);
            deckNameInput.setText(deck.getDeckName());
            numCards = deck.getCards().size();
            deckSize.setText(numCards + (numCards == 1 ? " card" : " cards"));
            doneEditingButton.setOnAction(e -> {
                int currentIndex = StateHolder.getState().hasDeckName(deck.getDeckName()); //can put in responses
                String name = deckNameInput.getText();
                if (!name.isBlank()) {
                    int index = StateHolder.getState().hasDeckName(name);
                    if (index == -1 || index == currentIndex) {
                        deck.setDeckName(name);
                    }
                }
                Consumers.doTask(ConsumerSchema.DISPLAY_DECKS, true);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
