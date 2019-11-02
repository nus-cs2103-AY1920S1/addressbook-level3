package seedu.address.itinerary.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.itinerary.logic.commands.AddEventCommand;
import seedu.address.itinerary.logic.commands.DeleteEventCommand;
import seedu.address.itinerary.logic.commands.DoneEventCommand;
import seedu.address.itinerary.logic.commands.SearchCommand;
import seedu.address.itinerary.logic.commands.SortCommand;
import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Tag;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;

class ItineraryParserTest {

    private final ItineraryParser parser = new ItineraryParser();

    private Title titleTest = new Title("Awesome Title");
    private Date dateTest = new Date("28102019");
    private Location locationTest = new Location("Singapore");
    private Description descTest = new Description("My awesome description");
    private Time timeTest = new Time("2000");
    private Tag tagTest = new Tag("Priority: High");
    private Event eventTest = new Event(titleTest, dateTest, locationTest
            , descTest, timeTest, tagTest);

    private Title titleTest2 = new Title("Another Cool Title");
    private Date dateTest2 = new Date("13071997");
    private Location locationTest2 = new Location("USA");
    private Description descTest2 = new Description("My cool description");
    private Time timeTest2 = new Time("0000");
    private Tag tagTest2 = new Tag("Priority: Medium");
    private Event eventTest2 = new Event(titleTest2, dateTest2, locationTest2
            , descTest2, timeTest2, tagTest2);

    AddEventCommand outcome = new AddEventCommand(eventTest);
    AddEventCommand outcome2 = new AddEventCommand(eventTest2);
    DeleteEventCommand delOutcome = new DeleteEventCommand(Index.fromOneBased(1));
    DoneEventCommand doneOutcome = new DoneEventCommand(Index.fromOneBased(1));
    SearchCommand.SearchEventDescriptor searchTest = new SearchCommand.SearchEventDescriptor();
    SearchCommand searchOutcome = new SearchCommand(searchTest);
    SortCommand sortOutcome = new SortCommand("priority");

    @Test
    public void parseCommand_add() throws Exception {
        assertEquals(new AddEventCommand(eventTest), outcome);
        assertEquals(new AddEventCommand(eventTest2), outcome2);
        assertNotEquals(new AddEventCommand(eventTest2), outcome);
        assertNotEquals(new AddEventCommand(eventTest), outcome2);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        assertEquals(new DeleteEventCommand(Index.fromOneBased(1)), delOutcome);
        assertNotEquals(new DeleteEventCommand(Index.fromZeroBased(2)), delOutcome);
        assertNotEquals(new DeleteEventCommand(Index.fromOneBased(2)), delOutcome);
        assertNotEquals(new DeleteEventCommand(Index.fromZeroBased(1)), delOutcome);
    }

    @Test
    public void parseCommand_done() throws Exception {
        assertEquals(new DoneEventCommand(Index.fromOneBased(1)), doneOutcome);
        assertNotEquals(new DoneEventCommand(Index.fromZeroBased(2)), doneOutcome);
        assertNotEquals(new DoneEventCommand(Index.fromOneBased(2)), doneOutcome);
        assertNotEquals(new DoneEventCommand(Index.fromZeroBased(1)), doneOutcome);
    }

    @Test
    public void parseCommand_search() throws Exception {
        assertNotEquals(new SearchCommand(searchTest), searchOutcome);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertNotEquals(new SortCommand("priority"), sortOutcome);
    }

}
