package seedu.moolah.logic.commands.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.budget.SwitchPeriodCommand.MESSAGE_PERIOD_IS_FUTURE;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TypicalMooLah.SCHOOL;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.general.Timestamp;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ModelHistory;

public class SwitchPeriodCommandTest {
    private static final Timestamp SEP_15 = new Timestamp(
            LocalDateTime.of(2019, 9, 15, 0, 0));
    private static final Timestamp OCT_14 = new Timestamp(
            LocalDateTime.of(2019, 10, 14, 23, 59)).toEndOfDay();

    @Test
    public void constructor_nullTargetDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SwitchPeriodCommand(null));
    }

    @Test
    public void run_validInput_success() {
        Model model = new ModelManager();
        model.addBudget(SCHOOL);
        assertTrue(model.getPrimaryBudget().isSameBudget(SCHOOL)); // primary budget is SCHOOL

        try {
            Timestamp validInput = Timestamp.createTimestampIfValid("01-10-2019").get();
            SwitchPeriodCommand command = new SwitchPeriodCommand(validInput);
            ModelHistory expectedModelHistory = new ModelHistory();
            expectedModelHistory.addToPastChanges(new ModelChanges(command.getDescription())
                    .setMooLah(model.getMooLah()));
            CommandResult commandResult = command.run(model);
            assertEquals(model.getPrimaryBudget().getWindowStartDate(), SEP_15);
            assertEquals(model.getPrimaryBudget().getWindowEndDate(), OCT_14);
            assertEquals(model.getModelHistory(), expectedModelHistory);
            assertEquals(String.format(SwitchPeriodCommand.MESSAGE_SWITCH_PERIOD_SUCCESS, validInput),
                    commandResult.getFeedbackToUser());
        } catch (CommandException e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    @Test
    public void run_periodIsFuture_throwsCommandException() {
        Model model = new ModelManager();
        model.addBudget(SCHOOL);
        assertTrue(model.getPrimaryBudget().isSameBudget(SCHOOL)); // primary budget is SCHOOL

        try {
            Timestamp validInput = Timestamp.createTimestampIfValid("01-01-2020").get();
            SwitchPeriodCommand command = new SwitchPeriodCommand(validInput);
            command.run(model);
        } catch (CommandException e) {
            assertEquals(MESSAGE_PERIOD_IS_FUTURE, e.getMessage());
        }
    }
}
