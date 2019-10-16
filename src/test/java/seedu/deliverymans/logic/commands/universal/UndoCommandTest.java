package seedu.deliverymans.logic.commands.universal;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.deliverymans.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.deliverymans.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.deliverymans.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.ModelManager;
import seedu.deliverymans.model.UserPrefs;
import seedu.deliverymans.model.addressbook.person.Person;
import seedu.deliverymans.model.database.CustomerDatabase;
import seedu.deliverymans.model.database.DeliverymenDatabase;
import seedu.deliverymans.model.database.OrderBook;
import seedu.deliverymans.model.database.RestaurantDatabase;

class UndoCommandTest {
    private static final String UNDONE_COMMAND_TEXT = "delete 1";
    private Model model = new ModelManager(getTypicalAddressBook(), new CustomerDatabase(),
            new DeliverymenDatabase(), new RestaurantDatabase(), new OrderBook(), new UserPrefs());

    @Test
    void execute_hasUndo_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, UNDONE_COMMAND_TEXT);

        Model updatedModel = new ModelManager(getTypicalAddressBook(), new CustomerDatabase(),
                new DeliverymenDatabase(), new RestaurantDatabase(), new OrderBook(), new UserPrefs());
        updatedModel.deletePerson(personToDelete);
        updatedModel.notifyChange(UNDONE_COMMAND_TEXT);

        assertCommandSuccess(new UndoCommand(), updatedModel, expectedMessage, model);
    }

    @Test
    void execute_noUndo_throwsCommandException() {
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_NOTHING_TO_UNDO);
    }
}
