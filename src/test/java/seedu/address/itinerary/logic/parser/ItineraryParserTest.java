package seedu.address.itinerary.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.itinerary.logic.commands.AddEventCommand;
import seedu.address.itinerary.logic.commands.DeleteEventCommand;
import seedu.address.itinerary.logic.commands.DoneEventCommand;
import seedu.address.itinerary.logic.commands.GreetCommand;
import seedu.address.itinerary.logic.commands.ListCommand;
import seedu.address.itinerary.logic.commands.SearchCommand;
import seedu.address.itinerary.logic.commands.SortCommand;
import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Tag;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class ItineraryParserTest {

    private final ItineraryParser parser = new ItineraryParser();

    private Title titleTest = new Title("Awesome Title");
    private Date dateTest = new Date("28102019");
    private Location locationTest = new Location("Singapore");
    private Description descTest = new Description("My awesome description");
    private Time timeTest = new Time("2000");
    private Tag tagTest = new Tag("Priority: High");
    private Event eventTest = new Event(titleTest, dateTest, locationTest,
            descTest, timeTest, tagTest);

    private Title titleTest2 = new Title("Another Cool Title");
    private Date dateTest2 = new Date("13071997");
    private Location locationTest2 = new Location("USA");
    private Description descTest2 = new Description("My cool description");
    private Time timeTest2 = new Time("0000");
    private Tag tagTest2 = new Tag("Priority: Medium");
    private Event eventTest2 = new Event(titleTest2, dateTest2, locationTest2,
            descTest2, timeTest2, tagTest2);

    private AddEventCommand outcome = new AddEventCommand(eventTest);
    private AddEventCommand outcome2 = new AddEventCommand(eventTest2);
    private DeleteEventCommand delOutcome = new DeleteEventCommand(Index.fromOneBased(1));
    private DoneEventCommand doneOutcome = new DoneEventCommand(Index.fromOneBased(1));

    @Test
    public void parseCommand_add() {
        // Adding words before the command word
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + AddEventCommand.COMMAND_WORD + "title/Awesome title date/13071997 time/0000"));
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + AddEventCommand.COMMAND_WORD + "word after"));

        // Add Event command requires the tag input which is dependant on the GUI combo box
        assertEquals(new AddEventCommand(eventTest), outcome);
        assertEquals(new AddEventCommand(eventTest2), outcome2);
        assertNotEquals(new AddEventCommand(eventTest2), outcome);
        assertNotEquals(new AddEventCommand(eventTest), outcome2);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        // Adding words before the command word
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + DeleteEventCommand.COMMAND_WORD + " 1"));
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + DeleteEventCommand.COMMAND_WORD + "word after"));

        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());

        assertThrows(ParseException.class, () -> parser.parseCommand(DeleteEventCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand(DeleteEventCommand.COMMAND_WORD + " 0"));

        assertEquals(new DeleteEventCommand(INDEX_FIRST_PERSON), command);
        assertEquals(new DeleteEventCommand(Index.fromOneBased(1)), delOutcome);

        assertNotEquals(new DeleteEventCommand(Index.fromZeroBased(2)), delOutcome);
        assertNotEquals(new DeleteEventCommand(Index.fromOneBased(2)), delOutcome);
        assertNotEquals(new DeleteEventCommand(Index.fromZeroBased(1)), delOutcome);
    }

    @Test
    public void parseCommand_done() throws Exception {
        // Adding words before the command word
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + DoneEventCommand.COMMAND_WORD + " 1"));
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + DoneEventCommand.COMMAND_WORD + "word after"));

        DoneEventCommand command = (DoneEventCommand) parser.parseCommand(
                DoneEventCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());

        assertThrows(ParseException.class, () -> parser.parseCommand(DoneEventCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand(DoneEventCommand.COMMAND_WORD + " 0"));

        assertEquals(new DoneEventCommand(INDEX_FIRST_PERSON), command);
        assertEquals(new DoneEventCommand(Index.fromOneBased(1)), doneOutcome);

        assertNotEquals(new DoneEventCommand(Index.fromZeroBased(2)), doneOutcome);
        assertNotEquals(new DoneEventCommand(Index.fromOneBased(2)), doneOutcome);
        assertNotEquals(new DoneEventCommand(Index.fromZeroBased(1)), doneOutcome);
    }

    @Test
    public void parseCommand_list() throws Exception {
        // Adding words before the command word
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + ListCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + ListCommand.COMMAND_WORD + "word after"));

        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 420") instanceof ListCommand);
    }

    @Test
    public void parseCommand_greet() throws Exception {
        // Adding words before the command word
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + GreetCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + GreetCommand.COMMAND_WORD + "word after"));

        assertTrue(parser.parseCommand(GreetCommand.COMMAND_WORD) instanceof GreetCommand);
        assertTrue(parser.parseCommand(GreetCommand.COMMAND_WORD + " Hello!") instanceof GreetCommand);
        assertTrue(parser.parseCommand(GreetCommand.COMMAND_WORD + " 420") instanceof GreetCommand);
    }

    @Test
    public void parseCommand_search() throws Exception {
        // Adding words before the command word
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + SearchCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + SearchCommand.COMMAND_WORD + "word after"));

        // Parse error for wrong user input
        assertThrows(ParseException.class, () -> parser.parseCommand(SearchCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand(SearchCommand.COMMAND_WORD
                + " y/hello"));
        assertThrows(ParseException.class, () -> parser.parseCommand(SearchCommand.COMMAND_WORD
                + " y/title"));
        assertThrows(ParseException.class, () -> parser.parseCommand(SearchCommand.COMMAND_WORD
                + " date/title"));
        assertThrows(ParseException.class, () -> parser.parseCommand(SearchCommand.COMMAND_WORD
                + " time/this is a string"));

        assertTrue(parser.parseCommand(SearchCommand.COMMAND_WORD + " title/Title") instanceof SearchCommand);
        assertTrue(parser.parseCommand(SearchCommand.COMMAND_WORD + " date/08082019")
                instanceof SearchCommand);
        assertTrue(parser.parseCommand(SearchCommand.COMMAND_WORD + " time/2000") instanceof SearchCommand);
        assertTrue(parser.parseCommand(SearchCommand.COMMAND_WORD + " l/Singapore") instanceof SearchCommand);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        // Adding words before / after the command word
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + SortCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + SortCommand.COMMAND_WORD + "word after"));

        // Parse error for wrong user input
        assertThrows(ParseException.class, () -> parser.parseCommand(SortCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand(SortCommand.COMMAND_WORD
                + " by/none"));
        assertThrows(ParseException.class, () -> parser.parseCommand(SortCommand.COMMAND_WORD
                + " y/priority"));
        assertThrows(ParseException.class, () -> parser.parseCommand(SortCommand.COMMAND_WORD
                + " y/"));
        assertThrows(ParseException.class, () -> parser.parseCommand(SortCommand.COMMAND_WORD
                + " 1"));

        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " by/title") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " by/chronological") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " by/completion") instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " by/priority") instanceof SortCommand);
    }


    @Test
    public void parseCommand_exit() throws Exception {
        // Adding words before the command word
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + ExitCommand.COMMAND_WORD));
        assertThrows(ParseException.class, () -> parser.parseCommand("word before "
                + ExitCommand.COMMAND_WORD + "word after"));

        // The exit command is linked from AB3 exit command
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

}
