package mams.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mams.testutil.Assert;

public class PrevModsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PrevMods(null));
    }

    @Test
    public void constructor_invalidPrevMods_throwsIllegalArgumentException() {
        String invalidPrevMods = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PrevMods(invalidPrevMods));
    }

    @Test
    public void isValidPrevMods() {
        // null prevMods
        Assert.assertThrows(NullPointerException.class, () -> PrevMods.isValidPrevMods(null));

        // blank prevMods
        assertFalse(PrevMods.isValidPrevMods("")); // empty string
        assertFalse(PrevMods.isValidPrevMods(" ")); // spaces only

        // missing parts
        assertFalse(PrevMods.isValidPrevMods("@example.com")); // missing local part
        assertFalse(PrevMods.isValidPrevMods("peterjackexample.com")); // missing '@' symbol
        assertFalse(PrevMods.isValidPrevMods("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(PrevMods.isValidPrevMods("peterjack@-")); // invalid domain name
        assertFalse(PrevMods.isValidPrevMods("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(PrevMods.isValidPrevMods("peter jack@example.com")); // spaces in local part
        assertFalse(PrevMods.isValidPrevMods("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(PrevMods.isValidPrevMods(" peterjack@example.com")); // leading space
        assertFalse(PrevMods.isValidPrevMods("peterjack@example.com ")); // trailing space
        assertFalse(PrevMods.isValidPrevMods("peterjack@@example.com")); // double '@' symbol
        assertFalse(PrevMods.isValidPrevMods("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(PrevMods.isValidPrevMods("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(PrevMods.isValidPrevMods("peterjack@.example.com")); // domain name starts with a period
        assertFalse(PrevMods.isValidPrevMods("peterjack@example.com.")); // domain name ends with a period
        assertFalse(PrevMods.isValidPrevMods("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(PrevMods.isValidPrevMods("peterjack@example.com-")); // domain name ends with a hyphen

        // valid prevMods
        assertTrue(PrevMods.isValidPrevMods("CS2030, CS1231"));
    }
}
