package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGroceryItems.getTypicalAddressBook;
import static seedu.address.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.address.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.address.testutil.TypicalWasteList.getTypicalWasteList;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalTemplateList(),
                getTypicalWasteList(), getTypicalShoppingList());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalTemplateList(),
                getTypicalWasteList(), getTypicalShoppingList());
        expectedModel.setGroceryList(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
