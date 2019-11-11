//@@author e0031374
package tagline.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_CONTACTID_HYDRA1;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_CONTACTID_HYDRA2;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_GROUPDESCRIPTION_HYDRA;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.group.TypicalGroups.CHILDREN;
import static tagline.testutil.group.TypicalGroups.HYDRA;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tagline.model.group.exceptions.DuplicateGroupException;
import tagline.model.group.exceptions.GroupNotFoundException;
import tagline.testutil.group.GroupBuilder;

public class UniqueGroupListTest {

    private final UniqueGroupList uniqueGroupList = new UniqueGroupList();

    @Test
    public void containsGroup_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.containsGroup(null));
    }

    @Test
    public void containsGroup_groupNotInList_returnsFalse() {
        assertFalse(uniqueGroupList.containsGroup(CHILDREN));
    }

    @Test
    public void containsGroup_groupInList_returnsTrue() {
        uniqueGroupList.addGroup(CHILDREN);
        assertTrue(uniqueGroupList.containsGroup(CHILDREN));
    }

    @Test
    public void containsGroup_groupWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGroupList.addGroup(CHILDREN);
        Group editedChildren = new GroupBuilder(CHILDREN).withGroupDescription(VALID_GROUPDESCRIPTION_HYDRA)
                .withMemberIds(VALID_CONTACTID_HYDRA1, VALID_CONTACTID_HYDRA2)
                .build();
        assertTrue(uniqueGroupList.containsGroup(editedChildren));
    }

    @Test
    public void addGroup_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.addGroup(null));
    }

    @Test
    public void addGroup_duplicateGroup_throwsDuplicateGroupException() {
        uniqueGroupList.addGroup(CHILDREN);
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.addGroup(CHILDREN));
    }

    @Test
    public void setGroup_nullTargetGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroup(null, CHILDREN));
    }

    @Test
    public void setGroup_nullEditedGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroup(CHILDREN, null));
    }

    @Test
    public void setGroup_targetGroupNotInList_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> uniqueGroupList.setGroup(CHILDREN, CHILDREN));
    }

    @Test
    public void setGroup_editedGroupIsSameGroup_success() {
        uniqueGroupList.addGroup(CHILDREN);
        uniqueGroupList.setGroup(CHILDREN, CHILDREN);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.addGroup(CHILDREN);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasSameIdentity_success() {
        uniqueGroupList.addGroup(CHILDREN);
        Group editedChildren = new GroupBuilder(CHILDREN).withGroupDescription(VALID_GROUPDESCRIPTION_HYDRA)
                .withMemberIds(VALID_CONTACTID_HYDRA1, VALID_CONTACTID_HYDRA2)
                .build();
        uniqueGroupList.setGroup(CHILDREN, editedChildren);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.addGroup(editedChildren);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasDifferentIdentity_success() {
        uniqueGroupList.addGroup(CHILDREN);
        uniqueGroupList.setGroup(CHILDREN, HYDRA);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.addGroup(HYDRA);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasNonUniqueIdentity_throwsDuplicateGroupException() {
        uniqueGroupList.addGroup(CHILDREN);
        uniqueGroupList.addGroup(HYDRA);
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.setGroup(CHILDREN, HYDRA));
    }

    @Test
    public void removeGroup_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.removeGroup(null));
    }

    @Test
    public void removeGroup_groupDoesNotExist_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> uniqueGroupList.removeGroup(CHILDREN));
    }

    @Test
    public void removeGroup_existingGroup_removesGroup() {
        uniqueGroupList.addGroup(CHILDREN);
        uniqueGroupList.removeGroup(CHILDREN);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullUniqueGroupList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroups((UniqueGroupList) null));
    }

    @Test
    public void setGroups_uniqueGroupList_replacesOwnListWithProvidedUniqueGroupList() {
        uniqueGroupList.addGroup(CHILDREN);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.addGroup(HYDRA);
        uniqueGroupList.setGroups(expectedUniqueGroupList);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroups((List<Group>) null));
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() {
        uniqueGroupList.addGroup(CHILDREN);
        List<Group> groupList = Collections.singletonList(HYDRA);
        uniqueGroupList.setGroups(groupList);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.addGroup(HYDRA);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_listWithDuplicateGroups_throwsDuplicateGroupException() {
        List<Group> listWithDuplicateGroups = Arrays.asList(CHILDREN, CHILDREN);
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.setGroups(listWithDuplicateGroups));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueGroupList.asUnmodifiableObservableList().remove(0));
    }
}
