package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.appointments.AppointmentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.events.Event;
import seedu.address.model.events.predicates.EventMatchesRefIdPredicate;
import seedu.address.model.person.parameters.PersonReferenceId;
import seedu.address.testutil.TestUtil;

class AppointmentsCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = TestUtil.getTypicalModelManager();
        expectedModel = TestUtil.getTypicalModelManager();
    }


    @Test
    void testEquals() {
        Event firstEvent = model.getFilteredAppointmentList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event secondEvent = model.getFilteredAppointmentList().get(INDEX_SECOND_EVENT.getZeroBased());

        EventMatchesRefIdPredicate firstPredicate =
                new EventMatchesRefIdPredicate(firstEvent.getPersonId());
        EventMatchesRefIdPredicate secondPredicate =
                new EventMatchesRefIdPredicate(secondEvent.getPersonId());

        AppointmentsCommand firstApptList = new AppointmentsCommand(firstPredicate);
        AppointmentsCommand secondApptList = new AppointmentsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstApptList.equals(firstApptList));

        // same values -> returns true
        AppointmentsCommand findFirstCommandCopy = new AppointmentsCommand(firstPredicate);
        assertTrue(firstApptList.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(firstApptList.equals(1));

        // null -> returns false
        assertFalse(firstApptList.equals(null));

        // different person -> returns false
        assertFalse(firstApptList.equals(secondApptList));
    }

    //    execute_zeroKeywords_noEventFound
    @Test
    public void execute_zeroKeywords_allEventsFound() {
        AppointmentsCommand command = new AppointmentsCommand(PREDICATE_SHOW_ALL_EVENTS);
        String expectedMessage = String.format(Messages.MESSAGE_ALL_EVENTS_LISTED_OVERVIEW,
                model.getFilteredAppointmentList().size(), PREDICATE_SHOW_ALL_EVENTS.toString());
        expectedModel.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_EVENTS);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidKeywords_noEventFound() throws ParseException {

        EventMatchesRefIdPredicate invalidPredicate = new EventMatchesRefIdPredicate(
                PersonReferenceId.issuePatientReferenceId(VALID_ID_AMY));

        String expectedMessage = String.format(
                Messages.MESSAGE_ALL_EVENTS_LISTED_OVERVIEW, 0, invalidPredicate.toString());

        AppointmentsCommand command = new AppointmentsCommand(invalidPredicate);
        expectedModel.updateFilteredAppointmentList(invalidPredicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        model.updateFilteredAppointmentList(invalidPredicate);
        assertEquals(Collections.emptyList(), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_validKeywords_eventsFound() {
        Event validEvent = model.getFilteredAppointmentList().get(INDEX_SECOND_EVENT.getZeroBased());

        EventMatchesRefIdPredicate validPredicate = new EventMatchesRefIdPredicate(validEvent.getPersonId());

        AppointmentsCommand command = new AppointmentsCommand(validPredicate);
        expectedModel.updateFilteredAppointmentList(validPredicate);

        String expectedMessage = String.format(Messages.MESSAGE_ALL_EVENTS_LISTED_OVERVIEW,
                expectedModel.getFilteredAppointmentList().size(), validPredicate.toString());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        model.updateFilteredAppointmentList(validPredicate);
    }
}
