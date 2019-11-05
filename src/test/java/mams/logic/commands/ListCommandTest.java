package mams.logic.commands;

import static mams.logic.commands.CommandTestUtil.assertCommandSuccess;
import static mams.logic.commands.CommandTestUtil.showAppealAtIndex;
import static mams.logic.commands.CommandTestUtil.showModuleAtIndex;
import static mams.logic.commands.CommandTestUtil.showStudentAtIndex;
import static mams.testutil.TypicalCommandHistory.getTypicalCommandHistory;
import static mams.testutil.TypicalIndexes.INDEX_FIRST;
import static mams.testutil.TypicalMams.getTypicalMams;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mams.model.Model;
import mams.model.ModelManager;
import mams.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private static final String MESSAGE_LIST_ALL_SUCCESS = ListCommand.MESSAGE_LIST_APPEALS_SUCCESS
            + ListCommand.NEWLINE
            + ListCommand.MESSAGE_LIST_MODULES_SUCCESS
            + ListCommand.NEWLINE
            + ListCommand.MESSAGE_LIST_STUDENTS_SUCCESS;

    private static final String MESSAGE_LIST_APPEALS_MODULES_SUCCESS = ListCommand.MESSAGE_LIST_APPEALS_SUCCESS
            + ListCommand.NEWLINE
            + ListCommand.MESSAGE_LIST_MODULES_SUCCESS;

    private static final String MESSAGE_LIST_APPEALS_STUDENTS_SUCCESS = ListCommand.MESSAGE_LIST_APPEALS_SUCCESS
            + ListCommand.NEWLINE
            + ListCommand.MESSAGE_LIST_STUDENTS_SUCCESS;

    private static final String MESSAGE_LIST_MODULES_STUDENTS_SUCCESS = ListCommand.MESSAGE_LIST_MODULES_SUCCESS
            + ListCommand.NEWLINE
            + ListCommand.MESSAGE_LIST_STUDENTS_SUCCESS;

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMams(), new UserPrefs());
        expectedModel = new ModelManager(model.getMams(), new UserPrefs());
    }

    @Test
    public void execute_allListsNotFiltered_showsSameLists() {

        // attempt listing appeals -> same lists
        assertCommandSuccess(new ListCommand(true, false, false),
                model,
                ListCommand.MESSAGE_LIST_APPEALS_SUCCESS,
                expectedModel);

        // attempt listing modules -> same lists
        assertCommandSuccess(new ListCommand(false, true, false),
                model,
                ListCommand.MESSAGE_LIST_MODULES_SUCCESS,
                expectedModel);

        // attempt listing students -> same lists
        assertCommandSuccess(new ListCommand(false, false, true),
                model,
                ListCommand.MESSAGE_LIST_STUDENTS_SUCCESS,
                expectedModel);

        // attempt listing all -> same lists
        assertCommandSuccess(new ListCommand(true, true, true),
                model,
                MESSAGE_LIST_ALL_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_appealListFilteredAndTargetedByListCommand_showsEverything() {
        // list all items -> all items listed
        showAppealAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(true, true, true),
                model,
                MESSAGE_LIST_ALL_SUCCESS,
                expectedModel);

        // list appeals only -> all items listed
        showAppealAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(true, false, false),
                model,
                ListCommand.MESSAGE_LIST_APPEALS_SUCCESS,
                expectedModel);

        // list appeals and students only -> all items listed
        showAppealAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(true, false, true),
                model,
                MESSAGE_LIST_APPEALS_STUDENTS_SUCCESS,
                expectedModel);

        // list appeals and modules only -> all items listed
        showAppealAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(true, true, false),
                model,
                MESSAGE_LIST_APPEALS_MODULES_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_moduleListFilteredAndTargetedByListCommand_showsEverything() {
        // list all items -> all items listed
        showModuleAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(true, true, true),
                model,
                MESSAGE_LIST_ALL_SUCCESS,
                expectedModel);

        // list modules only -> all items listed
        showModuleAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(false, true, false),
                model,
                ListCommand.MESSAGE_LIST_MODULES_SUCCESS,
                expectedModel);

        // list modules and students only -> all items listed
        showModuleAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(false, true, true),
                model,
                MESSAGE_LIST_MODULES_STUDENTS_SUCCESS,
                expectedModel);

        // list appeals and modules only -> all items listed
        showModuleAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(true, true, false),
                model,
                MESSAGE_LIST_APPEALS_MODULES_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_studentListFilteredAndTargetedByListCommand_showsEverything() {
        // list all items -> all items listed
        showStudentAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(true, true, true),
                model,
                MESSAGE_LIST_ALL_SUCCESS,
                expectedModel);

        // list students only -> all items listed
        showStudentAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(false, false, true),
                model,
                ListCommand.MESSAGE_LIST_STUDENTS_SUCCESS,
                expectedModel);

        // list appeals and students only -> all items listed
        showStudentAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(true, false, true),
                model,
                MESSAGE_LIST_APPEALS_STUDENTS_SUCCESS,
                expectedModel);

        // list students and modules only -> all items listed
        showStudentAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(false, true, true),
                model,
                MESSAGE_LIST_MODULES_STUDENTS_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_appealListFilteredButNotTargetedByListCommand_appealListRemainsFiltered() {
        // appeal list filtered, not targeted for list command -> appeal list remains filtered
        showAppealAtIndex(model, INDEX_FIRST);
        showAppealAtIndex(expectedModel, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(false, true, true),
                model,
                MESSAGE_LIST_MODULES_STUDENTS_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_moduleListFilteredButNotTargetedByListCommand_moduleListRemainsFiltered() {
        // module list filtered, not targeted for list command -> module list remains filtered
        showModuleAtIndex(model, INDEX_FIRST);
        showModuleAtIndex(expectedModel, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(true, false, true),
                model,
                MESSAGE_LIST_APPEALS_STUDENTS_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_studentListFilteredButNotTargetedByListCommand_studentListRemainsFiltered() {
        // student list filtered, not targeted for list command -> student list remains filtered
        showStudentAtIndex(model, INDEX_FIRST);
        showStudentAtIndex(expectedModel, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(true, true, false),
                model,
                MESSAGE_LIST_APPEALS_MODULES_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_allListsUnfilteredAndTargetedByListCommand_showAllLists() {
        showAppealAtIndex(model, INDEX_FIRST);
        showModuleAtIndex(model, INDEX_FIRST);
        showStudentAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(true, true, true),
                model,
                MESSAGE_LIST_ALL_SUCCESS,
                expectedModel);
    }

    @Test
    public void containsAtLeastOneTrue() {
        // all true -> true
        assertTrue(ListCommand.containsAtLeastOneTrue(true, true, true, true, true));

        // one true -> true
        assertTrue(ListCommand.containsAtLeastOneTrue(true, false, false, false));

        // none true -> false
        assertFalse(ListCommand.containsAtLeastOneTrue(false, false, false));
    }

    @Test
    public void execute_listCommandInitializedWithAllFalse_throwsError() {
        assertThrows(AssertionError.class, () -> new ListCommand(false, false, false)
                .execute(model, getTypicalCommandHistory()));
    }

    @Test
    public void equals() {
        ListCommand listAll = new ListCommand(true, true, true);
        ListCommand listAppealModule = new ListCommand(true, true, false);
        ListCommand listAppeal = new ListCommand(true, false, false);

        // same object -> returns true
        assertTrue(listAll.equals(listAll));

        // same values -> returns true
        assertTrue(listAll.equals(new ListCommand(true, true, true)));

        // different types -> returns false
        assertFalse(listAll.equals(1));

        // null -> returns false
        assertFalse(listAll.equals(null));

        // different internal values -> returns false
        assertFalse(listAll.equals(listAppealModule));
        assertFalse(listAll.equals(listAppeal));
    }
}
