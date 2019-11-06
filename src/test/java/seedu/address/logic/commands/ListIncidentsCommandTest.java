package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showIncidentAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INCIDENTS;
import static seedu.address.testutil.TypicalEntities.getTypicalIncidentManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTITY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListIncidentCommand.
 */
public class ListIncidentsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getIncidentManager(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListIncidentsCommand(PREDICATE_SHOW_ALL_INCIDENTS), model,
                Messages.MESSAGE_ALL_INCIDENTS_LISTED, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showIncidentAtIndex(model, INDEX_FIRST_ENTITY);
        assertCommandSuccess(new ListIncidentsCommand(PREDICATE_SHOW_ALL_INCIDENTS), model,
                Messages.MESSAGE_ALL_INCIDENTS_LISTED, expectedModel);
    }
}
