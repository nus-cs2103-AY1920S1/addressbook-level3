package dream.fcard.gui.controllers.windows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.displays.createandeditdeck.javacard.JavaTestCaseInputBox;
import dream.fcard.gui.controllers.displays.createandeditdeck.jscard.JsTestCaseInputTextArea;
import dream.fcard.gui.controllers.displays.createandeditdeck.mcqcard.McqOptionsSetter;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.model.TestCase;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.cards.JavaCard;
import dream.fcard.model.cards.JavascriptCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * A window that looks like CardCreatingWindow but which opens in DeckDisplay for the user to quickly edit a card.
 */
// todo: suggestion - instead of opening within DeckDisplay, do the same as CardCreatingWindow, so
//  that the "Save Deck" and "Cancel" buttons appear too
public class CardEditingWindow extends VBox {
    @FXML
    private TextField questionField;
    @FXML
    private VBox answerContainer;
    @FXML
    private ChoiceBox<String> cardTypeSelector;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Button cancelButton;

    private TextArea frontBackTextArea;
    private McqOptionsSetter mcqOptionsSetter;
    private JsTestCaseInputTextArea jsTestCaseInputTextArea;
    private JavaTestCaseInputBox javaTestCaseInputBox;

    private String cardType = "";
    private final String frontBack = "Front-back";
    private final String mcq = "MCQ";
    private final String java = "Java";
    private final String js = "JavaScript";

    private FlashCard card;


    public CardEditingWindow(FlashCard card, Consumer<FlashCard> onSave, Consumer<Boolean> onCancel) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Windows/CardEditingWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            this.card = card;
            cardTypeSelector.getItems().addAll(frontBack, mcq, js, java);
            cardTypeSelector.setOnAction(e -> {
                String currentlySelected = cardTypeSelector.getValue();
                if (!cardType.equals(currentlySelected)) {
                    cardType = currentlySelected;
                    changeInputBox(currentlySelected);
                }
            });
            questionField.setText(card.getFront());
            cancelButton.setOnAction(e -> onCancel.accept(true));
            saveChangesButton.setOnAction(e -> {
                Consumers.doTask(ConsumerSchema.CLEAR_MESSAGE, true);
                FlashCard newCard = getEditedCard();
                if (newCard == null) {
                    return;
                } // card editing has mistakes, keep the editing window open
                onSave.accept(newCard);
                onCancel.accept(true);
            });
            populateExistingAnswers();
        } catch (IOException e) {
            //TODO: replace with logger
            e.printStackTrace();
        }
    }

    /**
     * Changes the input box from a textbox to the MCQ setter and vice versa.
     *
     * @param cardType the type of card.
     */
    void changeInputBox(String cardType) {
        answerContainer.getChildren().clear();
        if (cardType.equals(frontBack)) {
            frontBackTextArea = new TextArea();
            answerContainer.getChildren().add(frontBackTextArea);
        } else if (cardType.equals(mcq)) {
            mcqOptionsSetter = new McqOptionsSetter();
            answerContainer.getChildren().add(mcqOptionsSetter);
        } else if (cardType.equals(js)) {
            jsTestCaseInputTextArea = new JsTestCaseInputTextArea();
            answerContainer.getChildren().add(jsTestCaseInputTextArea);
        } else if (cardType.equals(java)) {
            javaTestCaseInputBox = new JavaTestCaseInputBox();
            answerContainer.getChildren().add(javaTestCaseInputBox);
        }
    }

    /**
     * Take the card's existing answer(s) and add them to the cardEditingWindow.
     */
    private void populateExistingAnswers() {
        if (card instanceof MultipleChoiceCard) {
            ArrayList<String> choices = ((MultipleChoiceCard) card).generateCopyOfChoices();
            cardTypeSelector.setValue(mcq);
            mcqOptionsSetter.deleteFirstRow(); //take out the empty first row
            for (int i = 0; i < choices.size(); i++) {
                if (((MultipleChoiceCard) card).getCorrectAnswerIndex() - 1 == i) {
                    mcqOptionsSetter.addNewRow(choices.get(i), true);
                    continue;
                }
                mcqOptionsSetter.addNewRow(choices.get(i), false);
            }
        } else if (card instanceof FrontBackCard) {
            cardTypeSelector.setValue(frontBack);
            frontBackTextArea.setText(card.getBack());
        } else if (card instanceof JavascriptCard) {
            cardTypeSelector.setValue(js);
            jsTestCaseInputTextArea.setTextArea(card.getBack());
        } else if (card instanceof JavaCard) {
            cardTypeSelector.setValue(java);
            javaTestCaseInputBox.setTestCaseRows(((JavaCard) card).getTestCases());
        }
    }

    FlashCard getEditedCard() {
        if (cardType.equals(mcq)) {
            //validation - non-empty question, at least one non-empty option, and a designated right answer
            if (questionField.getText().isBlank()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter a question!");
                return null;
            } else if (!mcqOptionsSetter.hasAtLeastOneNonEmptyOption()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter at least 1 option!");
                return null;
            } else if (!mcqOptionsSetter.hasDesignatedRightAnswer()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to tell me which answer is correct!");
                return null;
            }

            String front = questionField.getText();
            String back = Integer.toString(mcqOptionsSetter.getIndexOfRightAnswer()); //already 1-indexed
            ArrayList<String> choices = mcqOptionsSetter.getChoices();
            return new MultipleChoiceCard(front, back, choices);

        } else if (cardType.equals(frontBack)) {
            // validation - non-empty fields
            if (questionField.getText().isBlank()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter a question!");
                return null;
            } else if (frontBackTextArea.getText().isBlank()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter an answer!");
                return null;
            }

            String front = questionField.getText();
            String back = frontBackTextArea.getText(); // NullPointerException will not happen
            return new FrontBackCard(front, back);

        } else if (cardType.equals(js)) {
            // validation
            if (questionField.getText().isBlank()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter a question!");
                return null;
            } else if (!jsTestCaseInputTextArea.hasTestCase()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter a test case!");
                return null;
            }

            String front = questionField.getText();
            String back = jsTestCaseInputTextArea.getAssertions();
            return new JavascriptCard(front, back);
        } else if (cardType.equals(java)) {
            //validation
            if (questionField.getText().isBlank()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter a question!");
                return null;
            }
            if (!javaTestCaseInputBox.hasAtLeastOneTestCase()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter a test case!");
                return null;
            }

            String front = questionField.getText();
            ArrayList<TestCase> testCases = javaTestCaseInputBox.getTestCases();
            return new JavaCard(front, testCases);
        }
        return null;
    }


}
