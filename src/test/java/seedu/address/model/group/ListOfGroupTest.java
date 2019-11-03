package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.group.TypicalGroups;


public class ListOfGroupTest {
    private final ListOfGroups listOfGroups = new ListOfGroups();

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(listOfGroups.contains(TypicalGroups.GROUP_ONE));
    }
}
