//@@author e0031374
package tagline.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_CONTACTID_HYDRA1;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_CONTACTID_HYDRA2;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_GROUPDESCRIPTION_HYDRA;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.TypicalGroups.CHILDREN;
import static tagline.testutil.TypicalGroups.getTypicalGroupBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tagline.model.group.exceptions.DuplicateGroupException;
import tagline.testutil.GroupBuilder;

public class GroupBookTest {

    private final GroupBook addressBook = new GroupBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getGroupList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyGroupBook_replacesData() {
        GroupBook newData = getTypicalGroupBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateGroups_throwsDuplicateGroupException() {
        // Two groups with the same identity fields
        Group editedAlice = new GroupBuilder(CHILDREN).withGroupDescription(VALID_GROUPDESCRIPTION_HYDRA)
            .withMemberIds(VALID_CONTACTID_HYDRA1, VALID_CONTACTID_HYDRA2)
            .build();
        List<Group> newGroups = Arrays.asList(CHILDREN, editedAlice);
        GroupBookStub newData = new GroupBookStub(newGroups);

        assertThrows(DuplicateGroupException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasGroup_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasGroup(null));
    }

    @Test
    public void hasGroup_groupNotInGroupBook_returnsFalse() {
        assertFalse(addressBook.hasGroup(CHILDREN));
    }

    @Test
    public void hasGroup_groupInGroupBook_returnsTrue() {
        addressBook.addGroup(CHILDREN);
        assertTrue(addressBook.hasGroup(CHILDREN));
    }

    @Test
    public void hasGroup_groupWithSameIdentityFieldsInGroupBook_returnsTrue() {
        addressBook.addGroup(CHILDREN);
        //use noteID instead of Address
        Group editedAlice = new GroupBuilder(CHILDREN).withGroupDescription(VALID_GROUPDESCRIPTION_HYDRA)
                .withMemberIds(VALID_CONTACTID_HYDRA1, VALID_CONTACTID_HYDRA2)
                .build();
        assertTrue(addressBook.hasGroup(editedAlice));
    }

    @Test
    public void getGroupList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getGroupList().remove(0));
    }

    /**
     * A stub ReadOnlyGroupBook whose groups list can violate interface constraints.
     */
    private static class GroupBookStub implements ReadOnlyGroupBook {
        private final ObservableList<Group> groups = FXCollections.observableArrayList();

        GroupBookStub(Collection<Group> groups) {
            this.groups.setAll(groups);
        }

        @Override
        public ObservableList<Group> getGroupList() {
            return groups;
        }
    }

}
