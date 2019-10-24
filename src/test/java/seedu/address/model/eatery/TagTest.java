package seedu.address.model.eatery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String spacing = " ";
        assertThrows(IllegalArgumentException.class, () -> new Tag(spacing));

        String empty = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(empty));
    }

    @Test
    public void constructor_validTag() {
        String validName = "FreeWifi";
        Tag validTag = new Tag(validName);
        assertEquals("FreeWifi", validTag.getName());
    }

    @Test
    public void isValidTag() {
        //null category
        assertThrows(NullPointerException.class, () -> Tag.isValidTag(null));

        //invalid category
        assertFalse(Tag.isValidTag(""));
        assertFalse(Tag.isValidTag("  "));
        assertFalse(Tag.isValidTag("0"));
        assertFalse(Tag.isValidTag("?"));
        assertFalse(Tag.isValidTag("FreeWifi?"));
        assertFalse(Tag.isValidTag("0 FreeWifi"));
        assertFalse(Tag.isValidTag("Free Wifi"));
        assertFalse(Tag.isValidTag("_FreeWifi"));
        assertFalse(Tag.isValidTag("FreeWifi_"));

        //valid category
        assertTrue(Tag.isValidTag("freewifi"));
        assertTrue(Tag.isValidTag("FreeWifi"));
        assertTrue(Tag.isValidTag("free_wifi"));
        assertTrue(Tag.isValidTag("Free_Wifi"));
    }

    @Test
    public void equals() {
        Tag quiet = new Tag("Quiet");
        Tag study = new Tag("Study");

        assertTrue(quiet.equals(quiet));
        assertTrue(study.equals(study));
        assertTrue(quiet.equals(new Tag("Quiet")));
        assertTrue(study.equals(new Tag("Study")));

        assertFalse(quiet.equals(study));
        assertFalse(study.equals(quiet));
    }

    @Test
    public void create() {
        // test if it returns the same object
        Tag firstPp = Tag.create("PowerPlug");
        Tag secondPp = Tag.create("PowerPlug");
        assertEquals(firstPp, secondPp);

        // test if it returns a different object
        Tag nearMrt = Tag.create("NearMRT");
        assertFalse(nearMrt == firstPp);
    }
}
