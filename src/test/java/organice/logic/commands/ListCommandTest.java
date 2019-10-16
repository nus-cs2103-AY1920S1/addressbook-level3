package organice.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.assertCommandSuccess;
import static organice.logic.commands.CommandTestUtil.showPersonAtIndex;
import static organice.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static organice.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import organice.model.Model;
import organice.model.ModelManager;
import organice.model.UserPrefs;
import organice.model.person.Type;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private static final Type TYPE_PATIENT = new Type("patient");
    private static final Type TYPE_DONOR = new Type("donor");
    private static final Type TYPE_DOCTOR = new Type("doctor");

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(null), model, ListCommand.LIST_OF_PERSONS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(null), model, ListCommand.LIST_OF_PERSONS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsPatients() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PATIENTS);
        assertCommandSuccess(new ListCommand(TYPE_PATIENT), model, ListCommand.LIST_OF_PATIENTS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsDonors() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_DONORS);
        assertCommandSuccess(new ListCommand(TYPE_DONOR), model, ListCommand.LIST_OF_DONORS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsDoctors() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_DOCTORS);
        assertCommandSuccess(new ListCommand(TYPE_DOCTOR), model, ListCommand.LIST_OF_DOCTORS, expectedModel);
    }

    @Test
    public void equals() {
        ListCommand listCommand = new ListCommand(TYPE_PATIENT);

        // same values -> returns true
        assertTrue(listCommand.equals(new ListCommand(TYPE_PATIENT)));

        // same object -> returns true
        assertTrue(listCommand.equals(listCommand));

        // null -> returns false
        assertFalse(listCommand.equals(null));

        // different type -> returns false
        assertFalse(listCommand.equals(5));

        // different type values -> returns false
        assertFalse(listCommand.equals(TYPE_DONOR));
        assertFalse(listCommand.equals(TYPE_DOCTOR));
    }
}
