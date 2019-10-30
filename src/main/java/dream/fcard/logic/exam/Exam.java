package dream.fcard.logic.exam;

import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.exceptions.IndexNotFoundException;

import java.util.ArrayList;

/**
 * Interface to define behaviour of exams.
 * Look to eventually expand to timed and untimed tests.
 */
public interface Exam {

    public FlashCard getCurrentCard();

    public ArrayList<FlashCard> getDeck();

    public void parseUserInputAndGrade(String answer) throws IndexNotFoundException;

    public void upIndex();

    public void downIndex();

    public String getResult();

    public int getIndex();
}