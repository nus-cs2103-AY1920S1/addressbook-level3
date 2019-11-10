package seedu.weme.model.templates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.weme.testutil.Assert.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import seedu.weme.model.template.MemeTextColor;
import seedu.weme.model.template.MemeTextStyle;

public class MemeTextColorTest {

    private static final String ERROR_MESSAGE = MemeTextColor.MESSAGE_CONSTRAINTS;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MemeTextColor(null));
    }

    @Test
    public void constructor_validColorName_success() {
        assertEquals(Color.BLACK, new MemeTextColor("black").getColor());
        assertEquals(Color.BLUE, new MemeTextColor("blue").getColor());
        assertEquals(Color.GRAY, new MemeTextColor("gray").getColor());
        assertEquals(Color.MAGENTA, new MemeTextColor("magenta").getColor());
        assertEquals(Color.WHITE, new MemeTextColor("white").getColor());
        assertEquals(Color.YELLOW, new MemeTextColor("yellow").getColor());
    }

    @Test
    public void constructor_invalidString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, ERROR_MESSAGE, () -> new MemeTextColor("not a color"));
        assertThrows(IllegalArgumentException.class, ERROR_MESSAGE, () -> new MemeTextColor("str1ng and numb3r"));
        assertThrows(IllegalArgumentException.class, ERROR_MESSAGE, () -> new MemeTextColor(""));
        assertThrows(IllegalArgumentException.class, ERROR_MESSAGE, () -> new MemeTextColor("#!@#%$"));
        assertThrows(IllegalArgumentException.class, ERROR_MESSAGE, () -> new MemeTextColor("SOMETHING#SOMETHING"));
    }

    @Test
    public void constructor_validHexValue_success() {
        assertEquals(new Color(0, 0, 0), new MemeTextColor("#000000").getColor());
        assertEquals(new Color(255, 255, 255), new MemeTextColor("#FFFFFF").getColor());
        assertEquals(new Color(200, 100, 50), new MemeTextColor("#C86432").getColor());
    }

    @Test
    public void constructor_invalidHexValue_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, ERROR_MESSAGE, () -> new MemeTextColor("#GGGGGG"));
        assertThrows(IllegalArgumentException.class, ERROR_MESSAGE, () -> new MemeTextColor("#GG0000"));
        assertThrows(IllegalArgumentException.class, ERROR_MESSAGE, () -> new MemeTextColor("#00GG00"));
        assertThrows(IllegalArgumentException.class, ERROR_MESSAGE, () -> new MemeTextColor("#0000GG"));
        assertThrows(IllegalArgumentException.class, ERROR_MESSAGE, () -> new MemeTextColor("#123GHJ"));
    }

    @Test
    public void equals() {
        // same color name -> true
        assertEquals(new MemeTextColor("black"), new MemeTextColor("black"));
        assertEquals(new MemeTextColor("white"), new MemeTextColor("white"));
        // different color name -> false
        assertNotEquals(new MemeTextColor("blue"), new MemeTextColor("yellow"));
        assertNotEquals(new MemeTextColor("red"), new MemeTextColor("brown"));
        // same hex values -> true
        assertEquals(new MemeTextColor("#123456"), new MemeTextColor("#123456"));
        assertEquals(new MemeTextColor("#EFD987"), new MemeTextColor("#EFD987"));
        // different hex values -> false
        assertNotEquals(new MemeTextColor("#876EAB"), new MemeTextColor("#12D45F"));
        assertNotEquals(new MemeTextColor("#812BAC"), new MemeTextColor("#1F29E7"));
        // equivalent color name and hex value -> true
        assertEquals(new MemeTextColor("red"), new MemeTextColor("#FF0000"));
        assertEquals(new MemeTextColor("yellow"), new MemeTextColor("#FFFF00"));
        assertEquals(new MemeTextColor("blue"), new MemeTextColor("#0000FF"));
        // in-equivalent color name and hex value -> false
        assertNotEquals(new MemeTextColor("red"), new MemeTextColor("#123EDA"));
        assertNotEquals(new MemeTextColor("yellow"), new MemeTextColor("#ACE987"));
        assertNotEquals(new MemeTextColor("blue"), new MemeTextColor("#0123DE"));
        // null -> false
        assertNotEquals(new MemeTextColor("red"), null);
        // different type -> false
        assertNotEquals(new MemeTextColor("red"), new MemeTextStyle("bold"));
    }
}

