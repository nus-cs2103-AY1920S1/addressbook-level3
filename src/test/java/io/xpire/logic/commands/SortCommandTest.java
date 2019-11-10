package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.testutil.TypicalItems.APPLE;
import static io.xpire.testutil.TypicalItems.BANANA;
import static io.xpire.testutil.TypicalItems.CORN;
import static io.xpire.testutil.TypicalItems.DUCK;
import static io.xpire.testutil.TypicalItems.EGG;
import static io.xpire.testutil.TypicalItems.FISH;
import static io.xpire.testutil.TypicalItems.GRAPE;
import static io.xpire.testutil.TypicalItems.HONEY;
import static io.xpire.testutil.TypicalItems.ICE_CREAM;
import static io.xpire.testutil.TypicalItems.PAPAYA_XPIRE;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.model.ListType;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.sort.XpireMethodOfSorting;

//@@author febee99
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
    }

    @Test
    public void execute_sortByName_showsSortedList() {
        String expectedMessage = SortCommand.MESSAGE_SUCCESS + " by name";
        XpireMethodOfSorting xpireMethodOfSorting = new XpireMethodOfSorting("name");
        SortCommand command = new SortCommand(xpireMethodOfSorting);
        expectedModel.sortXpire(xpireMethodOfSorting);
        expectedModel.filterCurrentList(ListType.XPIRE, Model.PREDICATE_SORT_ALL_ITEMS);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(
                Arrays.asList(APPLE, BANANA, CORN, DUCK, EGG, FISH, GRAPE, HONEY, ICE_CREAM, PAPAYA_XPIRE),
                model.getCurrentList()
        );
    }

    @Test
    public void execute_sortByDate_showsSortedList() {
        String expectedMessage = SortCommand.MESSAGE_SUCCESS + " by date";
        XpireMethodOfSorting xpireMethodOfSorting = new XpireMethodOfSorting("date");
        SortCommand command = new SortCommand(xpireMethodOfSorting);
        expectedModel.sortXpire(xpireMethodOfSorting);
        expectedModel.filterCurrentList(ListType.XPIRE, Model.PREDICATE_SORT_ALL_ITEMS);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(
                Arrays.asList(ICE_CREAM, HONEY, APPLE, GRAPE, BANANA, CORN, DUCK, EGG, FISH, PAPAYA_XPIRE),
                model.getCurrentList()
        );
    }
}
