package seedu.address.inventory.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import seedu.address.inventory.logic.commands.AddCommand;
import seedu.address.inventory.logic.commands.CommandResult;
import seedu.address.inventory.model.Item;
import seedu.address.stubs.InventoryModelStubWithItem;
import seedu.address.testutil.ItemBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.inventory.ui.InventoryMessages.MESSAGE_ADDED_ITEM;

public class AddCommandTest {
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_ItemAcceptedByModel_addSuccessful() {
        Item validItem = new ItemBuilder().build();
        InventoryModelStubWithItem modelStubWithItem = new InventoryModelStubWithItem(validItem);
        CommandResult commandResult = new AddCommand(validItem).execute(modelStubWithItem);
        assertEquals(String.format(MESSAGE_ADDED_ITEM, validItem),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Item fishBurger = new ItemBuilder().withDescription("Fish Burger").build();
        Item frenchFries = new ItemBuilder().withDescription("French Fries").build();
        AddCommand addBurgerCommand = new AddCommand(fishBurger);
        AddCommand addFriesCommand = new AddCommand(frenchFries);

        // same object -> returns true
        assertTrue(addBurgerCommand.equals(addBurgerCommand));

        // same values -> returns true
        AddCommand addBurgerCommandCopy = new AddCommand(fishBurger);
        assertTrue(addBurgerCommand.equals(addBurgerCommandCopy));

        // different types -> returns false
        assertFalse(addBurgerCommand.equals(1));

        // null -> returns false
        assertFalse(addBurgerCommand.equals(null));

        // different person -> returns false
        assertFalse(addBurgerCommand.equals(addFriesCommand));
    }
}