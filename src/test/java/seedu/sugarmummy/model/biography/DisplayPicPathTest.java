package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DisplayPicPathTest {

    @Test
    public void isValidDisplayPicPath_emptyString() {
        assertTrue(DisplayPicPath.isValidDisplayPicPath(""));
    }

    @Test
    public void isValidDisplayPicPath_firstExampleDisplayPicPath() {
        assertTrue(DisplayPicPath.isValidDisplayPicPath("/Users/dp.png"));
    }

    @Test
    public void isValidDisplayPicPath_secondExampleDisplayPicPath() {
        assertTrue(DisplayPicPath.isValidDisplayPicPath("\\Users\\dp.png"));
    }

    @Test
    public void testToString() {
        assertEquals("/Users/dp.png", (new DisplayPicPath("/Users/dp.png")).toString());
    }

    @Test
    public void testEquals_sameDisplayPicPath() {
        assertEquals(new DisplayPicPath("/Users/dp.png"),
                new DisplayPicPath("/Users/dp.png"));
    }

    @Test
    public void testEquals_differentDisplayPicPath() {
        assertNotEquals(new DisplayPicPath("/Users/dp.png"),
                new DisplayPicPath("/Users/dp2.png"));
    }

    @Test
    public void testEquals_object() {
        assertNotEquals(new Object(),
                new DisplayPicPath("/Users/dp.png"));
    }

    @Test
    public void testHashCode_sameDisplayPicPath() {
        assertEquals(new DisplayPicPath("/Users/dp.png").hashCode(),
                new DisplayPicPath("/Users/dp.png").hashCode());
    }

    @Test
    public void testHashCode_differentDisplayPicPath() {
        assertNotEquals(new DisplayPicPath("/Users/dp.png").hashCode(),
                new DisplayPicPath("/Users/dp2.png").hashCode());
    }

    @Test
    public void testHashCode_object() {
        assertNotEquals(new Object().hashCode(),
                new DisplayPicPath("/Users/dp.png").hashCode());
    }
}
