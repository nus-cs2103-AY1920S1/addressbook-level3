package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FinSec;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.income.Income;
import seedu.address.testutil.IncomeBuilder;

public class AddIncomeCommandTest {
    @Test
    public void constructor_nullIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddIncomeCommand(null));
    }

    @Test
    public void execute_incomeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIncomeAdded modelStub = new ModelStubAcceptingIncomeAdded();
        Income validIncome = new IncomeBuilder().build();

        CommandResult commandResult = new AddIncomeCommand(validIncome).execute(modelStub);

        assertEquals(String.format(AddIncomeCommand.MESSAGE_SUCCESS, validIncome), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validIncome), modelStub.incomesAdded);
    }

    @Test
    public void execute_duplicateClaim_throwsCommandException() {
        Income validIncome = new IncomeBuilder().build();
        AddIncomeCommand addContactCommand = new AddIncomeCommand(validIncome);
        ModelStub modelStub = new ModelStubWithIncome(validIncome);

        assertThrows(CommandException.class,
                AddIncomeCommand.MESSAGE_DUPLICATE_INCOME, () -> addContactCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Income shirtsales = new IncomeBuilder().withDescription("Shirt Sales").build();
        Income fundraising = new IncomeBuilder().withDescription("Fundraising").build();
        AddIncomeCommand addShirtSalesCommand = new AddIncomeCommand(shirtsales);
        AddIncomeCommand addFundraisingCommand = new AddIncomeCommand(fundraising);

        // same object -> returns true
        assertEquals(addShirtSalesCommand, addShirtSalesCommand);

        // same values -> returns true
        AddIncomeCommand copy = new AddIncomeCommand(shirtsales);
        assertEquals(addShirtSalesCommand, copy);

        // different types -> returns false
        assertNotEquals(1, addShirtSalesCommand);

        // null -> returns false
        assertNotEquals(null, addFundraisingCommand);

        // different incomes -> returns false
        assertNotEquals(addShirtSalesCommand, addFundraisingCommand);
    }

    /**
     * A Model stub that contains a single income.
     */
    private class ModelStubWithIncome extends ModelStub {
        private final Income income;

        ModelStubWithIncome(Income income) {
            requireNonNull(income);
            this.income = income;
        }

        @Override
        public boolean hasIncome(Income income) {
            requireNonNull(income);
            return this.income.isSameIncome(income);
        }

    }

    /**
     * A Model stub that always accept the income being added.
     */
    private class ModelStubAcceptingIncomeAdded extends ModelStub {
        final ArrayList<Income> incomesAdded = new ArrayList<>();

        @Override
        public boolean hasIncome(Income income) {
            requireNonNull(income);
            return incomesAdded.stream().anyMatch(income::isSameIncome);
        }

        @Override
        public void addIncome(Income income) {
            requireNonNull(income);
            incomesAdded.add(income);
        }

        @Override
        public ReadOnlyFinSec getFinSec() {
            return new FinSec();
        }

    }

}
