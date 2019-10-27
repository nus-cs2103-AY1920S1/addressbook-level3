package dream.fcard.gui.controllers.Displays;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.Cards.BasicFrontBackCard;
import dream.fcard.gui.controllers.Cards.MCQCard;
import dream.fcard.gui.controllers.Cards.MCQCardBack;
import dream.fcard.gui.controllers.Cards.SimpleCardBack;
import dream.fcard.gui.controllers.Windows.MainWindow;
import dream.fcard.model.ConsumerSchema;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class TestDisplay extends AnchorPane {
    @FXML
    private AnchorPane cardDisplay;
    @FXML
    private Button prevButton;
    @FXML
    private Button endSessionButton;
    @FXML
    private Button nextButton;

    private FlashCard cardOnDisplay;
    private Deck deck;
    private Consumer<Integer> seeFrontOfMcqCard = i -> seeFrontOfMcqCard(i);
    private Consumer<Integer> seeBackOfMcqCard = i -> seeBackOfMcqCard(i);
    private Consumer<Boolean> seeFrontOfFrontBackCard = b -> seeFrontOfFrontBackCard();
    private Consumer<Boolean> seeBackofFrontBackCard = b -> seeBackOfFrontBackCard();
    @SuppressWarnings("unchecked")
    private Consumer<Boolean> displayDecks = State.getState().getConsumer(ConsumerSchema.DISPLAY_DECKS);
    @SuppressWarnings("unchecked")
    private Consumer<Boolean> clearMessage = State.getState().getConsumer(ConsumerSchema.CLEAR_MESSAGE);
    private int nowShowing;

    public TestDisplay(Deck deck) {
        try {
            clearMessage.accept(true);
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays/TestDisplay.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            this.deck = deck;
            nowShowing = 0;
            this.cardOnDisplay = deck.getCards().get(nowShowing); //show the first card - fails if no cards are present
            seeFirstCard();
            prevButton.setOnAction(e -> onShowPrevious());
            endSessionButton.setOnAction(e -> displayDecks.accept(true));
            nextButton.setOnAction(e -> onShowNext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void seeFirstCard() {
        FlashCard card = cardOnDisplay;
        cardDisplay.getChildren().clear();
        String typeOfCard = card.getClass().getSimpleName();
        if (typeOfCard.equals("FrontBackCard")) {
            seeFrontOfFrontBackCard();
        } else if (typeOfCard.equals("MultipleChoiceCard")) {
            seeFrontOfMcqCard(-1);
        }
    }

    private void seeFrontOfFrontBackCard() {
        cardDisplay.getChildren().clear();
        BasicFrontBackCard frontBackCard = new BasicFrontBackCard(cardOnDisplay, seeBackofFrontBackCard);
        cardDisplay.getChildren().add(frontBackCard);
    }

    private void seeBackOfFrontBackCard() {
        cardDisplay.getChildren().clear();
        String back = cardOnDisplay.getBack();
        SimpleCardBack backOfCard = new SimpleCardBack(back, seeFrontOfFrontBackCard);
        cardDisplay.getChildren().add(backOfCard);
    }

    private void seeFrontOfMcqCard(int previouslySelectedAnswer) { //-1 indicates the user is doing this card the first time
        cardDisplay.getChildren().clear();
        MCQCard mcqCard = new MCQCard((MultipleChoiceCard) cardOnDisplay, seeBackOfMcqCard, previouslySelectedAnswer);
        cardDisplay.getChildren().add(mcqCard);
    }

    private void seeBackOfMcqCard(int selectedAnswer) { // 1-based
        cardDisplay.getChildren().clear();
        MCQCardBack backCard = new MCQCardBack((MultipleChoiceCard) cardOnDisplay, selectedAnswer, seeFrontOfMcqCard);
        cardDisplay.getChildren().add(backCard);
    }

    private void onShowPrevious() {
        if (nowShowing != 0) {
            nowShowing--;
            cardOnDisplay = deck.getCards().get(nowShowing);
            seeFirstCard();
        }
    }

    private void onShowNext() {
        if (nowShowing != deck.getCards().size() - 1) {
            nowShowing++;
            cardOnDisplay = deck.getCards().get(nowShowing);
            seeFirstCard();
        }
    }
}
