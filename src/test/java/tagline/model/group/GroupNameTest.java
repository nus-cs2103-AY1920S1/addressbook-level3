//@@author e0031374
package tagline.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GroupNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GroupName(null));
    }

    @Test
    public void constructor_invalidGroupName_throwsIllegalArgumentException() {
        String invalidGroupName = "";
        assertThrows(IllegalArgumentException.class, () -> new GroupName(invalidGroupName));
    }

    @Test
    public void isValidGroupName() {
        // null name
        assertThrows(NullPointerException.class, () -> GroupName.isValidGroupName(null));

        // invalid name
        assertFalse(GroupName.isValidGroupName("")); // empty string
        assertFalse(GroupName.isValidGroupName(" ")); // spaces only
        assertFalse(GroupName.isValidGroupName("  ")); // spaces only
        // has whitespace
        assertFalse(GroupName.isValidGroupName("peter jack")); // alphabets only
        assertFalse(GroupName.isValidGroupName("peter\nthe 2nd")); // with newline
        assertFalse(GroupName.isValidGroupName("Capital Tan")); // with capital letters and tab
        assertFalse(GroupName.isValidGroupName("David Roger Jackson Ray Jr 2nd")); // whitespace

        // valid name
        assertTrue(GroupName.isValidGroupName("^")); // only non-alphanumeric characters
        assertTrue(GroupName.isValidGroupName("peter*")); // contains non-alphanumeric characters
        assertTrue(GroupName.isValidGroupName("12345")); // numbers only
        assertTrue(GroupName.isValidGroupName("peters123jac12k@asd")); // alphanumeric
        assertTrue(GroupName.isValidGroupName("peterjack")); // alphabets only
        assertTrue(GroupName.isValidGroupName("peter_the_2nd")); // with underscore
        assertTrue(GroupName.isValidGroupName("Capital-Tan")); // with dash
        assertTrue(GroupName.isValidGroupName("DavidRogerJacksonRayJr2nd")); // long names
    }
}
