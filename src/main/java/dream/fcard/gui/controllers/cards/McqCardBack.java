package dream.fcard.gui.controllers.cards;

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


    public McqCardBack(MultipleChoiceCard card, int selectedAnswer, Consumer<Integer> seeFrontOfMcqCard) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Cards/Back/MCQCardBack.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            seeFrontButton.setOnAction(e -> seeFrontOfMcqCard.accept(selectedAnswer));
            correctOrWrongLabel.setText(checkAnswer(card, selectedAnswer));
            setColourOfLabel();
            int correctAnswer = card.getDisplayChoicesAnswerIndex();
            correctAnswerLabel.setText("Option " + correctAnswer + " is correct.");
        } catch (IOException | IndexNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String checkAnswer(MultipleChoiceCard card, int selectedAnswer) throws IndexNotFoundException {
        boolean isCorrect = card.evaluate(Integer.toString(selectedAnswer));
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
