package io.xpire.logic.commands;

import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.Model;
import io.xpire.model.Xpire;
import io.xpire.model.item.ContainsKeywordsPredicate;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.StackManager;
import io.xpire.model.state.StateManager;
import io.xpire.testutil.Assert;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    private static StateManager stateManager;

    private static void initialise() {
        stateManager = new StackManager();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel, stateManager);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command} while taking in a modified StateManager, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel, StateManager stateManager) {
        try {
            CommandResult result = command.execute(actualModel, stateManager);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        initialise();
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the expiry date tracker, filtered xpireItem list and selected xpireItem in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        initialise();
        Xpire expectedXpire = new Xpire(actualModel.getLists()[0]);
        @SuppressWarnings ("unchecked")
        List<XpireItem> expectedFilteredList = new ArrayList<>((Collection<XpireItem>) actualModel.getCurrentList());

        Assert.assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, stateManager));
        assertEquals(expectedXpire, actualModel.getLists()[0]);
        assertEquals(expectedFilteredList, actualModel.getCurrentList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the xpireItem at the given {@code targetIndex} in the
     * {@code model}'s expiry date tracker.
     */
    public static void showXpireItemAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getCurrentList().size());

        XpireItem xpireItem = (XpireItem) model.getCurrentList().get(targetIndex.getZeroBased());
        final String[] splitName = xpireItem.getName().toString().split("\\s+");

        model.filterCurrentList(XPIRE, new ContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getCurrentList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the replenishItem at the given {@code targetIndex} in the
     * {@code model}'s replenish list.
     */
    public static void showReplenishItemAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getCurrentList().size());

        Item replenishItem = model.getCurrentList().get(targetIndex.getZeroBased());
        final String[] splitName = replenishItem.getName().toString().split("\\s+");
        model.filterCurrentList(REPLENISH, new ContainsKeywordsPredicate(Arrays.asList(splitName[0])));
        assertEquals(1, model.getCurrentList().size());
    }

    /**
     * Executes given command and returns the updated stateManager.
     */
    public static void executeCommandAndUpdateStateManager(Model model, Command command, StateManager stateManager)
            throws CommandException, ParseException {
        command.execute(model, stateManager);
    }

}
