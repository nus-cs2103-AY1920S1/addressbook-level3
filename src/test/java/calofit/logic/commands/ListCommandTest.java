package calofit.logic.commands;

import static calofit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static calofit.logic.commands.CommandTestUtil.showMealAtIndex;

import calofit.testutil.TypicalIndexes;
import calofit.testutil.TypicalMeals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalMeals.getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showMealAtIndex(model, TypicalIndexes.INDEX_FIRST_MEAL);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
