package seedu.ichifund.logic.commands.transaction;

import static seedu.ichifund.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ichifund.testutil.TypicalFundBook.getTypicalFundBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.logic.commands.transaction.FilterTransactionCommand.FilterTransactionCommandBuilder;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.ModelManager;
import seedu.ichifund.model.UserPrefs;
import seedu.ichifund.model.context.TransactionContext;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.model.transaction.TransactionType;
import seedu.ichifund.testutil.TransactionBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * FilterTransactionCommand.
 */
class FilterTransactionCommandTest {
    private Model model = new ModelManager(getTypicalFundBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        model.setTransactionContext(new TransactionContext(new Month("6"), new Year("2008"),
                Optional.empty(), Optional.empty()));
        Transaction transaction = new TransactionBuilder().build();
        FilterTransactionCommand command = new FilterTransactionCommandBuilder()
                .withMonth(Optional.of(transaction.getMonth()))
                .withYear(Optional.of(transaction.getYear()))
                .withCategory(Optional.of(transaction.getCategory()))
                .withTransactionType(Optional.of(transaction.getTransactionType()))
                .build();

        Model expectedModel = new ModelManager(new FundBook(model.getFundBook()), new UserPrefs());
        expectedModel.setTransactionContext(new TransactionContext(transaction.getMonth(),
                transaction.getYear(), Optional.of(transaction.getCategory()),
                Optional.of(transaction.getTransactionType())));

        String expectedMessage = String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                expectedModel.getFilteredTransactionList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified() {
        model.setTransactionContext(new TransactionContext(new Month("6"), new Year("2007"),
                Optional.empty(), Optional.empty()));

        // Case where current context does not have category and transaction
        Transaction transaction = new TransactionBuilder().build();
        FilterTransactionCommand command = new FilterTransactionCommandBuilder()
                .withMonth(Optional.empty())
                .withYear(Optional.empty())
                .withCategory(Optional.of(transaction.getCategory()))
                .withTransactionType(Optional.of(transaction.getTransactionType()))
                .build();

        Model expectedModel = new ModelManager(new FundBook(model.getFundBook()), new UserPrefs());
        expectedModel.setTransactionContext(new TransactionContext(new Month("6"),
                new Year("2007"), Optional.of(transaction.getCategory()),
                Optional.of(transaction.getTransactionType())));

        String expectedMessage = String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                expectedModel.getFilteredTransactionList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Case where current context has category and transaction
        command = new FilterTransactionCommandBuilder()
                .withMonth(Optional.empty())
                .withYear(Optional.of(transaction.getYear()))
                .withCategory(Optional.of(transaction.getCategory()))
                .withTransactionType(Optional.empty())
                .build();
        expectedModel.setTransactionContext(new TransactionContext(new Month("6"),
                transaction.getYear(), Optional.of(transaction.getCategory()),
                Optional.of(transaction.getTransactionType())));
        expectedMessage = String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                expectedModel.getFilteredTransactionList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_specialFields() {
        Transaction transaction = new TransactionBuilder().build();
        model.setTransactionContext(new TransactionContext(transaction.getMonth(), transaction.getYear(),
                Optional.of(transaction.getCategory()), Optional.of(transaction.getTransactionType())));

        FilterTransactionCommand command = new FilterTransactionCommandBuilder()
                .withMonth(Optional.empty())
                .withYear(Optional.empty())
                .withCategory(Optional.of(Category.CATEGORY_ALL))
                .withTransactionType(Optional.of(TransactionType.TRANSACTION_TYPE_ALL))
                .build();

        Model expectedModel = new ModelManager(new FundBook(model.getFundBook()), new UserPrefs());
        expectedModel.setTransactionContext(new TransactionContext(transaction.getMonth(),
                transaction.getYear(), Optional.empty(),
                Optional.empty()));

        String expectedMessage = String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                expectedModel.getFilteredTransactionList().size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
