package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.guilttrip.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.addcommands.AddIncomeCommand;
import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.ModelStub;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.util.CategoryType;
import seedu.guilttrip.testutil.IncomeBuilder;

public class AddIncomeCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    private CommandHistory commandHistory = new CommandHistory();

    private ModelStub modelStub;

    @Test
    public void constructor_nullIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddIncomeCommand(null));
    }

    @Test
    public void execute_incomeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIncomeAdded modelStub = new ModelStubAcceptingIncomeAdded();
        Income validIncome = new IncomeBuilder().build();

        CommandResult commandResult = new AddIncomeCommand(validIncome).execute(modelStub, commandHistory);

        assertEquals(String.format(AddIncomeCommand.MESSAGE_SUCCESS, validIncome), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validIncome), modelStub.incomesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Income november = new IncomeBuilder().withDesc("november salary").build();
        Income tuition = new IncomeBuilder().withDesc("money from teaching tuition").build();
        AddIncomeCommand addNovemberCommand = new AddIncomeCommand(november);
        AddIncomeCommand addTuitionCommand = new AddIncomeCommand(tuition);

        // same object -> returns true
        assertEquals(addNovemberCommand, addNovemberCommand);

        // same values -> returns true
        AddIncomeCommand addAliceCommandCopy = new AddIncomeCommand(november);
        assertEquals(addNovemberCommand, addAliceCommandCopy);

        // different types -> not equals return true
        assertNotEquals(1, addNovemberCommand);

        // null -> not equals return true
        assertNotEquals(null, addNovemberCommand);

        // different income -> not equals returns true
        assertNotEquals(addNovemberCommand, addTuitionCommand);
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

    }

    /**
     * A Model stub that always accept the income being added.
     */
    private class ModelStubAcceptingIncomeAdded extends ModelStub {
        final ArrayList<Income> incomesAdded = new ArrayList<>();
        final ArrayList<Category> listOfCategories =
                new ArrayList<Category>(Arrays.asList(new Category("Salary", CategoryType.INCOME)));

        @Override
        public void addIncome(Income income) {
            requireNonNull(income);
            incomesAdded.add(income);
        }

        @Override
        public boolean hasCategory(Category category) {
            requireNonNull(category);
            return listOfCategories.stream().anyMatch(category::isSameCategory);
        }

        @Override
        public void commitGuiltTrip() {
            // called by {@code AddIncomeCommand#execute()}
        }

        @Override
        public GuiltTrip getGuiltTrip() {
            return new GuiltTrip(true);
        }
    }

}
