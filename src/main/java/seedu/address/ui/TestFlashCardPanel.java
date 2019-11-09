package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.flashcard.FlashCard;

/**
 * An UI component that displays information of a {@code FlashCard}.
 */
public class TestFlashCardPanel extends UiPart<Region> {

    private static final String FXML = "FlashCardPanel.fxml";

    @FXML
    private HBox flashcardPane;
    @FXML
    private TextArea testquestion;
    @FXML
    private HBox questionBox;

    @FXML
    private TextArea testanswer;
    @FXML
    private HBox answerBox;
    @FXML
    private Separator separator;



    public TestFlashCardPanel(FlashCard flashCard) {
        super(FXML);
        testanswer.setText(flashCard.getAnswer().fullAnswer);
        testquestion.setText(flashCard.getQuestion().fullQuestion);
        setAnswerInvisible();
    }

    public void setAnswerInvisible() {
        answerBox.setVisible(false);
        separator.setVisible(false);
    }

    /**
     * display the answer in the gui
     */
    public void showAnswer() {
        answerBox.setVisible(true);
        separator.setVisible(true);
    }


}
