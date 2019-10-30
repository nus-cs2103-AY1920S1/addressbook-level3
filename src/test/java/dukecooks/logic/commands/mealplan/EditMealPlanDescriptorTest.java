package dukecooks.logic.commands.mealplan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.testutil.mealplan.EditMealPlanDescriptorBuilder;

public class EditMealPlanDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditMealPlanCommand.EditMealPlanDescriptor descriptorWithSameValues = new EditMealPlanCommand
                .EditMealPlanDescriptor(CommandTestUtil.DESC_FISH_MP);
        Assertions.assertTrue(CommandTestUtil.DESC_FISH_MP.equals(descriptorWithSameValues));

        // same object -> returns true
        Assertions.assertTrue(CommandTestUtil.DESC_FISH_MP.equals(CommandTestUtil.DESC_FISH_MP));

        // null -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_FISH_MP.equals(null));

        // different types -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_FISH_MP.equals(5));

        // different values -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_FISH_MP.equals(CommandTestUtil.DESC_BURGER_MP));

        // different name -> returns false
        EditMealPlanCommand.EditMealPlanDescriptor editedFish = new EditMealPlanDescriptorBuilder(
                CommandTestUtil.DESC_FISH_MP).withMealPlanName(CommandTestUtil.VALID_NAME_BURGER_MP).build();
        Assertions.assertFalse(CommandTestUtil.DESC_FISH_MP.equals(editedFish));
    }
}
