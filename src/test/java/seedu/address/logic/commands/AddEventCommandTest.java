package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.person.schedule.Event;
import seedu.address.testutil.modelutil.TypicalModel;
import seedu.address.testutil.scheduleutil.TypicalEvents;

class AddEventCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    public void constructor_allNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddEventCommand(null, null));
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddEventCommand(null, TypicalEvents.generateTypicalEvent1()));
    }

    @Test
    void execute_success() throws CommandException {
        Event event = TypicalEvents.generateTypicalEvent1();

        CommandResult actualCommandResult =
                new AddEventCommand(ALICE.getName(), event).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(AddEventCommand.MESSAGE_SUCCESS + event.toString());

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }

    @Test
    void execute_failure() throws CommandException {
        Event event = TypicalEvents.generateTypicalEvent1();

        CommandResult actualCommandResult =
                new AddEventCommand(ZACK.getName(), event).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(AddEventCommand.MESSAGE_FAILURE
                        + AddEventCommand.MESSAGE_FAILURE_UNABLE_TO_FIND_PERSON);

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }
}
