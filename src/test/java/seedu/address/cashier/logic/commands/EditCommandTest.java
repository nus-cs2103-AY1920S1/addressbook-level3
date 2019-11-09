package seedu.address.cashier.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.cashier.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.cashier.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class EditCommandTest {

    private static final int VALID_INDEX = 2;
    private static final int VALID_QUANTITY = 59;
    private static final int INVALID_INDEX_1 = 0;
    private static final int INVALID_INDEX_2 = 100;
    private static final int INVALID_QUANTITY = -5;
    private static final int INVALID_QUANTITY_STORYBOOK = 5000000;

    private seedu.address.cashier.model.ModelManager model =
            new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());

    private Model personModel =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_invalidIndex_throwAssertionException() {
        assertThrows(AssertionError.class, () -> new EditCommand(INVALID_INDEX_1, VALID_QUANTITY));
    }

    @Test
    public void constructor_invalidQuantity_throwAssertionException() {
        assertThrows(AssertionError.class, () -> new EditCommand(VALID_INDEX, INVALID_QUANTITY));
    }

    @Test
    public void execute_validIndexValidQuantity_successful() throws NoSuchItemException {
        EditCommand editCommand = new EditCommand(VALID_INDEX, VALID_QUANTITY);
        String message = String.format(CashierMessages.MESSAGE_EDIT_SUCCESS,
                TypicalItem.STORYBOOK.getDescription(), VALID_QUANTITY);

        seedu.address.cashier.model.ModelManager expectedModel =
                new seedu.address.cashier.model.ModelManager(TypicalItem.getTypicalInventoryList(),
                TypicalTransactions.getTypicalTransactionList());
        expectedModel.addItem(TypicalItem.FISH_BURGER);
        expectedModel.addItem(TypicalItem.STORYBOOK);
        expectedModel.editItem(VALID_INDEX, VALID_QUANTITY);

        model.addItem(TypicalItem.FISH_BURGER);
        model.addItem(TypicalItem.STORYBOOK);

        assertCommandSuccess(editCommand, model, message, expectedModel, (CheckAndGetPersonByNameModel) personModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        EditCommand editCommand = new EditCommand(INVALID_INDEX_2, VALID_QUANTITY);
        String message = CashierMessages.NO_SUCH_INDEX_CASHIER;
        assertCommandFailure(editCommand, model, message, (CheckAndGetPersonByNameModel) personModel);
    }

    @Test
    public void execute_invalidQuantity_failure() {
        EditCommand editCommand = new EditCommand(VALID_INDEX, INVALID_QUANTITY_STORYBOOK);
        String message = String.format(CashierMessages.MESSAGE_INSUFFICIENT_STOCK,
                TypicalItem.STORYBOOK.getQuantity(), TypicalItem.STORYBOOK.getDescription());

        model.addItem(TypicalItem.FISH_BURGER);
        model.addItem(TypicalItem.STORYBOOK);

        assertCommandFailure(editCommand, model, message, (CheckAndGetPersonByNameModel) personModel);
    }


}

