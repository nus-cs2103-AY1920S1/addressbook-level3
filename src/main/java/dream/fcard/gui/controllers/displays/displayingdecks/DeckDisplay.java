package dream.fcard.gui.controllers.displays.displayingdecks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.displays.createandeditdeck.EditDeckDisplay;
import dream.fcard.gui.controllers.displays.test.EndOfTestAlert;
import dream.fcard.gui.controllers.displays.test.TestDisplay;
import dream.fcard.gui.controllers.windows.CardEditingWindow;
import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.exam.Exam;
import dream.fcard.logic.exam.ExamRunner;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.StateEnum;
import dream.fcard.model.StateHolder;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.model.exceptions.IndexNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * The pane to view the questions of a deck and access deck editing functionality.
 */
public class DeckDisplay extends VBox {
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
            deckName.setText("Deck: " + deck.getDeckName());
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
        if (deck.getNumberOfCards() == 0) {
            EndOfTestAlert.display("Error", "You cannot start a test on an empty deck!");
            StateHolder.getState().setCurrState(StateEnum.DEFAULT);
        } else {
            ArrayList<FlashCard> testArrayListOfCards = deck.getSubsetForTest();
            ExamRunner.createExam(testArrayListOfCards, 0);
            Exam exam = ExamRunner.getCurrentExam();
            TestDisplay testDisplay = new TestDisplay(exam);
            Consumers.doTask(ConsumerSchema.SWAP_DISPLAYS, testDisplay);
        }
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
            Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Your deck needs at least 1 card!");
            return;
        }
        try {
            deck.removeCard(index);
            renderQuestions();
        } catch (IndexNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a deck.
     * @throws DeckNotFoundException
     */
    private void deleteDeck() throws DeckNotFoundException { //can be in Responses
        State state = StateHolder.getState();
        state.removeDeck(deck.getDeckName());
        StorageManager.deleteDeck(deck);
        Consumers.doTask(ConsumerSchema.DISPLAY_DECKS, true);
    }

    private void addQuestion() {
        EditDeckDisplay display = new EditDeckDisplay(deck);
        Consumers.doTask(ConsumerSchema.SWAP_DISPLAYS, display);
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
