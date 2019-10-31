package seedu.ifridge.model;

import java.util.HashMap;

import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Food;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.exceptions.InvalidDictionaryException;
import seedu.ifridge.model.food.exceptions.InvalidUnitException;

/**
 * A unit dictionary.
 */
public class UnitDictionary {

    private HashMap<String, String> unitDictionary;

    public UnitDictionary(HashMap<String, String> unitDictionary) {
        this.unitDictionary = unitDictionary;
    }

    /**
     * Checks the unit dictionary to see that the unit type of the food item being added or edited does not conflict
     * with other unit types of food items in the groceryList, templateList, shoppingList with the same name.
     * Comparing of names is non-case-sensitive.
     * @param foodItem item to be checked
     * @param model model to be checked against
     * @throws InvalidUnitException unitType conflicts with other food items with the same name
     * @throws InvalidDictionaryException unitDictionary is missing information and is invalid
     */
    public void checkUnitDictionary(Food foodItem, Model model)
            throws InvalidUnitException, InvalidDictionaryException {

        Name inputName = foodItem.getName();
        //For case-insensitive referencing
        String savedName = inputName.toString().toUpperCase();
        Amount inputAmt = foodItem.getAmount();
        String inputUnitType = inputAmt.getUnitType(inputAmt);

        if (inAnyList(foodItem, model)) {
            String setUnitType = unitDictionary.get(savedName);
            if (setUnitType == null) {
                throw new InvalidDictionaryException();
            }
            if (!inputUnitType.equals(setUnitType)) {
                throw new InvalidUnitException();
            }
        } else {
            unitDictionary.put(savedName, inputUnitType);
        }
    }

    private boolean inAnyList(Food foodItem, Model model) {
        return (inGroceryList(foodItem, model) || inTemplateList(foodItem, model)
                || inShoppingList(foodItem, model));
    }

    private boolean inGroceryList(Food foodItem, Model model) {
        return model.hasGroceryItem((GroceryItem) foodItem);
    }

    private boolean inTemplateList(Food foodItem, Model model) {
        return model.containsTemplateItemWithName(foodItem);
    }

    private boolean inShoppingList(Food foodItem, Model model) {
        return false;
    }

    public HashMap<String, String> getUnitDictionary() {
        return this.unitDictionary;
    }

}
