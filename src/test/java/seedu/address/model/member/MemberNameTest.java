package seedu.address.model.member;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MemberNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MemberName(null));
    }

    @Test
    public void constructor_invalidMemberName_throwsIllegalArgumentException() {
        String invalidMemberName = "";
        assertThrows(IllegalArgumentException.class, () -> new MemberName(invalidMemberName));
    }

    @Test
    public void isValidMemberName() {
        // null MemberName
        assertThrows(NullPointerException.class, () -> MemberName.isValidMemberName(null));

        // invalid MemberName
        assertFalse(MemberName.isValidMemberName("")); // empty string
        assertFalse(MemberName.isValidMemberName(" ")); // spaces only
        assertFalse(MemberName.isValidMemberName("^")); // only non-alphanumeric characters
        assertFalse(MemberName.isValidMemberName("peter*")); // contains non-alphanumeric characters

        // valid MemberName
        assertTrue(MemberName.isValidMemberName("peter jack")); // alphabets only
        assertTrue(MemberName.isValidMemberName("peter the 2nd")); // alphanumeric characters
        assertTrue(MemberName.isValidMemberName("12345")); // numbers only
        assertTrue(MemberName.isValidMemberName("Capital Tan")); // with capital letters
        assertTrue(MemberName.isValidMemberName("David Roger Jackson Ray Jr 2nd")); // long MemberNames
    }
}
