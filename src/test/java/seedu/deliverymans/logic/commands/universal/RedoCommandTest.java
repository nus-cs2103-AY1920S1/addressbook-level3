package seedu.deliverymans.logic.commands.universal;
/*
import static seedu.deliverymans.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.deliverymans.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.ModelManager;
import seedu.deliverymans.model.UserPrefs;
import seedu.deliverymans.model.addressbook.person.Person;
import seedu.deliverymans.model.database.CustomerDatabase;
import seedu.deliverymans.model.database.DeliverymenDatabase;
import seedu.deliverymans.model.database.OrderDatabase;
import seedu.deliverymans.model.database.RestaurantDatabase;


class RedoCommandTest {
    private static final String REDONE_COMMAND_TEXT = "add someone";

    private Model model = new ModelManager(new CustomerDatabase(),
            new DeliverymenDatabase(), new RestaurantDatabase(), new OrderDatabase(), new UserPrefs());
    private Person validPerson = new PersonBuilder().build();

    @Test
    void execute_hasUndo_success() {
        model.addPerson(validPerson);

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, REDONE_COMMAND_TEXT);

        Model updatedModel = new ModelManager(new CustomerDatabase(),
                new DeliverymenDatabase(), new RestaurantDatabase(), new OrderDatabase(), new UserPrefs());
        updatedModel.addPerson(validPerson);
        updatedModel.notifyChange(REDONE_COMMAND_TEXT);
        updatedModel.undo();

        assertCommandSuccess(new RedoCommand(), updatedModel, expectedMessage, model);
    }

    @Test
    void execute_noUndo_throwsCommandException() {
        model.addPerson(validPerson);
        model.notifyChange(REDONE_COMMAND_TEXT);
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_NOTHING_TO_REDO);
    }
}

*/
