package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIncidents.getCompleteDraftIncidentManager;
import static seedu.address.testutil.TypicalIncidents.getIncompleteDraftIncidentManager;
import static seedu.address.testutil.TypicalIncidents.getSubmittedReportIncidentManager;
import static seedu.address.testutil.TypicalIncidents.getTypicalCallerNumbers;
import static seedu.address.testutil.TypicalIncidents.getTypicalDescriptions;
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
import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.Incident;
import seedu.address.testutil.IncidentBuilder;
import seedu.address.testutil.TypicalIndexes;

//@@author atharvjoshi
/**
 * Contains integration tests (interaction with the Model) and unit tests for FillCommand.
 */
public class FillCommandTest {

    private Model model;
    private Model expectedModel;

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FillCommand(null, null, null));
        assertThrows(NullPointerException.class, () -> new FillCommand(TypicalIndexes.INDEX_FIRST_ENTITY, null,
                getTypicalDescriptions().get(3)));
        assertThrows(NullPointerException.class, () -> new FillCommand(TypicalIndexes.INDEX_FIRST_ENTITY,
                getTypicalCallerNumbers().get(3), null));
        assertThrows(NullPointerException.class, () -> new FillCommand(null, getTypicalCallerNumbers().get(3),
                getTypicalDescriptions().get(3)));
    }

    @Test
    public void execute_fillIncompleteDraft_success() {
        // when same operator fills an incomplete draft they created
        model = new ModelManager(getIncompleteDraftIncidentManager(), new UserPrefs());
        model.setSession(CARL); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        CallerNumber callerNumberToFill = getTypicalCallerNumbers().get(3);
        Description descriptionToFill = getTypicalDescriptions().get(3);
        FillCommand fillCommand = new FillCommand(TypicalIndexes.INDEX_FIRST_ENTITY, callerNumberToFill,
                descriptionToFill);
                ;
        Incident toFill = getIncompleteDraftIncidentManager().getIncidentList().get(0);
        String expectedMessage = String.format(FillCommand.MESSAGE_FILL_DRAFT_SUCCESS, toFill);

        Incident updatedIncident = new IncidentBuilder(toFill, callerNumberToFill, descriptionToFill).build();
        expectedModel.removeIncident(toFill);
        expectedModel.addIncident(updatedIncident);

        assertCommandSuccess(fillCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_fillCompleteDraft_success() {
        // when same operator fills a complete draft they created
        model = new ModelManager(getCompleteDraftIncidentManager(), new UserPrefs());
        model.setSession(BENSON); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        CallerNumber callerNumberToFill = getTypicalCallerNumbers().get(3);
        Description descriptionToFill = getTypicalDescriptions().get(3);
        FillCommand fillCommand = new FillCommand(TypicalIndexes.INDEX_FIRST_ENTITY, callerNumberToFill,
                descriptionToFill);

        Incident toFill = getCompleteDraftIncidentManager().getIncidentList().get(0);
        String expectedMessage = String.format(FillCommand.MESSAGE_FILL_DRAFT_SUCCESS, toFill);

        Incident updatedIncident = new IncidentBuilder(toFill, callerNumberToFill, descriptionToFill).build();
        expectedModel.removeIncident(toFill);
        expectedModel.addIncident(updatedIncident);

        assertCommandSuccess(fillCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_fillSubmittedReport_throwsCommandException() {
        // when operator tries to fill a submitted report
        model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
        model.setSession(ALICE); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        CallerNumber callerNumberToFill = getTypicalCallerNumbers().get(2);
        Description descriptionToFill = getTypicalDescriptions().get(2);
        FillCommand fillCommand = new FillCommand(TypicalIndexes.INDEX_SECOND_ENTITY, callerNumberToFill,
                descriptionToFill); // the sixthIncident in TypicalIncidents has been submitted by Alice

        assertCommandFailure(fillCommand, model, Messages.MESSAGE_INCIDENT_HAS_BEEN_SUBMITTED);
    }

    @Test
    public void execute_noDraftsToFill_throwsCommandException() {
        // when operator tries to fill a draft when none are present in the system
        model = new ModelManager(getSubmittedReportIncidentManager(), new UserPrefs());
        model.setSession(BENSON); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        CallerNumber callerNumberToFill = getTypicalCallerNumbers().get(2);
        Description descriptionToFill = getTypicalDescriptions().get(2);
        FillCommand fillCommand = new FillCommand(TypicalIndexes.INDEX_FIRST_ENTITY, callerNumberToFill,
                descriptionToFill);

        assertCommandFailure(fillCommand, model, Messages.MESSAGE_NO_DRAFTS_LISTED);

    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // when operator tries to supply invalid index
        model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
        model.setSession(ALICE); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        CallerNumber callerNumberToFill = getTypicalCallerNumbers().get(2);
        Description descriptionToFill = getTypicalDescriptions().get(2);
        FillCommand fillCommand = new FillCommand(TypicalIndexes.INDEX_TENTH_ENTITY, callerNumberToFill,
                descriptionToFill); // the typicalIncidents list only has 7 entries

        assertCommandFailure(fillCommand, model, Messages.MESSAGE_INVALID_INCIDENT_INDEX);
    }

    @Test
    public void execute_fillDraftDifferentOperator_throwsCommandExecution() {
        // when operator tries to fill an incident they have not created
        model = new ModelManager(getIncompleteDraftIncidentManager(), new UserPrefs());
        model.setSession(ALICE); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        CallerNumber callerNumberToFill = getTypicalCallerNumbers().get(2);
        Description descriptionToFill = getTypicalDescriptions().get(2);
        FillCommand fillCommand = new FillCommand(TypicalIndexes.INDEX_FIRST_ENTITY, callerNumberToFill,
                descriptionToFill);

        assertCommandFailure(fillCommand, model, Messages.MESSAGE_NO_ACCESS_TO_FILL_DRAFT);

    }

    @Test
    public void execute_allIncidentFieldsUpdated_success() throws CommandException {
        // to check if incident has updated fields
        model = new ModelManager(getIncompleteDraftIncidentManager(), new UserPrefs());
        model.setSession(ALICE); // Added to simulate a logged in person
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        CallerNumber callerNumberToFill = getTypicalCallerNumbers().get(2);
        Description descriptionToFill = getTypicalDescriptions().get(2);
        FillCommand fillCommand = new FillCommand(TypicalIndexes.INDEX_SECOND_ENTITY, callerNumberToFill,
                descriptionToFill);
        Incident toFill = model.getFilteredIncidentList().get(1);

        CommandResult commandResult = fillCommand.execute(model);
        assertEquals(String.format(FillCommand.MESSAGE_FILL_DRAFT_SUCCESS, toFill), commandResult.getFeedbackToUser());

        Incident updatedIncident = model.getFilteredIncidentList().get(0);
        assertEquals(Incident.Status.COMPLETE_DRAFT, updatedIncident.getStatus());
        assertEquals(callerNumberToFill, updatedIncident.getCallerNumber());
        assertEquals(descriptionToFill, updatedIncident.getDesc());
    }

    @Test
    public void equals() {
        FillCommand validFillCommand = new FillCommand(TypicalIndexes.INDEX_SECOND_ENTITY,
                getTypicalCallerNumbers().get(2), getTypicalDescriptions().get(2));
        FillCommand anotherValidFillCommand = new FillCommand(TypicalIndexes.INDEX_SECOND_ENTITY,
                getTypicalCallerNumbers().get(0), getTypicalDescriptions().get(0));

        // same object -> returns true
        assertTrue(validFillCommand.equals(validFillCommand));

        // same values -> returns true
        FillCommand validFillCommandCopy = new FillCommand(TypicalIndexes.INDEX_SECOND_ENTITY,
                getTypicalCallerNumbers().get(2), getTypicalDescriptions().get(2));
        assertTrue(validFillCommand.equals(validFillCommandCopy));

        // different types -> returns false
        assertFalse(validFillCommand.equals(1));

        // null -> returns false
        assertFalse(validFillCommand.equals(null));

        // different person -> returns false
        assertFalse(validFillCommand.equals(anotherValidFillCommand));
    }
}
