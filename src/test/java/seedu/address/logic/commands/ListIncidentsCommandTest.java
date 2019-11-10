package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showIncidentAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INCIDENTS;
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
    public void execute_noParameters_showAllDrafts() {
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
    public void execute_noParameters_showNoDrafts() {
        // when no drafts to fill are present in the system
        model = new ModelManager(TypicalIncidents.getSubmittedReportIncidentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());

        ListIncidentsCommand fillNoParams = new ListIncidentsCommand(Model.PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS);
        String expectedMessage = Messages.MESSAGE_NO_DRAFTS_LISTED;

        expectedModel.updateFilteredIncidentList(Model.PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS);

        assertCommandSuccess(fillNoParams, model, expectedMessage, expectedModel);
    }
}
