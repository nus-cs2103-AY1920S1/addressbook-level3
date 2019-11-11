package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showIncidentAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INCIDENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_SUBMITTED_INCIDENT_REPORTS;
import static seedu.address.model.Model.PREDICATE_SHOW_INCIDENT_LISTING_ERROR;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTITY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalEntities;
import seedu.address.testutil.TypicalIncidents;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListIncidentCommand.
 */
public class ListIncidentsCommandTest {

    private Model model;
    private Model expectedModel;

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        model = new ModelManager(TypicalEntities.getTypicalIncidentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());
        assertCommandSuccess(new ListIncidentsCommand(PREDICATE_SHOW_ALL_INCIDENTS), model,
                Messages.MESSAGE_ALL_INCIDENTS_LISTED, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        model = new ModelManager(TypicalEntities.getTypicalIncidentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        showIncidentAtIndex(model, INDEX_FIRST_ENTITY);
        assertCommandSuccess(new ListIncidentsCommand(PREDICATE_SHOW_ALL_INCIDENTS), model,
                Messages.MESSAGE_ALL_INCIDENTS_LISTED, expectedModel);
    }

    //@@author atharvjoshi
    @Test
    public void execute_draftParameters_showAllDrafts() {
        // listing all drafts
        // also a test for fill command in parameter mode
        model = new ModelManager(TypicalIncidents.getTypicalIncidentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        ListIncidentsCommand fillNoParams = new ListIncidentsCommand(Model.PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS);
        String expectedMessage = Messages.MESSAGE_ALL_DRAFT_INCIDENTS_LISTED;

        expectedModel.updateFilteredIncidentList(Model.PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS);

        assertCommandSuccess(fillNoParams, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_draftParameters_showNoDrafts() {
        // when no drafts to fill are present in the system
        model = new ModelManager(TypicalIncidents.getSubmittedReportIncidentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        ListIncidentsCommand fillNoParams = new ListIncidentsCommand(Model.PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS);
        String expectedMessage = Messages.MESSAGE_NO_DRAFTS_LISTED;

        expectedModel.updateFilteredIncidentList(Model.PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS);

        assertCommandSuccess(fillNoParams, model, expectedMessage, expectedModel);
    }

    //@@author Yoshi275
    @Test
    public void execute_completedDraftParameters_showAllCompletedDrafts() {
        // listing all completed drafts
        // also a test for fill command in parameter mode
        model = new ModelManager(TypicalIncidents.getTypicalIncidentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        ListIncidentsCommand fillNoParams = new ListIncidentsCommand(Model.PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS);
        String expectedMessage = Messages.MESSAGE_ALL_COMPLETE_INCIDENTS_LISTED;

        expectedModel.updateFilteredIncidentList(Model.PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS);

        assertCommandSuccess(fillNoParams, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_completedDraftParameters_showNoCompletedDrafts() {
        // when no completed drafts are present in the system
        model = new ModelManager(TypicalIncidents.getIncompleteDraftIncidentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        ListIncidentsCommand fillNoParams = new ListIncidentsCommand(Model.PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS);
        String expectedMessage = Messages.MESSAGE_NO_INCIDENT_TO_SUBMIT;

        expectedModel.updateFilteredIncidentList(Model.PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS);

        assertCommandSuccess(fillNoParams, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_submittedIncidentParameters_showAllSubmittedIncidents() {
        // listing all submitted reports
        // also a test for fill command in parameter mode
        model = new ModelManager(TypicalIncidents.getTypicalIncidentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        ListIncidentsCommand fillNoParams = new ListIncidentsCommand(PREDICATE_SHOW_SUBMITTED_INCIDENT_REPORTS);
        String expectedMessage = Messages.MESSAGE_ALL_SUBMITTED_INCIDENTS_LISTED;

        expectedModel.updateFilteredIncidentList(PREDICATE_SHOW_SUBMITTED_INCIDENT_REPORTS);

        assertCommandSuccess(fillNoParams, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_submittedIncidentParameters_showNoSubmittedIncidents() {
        // when no submitted incidents are present in the system
        model = new ModelManager(TypicalIncidents.getIncompleteDraftIncidentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        ListIncidentsCommand fillNoParams = new ListIncidentsCommand(PREDICATE_SHOW_SUBMITTED_INCIDENT_REPORTS);
        String expectedMessage = Messages.MESSAGE_NO_INCIDENT_TO_EDIT;

        expectedModel.updateFilteredIncidentList(PREDICATE_SHOW_SUBMITTED_INCIDENT_REPORTS);

        assertCommandSuccess(fillNoParams, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_errorParameter_showIrrelevantPrefixesError() {
        // when the command returns error regardless due to irrelevant prefixes
        model = new ModelManager(TypicalIncidents.getTypicalIncidentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        ListIncidentsCommand fillNoParams = new ListIncidentsCommand(PREDICATE_SHOW_INCIDENT_LISTING_ERROR);
        String expectedMessage = Messages.MESSAGE_IRRELEVANT_PREFIXES;

        expectedModel.updateFilteredIncidentList(PREDICATE_SHOW_INCIDENT_LISTING_ERROR);

        assertCommandSuccess(fillNoParams, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noParameters_showNoIncidents() {
        // when the system is completely empty
        model = new ModelManager(TypicalIncidents.getEmptyIncidentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        ListIncidentsCommand fillNoParams = new ListIncidentsCommand(PREDICATE_SHOW_ALL_INCIDENTS);
        String expectedMessage = Messages.MESSAGE_NO_INCIDENTS_LISTED;

        expectedModel.updateFilteredIncidentList(PREDICATE_SHOW_ALL_INCIDENTS);

        assertCommandSuccess(fillNoParams, model, expectedMessage, expectedModel);
    }
}
