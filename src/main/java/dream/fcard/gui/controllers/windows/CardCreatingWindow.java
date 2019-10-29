package dream.fcard.gui.controllers.windows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.displays.JsFileUploader;
import dream.fcard.gui.controllers.displays.McqOptionsSetter;
import dream.fcard.gui.controllers.displays.TestCaseUploader;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.cards.JavascriptCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.model.exceptions.DuplicateInChoicesException;
import dream.fcard.model.exceptions.IndexNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Creates a card within CreateDeckDisplay.
 */
public class CardCreatingWindow extends AnchorPane {
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

    private final String frontBack = "Front-back";
    private final String mcq = "MCQ";
    //private final String java = "Java";
    private final String js = "JavaScript";

    private TextArea frontBackTextArea;
    private McqOptionsSetter mcqOptionsSetter;
    private TestCaseUploader testCaseUploader;
    private JsFileUploader jsFileUploader;

    private String cardType = "";
    private Deck tempDeck = new Deck();
    private Consumer<Integer> incrementCounterInParent;
    @SuppressWarnings("unchecked")
    private Consumer<String> displayMessage = State.getState().getConsumer(ConsumerSchema.DISPLAY_MESSAGE);

    public CardCreatingWindow(Consumer<Integer> incrementCounterInParent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Windows/CardCreatingWindow.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            cardTypeSelector.getItems().addAll(frontBack, mcq, js);
            cardTypeSelector.setOnAction(e -> {
                String currentlySelected = cardTypeSelector.getValue();
                if (!cardType.equals(currentlySelected)) {
                    cardType = currentlySelected;
                    changeInputBox(cardType);
                }
            });
            cardTypeSelector.setValue(frontBack);
            onAddQuestion.setOnAction(e -> {
                try {
                    addCardToDeck();
                } catch (DuplicateInChoicesException | IndexNotFoundException ex) {
                    displayMessage.accept("You have duplicated options!");
                }
            });
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
            jsFileUploader = new JsFileUploader();
            answerContainer.getChildren().add(jsFileUploader);
            addAnswerLabel.setText("Upload your test file");
        }
    }

    /**
     * Adds a card to the temporary deck inside CardCreatingWindow.
     *
     * @throws DuplicateInChoicesException if the user enters the same multiple choice option more than once.
     */
    void addCardToDeck() throws DuplicateInChoicesException, IndexNotFoundException {
        if (cardType.equals(mcq)) {
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
            MultipleChoiceCard mcqCard = new MultipleChoiceCard(front, back, choices);
            tempDeck.addNewCard(mcqCard);
        } else if (cardType.equals(frontBack)) {
            // validation - non-empty fields
            if (questionField.getText().isBlank()) {
                displayMessage.accept("You need to enter a question!");
                return;
            } else if (frontBackTextArea.getText().isBlank()) {
                displayMessage.accept("You need to enter an answer!");
                return;
            }

            String front = questionField.getText();
            String back = frontBackTextArea.getText();
            FrontBackCard card = new FrontBackCard(front, back);
            tempDeck.addNewCard(card);
        } else if (cardType.equals(js)) {
            if (questionField.getText().isBlank()) {
                displayMessage.accept("You need to enter a question!");
                return;
            }
            if (!jsFileUploader.hasFile()) {
                displayMessage.accept("You need to upload a JS file");
                return;
            }
            try {
                String testCases = jsFileUploader.getAssertions();
                String front = questionField.getText();
                JavascriptCard card = new JavascriptCard(front, testCases);
                tempDeck.addNewCard(card);
            } catch (FileNotFoundException e) {
                displayMessage.accept("I could not read in your file. Please try again");
                return;
            }

        }
        //else if (cardType.equals(java)) {
        //    //validation - all files uploaded
        //    if (questionField.getText().isBlank()) {
        //        displayMessage.accept("You need to enter a question!");
        //        return;
        //    }
        //    //for more specific file-hunting, the error messages are printed within JavaTestCaseInputRow so
        //    //we don't need to directly handle checking for missing files here.
        //    if (testCaseUploader.hasNoMissingFiles()) {
        //        ArrayList<TestCase> cases = testCaseUploader.getTestCases();
        //        String front = questionField.getText();
        //        JavaCard card = new JavaCard(front, cases);
        //        tempDeck.addNewCard(card);
        //    }
        //}
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
        if (jsFileUploader != null) {
            jsFileUploader = new JsFileUploader();
            answerContainer.getChildren().clear();
            answerContainer.getChildren().add(jsFileUploader);
        }
        //if (testCaseUploader != null) {
        //    testCaseUploader = new TestCaseUploader();
        //    answerContainer.getChildren().clear();
        //    answerContainer.getChildren().add(testCaseUploader);
        //}
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
}
