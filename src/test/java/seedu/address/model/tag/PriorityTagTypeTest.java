package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PriorityTagTypeTest {

    @Test
    public void isValidPriorityTagString() {
        String validStringOne = "HIGH";
        String validStringTwo = "MEDIUM";
        String validStringThree = "LOW";
        assertTrue(PriorityTagType.isValidPriorityTagString(validStringOne));
        assertTrue(PriorityTagType.isValidPriorityTagString(validStringTwo));
        assertTrue(PriorityTagType.isValidPriorityTagString(validStringThree));
    }

    @Test
    public void getStyle() {
        String highPriorityStyle = "highPriority";
        String mediumPriorityStyle = "mediumPriority";
        String lowPriorityStyle = "lowPriority";
        assertEquals(highPriorityStyle, PriorityTagType.HIGH.getStyle());
        assertEquals(mediumPriorityStyle, PriorityTagType.MEDIUM.getStyle());
        assertEquals(lowPriorityStyle, PriorityTagType.LOW.getStyle());
    }

}
