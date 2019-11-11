//@author shawnpunchew11

package dream.fcard.logic.exam;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.FrontBackCard;
import org.junit.jupiter.api.Test;

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

    void testGetCurrentCard() {
        ArrayList<FlashCard> testDeck = new ArrayList<FlashCard>();
        FlashCard testCard = new FrontBackCard("hi", "bye");
        testDeck.add(testCard);
        StandardExam testExam = new StandardExam(testDeck, 0);
        assertEquals(testCard, testExam.getCurrentCard());
    }
}
