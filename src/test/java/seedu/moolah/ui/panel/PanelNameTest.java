package seedu.moolah.ui.panel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.ui.panel.PanelName.isValidPanelName;

import org.junit.jupiter.api.Test;

/**
 * Contains tests for {@code PanelName}.
 */
class PanelNameTest {

    @Test
    void testEquals_alphanumericName_equal() {
        PanelName panel2 = new PanelName("panel2");
        PanelName panel1 = new PanelName("abcd123");
        PanelName panel3 = new PanelName("Panel3");
        PanelName panel2Copy = new PanelName("panel2");
        PanelName panel1Copy = new PanelName("abcd123");
        PanelName panel3Copy = new PanelName("Panel3");
        assertEquals(panel1, panel1Copy);
        assertEquals(panel2, panel2Copy);
        assertEquals(panel3, panel3Copy);
    }

    @Test
    void testEquals_nameWithLeadingAndTrailingWhiteSpace_equal() {
        PanelName panel2 = new PanelName("panel2");
        PanelName panel1 = new PanelName("panel1");
        PanelName panel3 = new PanelName("panel3");
        PanelName panel2WithWhiteSpace = new PanelName(" panel2  ");
        PanelName panel1WithWhiteSpace = new PanelName("\n panel1 \t");
        PanelName panel3WithWhiteSpace = new PanelName(" panel3  \n ");
        assertEquals(panel1, panel1WithWhiteSpace);
        assertEquals(panel2, panel2WithWhiteSpace);
        assertEquals(panel3, panel3WithWhiteSpace);
    }

    @Test
    void testEquals_differentName_notEqual() {
        PanelName panel2 = new PanelName("panel2");
        PanelName panel1 = new PanelName("panel1");
        PanelName panel3 = new PanelName("panel3");
        PanelName panel2WithWhiteSpace = new PanelName(" panel2  ");
        PanelName panel1WithWhiteSpace = new PanelName("\n panel1 \t");
        PanelName panel3WithWhiteSpace = new PanelName(" panel3  \n ");
        assertNotEquals(panel1, panel2WithWhiteSpace);
        assertNotEquals(panel2, panel3WithWhiteSpace);
        assertNotEquals(panel3, panel1WithWhiteSpace);
    }


    @Test
    void isValidPanelName_valid_true() {
        assertTrue(isValidPanelName("nam3W1thAlph4num3r1cCharacters"));
        assertTrue(isValidPanelName("name with spaces in between"));
        assertTrue(isValidPanelName("12313"));
    }


    @Test
    void isValidPanelName_invalid_false() {
        assertFalse(isValidPanelName("n/\\mW1th$pe<!@|(¢$ctªrs"));
        assertFalse(isValidPanelName(" name with leading spaces "));
        assertFalse(isValidPanelName("       "));
        assertFalse(isValidPanelName("\n"));
        assertFalse(isValidPanelName("\t"));
    }
}
