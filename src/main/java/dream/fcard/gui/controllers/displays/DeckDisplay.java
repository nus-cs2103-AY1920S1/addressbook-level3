package dream.fcard.gui.controllers.displays;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.CardEditingWindow;
import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.model.exceptions.IndexNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * The pane to view the questions of a deck and access deck editing functionality.
 */
public class DeckDisplay extends AnchorPane {
    @FXML
    private Label deckName;
    @FXML
    private Label deckSize;
    @FXML
    private Button startTest;
    @FXML
    private VBox questionList;
    @FXML
    private Button deleteDeckButton;
    @FXML
    private Button addQuestionButton;

    /**
     * Allows deck display to trigger a change of displays in the parent container MainWindow
     */
    @SuppressWarnings("unchecked")
    private Consumer<Pane> swapDisplaysInMain = State.getState().getConsumer(ConsumerSchema.SWAP_DISPLAYS);
    @SuppressWarnings("unchecked")
    private Consumer<String> displayMessage = State.getState().getConsumer(ConsumerSchema.DISPLAY_MESSAGE);
    @SuppressWarnings("unchecked")
    private Consumer<Boolean> displayDecks = State.getState().getConsumer(ConsumerSchema.DISPLAY_DECKS);

    private Consumer<Integer> deleteCard = this::deleteCard;
    private Consumer<Integer> editCard = this::editCard;
    private Deck deck;
    private Consumer<Pane> swapDisplaysInDeckDisplay = p -> {
        questionList.getChildren().clear();
        questionList.getChildren().add(p);
    };

    public DeckDisplay(Deck deck) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays/DeckDisplay.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            this.deck = deck;
            deckName.setText(deck.getName());
            renderQuestions();
            deleteDeckButton.setOnAction(e -> {
                try {
                    deleteDeck();
                } catch (DeckNotFoundException ex) {
                    ex.printStackTrace();
                }
            });
            startTest.setOnAction(e -> startTest());
            addQuestionButton.setOnAction(e -> addQuestion());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Starts a flashcard review session.
     */
    private void startTest() {
        //display the first card
        TestDisplay testDisplay = new TestDisplay(deck);
        swapDisplaysInMain.accept(testDisplay);
    }

    /**
     * Displays the questions of the deck inside DeckDisplay as a list of CardTitles.
     */
    private void renderQuestions() {
        questionList.getChildren().clear();
        ArrayList<FlashCard> cards = deck.getCards();
        int numCards = cards.size();
        deckSize.setText(numCards + (numCards == 1 ? " card" : " cards"));
        for (int i = 0; i < numCards; i++) {
            CardTitle cardTitle = new CardTitle(cards.get(i), i + 1, deleteCard, editCard);
            questionList.getChildren().add(cardTitle);
        }
        if (numCards == 1) { //disable the delete button if user has only 1 card in the deck
            CardTitle cardTitle = (CardTitle) questionList.getChildren().get(0);
            cardTitle.disableDelete();
        }
    }

    /**
     * Used by CardTitle to trigger a deletion of a card.
     * @param index the 1-based index of a card.
     */
    private void deleteCard(int index) {
        if (deck.getCards().size() == 1) {
            displayMessage.accept("Your deck needs at least 1 card!");
            return;
        }
        try {
            deck.removeCardFromDeck(index);
            renderQuestions();
        } catch (IndexNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteDeck() throws DeckNotFoundException {
        State state = State.getState();
        state.removeDeck(deck.getName());
        displayDecks.accept(true);
    }

    private void addQuestion() {
        EditDeckDisplay display = new EditDeckDisplay(deck);
        swapDisplaysInMain.accept(display);
    }

    /**
     * Replace a card with an edited one.
     */
    private void editCard(int index) {
        FlashCard cardToEdit = deck.getCards().get(index - 1);
        Consumer<FlashCard> onSave = card -> {
            deck.getCards().set(index - 1, card);
        };
        Consumer<Boolean> onCancel = b -> renderQuestions();
        CardEditingWindow window = new CardEditingWindow(cardToEdit, onSave, onCancel);
        questionList.getChildren().clear();
        questionList.getChildren().add(window);

    }

}
