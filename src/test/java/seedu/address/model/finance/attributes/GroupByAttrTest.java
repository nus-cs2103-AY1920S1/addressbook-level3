package seedu.address.model.finance.attributes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GroupByAttrTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GroupByAttr(null));
    }

    @Test
    public void constructor_invalidGroupByAttr_throwsIllegalArgumentException() {
        String invalidAttr = "";
        assertThrows(IllegalArgumentException.class, () -> new GroupByAttr(invalidAttr));
    }

    @Test
    public void isValidName() {
        // null attributes
        assertThrows(NullPointerException.class, () -> GroupByAttr.isValidGroupByAttr(null));

        // invalid attributes
        assertFalse(GroupByAttr.isValidGroupByAttr("")); // empty string
        assertFalse(GroupByAttr.isValidGroupByAttr(" ")); // spaces only
        assertFalse(GroupByAttr.isValidGroupByAttr("^")); // only non-alphanumeric characters
        assertFalse(GroupByAttr.isValidGroupByAttr("peter*")); // contains non-alphanumeric characters
        assertFalse(GroupByAttr.isValidGroupByAttr("entry type")); // contains whitespace


        // valid attributes
        assertTrue(GroupByAttr.isValidGroupByAttr("entrytype"));
        assertTrue(GroupByAttr.isValidGroupByAttr("met"));
        assertTrue(GroupByAttr.isValidGroupByAttr("cat"));
        assertTrue(GroupByAttr.isValidGroupByAttr("place"));
        assertTrue(GroupByAttr.isValidGroupByAttr("month"));
        // uppercase
        assertTrue(GroupByAttr.isValidGroupByAttr("ENTRYTYPE"));
        assertTrue(GroupByAttr.isValidGroupByAttr("MET"));
        assertTrue(GroupByAttr.isValidGroupByAttr("CAT"));
        assertTrue(GroupByAttr.isValidGroupByAttr("PLACE"));
        assertTrue(GroupByAttr.isValidGroupByAttr("MONTH"));

        // mixcase
        assertTrue(GroupByAttr.isValidGroupByAttr("plAce"));
        assertTrue(GroupByAttr.isValidGroupByAttr("mOnTh"));
        assertTrue(GroupByAttr.isValidGroupByAttr("enTRYtype"));
    }
}
