package seedu.address.itinerary.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.model.event.*;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Contains integration tests (interaction with the ItineraryModel, UndoCommand and HistoryCommand) and unit tests for
 * {@code DeleteEventCommand}.
 */
public class DeleteEventCommandTest {
    Index index_first_event = Index.fromOneBased(1);
    Index index_second_event = Index.fromOneBased(2);

    private Title titleTest = new Title("Awesome Title");
    private Date dateTest = new Date("28102019");
    private Location locationTest = new Location("Singapore");
    private Description descTest = new Description("My awesome description");
    private Time timeTest = new Time("2000");
    private Tag tagTest = new Tag("Priority: High");
    private Event eventTest = new Event(titleTest, dateTest, locationTest
            , descTest, timeTest, tagTest);

    private Model model = new Model();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.addEvent(eventTest);
        Event eventToDelete = model.getSortedEventList().get(index_first_event.getZeroBased());
        DeleteEventCommand deleteCommand = new DeleteEventCommand(index_first_event);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_SUCCESS, eventToDelete);

        Model expectedModel = new Model();
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        model.addEvent(eventTest);
        Event eventToDelete = model.getSortedEventList().get(index_first_event.getZeroBased());
        DeleteEventCommand deleteCommand = new DeleteEventCommand(index_first_event);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_SUCCESS, eventToDelete);

        Model expectedModel = new Model();
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteFirstCommand = new DeleteEventCommand(index_first_event);
        DeleteEventCommand deleteSecondCommand = new DeleteEventCommand(index_second_event);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstCommandCopy = new DeleteEventCommand(index_first_event);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualItineraryModel} matches {@code expectedItineraryModel}
     */
    public static void assertCommandSuccess(Command command, Model model,
                                            CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(model);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, model);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    public static void assertCommandSuccess(Command command, Model model,
                                            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }
}
