package seedu.address.itinerary.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import seedu.address.address.logic.commands.ClearCommand;
import seedu.address.commons.core.index.Index;
import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Tag;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;

class EditCommandTest {
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

    @Test
    public void equals() {
        EditCommand.EditEventDescriptor eventDescriptor = new EditCommand.EditEventDescriptor();
        final EditCommand standardCommand = new EditCommand(index_first_event, eventDescriptor);

        // same values -> returns true
        EditCommand.EditEventDescriptor copyDescriptor = new EditCommand.EditEventDescriptor(eventDescriptor);
        EditCommand commandWithSameValues = new EditCommand(index_first_event, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        assertTrue(standardCommand.equals(new EditCommand(index_first_event, eventDescriptor)));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(index_second_event, eventDescriptor)));
    }
}
