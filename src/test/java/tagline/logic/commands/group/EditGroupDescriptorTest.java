//@@author e0031374
package tagline.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.group.GroupCommandTestUtil.DESC_HYDRA;
import static tagline.logic.commands.group.GroupCommandTestUtil.DESC_SHIELD;
import static tagline.logic.commands.group.GroupCommandTestUtil.VALID_CONTACTID_SHIELD;
import static tagline.logic.commands.group.GroupCommandTestUtil.VALID_GROUPDESCRIPTION_SHIELD;
import static tagline.logic.commands.group.GroupCommandTestUtil.VALID_GROUPNAME_SHIELD;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.group.EditGroupCommand.EditGroupDescriptor;
import tagline.testutil.group.EditGroupDescriptorBuilder;

public class EditGroupDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditGroupDescriptor descriptorWithSameValues = new EditGroupDescriptor(DESC_HYDRA);
        assertTrue(DESC_HYDRA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_HYDRA.equals(DESC_HYDRA));

        // null -> returns false
        assertFalse(DESC_HYDRA.equals(null));

        // different types -> returns false
        assertFalse(DESC_HYDRA.equals(5));

        // different values -> returns false
        assertFalse(DESC_HYDRA.equals(DESC_SHIELD));

        // different name -> returns false
        EditGroupDescriptor editedHydra = new EditGroupDescriptorBuilder(DESC_HYDRA)
            .withGroupName(VALID_GROUPNAME_SHIELD).build();
        assertFalse(DESC_HYDRA.equals(editedHydra));

        // different description -> returns true
        editedHydra = new EditGroupDescriptorBuilder(DESC_HYDRA)
            .withGroupDescription(VALID_GROUPDESCRIPTION_SHIELD).build();
        assertTrue(DESC_HYDRA.equals(editedHydra));

        // different tags -> returns false
        editedHydra = new EditGroupDescriptorBuilder(DESC_HYDRA).withMemberIds(VALID_CONTACTID_SHIELD).build();
        assertFalse(DESC_HYDRA.equals(editedHydra));
    }
}
