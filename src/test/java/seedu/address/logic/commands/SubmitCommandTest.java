package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIncidents.getCompleteDraftIncidentManager;
import static seedu.address.testutil.TypicalIncidents.getIncompleteDraftIncidentManager;
import static seedu.address.testutil.TypicalIncidents.getTypicalIncidentManager;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.incident.Incident;
import seedu.address.testutil.IncidentBuilder;
import seedu.address.testutil.TypicalIndexes;

//@@author atharvjoshi
/**
 * Contains integration tests (interaction with the Model) and unit tests for FillCommand.
 */
public class SubmitCommandTest {
    private Model model;
    private Model expectedModel;

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SubmitCommand(null));
    }

    @Test
    public void execute_submitCompleteDraft_success() {
        // when same operator submits a complete draft they created
        model = new ModelManager(getCompleteDraftIncidentManager(), new UserPrefs());
        model.setSession(BENSON); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        SubmitCommand submitCommand = new SubmitCommand(TypicalIndexes.INDEX_FIRST_ENTITY);

        Incident toSubmit = getCompleteDraftIncidentManager().getIncidentList().get(0);
        String expectedMessage = String.format(SubmitCommand.MESSAGE_SUBMIT_SUCCESS, toSubmit);

        Incident updatedIncident = new IncidentBuilder(toSubmit, Incident.Status.SUBMITTED_REPORT).build();
        expectedModel.removeIncident(toSubmit);
        expectedModel.addIncident(updatedIncident);

        assertCommandSuccess(submitCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_submitIncompleteDraft_throwsCommandException() {
        // when operator tries to submit an incomplete report
        model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
        model.setSession(CARL); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        SubmitCommand submitCommand = new SubmitCommand(TypicalIndexes.INDEX_THIRD_ENTITY); // written by carl

        assertCommandFailure(submitCommand, model, Messages.MESSAGE_DRAFT_IS_INCOMPLETE);
    }

    @Test
    public void execute_submitSubmittedReport_throwsCommandException() {
        // when operator tries to submit an already submitted report
        model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
        model.setSession(ALICE); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        SubmitCommand submitCommand = new SubmitCommand(TypicalIndexes.INDEX_SECOND_ENTITY); // submitted by alice

        assertCommandFailure(submitCommand, model, Messages.MESSAGE_INCIDENT_HAS_BEEN_SUBMITTED);
    }

    @Test
    public void execute_noReportsToSubmit_throwsCommandException() {
        // when there are no reports to submit
        model = new ModelManager(getIncompleteDraftIncidentManager(), new UserPrefs());
        model.setSession(ALICE); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        SubmitCommand submitCommand = new SubmitCommand(TypicalIndexes.INDEX_SECOND_ENTITY);

        assertCommandFailure(submitCommand, model, Messages.MESSAGE_NO_INCIDENT_TO_SUBMIT);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // when index entered is invalid
        model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
        model.setSession(ALICE); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        SubmitCommand submitCommand = new SubmitCommand(TypicalIndexes.INDEX_TENTH_ENTITY);

        assertCommandFailure(submitCommand, model, Messages.MESSAGE_INVALID_INCIDENT_INDEX);
    }

    @Test
    public void execute_submitReportDifferentOperator_throwsCommandException() {
        // when operator tries to submit incident not filled by them
        model = new ModelManager(getCompleteDraftIncidentManager(), new UserPrefs());
        model.setSession(ALICE); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        SubmitCommand submitCommand = new SubmitCommand(TypicalIndexes.INDEX_FIRST_ENTITY); // not filled by Alice

        assertCommandFailure(submitCommand, model, Messages.MESSAGE_NO_ACCESS_TO_SUBMIT_REPORT);
    }

    @Test
    public void execute_incidentStatusUpdated_success() throws CommandException {
        // to check if incident has updated fields
        model = new ModelManager(getCompleteDraftIncidentManager(), new UserPrefs());
        model.setSession(ALICE); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        SubmitCommand submitCommand = new SubmitCommand(TypicalIndexes.INDEX_SECOND_ENTITY);
        Incident toSubmit = model.getFilteredIncidentList().get(1);

        CommandResult commandResult = submitCommand.execute(model);
        assertEquals(String.format(SubmitCommand.MESSAGE_SUBMIT_SUCCESS, toSubmit), commandResult.getFeedbackToUser());

        Incident updatedIncident = model.getFilteredIncidentList().get(0);
        assertEquals(Incident.Status.SUBMITTED_REPORT, updatedIncident.getStatus());
    }

    @Test
    public void equals() {
        SubmitCommand validSubmitCommand = new SubmitCommand(TypicalIndexes.INDEX_SECOND_ENTITY);
        SubmitCommand anotherValidSubmitCommand = new SubmitCommand(TypicalIndexes.INDEX_FIRST_ENTITY);

        // same object -> returns true
        assertTrue(validSubmitCommand.equals(validSubmitCommand));

        // same values -> returns true
        SubmitCommand validSubmitCommandCopy = new SubmitCommand(TypicalIndexes.INDEX_SECOND_ENTITY);
        assertTrue(validSubmitCommand.equals(validSubmitCommandCopy));

        // different types -> returns false
        assertFalse(validSubmitCommand.equals(1));

        // null -> returns false
        assertFalse(validSubmitCommand.equals(null));

        // different person -> returns false
        assertFalse(validSubmitCommand.equals(anotherValidSubmitCommand));
    }
}
