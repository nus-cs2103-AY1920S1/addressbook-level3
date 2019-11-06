package seedu.sugarmummy.model.aesthetics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ColourTest {

    @Test
    public void isValidColour_firstColourName() {
        assertTrue(Colour.isValidColour("yellow"));
    }

    @Test
    public void isValidColour_secondColourName() {
        assertTrue(Colour.isValidColour("skyBlue"));
    }

    @Test
    public void isValidColour_firstHexadecimal() {
        assertTrue(Colour.isValidColour("#202020"));
    }

    @Test
    public void isValidColour_secondHexadecimal() {
        assertTrue(Colour.isValidColour("#FfFfFF"));
    }

    @Test
    public void isValidColour_invalidColourName() {
        assertFalse(Colour.isValidColour("Bloo"));
    }

    @Test
    public void isValidColour_invalidHexadecimal() {
        assertFalse(Colour.isValidColour("#FF20FZ"));
    }

    @Test
    public void isCloseTo_true_colourTest() {
        assertTrue(new Colour("yellow").isCloseTo(new Colour("white")));
    }

    @Test
    public void isCloseTo_true_hexadecimalTest() {
        assertTrue(new Colour("#202020").isCloseTo(new Colour("#101010")));
    }

    @Test
    public void isCloseTo_true_colourAndHexadecimalTest() {
        assertTrue(new Colour("#FFFF20").isCloseTo(new Colour("white")));
    }

    @Test
    public void isCloseTo_false_colourTest() {
        assertFalse(new Colour("darkblue").isCloseTo(new Colour("white")));
    }

    @Test
    public void isCloseTo_false_hexadecimalTest() {
        assertFalse(new Colour("#202020").isCloseTo(new Colour("#FFFF20")));
    }

    @Test
    public void isCloseTo_false_colourAndHexadecimalTest() {
        assertFalse(new Colour("#FFFF20").isCloseTo(new Colour("black")));
    }

    @Test
    public void testToString_colourName() {
        assertEquals("white", (new Colour("WhIte")).toString());
    }

    @Test
    public void testToString_hexadecimal() {
        assertEquals("white", (new Colour("#FfFffF")).toString());
    }

    @Test
    public void testToString_hexadecimal_unconvertible() {
        assertEquals("#202020", (new Colour("#202020")).toString());
    }

    @Test
    public void testEquals_colourName() {
        assertEquals(new Colour("white"), new Colour("WhIte"));
    }

    @Test
    public void testEquals_hexadecimal() {
        assertEquals(new Colour("white"), new Colour("#FfFffF"));
    }

    @Test
    public void testEquals_hexadecimal_unconvertible() {
        assertEquals(new Colour("#202020"), new Colour("#202020"));
    }

    @Test
    public void test_notEquals_object() {
        assertNotEquals(new Colour("#202020"), new Object());
    }

    @Test
    public void test_notEquals_differentHexadecimalColours() {
        assertNotEquals(new Colour("#202020"), new Colour("#101010"));
    }

    @Test
    public void test_notEquals_differentColourNames() {
        assertNotEquals(new Colour("yellow"), new Colour("blue"));
    }

    @Test
    public void testHashCode_colourName() {
        assertEquals(new Colour("white").hashCode(), new Colour("WhIte").hashCode());
    }

    @Test
    public void testHashCode_hexadecimal() {
        assertEquals(new Colour("white").hashCode(), new Colour("#FfFffF").hashCode());
    }

    @Test
    public void testHashCode_hexadecimal_unconvertible() {
        assertEquals(new Colour("#202020").hashCode(), new Colour("#202020").hashCode());
    }

    @Test
    public void test_differentHashCode_object() {
        assertNotEquals(new Colour("#202020").hashCode(), new Object().hashCode());
    }

    @Test
    public void test_differentHashCode_differentHexadecimalColours() {
        assertNotEquals(new Colour("#202020").hashCode(), new Colour("#101010").hashCode());
    }

    @Test
    public void test_differentHashCode_differentColourNames() {
        assertNotEquals(new Colour("yellow").hashCode(), new Colour("blue").hashCode());
    }

}
