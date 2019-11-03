package seedu.address.model.group;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.group.TypicalGroups;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

public class ListOfGroupTest {
    private final ListOfGroups listOfGroups = new ListOfGroups();

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> listOfGroups.contains(null));
    }

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(listOfGroups.contains(TypicalGroups.groupOne));
    }


}
