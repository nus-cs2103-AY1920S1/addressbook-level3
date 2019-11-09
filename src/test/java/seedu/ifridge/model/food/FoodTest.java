package seedu.ifridge.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_AMOUNT_NUTS;
import static seedu.ifridge.logic.commands.grocerylist.GroceryCommandTestUtil.VALID_NAME_NUTS;
import static seedu.ifridge.testutil.TypicalFoodItems.OREO;
import static seedu.ifridge.testutil.TypicalFoodItems.YAKITORI;

import org.junit.jupiter.api.Test;

import seedu.ifridge.testutil.FoodBuilder;

public class FoodTest {
    @Test
    public void isSameFood() {
        // same object -> returns true
        assertTrue(OREO.isSameFood(OREO));

        // null -> returns false
        assertFalse(OREO.isSameFood(null));

        // different name -> returns false
        Food editedOreo = new FoodBuilder(OREO).withName(VALID_NAME_NUTS).build();
        assertFalse(OREO.isSameFood(editedOreo));

        // same name, but different amount -> returns true
        editedOreo = new FoodBuilder(OREO).withAmount(VALID_AMOUNT_NUTS).build();
        assertTrue(OREO.isSameFood(editedOreo));
    }

    // TODO: do we need to check for equals? it's not even called anywhere
    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(OREO.equals(OREO));

        // same values but different object -> returns true
        Food oreoCopy = new FoodBuilder(OREO).build();
        assertFalse(OREO.equals(oreoCopy));

        // null -> returns false
        assertFalse(OREO.equals(null));

        // different type -> returns false
        assertFalse(OREO.equals(5));

        // different person -> returns false
        assertFalse(OREO.equals(YAKITORI));

        // different name -> returns false
        Food editedOreo = new FoodBuilder(OREO).withName(VALID_NAME_NUTS).build();
        assertFalse(OREO.equals(editedOreo));

        // different amount -> returns false
        editedOreo = new FoodBuilder(OREO).withAmount(VALID_AMOUNT_NUTS).build();
        assertFalse(OREO.equals(editedOreo));
    }
}
