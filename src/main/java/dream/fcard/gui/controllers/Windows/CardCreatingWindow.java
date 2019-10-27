package dream.fcard.gui.controllers.Windows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.Displays.MCQOptionsSetter;
import dream.fcard.model.ConsumerSchema;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.model.exceptions.DuplicateInChoicesException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class CardCreatingWindow extends AnchorPane {
    @FXML
    private TextField questionField;
    @FXML
    private VBox answerContainer;
    @FXML
    private ChoiceBox<String> cardTypeSelector;
    @FXML
    private Button onAddQuestion;

    private TextArea frontBackTextArea;
    private MCQOptionsSetter mcqOptionsSetter;
    private String cardType = "";
    private Deck tempDeck = new Deck();
    private Consumer<Integer> incrementCounterInParent;
    @SuppressWarnings("unchecked")
    private Consumer<String> displayMessage = State.getState().getConsumer(ConsumerSchema.DISPLAY_MESSAGE);

    public CardCreatingWindow(Consumer<Integer> incrementCounterInParent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Windows/CardCreatingWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            cardTypeSelector.getItems().addAll("Front-back", "MCQ");
            cardTypeSelector.setOnAction(e -> {
                String currentlySelected = cardTypeSelector.getValue();
                if (!cardType.equals(currentlySelected)) {
                    cardType = currentlySelected;
                    changeInputBox(cardType.equals("MCQ"));
                }
            });
            cardTypeSelector.setValue("Front-back");
            onAddQuestion.setOnAction(e -> {
                try {
                    addCardToDeck();
                } catch (DuplicateInChoicesException ex) {
                    displayMessage.accept("You have duplicated options!");
                }
            });
            this.incrementCounterInParent = incrementCounterInParent;
        } catch (IOException e) {
            //TODO: replace with logger
            e.printStackTrace();
        }
    }

    /**
     * Changes the input box from a textbox to the MCQ setter and vice versa.
     *
     * @param isMCQ whether the user is trying to create an MCQ card
     */
    void changeInputBox(boolean isMCQ) {
        answerContainer.getChildren().clear();
        if (!isMCQ) {
            frontBackTextArea = new TextArea();
            answerContainer.getChildren().add(frontBackTextArea);
        } else {
            mcqOptionsSetter = new MCQOptionsSetter();
            answerContainer.getChildren().add(mcqOptionsSetter);
        }
    }


    void addCardToDeck() throws DuplicateInChoicesException {
        State state = State.getState();
        if (cardType.equals("MCQ")) {
            //validation - non-empty question, at least one non-empty option, and a designated right answer
            if (questionField.getText().isBlank()) {
                displayMessage.accept("You need to enter a question!");
                return;
            } else if (!mcqOptionsSetter.hasAtLeastOneNonEmptyOption()) {
                displayMessage.accept("You need to enter at least 1 option!");
                return;
            } else if (!mcqOptionsSetter.hasDesignatedRightAnswer()) {
                displayMessage.accept("You need to tell me which answer is correct!");
                return;
            }

            String front = questionField.getText();
            String back = Integer.toString(mcqOptionsSetter.getIndexOfRightAnswer()); //already 1-indexed
            ArrayList<String> choices = mcqOptionsSetter.getChoices();
            MultipleChoiceCard mcqCard = new MultipleChoiceCard(front,back,choices);
            tempDeck.addNewCard(mcqCard);
        } else { //front-back
            // validation - non-empty fields
            if (questionField.getText().isBlank()) {
                displayMessage.accept("You need to enter a question!");
                return;
            } else if (frontBackTextArea.getText().isBlank()) {
                displayMessage.accept("You need to enter an answer!");
                return;
            }

            String front = questionField.getText();
            String back = frontBackTextArea.getText(); // I don't think NullPointerException will happen
            FrontBackCard card = new FrontBackCard(front, back);
            tempDeck.addNewCard(card);
        }
        incrementCounterInParent.accept(1);
        clearFields();
    }

    void clearFields() {
        questionField.setText("");
        if (frontBackTextArea != null) {
            frontBackTextArea.setText("");
        }
        if (mcqOptionsSetter != null) {
            mcqOptionsSetter = new MCQOptionsSetter();
            answerContainer.getChildren().clear();
            answerContainer.getChildren().add(mcqOptionsSetter);
        }
    }

    public Deck getTempDeck() {
        return tempDeck;
    }
}
