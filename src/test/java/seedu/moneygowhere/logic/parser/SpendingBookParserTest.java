package seedu.moneygowhere.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_FIRST_SPENDING;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.AddCommand;
import seedu.moneygowhere.logic.commands.BudgetCommand;
import seedu.moneygowhere.logic.commands.ClearCommand;
import seedu.moneygowhere.logic.commands.DeleteCommand;
import seedu.moneygowhere.logic.commands.EditCommand;
import seedu.moneygowhere.logic.commands.EditCommand.EditSpendingDescriptor;
import seedu.moneygowhere.logic.commands.ExitCommand;
import seedu.moneygowhere.logic.commands.FindCommand;
import seedu.moneygowhere.logic.commands.HelpCommand;
import seedu.moneygowhere.logic.commands.ImportCommand;
import seedu.moneygowhere.logic.commands.ListCommand;
import seedu.moneygowhere.logic.commands.ShowBudgetCommand;
import seedu.moneygowhere.logic.commands.StatsCommand;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.spending.NameContainsKeywordsPredicate;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.testutil.EditSpendingDescriptorBuilder;
import seedu.moneygowhere.testutil.SpendingBuilder;
import seedu.moneygowhere.testutil.SpendingUtil;

public class SpendingBookParserTest {

    private final SpendingBookParser parser = new SpendingBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Spending spending = new SpendingBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(SpendingUtil.getAddCommand(spending));
        assertEquals(new AddCommand(spending), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_SPENDING.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_SPENDING), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Spending spending = new SpendingBuilder().build();
        EditSpendingDescriptor descriptor = new EditSpendingDescriptorBuilder(spending).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_SPENDING.getOneBased() + " " + SpendingUtil.getEditSpendingDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_SPENDING, descriptor), command);
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
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_stats() throws Exception {
        assertTrue(parser.parseCommand(StatsCommand.COMMAND_WORD) instanceof StatsCommand);
        assertTrue(parser.parseCommand(StatsCommand.COMMAND_WORD + " 3") instanceof StatsCommand);
    }

    @Test
    public void parseCommand_budget() throws Exception {
        Budget budget = new Budget(300);
        BudgetCommand command = (BudgetCommand) parser.parseCommand(
                BudgetCommand.COMMAND_WORD + " 300");
        assertEquals(new BudgetCommand(budget), command);
    }

    @Test
    public void parseCommand_showBudget() throws Exception {
        assertTrue(parser.parseCommand(ShowBudgetCommand.COMMAND_WORD) instanceof ShowBudgetCommand);
        assertTrue(parser.parseCommand(ShowBudgetCommand.COMMAND_WORD + " 3") instanceof ShowBudgetCommand);
    }

    @Test
    public void parseCommand_import() throws Exception {
        assertTrue(parser.parseCommand(ImportCommand.COMMAND_WORD) instanceof ImportCommand);
        assertTrue(parser.parseCommand(ImportCommand.COMMAND_WORD + " 3") instanceof ImportCommand);
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
