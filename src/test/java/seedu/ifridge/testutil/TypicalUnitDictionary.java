package seedu.ifridge.testutil;

import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtItems;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryItems;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingItems;
import static seedu.ifridge.testutil.TypicalTemplateItems.getTypicalTemplateItems;

import java.util.HashMap;

import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.ShoppingItem;
import seedu.ifridge.model.food.TemplateItem;

/**
 * A utility class containing a UnitDictionary class to be used in tests.
 */
public class TypicalUnitDictionary {

    private static UnitDictionary unitDictionary = null;

    private TypicalUnitDictionary() {} // prevents instantiation

    /**
     * Returns an {@code UnitDictionary} with all the typical dictionary items based on the typical lists.
     */
    public static UnitDictionary getTypicalUnitDictionary() {
        UnitDictionaryBuilder unitDictionaryBuilder = new UnitDictionaryBuilder();

        for (TemplateItem templateItem : getTypicalTemplateItems()) {
            unitDictionaryBuilder.withItem(templateItem);
        }
        for (GroceryItem groceryItem : getTypicalGroceryItems()) {
            unitDictionaryBuilder.withItem(groceryItem);
        }
        for (GroceryItem boughtItem : getTypicalBoughtItems()) {
            unitDictionaryBuilder.withItem(boughtItem);
        }
        for (ShoppingItem shoppingItem : getTypicalShoppingItems()) {
            unitDictionaryBuilder.withItem(shoppingItem);
        }

        unitDictionary = unitDictionaryBuilder.build();

        return unitDictionary;
    }

    public static HashMap<String, String> getTypicalUnitDictionaryItems() {
        return unitDictionary.getUnitDictionary();
    }
}
