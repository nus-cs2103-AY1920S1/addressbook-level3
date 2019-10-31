//@@author e0031374
package tagline.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_CONTACTID_CHILDREN1;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_CONTACTID_HYDRA1;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_CONTACTID_HYDRA2;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_GROUPDESCRIPTION_HYDRA;
import static tagline.logic.commands.GroupCommandTestUtil.VALID_GROUPNAME_HYDRA;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.group.TypicalGroups.CHILDREN;
import static tagline.testutil.group.TypicalGroups.HYDRA;

import org.junit.jupiter.api.Test;

import tagline.testutil.group.GroupBuilder;

public class GroupTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Group group = new GroupBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> group.getMemberIds().remove(0));
    }

    @Test
    public void isSameGroup() {
        // same object -> returns true
        assertTrue(CHILDREN.isSameGroup(CHILDREN));

        // null -> returns false
        assertFalse(CHILDREN.isSameGroup(null));

        // different name -> returns false
        Group editedChildren = new GroupBuilder(CHILDREN).withGroupName(VALID_GROUPNAME_HYDRA).build();
        assertFalse(CHILDREN.isSameGroup(editedChildren));

        // same name, different attributes -> returns true
        editedChildren = new GroupBuilder(CHILDREN).withGroupDescription(VALID_GROUPDESCRIPTION_HYDRA)
                .withMemberIds(VALID_CONTACTID_HYDRA1, VALID_CONTACTID_HYDRA2).build();
        assertTrue(CHILDREN.isSameGroup(editedChildren));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Group childrenCopy = new GroupBuilder(CHILDREN).build();
        assertTrue(CHILDREN.equals(childrenCopy));

        // same object -> returns true
        assertTrue(CHILDREN.equals(CHILDREN));

        // null -> returns false
        assertFalse(CHILDREN.equals(null));

        // different type -> returns false
        assertFalse(CHILDREN.equals(5));

        // different person -> returns false
        assertFalse(CHILDREN.equals(HYDRA));

        // different name -> returns false
        Group editedChildren = new GroupBuilder(CHILDREN).withGroupName(VALID_GROUPNAME_HYDRA).build();
        assertFalse(CHILDREN.equals(editedChildren));

        // different description -> returns false
        editedChildren = new GroupBuilder(CHILDREN).withGroupDescription(VALID_GROUPDESCRIPTION_HYDRA).build();
        assertFalse(CHILDREN.equals(editedChildren));

        // different tags -> returns false
        editedChildren = new GroupBuilder(CHILDREN)
            .withMemberIds(VALID_CONTACTID_HYDRA1, VALID_CONTACTID_HYDRA2).build();
        assertFalse(CHILDREN.equals(editedChildren));

        editedChildren = new GroupBuilder(CHILDREN)
                .withMemberIds(VALID_CONTACTID_CHILDREN1).build();
        assertFalse(CHILDREN.equals(editedChildren));

        //editedChildren = new GroupBuilder(CHILDREN)
        //        .withMemberIds(null).build();
        assertFalse(CHILDREN.equals(editedChildren));
    }
}
