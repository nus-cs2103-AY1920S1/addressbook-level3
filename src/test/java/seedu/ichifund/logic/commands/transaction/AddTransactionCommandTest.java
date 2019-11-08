package seedu.ichifund.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TransactionUtil.getAddTransactionCommandBuilder;
import static seedu.ichifund.testutil.TypicalFundBook.TRANSACTION_ALLOWANCE;
import static seedu.ichifund.testutil.TypicalFundBook.TRANSACTION_BUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.context.TransactionContext;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.model.transaction.TransactionType;
import seedu.ichifund.testutil.DateBuilder;
import seedu.ichifund.testutil.ModelStub;
import seedu.ichifund.testutil.TransactionBuilder;

public class AddTransactionCommandTest {

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTransactionCommand(null, null, null, null,
                null, null, null));
    }

    @Test
    public void execute_transactionAcceptedByModel_addSuccessful() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        ModelStubWithTransactionContext modelStub = new ModelStubWithTransactionContext(new TransactionContext(
                transaction.getMonth(), transaction.getYear(), Optional.empty(), Optional.empty()));
        AddTransactionCommand command = getAddTransactionCommandBuilder(transaction).build();

        CommandResult commandResult = command.execute(modelStub);

        assertEquals(String.format(AddTransactionCommand.MESSAGE_SUCCESS, transaction),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(transaction), modelStub.transactionsAdded);
    }

    @Test
    public void execute_commandWithEmptyOptionalFields() throws Exception {
        Transaction transaction = new TransactionBuilder()
                .withDate(new DateBuilder().withDay(Day.getCurrent().toString()).build())
                .build();
        TransactionContext context = new TransactionContext(transaction.getMonth(), transaction.getYear(),
                Optional.of(transaction.getCategory()), Optional.of(transaction.getTransactionType()));
        ModelStubWithTransactionContext modelStub = new ModelStubWithTransactionContext(context);

        AddTransactionCommand command = getAddTransactionCommandBuilder(transaction)
                .withDay(Optional.empty())
                .withMonth(Optional.empty())
                .withYear(Optional.empty())
                .withCategory(Optional.empty())
                .withTransactionType(Optional.empty())
                .build();

        CommandResult commandResult = command.execute(modelStub);

        assertEquals(String.format(AddTransactionCommand.MESSAGE_SUCCESS, transaction),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(transaction), modelStub.transactionsAdded);
    }

    @Test
    public void execute_updateContext() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        TransactionContext context = new TransactionContext(new Month("6"), new Year("2018"),
                Optional.of(new Category("salary")), Optional.of(new TransactionType("in")));
        TransactionContext updatedContext = new TransactionContext(transaction.getMonth(), transaction.getYear(),
                Optional.empty(), Optional.empty());

        ModelStubWithTransactionContext modelStub = new ModelStubWithTransactionContext(context);

        AddTransactionCommand command = getAddTransactionCommandBuilder(transaction).build();

        CommandResult commandResult = command.execute(modelStub);

        assertEquals(modelStub.getTransactionContext(), updatedContext);
    }

    @Test
    public void equals() {
        AddTransactionCommand addAllowanceCommand = getAddTransactionCommandBuilder(TRANSACTION_ALLOWANCE)
                .build();
        AddTransactionCommand addBusCommand = getAddTransactionCommandBuilder(TRANSACTION_BUS).build();

        // same object -> returns true
        assertTrue(addAllowanceCommand.equals(addAllowanceCommand));

        // same values -> returns true
        AddTransactionCommand addAllowanceCommandCopy = getAddTransactionCommandBuilder(TRANSACTION_ALLOWANCE)
                .build();
        assertTrue(addAllowanceCommand.equals(addAllowanceCommandCopy));

        // different types -> returns false
        assertFalse(addAllowanceCommand.equals(1));

        // null -> returns false
        assertFalse(addAllowanceCommand.equals(null));

        // different transaction -> returns false
        assertFalse(addAllowanceCommand.equals(addBusCommand));
    }

    /**
     * A Model stub that has a transaction context.
     */
    private class ModelStubWithTransactionContext extends ModelStub {
        final ArrayList<Transaction> transactionsAdded = new ArrayList<>();
        private TransactionContext context;

        public ModelStubWithTransactionContext(TransactionContext context) {
            this.context = context;
        }

        @Override
        public TransactionContext getTransactionContext() {
            return context;
        }

        @Override
        public void updateTransactionContext(Transaction transaction) {
            this.context = context.getAccommodatingContext(transaction);
        }

        @Override
        public void addTransaction(Transaction transaction) {
            requireNonNull(transaction);
            transactionsAdded.add(transaction);
        }

        @Override
        public ReadOnlyFundBook getFundBook() {
            return new FundBook();
        }
    }

}


