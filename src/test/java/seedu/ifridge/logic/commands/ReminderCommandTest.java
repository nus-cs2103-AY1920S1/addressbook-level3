package seedu.ifridge.logic.commands;

import static seedu.ifridge.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.logic.commands.grocerylist.ReminderCommand;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.food.NameContainsCloseExpiryDatePredicate;
import seedu.ifridge.testutil.GroceryItemBuilder;

public class ReminderCommandTest {
    private Model model = new ModelManager(new GroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            new UnitDictionary(new HashMap<String, String>()));
    private Model expectedModel = new ModelManager(new GroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            new UnitDictionary(new HashMap<String, String>()));
    @Test
    public void execute_validNumberOfDays_successful() {
        String dateOfToday = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        model.addGroceryItem(new GroceryItemBuilder().withName("Apple")
                .withAmount("8units").withExpiryDate(dateOfToday).withTags("healthy", "fruits").build());
        model.addGroceryItem(new GroceryItemBuilder().withName("Banana")
                .withAmount("8units").withExpiryDate("10/10/2000").withTags("potassium", "fruits").build());
        expectedModel.addGroceryItem(new GroceryItemBuilder().withName("Apple")
                .withAmount("8units").withExpiryDate(dateOfToday).withTags("healthy", "fruits").build());
        expectedModel.addGroceryItem(new GroceryItemBuilder().withName("Banana")
                .withAmount("8units").withExpiryDate("10/10/2000").withTags("potassium", "fruits").build());

        String expectedMessage = String.format(Messages.MESSAGE_GROCERY_LIST_LISTED_OVERVIEW, 1);
        NameContainsCloseExpiryDatePredicate predicate = new NameContainsCloseExpiryDatePredicate(0);
        ReminderCommand reminderCommand = new ReminderCommand(predicate);
        expectedModel.updateFilteredGroceryItemList(predicate);
        assertCommandSuccess(reminderCommand, model, expectedMessage, expectedModel);
    }

    private NameContainsCloseExpiryDatePredicate preparePredicate(int input) {
        return new NameContainsCloseExpiryDatePredicate(input);
    }
}
