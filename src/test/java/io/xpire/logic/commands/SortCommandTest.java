package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.testutil.TypicalItems.BANANA;
import static io.xpire.testutil.TypicalItems.DUCK;
import static io.xpire.testutil.TypicalItems.EXPIRED_APPLE;
import static io.xpire.testutil.TypicalItems.EXPIRED_MILK;
import static io.xpire.testutil.TypicalItems.EXPIRED_ORANGE;
import static io.xpire.testutil.TypicalItems.EXPIRING_FISH;
import static io.xpire.testutil.TypicalItems.JELLY;
import static io.xpire.testutil.TypicalItems.getTypicalExpiryDateTracker;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.sort.MethodOfSorting;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpiryDateTracker(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalExpiryDateTracker(), new UserPrefs());
    }

    @Test
    public void execute_sortByName_showsSortedList() {
        String expectedMessage = SortCommand.MESSAGE_SUCCESS + " by name";
        MethodOfSorting methodOfSorting = new MethodOfSorting("name");
        SortCommand command = new SortCommand(methodOfSorting);
        expectedModel.sortItemList(methodOfSorting);
        expectedModel.updateFilteredItemList(Model.PREDICATE_SORT_ALL_ITEMS);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(
                Arrays.asList(EXPIRED_APPLE, BANANA, DUCK, EXPIRING_FISH, JELLY, EXPIRED_MILK, EXPIRED_ORANGE),
                model.getFilteredItemList()
        );
    }

    @Test
    public void execute_sortByDate_showsSortedList() {
        String expectedMessage = SortCommand.MESSAGE_SUCCESS + " by date";
        MethodOfSorting methodOfSorting = new MethodOfSorting("date");
        SortCommand command = new SortCommand(methodOfSorting);
        expectedModel.sortItemList(methodOfSorting);
        expectedModel.updateFilteredItemList(Model.PREDICATE_SORT_ALL_ITEMS);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(
                Arrays.asList(EXPIRED_MILK, EXPIRED_ORANGE, EXPIRED_APPLE, EXPIRING_FISH, BANANA, DUCK, JELLY),
                model.getFilteredItemList()
        );
    }
}
