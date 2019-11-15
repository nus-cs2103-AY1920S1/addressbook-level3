package dukecooks.logic.commands.profile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.testutil.profile.EditPersonDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditProfileCommand.EditPersonDescriptor descriptorWithSameValues = new EditProfileCommand
                .EditPersonDescriptor(CommandTestUtil.DESC_AMY);
        Assertions.assertTrue(CommandTestUtil.DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        Assertions.assertTrue(CommandTestUtil.DESC_AMY.equals(CommandTestUtil.DESC_AMY));

        // null -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(null));

        // different types -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(5));

        // different values -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(CommandTestUtil.DESC_BOB));

        // different name -> returns false
        EditProfileCommand.EditPersonDescriptor editedAmy = new EditPersonDescriptorBuilder(CommandTestUtil
                .DESC_AMY).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPersonDescriptorBuilder(CommandTestUtil.DESC_AMY).withMedicalHistories(CommandTestUtil
                .VALID_HISTORY_DENGUE).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY.equals(editedAmy));
    }
}
