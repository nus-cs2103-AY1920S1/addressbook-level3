package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BURGER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditRecipeCommand.EditRecipeDescriptor;
import seedu.address.testutil.EditRecipeDescriptorBuilder;

public class EditRecipeDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditRecipeCommand.EditRecipeDescriptor descriptorWithSameValues = new EditRecipeCommand
                .EditRecipeDescriptor(DESC_FISH);
        assertTrue(DESC_FISH.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_FISH.equals(DESC_FISH));

        // null -> returns false
        assertFalse(DESC_FISH.equals(null));

        // different types -> returns false
        assertFalse(DESC_FISH.equals(5));

        // different values -> returns false
        assertFalse(DESC_FISH.equals(DESC_BURGER));

        // different name -> returns false
        EditRecipeDescriptor editedAmy = new EditRecipeDescriptorBuilder(DESC_FISH).withName(VALID_NAME_BURGER).build();
        assertFalse(DESC_FISH.equals(editedAmy));

        // different ingredients -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_FISH).withIngredients(VALID_INGREDIENT_BURGER).build();
        assertFalse(DESC_FISH.equals(editedAmy));
    }
}
