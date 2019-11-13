package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.planner.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.day.Day;
import seedu.planner.testutil.day.TypicalDays;

public class OptimiseCommandTest {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OptimiseCommand(null, true));
    }

    @Test
    public void execute_emptyDay_throwsCommandException() {
        Model model = new ModelManager();
        model.addDays(1);
        OptimiseCommand optimiseCommand = new OptimiseCommand(Index.fromOneBased(1), false);
        assertThrows(CommandException.class, OptimiseCommand.MESSAGE_DAY_EMPTY, () -> optimiseCommand.execute(model));
    }

    @Test
    public void execute_optimise_success() {
        Model model = new ModelManager();
        Day conflictedDay1 = TypicalDays.CONFLICTED_DAY1;
        Day successDay1 = TypicalDays.CONFLICTLESS_DAY1;
        Day conflictedDay2 = TypicalDays.CONFLICTED_DAY2;
        Day successDay2 = TypicalDays.CONFLICTLESS_DAY2;

        model.addDays(2);
        model.addDayAtIndex(Index.fromOneBased(1), conflictedDay1);
        model.addDayAtIndex(Index.fromOneBased(2), conflictedDay2);

        try {
            CommandResult commandResult = new OptimiseCommand(Index.fromOneBased(1), false).execute(model);
            assertEquals(OptimiseCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
            assertEquals(successDay1, model.getDay(Index.fromOneBased(1)));
        } catch (CommandException e) {
            fail();
        }

        try {
            CommandResult commandResult = new OptimiseCommand(Index.fromOneBased(2), false).execute(model);
            assertEquals(OptimiseCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
            assertEquals(successDay2, model.getDay(Index.fromOneBased(2)));
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void equals() {
        OptimiseCommand optimiseCommand1 = new OptimiseCommand(Index.fromOneBased(1), false);
        OptimiseCommand optimiseCommand2 = new OptimiseCommand(Index.fromOneBased(2), false);
        OptimiseCommand optimiseCommand3 = new OptimiseCommand(Index.fromOneBased(1), false);

        //same object -> returns true
        assertTrue(optimiseCommand1.equals(optimiseCommand1));

        //same values -> returns true
        assertTrue(optimiseCommand1.equals(optimiseCommand3));

        //different types -> returns false
        assertFalse(optimiseCommand1.equals(1));

        //different values -> returns false
        assertFalse(optimiseCommand1.equals(optimiseCommand2));
    }
}
