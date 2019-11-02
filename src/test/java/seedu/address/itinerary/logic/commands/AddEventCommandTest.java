package seedu.address.itinerary.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.transformation.SortedList;
import org.junit.jupiter.api.Test;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Tag;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;
import seedu.address.logic.commands.CommandResult;

/**
 * Contains integration tests (interaction with the ItineraryModel) for {@code AddEventCommand}.
 */
class AddEventCommandTest {

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


    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddEventCommand(null));
    }

    /**
     * A default itineraryModel stub that have all of the methods failing.
     */
    private class ItineraryModelStub extends Model {
        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<String> getActionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEvent(Event event, Event toEdit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SortedList<Event> getSortedEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void doneEvent(Event event, Event toEdit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAction(String action) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearEvent() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A ItineraryModel stub that always accept the event being added.
     */
    private class ItineraryModelStubAcceptingEventAdded extends ItineraryModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ItineraryModelStubAcceptingEventAdded modelStub = new ItineraryModelStubAcceptingEventAdded();

        CommandResult commandResult = new AddEventCommand(eventTest).execute(modelStub);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, eventTest), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(eventTest), modelStub.eventsAdded);
    }

    @Test
    public void equals() {
        AddEventCommand addEventCommand = new AddEventCommand(eventTest);
        AddEventCommand addEventCommand1 = new AddEventCommand(eventTest2);

        // same object -> returns true
        assertTrue(addEventCommand.equals(addEventCommand));

        // same object -> returns true
        AddEventCommand addEventCommandCopy = new AddEventCommand(eventTest);
        assertTrue(addEventCommand.equals(addEventCommandCopy));

        // different types -> returns false
        assertFalse(addEventCommand.equals(1));

        // null -> returns false
        assertFalse(addEventCommand.equals(null));

        // different person -> returns false
        assertFalse(addEventCommand.equals(addEventCommand1));
    }
}
