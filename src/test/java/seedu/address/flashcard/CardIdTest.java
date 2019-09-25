package seedu.address.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CardIdTest {

    private CardId id1 = new CardId();
    private CardId id2 = new CardId();
    private CardId id3 = new CardId();
    private CardId id4 = new CardId();

    @Test
    public void generateIdTest() {
        assertEquals(0, id1.getIdentityNumber());
        assertEquals(1, id2.getIdentityNumber());
        assertEquals(2, id3.getIdentityNumber());
        assertEquals(3, id4.getIdentityNumber());
    }
}
