package dream.fcard.logic.exam;

import dream.fcard.model.cards.FlashCard;

/**
 * Interface to define behaviour of exams.
 * Look to eventually expand to timed and untimed tests.
 */
public interface Exam {

    public FlashCard nextCard(int index);

}
