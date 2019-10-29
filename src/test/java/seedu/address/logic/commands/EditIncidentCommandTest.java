package seedu.address.logic.commands;

/*import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static org.junit.jupiter.api.Assertions.assertTrue;*/

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showIncidentAtIndex;
import static seedu.address.testutil.IncidentBuilder.DEFAULT_CALLER;
import static seedu.address.testutil.IncidentBuilder.DEFAULT_DISTRICT;
import static seedu.address.testutil.IncidentBuilder.DEFAULT_ID;
import static seedu.address.testutil.TypicalEntities.getTypicalIncidentManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTITY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditIncidentCommand.EditIncident;
import seedu.address.model.IncidentManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.incident.Incident;
import seedu.address.testutil.EditIncidentBuilder;
import seedu.address.testutil.IncidentBuilder;
/*
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
*/
//import org.junit.jupiter.api.Test;

//import seedu.address.model.IncidentManager;

//import seedu.address.model.incident.Incident;
//import seedu.address.testutil.EditIncidentBuilder;
//import seedu.address.testutil.IncidentBuilder;
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditIncidentCommandTest {

    private Model model = new ModelManager(getTypicalIncidentManager(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {

        Incident editedIncident = new IncidentBuilder().build();
        EditIncident editor = new EditIncidentBuilder(editedIncident).build();
        EditIncidentCommand editIncidentCommand = new EditIncidentCommand(INDEX_FIRST_ENTITY, editor);

        String expectedMessage = String.format(EditIncidentCommand.MESSAGE_EDIT_INCIDENT_SUCCESS, editedIncident);

        Model expectedModel = new ModelManager(new IncidentManager(model.getIncidentManager()), new UserPrefs());
        expectedModel.setIncident(model.getFilteredIncidentList().get(0), editedIncident);

        assertCommandSuccess(editIncidentCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastIncident = Index.fromOneBased(model.getFilteredIncidentList().size());
        Incident lastIncident = model.getFilteredIncidentList().get(indexLastIncident.getZeroBased());

        IncidentBuilder incidentBuilder = new IncidentBuilder(lastIncident);
        Incident editedIncident = incidentBuilder.withCaller(DEFAULT_CALLER).withDistrict(DEFAULT_DISTRICT)
                .withId(DEFAULT_ID).build();

        EditIncident editor = new EditIncidentBuilder().withCaller(DEFAULT_CALLER)
                .withDistrict(DEFAULT_DISTRICT).build();
        EditIncidentCommand editIncidentCommand = new EditIncidentCommand(indexLastIncident, editor);

        String expectedMessage = String.format(EditIncidentCommand.MESSAGE_EDIT_INCIDENT_SUCCESS, editedIncident);


        Model expectedModel = new ModelManager(new IncidentManager(model.getIncidentManager()), new UserPrefs());
        expectedModel.setIncident(lastIncident, editedIncident);


        assertCommandSuccess(editIncidentCommand, model, expectedMessage, expectedModel);
    }



    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditIncidentCommand editIncidentCommand = new EditIncidentCommand(INDEX_FIRST_ENTITY, new EditIncident());
        Incident editedIncident = model.getFilteredIncidentList().get(INDEX_FIRST_ENTITY.getZeroBased());
        String expectedMessage = String.format(EditIncidentCommand.MESSAGE_EDIT_INCIDENT_SUCCESS, editedIncident);
        Model expectedModel = new ModelManager(new IncidentManager(model.getIncidentManager()), new UserPrefs());


        assertCommandSuccess(editIncidentCommand, model, expectedMessage, expectedModel);
    }


    /* @Test
    public void execute_filteredList_success() {
        showIncidentAtIndex(model, INDEX_FIRST_ENTITY);

        Incident incidentInFilteredList = model.getFilteredIncidentList().get(INDEX_FIRST_ENTITY.getZeroBased());
        Incident editedIncident = new IncidentBuilder(incidentInFilteredList).withCaller(DEFAULT_CALLER).build();
        EditIncidentCommand editIncidentCommand = new EditIncidentCommand(INDEX_FIRST_ENTITY,
                new EditIncidentBuilder().withCaller(DEFAULT_CALLER).build());

        String expectedMessage = String.format(EditIncidentCommand.MESSAGE_EDIT_INCIDENT_SUCCESS, editedIncident);
        Model expectedModel = new ModelManager(new IncidentManager(model.getIncidentManager()), new UserPrefs());
        expectedModel.setIncident(model.getFilteredIncidentList().get(0), editedIncident);

        assertCommandSuccess(editIncidentCommand, model, expectedMessage, expectedModel);
     }*/

    @Test
    public void execute_duplicateIncidentUnfilteredList_failure() {
        Incident firstIncident = model.getFilteredIncidentList().get(INDEX_FIRST_ENTITY.getZeroBased());
        EditIncident editor = new EditIncidentBuilder(firstIncident).build();

        EditIncidentCommand editIncidentCommand = new EditIncidentCommand(INDEX_SECOND_ENTITY, editor);

        assertCommandFailure(editIncidentCommand, model, EditIncidentCommand.MESSAGE_DUPLICATE_INCIDENT);
    }

    @Test
    public void execute_duplicateIncidentFilteredList_failure() {
        showIncidentAtIndex(model, INDEX_FIRST_ENTITY);

        // edit incident in filtered list into a duplicate in address book
        Incident incidentInList = model.getIncidentManager().getIncidentList().get(INDEX_FIRST_ENTITY.getZeroBased());
        EditIncidentCommand editIncidentCommand = new EditIncidentCommand(INDEX_SECOND_ENTITY,
                new EditIncidentBuilder(incidentInList).build());

        assertCommandFailure(editIncidentCommand, model, EditIncidentCommand.MESSAGE_DUPLICATE_INCIDENT);
    }

    @Test
    public void execute_invalidIncidentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        //EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        //EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);
        EditIncident editor = new EditIncidentBuilder().withCaller(DEFAULT_CALLER).build();
        EditIncidentCommand editIncidentCommand = new EditIncidentCommand(outOfBoundIndex, editor);

        assertCommandFailure(editIncidentCommand, model, Messages.MESSAGE_INVALID_INCIDENT_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */

    /*
    @Test
    public void execute_invalidIncidentIndexFilteredList_failure() {
        showIncidentAtIndex(model, INDEX_FIRST_ENTITY);
        Index outOfBoundIndex = INDEX_SECOND_ENTITY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getIncidentList().size());


        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditIncidentBuilder().withCaller(DEFAULT_CALLER).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_INCIDENT_INDEX);
    }




    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ENTITY, DESC_AMY);
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ENTITY, )
        // same values -> returns true
        EditIncident editor = new EditIncident();
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ENTITY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ENTITY, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ENTITY, DESC_BOB)));
    }
    */
}
