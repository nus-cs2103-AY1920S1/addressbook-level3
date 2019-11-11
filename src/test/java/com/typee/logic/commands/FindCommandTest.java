package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalEngagements.TYPICAL_APPOINTMENT;
import static com.typee.testutil.TypicalEngagements.TYPICAL_MEETING;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagementList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.typee.commons.core.Messages;
import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;
import com.typee.model.engagement.EngagementPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {

    private Model model = new ModelManager(getTypicalEngagementList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEngagementList(), new UserPrefs());

    @Test
    public void equals() {
        EngagementPredicate firstPredicate = new EngagementPredicate().setDescription("first description");
        EngagementPredicate secondPredicate = new EngagementPredicate().setDescription("second description");

        EngagementPredicate thirdPredicate = firstPredicate.setAttendees("Alice Pauline");
        EngagementPredicate fourthPredicate = new EngagementPredicate().setLocation("Somewhere over the rainbow");
        EngagementPredicate fifthPredicate = new EngagementPredicate().setLocation("Random location");

        EngagementPredicate sixthPredicate = new EngagementPredicate().setPriority("High");
        EngagementPredicate seventhPredicate = new EngagementPredicate().setPriority("Low");

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);
        FindCommand findThirdCommand = new FindCommand(thirdPredicate);
        FindCommand findFourthCommand = new FindCommand(fourthPredicate);
        FindCommand findFifthCommand = new FindCommand(fifthPredicate);
        FindCommand findSixthCommand = new FindCommand(sixthPredicate);
        FindCommand findSeventhCommand = new FindCommand(seventhPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different description -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // different attendees -> returns false
        assertFalse(findSecondCommand.equals(findThirdCommand));

        // different location -> returns false
        assertFalse(findFourthCommand.equals(findFifthCommand));

        // different priority -> returns false
        assertFalse(findSixthCommand.equals(findSeventhCommand));
    }

    @Test
    public void execute_zeroKeywords_allEngagementsListed() {
        String expectedMessage = String.format(Messages.MESSAGE_ENGAGEMENT_LISTED_OVERVIEW, 3);
        EngagementPredicate predicate = new EngagementPredicate();
        FindCommand command = new FindCommand(predicate);

        expectedModel.updateFilteredEngagementList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredEngagementList(), model.getFilteredEngagementList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_ENGAGEMENT_LISTED_OVERVIEW, 1);

        EngagementPredicate appointmentPredicate = new EngagementPredicate();
        appointmentPredicate.setDescription("Appointment");
        appointmentPredicate.setPriority("Low");
        FindCommand commandFilterAppointment = new FindCommand(appointmentPredicate);
        expectedModel.updateFilteredEngagementList(appointmentPredicate);
        assertCommandSuccess(commandFilterAppointment, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TYPICAL_APPOINTMENT), model.getFilteredEngagementList());

        EngagementPredicate meetingPredicate = new EngagementPredicate();
        meetingPredicate.setDescription("Meeting");
        meetingPredicate.setAttendees("Alice Pauline");
        FindCommand commandFilterMeeting = new FindCommand(meetingPredicate);
        expectedModel.updateFilteredEngagementList(meetingPredicate);
        assertCommandSuccess(commandFilterMeeting, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TYPICAL_MEETING), model.getFilteredEngagementList());
    }
}
