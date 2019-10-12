package thrift.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static thrift.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static thrift.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import thrift.logic.commands.AddExpenseCommand;
import thrift.logic.commands.ClearCommand;
import thrift.logic.commands.DeleteCommand;
import thrift.logic.commands.ExitCommand;
import thrift.logic.commands.FindCommand;
import thrift.logic.commands.HelpCommand;
import thrift.logic.commands.ListCommand;
import thrift.logic.commands.UpdateCommand;
import thrift.logic.commands.UpdateCommand.UpdateTransactionDescriptor;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.transaction.DescriptionContainsKeywordsPredicate;
import thrift.model.transaction.Expense;
import thrift.testutil.ExpenseBuilder;
import thrift.testutil.TransactionUtil;
import thrift.testutil.TypicalIndexes;
import thrift.testutil.UpdateTransactionDescriptorBuilder;

public class ThriftParserTest {

    private final ThriftParser parser = new ThriftParser();

    @Test
    public void parseCommand_addExpense() throws Exception {
        assertDoesNotThrow(() -> (AddExpenseCommand) parser.parseCommand(TransactionUtil
                .getAddExpenseCommand(new ExpenseBuilder().build())));
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_INDEX
                        + TypicalIndexes.INDEX_FIRST_TRANSACTION.getOneBased());
        assertEquals(new DeleteCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION), command);
    }

    @Test
    public void parseCommand_update() throws Exception {
        Expense expense = new ExpenseBuilder().build();
        UpdateTransactionDescriptor descriptor = new UpdateTransactionDescriptorBuilder(expense).build();
        assertDoesNotThrow(() -> (UpdateCommand) parser.parseCommand(UpdateCommand.COMMAND_WORD
                + " " + CliSyntax.PREFIX_INDEX + TypicalIndexes.INDEX_FIRST_TRANSACTION.getOneBased() + " "
                + TransactionUtil.getUpdateTransactionDescriptorDetails(descriptor)));
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new DescriptionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand"));
    }
}
