package seedu.ichifund.logic.commands.repeater;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.testutil.ModelStub;
import seedu.ichifund.testutil.RepeaterBuilder;

public class AddRepeaterCommandTest {

    @Test
    public void constructor_nullRepeater_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddRepeaterCommand(null));
    }

    @Test
    public void execute_repeaterAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRepeaterAdded modelStub = new ModelStubAcceptingRepeaterAdded();
        Repeater validRepeater = new RepeaterBuilder().build();

        CommandResult commandResult = new AddRepeaterCommand(validRepeater).execute(modelStub);

        assertEquals(String.format(AddRepeaterCommand.MESSAGE_ADD_REPEATER_SUCCESS, validRepeater),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validRepeater), modelStub.repeatersAdded);
    }

    @Test
    public void execute_duplicateRepeater_throwsCommandException() {
        Repeater validRepeater = new RepeaterBuilder().build();
        AddRepeaterCommand addRepeaterCommand = new AddRepeaterCommand(validRepeater);
        ModelStub modelStub = new ModelStubWithRepeater(validRepeater);

        assertThrows(CommandException.class, AddRepeaterCommand.MESSAGE_DUPLICATE_REPEATER, ()
            -> addRepeaterCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Repeater overall = new RepeaterBuilder().withDescription("Limit for Spendings").build();
        Repeater food = new RepeaterBuilder().withDescription("Limit for Food").build();
        AddRepeaterCommand addOverallCommand = new AddRepeaterCommand(overall);
        AddRepeaterCommand addFoodCommand = new AddRepeaterCommand(food);

        // same object -> returns true
        assertTrue(addOverallCommand.equals(addOverallCommand));

        // same values -> returns true
        AddRepeaterCommand addOverallCommandCopy = new AddRepeaterCommand(overall);
        assertTrue(addOverallCommand.equals(addOverallCommandCopy));

        // different types -> returns false
        assertFalse(addOverallCommand.equals(1));

        // null -> returns false
        assertFalse(addOverallCommand.equals(null));

        // different repeater -> returns false
        assertFalse(addOverallCommand.equals(addFoodCommand));
    }


    /**
     * A Model stub that contains a single repeater.
     */
    private class ModelStubWithRepeater extends ModelStub {
        private final Repeater repeater;

        ModelStubWithRepeater(Repeater repeater) {
            requireNonNull(repeater);
            this.repeater = repeater;
        }

        @Override
        public boolean hasRepeater(Repeater repeater) {
            requireNonNull(repeater);
            return this.repeater.isSameRepeater(repeater);
        }
    }

    /**
     * A Model stub that always accept the repeater being added.
     */
    private class ModelStubAcceptingRepeaterAdded extends ModelStub {
        final ArrayList<Transaction> transactionsAdded = new ArrayList<>();
        final ArrayList<Repeater> repeatersAdded = new ArrayList<>();
        private RepeaterUniqueId currentRepeaterUniqueId;

        public ModelStubAcceptingRepeaterAdded() {
            currentRepeaterUniqueId = new RepeaterUniqueId("0");
        }

        @Override
        public RepeaterUniqueId getCurrentRepeaterUniqueId() {
            return currentRepeaterUniqueId;
        }

        @Override
        public void setCurrentRepeaterUniqueId(RepeaterUniqueId uniqueId) {
            currentRepeaterUniqueId = uniqueId;
        }

        @Override
        public void addTransaction(Transaction transaction) {
            requireNonNull(transaction);
            transactionsAdded.add(transaction);
        }

        @Override
        public boolean hasRepeater(Repeater repeater) {
            requireNonNull(repeater);
            return repeatersAdded.stream().anyMatch(repeater::isSameRepeater);
        }

        @Override
        public void addRepeater(Repeater repeater) {
            requireNonNull(repeater);
            repeatersAdded.add(repeater);
        }

        @Override
        public ReadOnlyFundBook getFundBook() {
            return new FundBook();
        }

        @Override
        public void createRepeaterTransactions(Repeater repeater) {
            int currentMonth = repeater.getStartDate().getMonth().monthNumber;
            int currentYear = repeater.getStartDate().getYear().yearNumber;
            int endMonth = repeater.getEndDate().getMonth().monthNumber;
            int endYear = repeater.getEndDate().getYear().yearNumber;

            while ((currentYear < endYear) || (currentYear == endYear && currentMonth <= endMonth)) {
                if (!repeater.getMonthStartOffset().isIgnored()) {
                    Transaction transaction = new Transaction(
                            repeater.getDescription(),
                            repeater.getAmount(),
                            repeater.getCategory(),
                            new Date(
                                new Day(repeater.getMonthStartOffset().toString()),
                                new Month(String.valueOf(currentMonth)),
                                new Year(String.valueOf(currentYear))),
                            repeater.getTransactionType(),
                            repeater.getUniqueId());
                    addTransaction(transaction);
                }

                if (!repeater.getMonthEndOffset().isIgnored()) {
                    int daysInMonth;
                    if ((new Month(String.valueOf(currentMonth))).has30Days()) {
                        daysInMonth = 30;
                    } else if ((new Month(String.valueOf(currentMonth))).has31Days()) {
                        daysInMonth = 31;
                    } else if ((new Year(String.valueOf(currentYear))).isLeapYear()) {
                        daysInMonth = 29;
                    } else {
                        daysInMonth = 28;
                    }

                    Transaction transaction = new Transaction(
                            repeater.getDescription(),
                            repeater.getAmount(),
                            repeater.getCategory(),
                            new Date(
                                new Day(String.valueOf(daysInMonth - (repeater.getMonthEndOffset().value - 1))),
                                new Month(String.valueOf(currentMonth)),
                                new Year(String.valueOf(currentYear))),
                            repeater.getTransactionType(),
                            repeater.getUniqueId());
                    addTransaction(transaction);
                }

                currentMonth++;
                if (currentMonth == 13) {
                    currentMonth = 1;
                    currentYear++;
                }
            }
        }
    }

}
