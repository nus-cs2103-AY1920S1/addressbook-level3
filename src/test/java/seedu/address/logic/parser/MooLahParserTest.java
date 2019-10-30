package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_INPUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.alias.AddAliasCommand;
import seedu.address.logic.commands.expense.ClearCommand;
import seedu.address.logic.commands.expense.DeleteExpenseCommand;
import seedu.address.logic.commands.expense.EditExpenseCommand;
import seedu.address.logic.commands.expense.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.logic.commands.expense.FindExpenseCommand;
import seedu.address.logic.commands.expense.ListExpenseCommand;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.statistics.StatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.DescriptionContainsKeywordsPredicate;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.AliasTestUtil;
import seedu.address.testutil.EditExpenseDescriptorBuilder;
import seedu.address.testutil.ExpenseBuilder;
import seedu.address.testutil.ExpenseUtil;

public class MooLahParserTest {

    private final MooLahParser parser;

    private final ReadOnlyUserPrefs readOnlyUserPrefs;

    public MooLahParserTest() {
        parser = new MooLahParser();

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAliasMappings(AliasTestUtil.VALID_ALIAS_MAPPINGS);
        readOnlyUserPrefs = userPrefs;
    }

    @Test
    public void parseCommand_alias() throws Exception {
        AddAliasCommand aliasCommand = (AddAliasCommand) parser.parseCommand(
                String.format("%s %s a %s b",
                        AddAliasCommand.COMMAND_WORD, PREFIX_ALIAS_ALIAS_NAME, PREFIX_ALIAS_ALIAS_INPUT),
                "", readOnlyUserPrefs);
        assertEquals(aliasCommand, new AddAliasCommand(AliasTestUtil.ALIAS_A_TO_B));
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, "", readOnlyUserPrefs) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", "", readOnlyUserPrefs) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteExpenseCommand command = (DeleteExpenseCommand) parser.parseCommand(
                DeleteExpenseCommand.COMMAND_WORD + " " + INDEX_FIRST_EXPENSE.getOneBased(), "", readOnlyUserPrefs);
        assertEquals(new DeleteExpenseCommand(INDEX_FIRST_EXPENSE), command);
    }


    @Test
    public void parseCommand_edit() throws Exception {
        Expense expense = new ExpenseBuilder().build();
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(expense).build();
        EditExpenseCommand command = (EditExpenseCommand) parser.parseCommand(EditExpenseCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_EXPENSE.getOneBased() + " "
                        + ExpenseUtil.getEditExpenseDescriptorDetails(descriptor),
                "", readOnlyUserPrefs);

        assertEquals(new EditExpenseCommand(INDEX_FIRST_EXPENSE, descriptor), command);
    }


    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, "", readOnlyUserPrefs) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", "", readOnlyUserPrefs) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindExpenseCommand command = (FindExpenseCommand) parser.parseCommand(
                FindExpenseCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")), "", readOnlyUserPrefs);
        assertEquals(new FindExpenseCommand(new DescriptionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, "", readOnlyUserPrefs) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", "", readOnlyUserPrefs) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD, "", readOnlyUserPrefs) instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD + " 3", "", readOnlyUserPrefs) instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", "", readOnlyUserPrefs));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand", "", readOnlyUserPrefs));
    }

    @Test
    void parseCommand_stats() throws Exception {
        StatsCommand command = (StatsCommand) parser.parseCommand(
                String.format("%s %s01-10-2019 %s31-10-2019",
                        StatsCommand.COMMAND_WORD,
                        PREFIX_START_DATE,
                        PREFIX_END_DATE),
                "", readOnlyUserPrefs);
        assertEquals(command, new StatsCommand(
                CommandTestUtil.OCTOBER_FIRST,
                CommandTestUtil.OCTOBER_LAST));

    }

}
