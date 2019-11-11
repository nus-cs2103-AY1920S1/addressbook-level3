//@author shawnpunchew11

package dream.fcard.logic.exam;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.FrontBackCard;

class StandardExamTest {

    @Test
    void testGetIndex() {
        StandardExam testExam = new StandardExam(new ArrayList<FlashCard>(), 0);
        assertEquals(0, testExam.getIndex());
    }

    void testUpIndex() {
        StandardExam testExam = new StandardExam(new ArrayList<FlashCard>(), 0);
        testExam.upIndex();
        assertEquals(1, testExam.getIndex());
    }

    /**
     * test method that checks the functionality of the downIndex() method.
     */
    void testDownIndex() {
        StandardExam testExam = new StandardExam(new ArrayList<FlashCard>(), 0);
        testExam.downIndex();
        assertEquals(0, testExam.getIndex());
        testExam.upIndex();
        testExam.upIndex();
        testExam.downIndex();
        assertEquals(1, testExam.getIndex());
    }

    void testGetDuration() {
        StandardExam testExam = new StandardExam(new ArrayList<FlashCard>(), 200);
        assertEquals(200, testExam.getDuration());
    }

    /**
     * test method to check if getCurrentCard() returns the card of that specific index.
     */
    void testGetCurrentCard() {
        ArrayList<FlashCard> testDeck = new ArrayList<FlashCard>();
        FlashCard testCard = new FrontBackCard("hi", "bye");
        testDeck.add(testCard);
        StandardExam testExam = new StandardExam(testDeck, 0);
        assertEquals(testCard, testExam.getCurrentCard());
    }
}
