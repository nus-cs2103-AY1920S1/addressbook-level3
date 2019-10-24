package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.address.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.MonthlyLimit;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.testutil.ModelStub;
import seedu.jarvis.testutil.finance.MonthlyLimitBuilder;

public class SetMonthlyLimitCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new CcaTracker(), new HistoryManager(), new FinanceTracker(), getTypicalAddressBook(),
                new UserPrefs(), new Planner(), new CoursePlanner());
        model.setMonthlyLimit(new MonthlyLimitBuilder().withLimit("1000.0").build());
    }

    /**
     * Verifies that checking {@code SetMonthlyLimitCommand} for the availability of inverse execution returns true.
     */
    @BeforeEach
    public void hasInverseExecution() {
        MonthlyLimit validLimit = new MonthlyLimitBuilder().build();
        SetMonthlyLimitCommand setMonthlyLimitCommand = new SetMonthlyLimitCommand(validLimit);
        assertTrue(setMonthlyLimitCommand.hasInverseExecution());
    }

    @Test
    public void constructor_nullInstallment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetMonthlyLimitCommand(null));
    }

    @Test
    public void execute_limitAcceptedByModel_setSuccessful() throws CommandException {
        ModelStubAcceptingSpendingLimitSet modelStub = new ModelStubAcceptingSpendingLimitSet();
        MonthlyLimit validLimit = new MonthlyLimitBuilder().withLimit("800.0").build();

        CommandResult commandResult = new SetMonthlyLimitCommand(validLimit).execute(modelStub);

        assertEquals(String.format(SetMonthlyLimitCommand.MESSAGE_SUCCESS, validLimit),
                commandResult.getFeedbackToUser());
        assertEquals(validLimit, modelStub.spendingLimit);
    }

    /**
     * Ensures that the {@code CommandResult} with the appropriate message is returned from a successful inverse
     * execution, that the monthly limit that was set was reverted to the original limit (if it existed).
     * */
    @Test
    public void executeInverse_success() {
        MonthlyLimit originalLimit = model.getMonthlyLimit().get();
        MonthlyLimit limitToAdd = new MonthlyLimitBuilder().build();

        SetMonthlyLimitCommand setMonthlyLimitCommand = new SetMonthlyLimitCommand(limitToAdd);

        String expectedMessage = String.format(SetMonthlyLimitCommand.MESSAGE_SUCCESS, limitToAdd);

        Model expectedModel = new ModelManager(model.getCcaTracker(), model.getHistoryManager(),
                model.getFinanceTracker(), model.getAddressBook(), new UserPrefs(),
                model.getPlanner(), model.getCoursePlanner());

        assertCommandSuccess(setMonthlyLimitCommand, model, expectedMessage, expectedModel);

        String inverseExpectedMessage = String.format(
                SetMonthlyLimitCommand.MESSAGE_INVERSE_SUCCESS_RESET, limitToAdd);
        expectedModel.setMonthlyLimit(originalLimit);

        assertCommandInverseSuccess(setMonthlyLimitCommand, model, inverseExpectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        MonthlyLimit highLimit = new MonthlyLimitBuilder().withLimit("900.0").build();
        MonthlyLimit lowLimit = new MonthlyLimitBuilder().withLimit("400.0").build();
        SetMonthlyLimitCommand addHighLimitCommand = new SetMonthlyLimitCommand(highLimit);
        SetMonthlyLimitCommand addLowLimitCommand = new SetMonthlyLimitCommand(lowLimit);

        // same object -> returns true
        assertTrue(addHighLimitCommand.equals(addHighLimitCommand));

        // same values -> returns true
        SetMonthlyLimitCommand addHighLimitCommandCopy = new SetMonthlyLimitCommand(highLimit);
        assertTrue(addHighLimitCommand.equals(addHighLimitCommandCopy));

        // different values -> returns false
        assertFalse(addHighLimitCommand.equals("4"));

        // null -> returns false
        assertFalse(addHighLimitCommand.equals(null));

        // different limit -> returns false
        assertFalse(addHighLimitCommand.equals(addLowLimitCommand));
    }

    /**
     * A Model stub that always accepts the monthly limit that is being set.
     */
    private class ModelStubAcceptingSpendingLimitSet extends ModelStub {

        private MonthlyLimit spendingLimit = null;

        @Override
        public void setMonthlyLimit(MonthlyLimit limit) {
            requireNonNull(limit);
            spendingLimit = limit;
        }

        @Override
        public Optional<MonthlyLimit> getMonthlyLimit() {
            if (spendingLimit == null) {
                return Optional.empty();
            }
            return Optional.of(spendingLimit);
        }
    }
}
