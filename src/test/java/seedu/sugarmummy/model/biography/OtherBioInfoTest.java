package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OtherBioInfoTest {

    @Test
    public void isValidOtherBioInfo_emptyString() {
        assertTrue(OtherBioInfo.isValidOtherBioInfo(""));
    }

    @Test
    public void isValidOtherBioInfo_firstExampleOtherBioInfo() {
        assertTrue(OtherBioInfo.isValidOtherBioInfo("Dislikes carrots"));
    }

    @Test
    public void isValidOtherBioInfo_secondExampleOtherBioInfo() {
        assertTrue(OtherBioInfo.isValidOtherBioInfo("Loves well-done steak"));
    }

    @Test
    public void testToString() {
        assertEquals("Dislikes carrots", (new OtherBioInfo("Dislikes carrots")).toString());
    }

    @Test
    public void testEquals_sameOtherBioInfo() {
        assertEquals(new OtherBioInfo("Dislikes carrots"),
                new OtherBioInfo("Dislikes carrots"));
    }

    @Test
    public void testEquals_differentOtherBioInfo() {
        assertNotEquals(new OtherBioInfo("Dislikes carrots"),
                new OtherBioInfo("Dislikes carrot juice"));
    }

    @Test
    public void testEquals_object() {
        assertNotEquals(new Object(),
                new OtherBioInfo("Dislikes carrots"));
    }

    @Test
    public void testHashCode_sameOtherBioInfo() {
        assertEquals(new OtherBioInfo("Dislikes carrots").hashCode(),
                new OtherBioInfo("Dislikes carrots").hashCode());
    }

    @Test
    public void testHashCode_differentOtherBioInfo() {
        assertNotEquals(new OtherBioInfo("Dislikes carrots").hashCode(),
                new OtherBioInfo("Dislikes carrot juice").hashCode());
    }

    @Test
    public void testHashCode_object() {
        assertNotEquals(new Object().hashCode(),
                new OtherBioInfo("Dislikes carrots").hashCode());
    }
}
