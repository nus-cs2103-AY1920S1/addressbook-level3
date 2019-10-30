package dream.fcard.logic.exam;

import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.exceptions.IndexNotFoundException;

/**
 * Interface to define behaviour of exams.
 * Look to eventually expand to timed and untimed tests.
 */
public interface Exam {

    public FlashCard getCurrentCard();

    public void parseUserInputAndGrade(String answer) throws IndexNotFoundException;

    public void upIndex();

    public String getResult();
}
