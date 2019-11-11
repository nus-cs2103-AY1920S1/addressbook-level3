package seedu.billboard.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.billboard.logic.commands.AddCommand;
import seedu.billboard.logic.commands.ClearCommand;
import seedu.billboard.logic.commands.DeleteCommand;
import seedu.billboard.logic.commands.EditCommand;
import seedu.billboard.logic.commands.EditCommand.EditExpenseDescriptor;
import seedu.billboard.logic.commands.ExitCommand;
import seedu.billboard.logic.commands.FindCommand;
import seedu.billboard.logic.commands.HelpCommand;
import seedu.billboard.logic.commands.ListCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.MultiArgPredicate;
import seedu.billboard.model.expense.Name;
import seedu.billboard.testutil.EditExpenseDescriptorBuilder;
import seedu.billboard.testutil.ExpenseBuilder;
import seedu.billboard.testutil.ExpenseUtil;

public class BillboardParserTest {

    private final BillboardParser parser = new BillboardParser();

    @Test
    public void parseCommand_add() throws Exception {
        Expense expense = new ExpenseBuilder().build();
        Name name = expense.getName();
        Description description = expense.getDescription();
        Amount amount = expense.getAmount();
        CreatedDateTime dateTime = expense.getCreated();
        List<String> tagNames = expense.getTags().stream().map(x -> x.tagName).collect(Collectors.toList());

        AddCommand command = (AddCommand) parser.parseCommand(ExpenseUtil.getAddCommand(expense));
        assertEquals(new AddCommand(name, description, amount, dateTime, tagNames), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_EXPENSE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_EXPENSE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Expense expense = new ExpenseBuilder().build();
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(expense).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_EXPENSE.getOneBased() + " " + ExpenseUtil.getEditExpenseDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_EXPENSE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");

        FindCommand command = (FindCommand) parser.parseCommand(FindCommand.COMMAND_WORD + " "
                + CliSyntax.PREFIX_DESCRIPTION + String.join(" ", keywords));
        MultiArgPredicate predicate = new MultiArgPredicate();
        predicate.setKeywords(keywords);
        assertEquals(new FindCommand(predicate), command);
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
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
