package dukecooks.logic.commands.diary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.testutil.diary.EditDiaryDescriptorBuilder;

public class EditDiaryDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditDiaryCommand.EditDiaryDescriptor descriptorWithSameValues = new EditDiaryCommand
                .EditDiaryDescriptor(CommandTestUtil.DESC_AMY_DIARY);
        Assertions.assertTrue(CommandTestUtil.DESC_AMY_DIARY.equals(descriptorWithSameValues));

        // same object -> returns true
        Assertions.assertTrue(CommandTestUtil.DESC_AMY_DIARY.equals(CommandTestUtil.DESC_AMY_DIARY));

        // null -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_AMY_DIARY.equals(null));

        // different types -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_AMY_DIARY.equals(5));

        // different values -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_AMY_DIARY.equals(CommandTestUtil.DESC_BOB_DIARY));

        // different name -> returns false
        EditDiaryCommand.EditDiaryDescriptor editedAmy = new EditDiaryDescriptorBuilder(CommandTestUtil
                .DESC_AMY_DIARY).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(CommandTestUtil.DESC_AMY_DIARY.equals(editedAmy));
    }
}
