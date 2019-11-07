package seedu.ifridge.storage.shoppinglist;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.model.food.ShoppingItemTest.areExactlySameShoppingItems;
import static seedu.ifridge.storage.shoppinglist.JsonAdaptedShoppingItem.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalShoppingList.CAKE;
import static seedu.ifridge.testutil.TypicalShoppingList.EGGS;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Name;

public class JsonAdaptedShoppingItemTest {
    private static final String INVALID_NAME = "Or@nge";
    private static final String INVALID_AMOUNT = "noNumbers";

    private static final String VALID_NAME = CAKE.getName().toString();
    private static final String VALID_AMOUNT = CAKE.getAmount().toString();

    @Test
    public void toModelType_validShoppingItemDetails_returnsShoppingItem() throws Exception {
        JsonAdaptedShoppingItem shoppingItem = new JsonAdaptedShoppingItem(EGGS);
        assertTrue(areExactlySameShoppingItems(EGGS, shoppingItem.toModelType()));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedShoppingItem shoppingItem =
                new JsonAdaptedShoppingItem(INVALID_NAME, VALID_AMOUNT, false, false);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, shoppingItem::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedShoppingItem shoppingItem = new JsonAdaptedShoppingItem(null, VALID_AMOUNT, false, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, shoppingItem::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedShoppingItem shoppingItem = new JsonAdaptedShoppingItem(VALID_NAME, null, false, false);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, shoppingItem::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedShoppingItem shoppingItem =
                new JsonAdaptedShoppingItem(VALID_NAME, INVALID_AMOUNT, false, false);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, shoppingItem::toModelType);
    }
}
