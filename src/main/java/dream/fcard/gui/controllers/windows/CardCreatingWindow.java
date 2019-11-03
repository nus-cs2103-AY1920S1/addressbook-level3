package dream.fcard.gui.controllers.windows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.displays.createandeditdeck.javacard.JavaTestCaseInputBox;
import dream.fcard.gui.controllers.displays.createandeditdeck.jscard.JsTestCaseInputTextArea;
import dream.fcard.gui.controllers.displays.createandeditdeck.mcqcard.McqOptionsSetter;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.model.Deck;
import dream.fcard.model.TestCase;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.cards.JavaCard;
import dream.fcard.model.cards.JavascriptCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Creates a card within CreateDeckDisplay.
 */
public class CardCreatingWindow extends VBox {
    @FXML
    private TextField questionField;
    @FXML
    private VBox answerContainer;
    @FXML
    private ChoiceBox<String> cardTypeSelector;
    @FXML
    private Button onAddQuestion;
    @FXML
    private Label addAnswerLabel;

    /**
     * Strings used to represent card types.
     */
    private final String frontBack = "Front-back";
    private final String mcq = "MCQ";
    private final String js = "JavaScript";
    private final String java = "Java";

    /**
     * Controllers for the card inputs
     */
    private TextArea frontBackTextArea;
    private McqOptionsSetter mcqOptionsSetter;
    private JsTestCaseInputTextArea jsTestCaseInputTextArea;
    private JavaTestCaseInputBox javaTestCaseInputBox;

    private String cardType = "";
    private Deck tempDeck = new Deck();
    private String testCases;
    private Consumer<Integer> incrementCounterInParent;


    public CardCreatingWindow(Consumer<Integer> incrementCounterInParent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Windows/CardCreatingWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            cardTypeSelector.getItems().addAll(frontBack, mcq, js, java);
            cardTypeSelector.setOnAction(e -> {
                String currentlySelected = cardTypeSelector.getValue();
                if (!cardType.equals(currentlySelected)) {
                    cardType = currentlySelected;
                    changeInputBox(cardType);
                }
            });
            cardTypeSelector.setValue(frontBack);
            onAddQuestion.setOnAction(e -> addCardToDeck());
            this.incrementCounterInParent = incrementCounterInParent;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the input box according to the card the user is making.
     *
     * @param cardType the type of card the user is making.
     */
    void changeInputBox(String cardType) {
        answerContainer.getChildren().clear();
        if (cardType.equals(frontBack)) {
            frontBackTextArea = new TextArea();
            answerContainer.getChildren().add(frontBackTextArea);
            addAnswerLabel.setText("Add your answer");
        } else if (cardType.equals(mcq)) {
            mcqOptionsSetter = new McqOptionsSetter();
            answerContainer.getChildren().add(mcqOptionsSetter);
            addAnswerLabel.setText("Enter your options");
        } else if (cardType.equals(js)) {
            jsTestCaseInputTextArea = new JsTestCaseInputTextArea();
            answerContainer.getChildren().add(jsTestCaseInputTextArea);
            addAnswerLabel.setText("Add your asserts");
        } else if (cardType.equals(java)) {
            javaTestCaseInputBox = new JavaTestCaseInputBox();
            answerContainer.getChildren().add(javaTestCaseInputBox);
            addAnswerLabel.setText("Add your test cases");
        }
    }

    /**
     * Adds a card to the temporary deck inside CardCreatingWindow.
     *
     */
    void addCardToDeck() {
        if (cardType.equals(mcq)) {
            //validation - non-empty question, at least one non-empty option, and a designated right answer
            if (questionField.getText().isBlank()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter a question!");
                return;
            } else if (!mcqOptionsSetter.hasAtLeastOneNonEmptyOption()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter at least 1 option!");
                return;
            } else if (!mcqOptionsSetter.hasDesignatedRightAnswer()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to tell me which answer is correct!");
                return;
            }

            String front = questionField.getText();
            String back = Integer.toString(mcqOptionsSetter.getIndexOfRightAnswer()); //already 1-indexed
            ArrayList<String> choices = mcqOptionsSetter.getChoices();

            int numChoices = choices.size();
            for (int i = 0; i < numChoices; i++) {
                String curr = choices.remove(0);
                if (!curr.equals("")) {
                    choices.add(curr);
                }
            }

            MultipleChoiceCard mcqCard = new MultipleChoiceCard(front, back, choices);
            tempDeck.addNewCard(mcqCard);
        } else if (cardType.equals(frontBack)) {
            // validation - non-empty fields
            if (questionField.getText().isBlank()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter a question!");
                return;
            } else if (frontBackTextArea.getText().isBlank()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter an answer!");
                return;
            }

            String front = questionField.getText();
            String back = frontBackTextArea.getText();
            FrontBackCard card = new FrontBackCard(front, back);
            tempDeck.addNewCard(card);
        } else if (cardType.equals(js)) {
            if (questionField.getText().isBlank()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter a question!");
                return;
            }

            if (!jsTestCaseInputTextArea.hasTestCase()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter a test case!");
                return;
            }
            String testCases = jsTestCaseInputTextArea.getAssertions();
            String front = questionField.getText();
            JavascriptCard card = new JavascriptCard(front, testCases);
            tempDeck.addNewCard(card);

        } else if (cardType.equals(java)) {
            if (questionField.getText().isBlank()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter a question!");
                return;
            }
            if (!javaTestCaseInputBox.hasAtLeastOneTestCase()) {
                Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to enter a test case!");
                return;
            }

            String front = questionField.getText();
            ArrayList<TestCase> testCases = javaTestCaseInputBox.getTestCases();
            JavaCard card = new JavaCard(front, testCases);
            tempDeck.addNewCard(card);
        }
        incrementCounterInParent.accept(1);
        clearFields();
    }

    /**
     * Wipe user input from existing input fields to make way for a new card.
     */
    void clearFields() {
        questionField.setText("");
        if (frontBackTextArea != null) {
            frontBackTextArea.setText("");
        }
        if (mcqOptionsSetter != null) {
            mcqOptionsSetter = new McqOptionsSetter();
            answerContainer.getChildren().clear();
            answerContainer.getChildren().add(mcqOptionsSetter);
        }
        if (jsTestCaseInputTextArea != null) {
            jsTestCaseInputTextArea = new JsTestCaseInputTextArea();
            answerContainer.getChildren().clear();
            answerContainer.getChildren().add(jsTestCaseInputTextArea);
        }
        if (javaTestCaseInputBox != null) {
            javaTestCaseInputBox = new JavaTestCaseInputBox();
            answerContainer.getChildren().clear();
            answerContainer.getChildren().add(javaTestCaseInputBox);
        }
    }

    /**
     * A deck that keeps all the cards that were made inside CardCreatingWindow. CreateDeckDisplay
     * will pull out this deck when the user is done making a new deck.
     *
     * @return the deck of all newly created cards.
     */
    public Deck getTempDeck() {
        return tempDeck;
    }

    public void setCardType(String s) {
        this.cardType = s;
    }

    public void setQuestionFieldText(String s) {
        this.questionField.setText(s);
    }

    public void setAnswerFieldText(String s) {
        this.frontBackTextArea.setText(s);
    }

    public void setTestCases (String s) {
        this.testCases = s;
    }

    /**
     * Allows public access to the addCardToDeck method.
     */
    public void publicAddCard() {
        addCardToDeck();
    }

    public void publicChangeInputBox(String type) {
        changeInputBox(type);
    }

    public McqOptionsSetter getMcqOptionsSetter() {
        return this.mcqOptionsSetter;
    }

    public String getCardType() {
        return cardType;
    }
}
