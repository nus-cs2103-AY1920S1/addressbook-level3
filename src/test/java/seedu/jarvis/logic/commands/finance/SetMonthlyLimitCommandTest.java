package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.financetracker.MonthlyLimit;
import seedu.jarvis.testutil.ModelStub;
import seedu.jarvis.testutil.finance.MonthlyLimitBuilder;

public class SetMonthlyLimitCommandTest {

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
