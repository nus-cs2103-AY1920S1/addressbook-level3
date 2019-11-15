package dukecooks.logic.commands.recipe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.testutil.recipe.EditRecipeDescriptorBuilder;

public class EditRecipeDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditRecipeCommand.EditRecipeDescriptor descriptorWithSameValues = new EditRecipeCommand
                .EditRecipeDescriptor(CommandTestUtil.DESC_FISH);
        Assertions.assertTrue(CommandTestUtil.DESC_FISH.equals(descriptorWithSameValues));

        // same object -> returns true
        Assertions.assertTrue(CommandTestUtil.DESC_FISH.equals(CommandTestUtil.DESC_FISH));

        // null -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_FISH.equals(null));

        // different types -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_FISH.equals(5));

        // different values -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_FISH.equals(CommandTestUtil.DESC_BURGER));

        // different name -> returns false
        EditRecipeCommand.EditRecipeDescriptor editedAmy = new EditRecipeDescriptorBuilder(CommandTestUtil.DESC_FISH)
                .withRecipeName(CommandTestUtil.VALID_NAME_BURGER).build();
        Assertions.assertFalse(CommandTestUtil.DESC_FISH.equals(editedAmy));

        // different ingredients -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(CommandTestUtil.DESC_FISH).withIngredientsToAdd(CommandTestUtil
                .VALID_INGREDIENT_BURGER).build();
        Assertions.assertFalse(CommandTestUtil.DESC_FISH.equals(editedAmy));
    }
}
