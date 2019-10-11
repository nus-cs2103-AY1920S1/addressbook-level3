package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.AliasTestUtil.ALIAS_A_TO_B;

import org.junit.jupiter.api.Test;


class AliasTest {

    public static final String VALID_ALIAS_NAME_1 = "name";
    public static final String VALID_ALIAS_NAME_2 = "nAm3";
    public static final String INVALID_ALIAS_NAME = "n@#é";

    public static final String VALID_INPUT = "  7n1293jmj98(*@##*ybC (@*8 @9n wkdnqwdk   ";
    public static final String WHITE_SPACE = "     \n \t  ";

    @Test
    void testEquals() {
        //same alias
        assertEquals(ALIAS_A_TO_B, ALIAS_A_TO_B);
        //same inputs
        assertEquals(new Alias(VALID_ALIAS_NAME_1, VALID_INPUT), new Alias(VALID_ALIAS_NAME_1, VALID_INPUT));
        //null false
        assertNotEquals(ALIAS_A_TO_B, null);
        //not instance false
        assertNotEquals(ALIAS_A_TO_B, 1);
    }

    @Test
    void isValidAliasName_validAliasName_returnsTrue() {
        assertTrue(Alias.isValidAliasName(VALID_ALIAS_NAME_1));
        assertTrue(Alias.isValidAliasName(VALID_ALIAS_NAME_2));
    }

    @Test
    void isValidAliasName_invalidAliasName_returnsTrue() {
        assertFalse(Alias.isValidAliasName(INVALID_ALIAS_NAME));
        assertFalse(Alias.isValidAliasName(WHITE_SPACE));
    }


    @Test
    void isValidInput_validInput_returnsTrue() {
        assertTrue(Alias.isValidInput(VALID_INPUT));
    }

    @Test
    void isValidInput_invalidInput_returnFalse() {
        assertFalse(Alias.isValidInput(WHITE_SPACE));
    }
}
