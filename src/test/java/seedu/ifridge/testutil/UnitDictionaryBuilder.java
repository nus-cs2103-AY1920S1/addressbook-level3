package seedu.ifridge.testutil;

import java.util.HashMap;

import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.food.Food;


/**
 * A utility class to help with building GroceryList objects.
 * Example usage: <br>
 *     {@code GroceryList ab = new GroceryListBuilder().withPerson("John", "Doe").build();}
 */
public class UnitDictionaryBuilder {

    private UnitDictionary unitDictionary;

    public UnitDictionaryBuilder() {
        unitDictionary = new UnitDictionary(new HashMap<String, String>());
    }

    public UnitDictionaryBuilder(UnitDictionary unitDictionary) {
        this.unitDictionary = unitDictionary;
    }

    /**
     * Adds a new entry with {@code name} and {@code unit} to the {@code UnitDictionary} that we are building.
     */
    public UnitDictionaryBuilder withItem(Food foodItem) {
        unitDictionary.setUnitDictionary(foodItem);
        return this;
    }

    public UnitDictionary build() {
        return unitDictionary;
    }
}
