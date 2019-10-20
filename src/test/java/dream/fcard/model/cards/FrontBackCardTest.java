package dream.fcard.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dream.fcard.model.exceptions.IndexNotFoundException;

class FrontBackCardTest {

    @Test
    void testEvaluate() throws IndexNotFoundException {
        FrontBackCard card = new FrontBackCard("asd", "123");
        assertEquals(true, card.evaluate("123"));
        assertEquals(false, card.evaluate("abc"));
    }
}
