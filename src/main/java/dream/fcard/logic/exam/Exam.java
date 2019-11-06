package dream.fcard.logic.exam;

import java.util.ArrayList;

import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.exceptions.IndexNotFoundException;
import javafx.scene.layout.AnchorPane;

/**
 * Interface to define behaviour of exams.
 * Look to eventually expand to timed and untimed tests.
 */
public interface Exam {

    public FlashCard getCurrentCard();

    public ArrayList<FlashCard> getDeck();

    public void gradeQuestion(Boolean isCorrect) throws IndexNotFoundException;

    public void upIndex();

    public void downIndex();

    public String getResult();

    public int getIndex();

    public int getDuration();

    public AnchorPane getCardDisplayFront();

    public AnchorPane getCardDisplayBack();
}
