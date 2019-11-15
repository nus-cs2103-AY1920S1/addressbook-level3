package dream.fcard.gui.controllers.cards.backview;

import java.io.IOException;

import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.exam.Exam;
import dream.fcard.logic.exam.ExamRunner;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.model.exceptions.IndexNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 * The rear view of a multiple choice card.
 */
public class McqCardBack extends VBox {
    @FXML
    private Label correctOrWrongLabel;
    @FXML
    private Button seeFrontButton;
    @FXML
    private Label correctAnswerLabel;

    public McqCardBack(MultipleChoiceCard card) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Cards/Back/MCQCardBack.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            seeFrontButton.setOnAction(e ->seeFront());
            correctOrWrongLabel.setText(checkAnswer(card));
            setColourOfLabel();
            int correctAnswer = card.getDisplayChoicesAnswerIndex();
            correctAnswerLabel.setText("Option " + correctAnswer + " is correct.");
        } catch (IOException | IndexNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the answer that the user input is correct.
     * Adds 1 to score if correct.
     * @param card the card the user is viewing.
     * @return "correct" or "wrong" accordingly
     * @throws IndexNotFoundException //exception
     */
    private String checkAnswer(MultipleChoiceCard card) throws IndexNotFoundException {
        boolean isCorrect = card.evaluate(Integer.toString(card.getUserAttempt()));
        if (isCorrect) {
            Consumers.doTask("GET_SCORE", true);
        } else {
            Consumers.doTask("GET_SCORE", false);
        }
        Consumers.doTask("UPDATE_MCQ_ATTEMPT", card.getUserAttempt());
        return isCorrect ? "Correct!" : "Wrong...";
    }

    private void setColourOfLabel() {
        if (correctOrWrongLabel.getText().equals("Correct!")) {
            correctOrWrongLabel.setTextFill(Paint.valueOf("#3fb710"));
        } else {
            correctOrWrongLabel.setTextFill(Paint.valueOf("#dc0b0b"));
        }
    }

    private void seeFront() {
        Exam exam = ExamRunner.getCurrentExam();
        Pane cardFront = exam.getCardDisplayFront();
        Consumers.doTask("SWAP_CARD_DISPLAY", cardFront);
    }
}
