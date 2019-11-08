package seedu.ichifund.logic.parser.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.ichifund.logic.commands.transaction.AddTransactionCommand;
import seedu.ichifund.logic.commands.transaction.DeleteTransactionCommand;
import seedu.ichifund.logic.commands.transaction.EditTransactionCommand;
import seedu.ichifund.logic.commands.transaction.EditTransactionCommand.EditTransactionDescriptor;
import seedu.ichifund.logic.commands.transaction.FilterTransactionCommand;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.testutil.ArgumentBuilder;
import seedu.ichifund.testutil.EditTransactionDescriptorBuilder;
import seedu.ichifund.testutil.TransactionBuilder;
import seedu.ichifund.testutil.TransactionUtil;

public class TransactionFeatureParserTest {

    private final TransactionFeatureParser parser = new TransactionFeatureParser();

    @Test
    public void parseCommand_filterTransaction() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        String commandArguments = new ArgumentBuilder()
                .withMonth(transaction.getMonth().toString())
                .build();
        FilterTransactionCommand command = new FilterTransactionCommand(Optional.of(transaction.getMonth()),
                Optional.empty(), Optional.empty(), Optional.empty());
        assertEquals(command, (FilterTransactionCommand) parser
                .parseCommand(FilterTransactionCommand.COMMAND_WORD, commandArguments));
    }

    @Test
    public void parseCommand_addTransaction() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        String commandArguments = new ArgumentBuilder()
                .withDescription(transaction.getDescription().toString())
                .withAmount(transaction.getAmount().toString())
                .build();
        AddTransactionCommand command = new AddTransactionCommand(transaction.getDescription(),
                transaction.getAmount(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty());
        assertEquals(command, (AddTransactionCommand) parser
                .parseCommand(AddTransactionCommand.COMMAND_WORD, commandArguments));
    }

    @Test
    public void parseCommand_editTransaction() throws Exception {
        Transaction transaction = new TransactionBuilder().build();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(transaction).build();
        EditTransactionCommand command = (EditTransactionCommand) parser
                .parseCommand(EditTransactionCommand.COMMAND_WORD,
                        INDEX_FIRST.getOneBased() + " "
                        + TransactionUtil.getEditTransactionDescriptorDetails(descriptor));
        assertEquals(new EditTransactionCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_deleteTransaction() throws Exception {
        DeleteTransactionCommand command = (DeleteTransactionCommand) parser.parseCommand(
                DeleteTransactionCommand.COMMAND_WORD, "" + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteTransactionCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand", ""));
    }
}
