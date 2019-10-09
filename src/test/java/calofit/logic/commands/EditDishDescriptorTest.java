package calofit.logic.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import calofit.testutil.EditDishDescriptorBuilder;

public class EditDishDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditDishDescriptor descriptorWithSameValues =
            new EditCommand.EditDishDescriptor(CommandTestUtil.DESC_DUCK_RICE);
        Assertions.assertTrue(CommandTestUtil.DESC_DUCK_RICE.equals(descriptorWithSameValues));

        // same object -> returns true
        Assertions.assertTrue(CommandTestUtil.DESC_DUCK_RICE.equals(CommandTestUtil.DESC_DUCK_RICE));

        // null -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_DUCK_RICE.equals(null));

        // different types -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_DUCK_RICE.equals(5));

        // different values -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_DUCK_RICE.equals(CommandTestUtil.DESC_MACARONI));

        // different name -> returns false
        EditCommand.EditDishDescriptor editedAmy =
            new EditDishDescriptorBuilder(CommandTestUtil.DESC_DUCK_RICE)
                .withName(CommandTestUtil.VALID_NAME_MACARONI).build();
        Assertions.assertFalse(CommandTestUtil.DESC_DUCK_RICE.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditDishDescriptorBuilder(CommandTestUtil.DESC_DUCK_RICE)
                .withTags(CommandTestUtil.VALID_TAG_SALTY).build();
        Assertions.assertFalse(CommandTestUtil.DESC_DUCK_RICE.equals(editedAmy));
    }
}
