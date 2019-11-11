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

    public UnitDictionary(UnitDictionary unitDictionary) {
        this.unitDictionary = unitDictionary.getUnitDictionary();
    }

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
                || inShoppingList(foodItem, model)) || inBoughtList(foodItem, model);
    }

    /**
     * Check if there is a food item with the same name in the grocery list as the food argument.
     * @param foodItem The food argument.
     * @param model The model to check with.
     * @return Returns true if the grocery list in model contains an item with the same name as the food argument.
     */
    private boolean inGroceryList(Food foodItem, Model model) {
        for (int i = 0; i < model.getGroceryList().getGroceryList().size(); i++) {
            Food foodInModel = model.getGroceryList().getGroceryList().get(i);
            if (foodItem.isSameFood(foodInModel)) {
                return true;
            }
        }
        return false;
    }

    private boolean inTemplateList(Food foodItem, Model model) {
        return model.containsTemplateItemWithName(foodItem);
    }

    /**
     * Check if there is a shopping item in the shopping list with same name as that of the food in argument.
     * @param foodItem food passed as argument
     * @param model model to compare with
     * @return true if shopping list in model contains a shopping item with same name as food argument
     */
    private boolean inShoppingList(Food foodItem, Model model) {
        return model.containsShoppingItemWithName(foodItem);
    }

    /**
     * Check if there is a bought item in the bought list with same name as that of the food argument.
     * @param foodItem food passed as argument
     * @param model model to compare with
     * @return true if bought list in model contains a bought item with same name as food argument
     */
    private boolean inBoughtList(Food foodItem, Model model) {
        for (GroceryItem boughtItem : model.getBoughtList().getGroceryList()) {
            if (foodItem.isSameFood(boughtItem)) {
                return true;
            }
        }
        return false;
    }

    public HashMap<String, String> getUnitDictionary() {
        return this.unitDictionary;
    }

    public HashMap<String, String> setUnitDictionary(Food foodItem) {
        Name inputName = foodItem.getName();
        //For case-insensitive referencing
        String savedName = inputName.toString().toUpperCase();
        Amount inputAmt = foodItem.getAmount();
        String inputUnitType = inputAmt.getUnitType(inputAmt);

        unitDictionary.put(savedName, inputUnitType);

        return this.unitDictionary;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof UnitDictionary
                && this.getUnitDictionary().keySet().equals(((UnitDictionary) other).getUnitDictionary().keySet()));
    }

}
