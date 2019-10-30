package seedu.ifridge.model;

import seedu.ifridge.model.Model;

import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Food;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.exceptions.InvalidUnitException;
import seedu.ifridge.model.food.exceptions.InvalidDictionaryException;


import java.util.HashMap;

/**
 * A unit dictionary.
 */
public class UnitDictionary {

    private HashMap<String, String> unitDictionary;

    public UnitDictionary(HashMap<String, String> unitDictionary) {
        this.unitDictionary = unitDictionary;
    }

    public void checkUnitDictionary(Food foodItem, Model model) {

        Name inputName = foodItem.getName();
        String savedName = inputName.toString().toUpperCase(); //For case-insensitive referencing
        Amount inputAmt = foodItem.getAmount();
        String inputUnitType = inputAmt.getUnitType(inputAmt);

        if (inAnyList(foodItem, model)) {
            String setUnitType = unitDictionary.get(savedName);
            assert(setUnitType, null);
            if (setUnitType == null) {
                throw new InvalidDictionaryException();
            }
            if (!inputUnitType.equals(setUnitType)) {
                throw new InvalidUnitException();
            }
        }
        else {
            unitDictionary.put(savedName, inputUnitType);
        }
    }

    private boolean inAnyList(Food foodItem, Model model) {
        return (inGroceryList(foodItem, model) || inTemplateList(foodItem, model)
                || inShoppingList(foodItem, model));
    }

    private boolean inGroceryList(Food foodItem, Model model) {
        return false;
    }

    private boolean inTemplateList(Food foodItem, Model model) {
        return model.containsTemplateItemWithName(foodItem);
    }

    private boolean inShoppingList(Food foodItem, Model model) {
        return false;
    }

    public HashMap<String, String> getUnitDictionary() { return this.unitDictionary; }

}
