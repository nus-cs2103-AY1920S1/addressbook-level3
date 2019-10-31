package dream.fcard.gui.controllers.cards.backview;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.model.exceptions.IndexNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

/**
 * The rear view of a multiple choice card.
 */
public class McqCardBack extends AnchorPane {
    @FXML
    private Label correctOrWrongLabel;
    @FXML
    private Button seeFrontButton;
    @FXML
    private Label correctAnswerLabel;

    private Consumer<Boolean> updateScore;
    private Consumer<Integer> updateUserInput;

    public McqCardBack(MultipleChoiceCard card, Consumer<Boolean> seeFrontOfMcqCard,
                       Consumer<Boolean> updateScore, Consumer<Integer> updateUserInput) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class
                    .getResource("/view/Cards/Back/MCQCardBack.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            this.updateScore = updateScore;
            this.updateUserInput = updateUserInput;
            seeFrontButton.setOnAction(e -> seeFrontOfMcqCard.accept(true));
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
            updateScore.accept(true);
        } else {
            updateScore.accept(false);
        }
        updateUserInput.accept(card.getUserAttempt());
        return isCorrect ? "Correct!" : "Wrong...";
    }

    private void setColourOfLabel() {
        if (correctOrWrongLabel.getText().equals("Correct!")) {
            correctOrWrongLabel.setTextFill(Paint.valueOf("#3fb710"));
        } else {
            correctOrWrongLabel.setTextFill(Paint.valueOf("#dc0b0b"));
        }
    }
}
