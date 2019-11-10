package seedu.moolah.ui.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.ui.testutil.GuiTestAssert.assertCardDisplaysEvent;

import org.junit.jupiter.api.Test;

import guitests.guihandles.event.EventCardHandle;
import seedu.moolah.model.expense.Event;
import seedu.moolah.testutil.EventBuilder;
import seedu.moolah.ui.GuiUnitTest;

/**
 * Contains tests for {@code EventCard}.
 *
 * Refactored from:
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/seedu/address/ui/PersonCardTest.java
 */
public class EventCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // defaultValues
        Event defaultSampleEvent = new EventBuilder().build();
        EventCard eventCard = new EventCard(defaultSampleEvent, 1);
        uiPartExtension.setUiPart(eventCard);
        assertCardDisplay(eventCard, defaultSampleEvent, 1);

        // with description
        Event eventWithDescription = new EventBuilder().withDescription("description").build();
        eventCard = new EventCard(eventWithDescription, 2);
        uiPartExtension.setUiPart(eventCard);
        assertCardDisplay(eventCard, eventWithDescription, 2);
    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 0);

        // same Event, same index -> returns true
        EventCard copy = new EventCard(event, 0);
        assertEquals(eventCard, copy);

        // same object -> returns true
        assertEquals(eventCard, eventCard);

        // null -> returns false
        assertNotEquals(null, eventCard);

        // different types -> returns false
        assertNotEquals(0, eventCard);

        // same Event, different index -> returns false
        assertNotEquals(eventCard, new EventCard(event, 1));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EventCard personCard, Event expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle personCardHandle = new EventCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId), personCardHandle.getIndex());

        // verify person details are displayed correctly
        assertCardDisplaysEvent(expectedPerson, personCardHandle);
    }
}
