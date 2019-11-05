package seedu.address.itinerary.logic.commands;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.itinerary.logic.commands.ListCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Tag;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

class ListCommandTest {

    @Test
    public void execute_list_success() {
        // Checks for the success condition message of the list command
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, true, false);
        assertEquals(expectedCommandResult.getFeedbackToUser(), MESSAGE_SUCCESS);
    }

    @Test
    public void list_message_shown() {

        // Message given upon the successful execution of the list command
        // Also checks whether the sorted list for both the models contain the same elements.
        ListCommand listCommand = new ListCommand();
        Model model = new Model();

        Model expectedModel = new Model();
        String expectedMessage = ListCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void list_with_events() {

        Title titleTest = new Title("Awesome Title");
        Date dateTest = new Date("28102019");
        Location locationTest = new Location("Singapore");
        Description descTest = new Description("My awesome description");
        Time timeTest = new Time("2000");
        Tag tagTest = new Tag("Priority: High");
        Event eventTest = new Event(titleTest, dateTest, locationTest,
                descTest, timeTest, tagTest);

        Title titleTest2 = new Title("Another Cool Title");
        Date dateTest2 = new Date("13071997");
        Location locationTest2 = new Location("USA");
        Description descTest2 = new Description("My cool description");
        Time timeTest2 = new Time("0000");
        Tag tagTest2 = new Tag("Priority: Medium");
        Event eventTest2 = new Event(titleTest2, dateTest2, locationTest2,
                descTest2, timeTest2, tagTest2);

        Model model = new Model();
        model.addEvent(eventTest);

        ListCommand listCommand = new ListCommand();

        Model expectedModel = new Model();
        expectedModel.addEvent(eventTest);
        String expectedMessage = MESSAGE_SUCCESS;

        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);

        // Adding an additional event into the expected
        // While the list command will still pass through, the two list are different.
        expectedModel.addEvent(eventTest2);
        assertCommandFailure(listCommand, model, new CommandResult(expectedMessage), expectedModel);

        // Re-adding the event test 2 into the actual model
        model.addEvent(eventTest2);
        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);

    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualItineraryModel} matches {@code expectedItineraryModel}
     */
    public static void assertCommandFailure(Command command, Model model,
                                            CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(model);
            assertEquals(expectedCommandResult.getFeedbackToUser(), result.getFeedbackToUser());
            assertNotEquals(expectedModel.getSortedEventList().toArray(), model.getSortedEventList().toArray());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
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
            assertEquals(expectedCommandResult.getFeedbackToUser(), result.getFeedbackToUser());
            assertArrayEquals(expectedModel.getSortedEventList().toArray(), model.getSortedEventList().toArray());
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
