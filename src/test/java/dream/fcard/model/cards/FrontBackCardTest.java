package dream.fcard.model.cards;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FrontBackCardTest {

    @Test
    void testEvaluate() {
        FrontBackCard card = new FrontBackCard("asd", "123");
        assertEquals(true, card.evaluate("123"));
        assertEquals(false, card.evaluate("abc"));
    }
}
