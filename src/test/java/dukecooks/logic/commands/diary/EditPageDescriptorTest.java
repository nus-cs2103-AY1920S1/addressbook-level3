package dukecooks.logic.commands.diary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.diary.EditPageCommand.EditPageDescriptor;
import dukecooks.testutil.diary.EditPageDescriptorBuilder;

public class EditPageDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPageDescriptor descriptorWithSameValues = new EditPageDescriptor(CommandTestUtil.DESC_PHO_PAGE);
        Assertions.assertTrue(CommandTestUtil.DESC_PHO_PAGE.equals(descriptorWithSameValues));

        // same object -> returns true
        Assertions.assertTrue(CommandTestUtil.DESC_PHO_PAGE.equals(CommandTestUtil.DESC_PHO_PAGE));

        // null -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_PHO_PAGE.equals(null));

        // different types -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_PHO_PAGE.equals(5));

        // different values -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_PHO_PAGE.equals(CommandTestUtil.DESC_SUSHI_PAGE));

        // different title -> returns false
        EditPageDescriptor editedPage = new EditPageDescriptorBuilder(CommandTestUtil.DESC_PHO_PAGE)
                .withTitle(CommandTestUtil.VALID_SUSHI_TITLE).build();
        Assertions.assertFalse(CommandTestUtil.DESC_PHO_PAGE.equals(editedPage));

    }
}
