package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ProfileDescTest {

    @Test
    public void isValidProfileDesc_emptyString() {
        assertTrue(ProfileDesc.isValidProfileDesc(""));
    }

    @Test
    public void isValidProfileDesc_firstExampleProfileDesc() {
        assertTrue(ProfileDesc.isValidProfileDesc("Life is not a bed of roses"));
    }

    @Test
    public void isValidProfileDesc_secondExampleProfileDesc() {
        assertTrue(ProfileDesc.isValidProfileDesc("2017-01-12 is my favourite date"));
    }

    @Test
    public void testToString() {
        assertEquals("Life is not a bed of roses", (new ProfileDesc("Life is not a bed of roses")).toString());
    }

    @Test
    public void testEquals_sameProfileDesc() {
        assertEquals(new ProfileDesc("Life is not a bed of roses"),
                new ProfileDesc("Life is not a bed of roses"));
    }

    @Test
    public void testEquals_differentProfileDesc() {
        assertNotEquals(new ProfileDesc("Life is not a bed of roses"),
                new ProfileDesc("Never give up."));
    }

    @Test
    public void testEquals_object() {
        assertNotEquals(new Object(),
                new ProfileDesc("Life is not a bed of roses"));
    }

    @Test
    public void testHashCode_sameProfileDesc() {
        assertEquals(new ProfileDesc("Life is not a bed of roses").hashCode(),
                new ProfileDesc("Life is not a bed of roses").hashCode());
    }

    @Test
    public void testHashCode_differentProfileDesc() {
        assertNotEquals(new ProfileDesc("Life is not a bed of roses").hashCode(),
                new ProfileDesc("Never give up.").hashCode());
    }

    @Test
    public void testHashCode_object() {
        assertNotEquals(new Object().hashCode(),
                new ProfileDesc("Life is not a bed of roses").hashCode());
    }
}
