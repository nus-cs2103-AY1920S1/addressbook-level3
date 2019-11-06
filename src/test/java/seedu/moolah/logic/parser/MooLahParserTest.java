package seedu.moolah.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_INPUT;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_NAME;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_FIRST_START_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_SECOND_START_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_THIRD;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.Command;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandTestUtil;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.RedoCommand;
import seedu.moolah.logic.commands.UndoCommand;
import seedu.moolah.logic.commands.alias.AddAliasCommand;
import seedu.moolah.logic.commands.alias.DeleteAliasCommand;
import seedu.moolah.logic.commands.alias.ListAliasesCommand;
import seedu.moolah.logic.commands.budget.AddBudgetCommand;
import seedu.moolah.logic.commands.budget.ClearBudgetsCommand;
import seedu.moolah.logic.commands.budget.DeleteBudgetByIndexCommand;
import seedu.moolah.logic.commands.budget.DeleteBudgetByNameCommand;
import seedu.moolah.logic.commands.budget.DeleteExpenseFromBudgetCommand;
import seedu.moolah.logic.commands.budget.EditBudgetCommand;
import seedu.moolah.logic.commands.budget.EditExpenseFromBudgetCommand;
import seedu.moolah.logic.commands.budget.ListBudgetsCommand;
import seedu.moolah.logic.commands.budget.SwitchBudgetCommand;
import seedu.moolah.logic.commands.budget.SwitchPeriodCommand;
import seedu.moolah.logic.commands.event.AddEventCommand;
import seedu.moolah.logic.commands.event.DeleteEventCommand;
import seedu.moolah.logic.commands.event.EditEventCommand;
import seedu.moolah.logic.commands.event.EditEventCommand.EditEventDescriptor;
import seedu.moolah.logic.commands.event.ListEventsCommand;
import seedu.moolah.logic.commands.expense.AddExpenseCommand;
import seedu.moolah.logic.commands.expense.DeleteExpenseCommand;
import seedu.moolah.logic.commands.expense.EditExpenseCommand;
import seedu.moolah.logic.commands.expense.EditExpenseCommand.EditExpenseDescriptor;
import seedu.moolah.logic.commands.expense.FindExpenseCommand;
import seedu.moolah.logic.commands.expense.ListExpensesCommand;
import seedu.moolah.logic.commands.general.ClearCommand;
import seedu.moolah.logic.commands.general.ExitCommand;
import seedu.moolah.logic.commands.general.HelpCommand;
import seedu.moolah.logic.commands.statistics.StatsCommand;
import seedu.moolah.logic.commands.statistics.StatsCompareCommand;
import seedu.moolah.logic.commands.statistics.StatsTrendCommand;
import seedu.moolah.logic.commands.ui.ViewPanelCommand;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.ReadOnlyUserPrefs;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.expense.DescriptionContainsKeywordsPredicate;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.testutil.AliasTestUtil;
import seedu.moolah.testutil.EditEventDescriptorBuilder;
import seedu.moolah.testutil.EditExpenseDescriptorBuilder;
import seedu.moolah.testutil.EventBuilder;
import seedu.moolah.testutil.EventUtil;
import seedu.moolah.testutil.ExpenseBuilder;
import seedu.moolah.testutil.ExpenseUtil;
import seedu.moolah.ui.panel.PanelName;

public class MooLahParserTest {

    private final MooLahParser parser;

    private final ReadOnlyUserPrefs readOnlyUserPrefs;

    public MooLahParserTest() {
        parser = new MooLahParser();

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.addUserAlias(AliasTestUtil.ALIAS_ADD_WITH_ARGUMENTS);
        readOnlyUserPrefs = userPrefs;
    }

    // alias
    @Test
    public void parseCommand_addAliasCommand() throws Exception {
        Command aliasCommand = parser.parseCommand(
                String.format("%s %s a %s b",
                        AddAliasCommand.COMMAND_WORD, PREFIX_ALIAS_ALIAS_NAME, PREFIX_ALIAS_ALIAS_INPUT),
                CommandGroup.ALIAS, readOnlyUserPrefs);
        assertTrue(aliasCommand instanceof AddAliasCommand);
        assertEquals(aliasCommand, new AddAliasCommand(AliasTestUtil.ALIAS_A_TO_B));
    }

    @Test
    public void parseCommand_deleteAliasCommand() throws Exception {
        String aliasName = AliasTestUtil.ALIAS_A_TO_B.getAliasName();
        Command deleteAliasCommand = parser.parseCommand(
                String.format("%s %s", DeleteAliasCommand.COMMAND_WORD, aliasName),
                CommandGroup.ALIAS, readOnlyUserPrefs);
        assertTrue(deleteAliasCommand instanceof DeleteAliasCommand);
        assertEquals(deleteAliasCommand, new DeleteAliasCommand(aliasName));
    }

    @Test
    public void parseCommand_listAliasCommand() throws Exception {
        Command listAliasesCommand = parser.parseCommand(
                ListAliasesCommand.COMMAND_WORD,
                CommandGroup.ALIAS, readOnlyUserPrefs);
        assertTrue(listAliasesCommand instanceof ListAliasesCommand);
        assertEquals(listAliasesCommand, new ListAliasesCommand());
    }

    //general
    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(
                ClearCommand.COMMAND_WORD, CommandGroup.GENERAL, readOnlyUserPrefs) instanceof ClearCommand);
        assertTrue(
                parser.parseCommand(
                        ClearCommand.COMMAND_WORD + " 3", CommandGroup.GENERAL, readOnlyUserPrefs)
                        instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(
                ExitCommand.COMMAND_WORD, CommandGroup.GENERAL, readOnlyUserPrefs)
                instanceof ExitCommand);
        assertTrue(parser.parseCommand(
                ExitCommand.COMMAND_WORD + " 3", CommandGroup.GENERAL, readOnlyUserPrefs)
                instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(
                HelpCommand.COMMAND_WORD, CommandGroup.GENERAL, readOnlyUserPrefs) instanceof HelpCommand);
        assertTrue(parser.parseCommand(
                HelpCommand.COMMAND_WORD + " 3", CommandGroup.GENERAL, readOnlyUserPrefs)
                instanceof HelpCommand);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(
                UndoCommand.COMMAND_WORD, CommandGroup.GENERAL, readOnlyUserPrefs)
                instanceof UndoCommand);
        assertTrue(parser.parseCommand(
                UndoCommand.COMMAND_WORD + " 3", CommandGroup.GENERAL, readOnlyUserPrefs)
                instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(
                parser.parseCommand(
                        RedoCommand.COMMAND_WORD, CommandGroup.GENERAL, readOnlyUserPrefs)
                        instanceof RedoCommand);
        assertTrue(
                parser.parseCommand(
                        RedoCommand.COMMAND_WORD + " 3", CommandGroup.GENERAL, readOnlyUserPrefs)
                        instanceof RedoCommand);
    }

    @Test
    public void parseCommand_viewCommand() throws Exception {
        assertTrue(
                parser.parseCommand(ViewPanelCommand.COMMAND_WORD + " " + "ignored panel name",
                CommandGroup.GENERAL, readOnlyUserPrefs) instanceof ViewPanelCommand);
        String panelName = "panel name placeholder";
        assertEquals(
                parser.parseCommand(
                        ViewPanelCommand.COMMAND_WORD + " " + panelName,
                        CommandGroup.GENERAL,
                        readOnlyUserPrefs),
                new ViewPanelCommand(new PanelName(panelName)));
    }

    // ---------- expense commands ----------

    @Test
    public void parseCommand_addExpense() throws Exception {
        Command command = parser.parseCommand(
                AddExpenseCommand.COMMAND_WORD + " "
                        + PREFIX_DESCRIPTION + "d "
                        + PREFIX_CATEGORY + "food "
                        + PREFIX_PRICE + "123",
                CommandGroup.EXPENSE, readOnlyUserPrefs);
        assertTrue(command instanceof AddExpenseCommand);
    }

    @Test
    public void parseCommand_deleteExpense() throws Exception {
        Command command = parser.parseCommand(
                DeleteExpenseCommand.COMMAND_WORD + " " + INDEX_SECOND.getOneBased(),
                CommandGroup.EXPENSE, readOnlyUserPrefs);
        assertTrue(command instanceof DeleteExpenseCommand);
        assertEquals(new DeleteExpenseCommand(INDEX_SECOND), command);
    }

    @Test
    public void parseCommand_editExpense() throws Exception {
        Expense expense = new ExpenseBuilder().build();
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(expense).build();
        Command command = parser.parseCommand(EditExpenseCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + " "
                        + ExpenseUtil.getEditExpenseDescriptorDetails(descriptor),
                CommandGroup.EXPENSE, readOnlyUserPrefs);
        assertTrue(command instanceof EditExpenseCommand);
        assertEquals(new EditExpenseCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        Command command = parser.parseCommand(
                FindExpenseCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")),
                CommandGroup.BUDGET,
                readOnlyUserPrefs);
        assertTrue(command instanceof FindExpenseCommand);
        assertEquals(new FindExpenseCommand(new DescriptionContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_listExpense() throws Exception {
        assertTrue(parser.parseCommand(
                ListExpensesCommand.COMMAND_WORD,
                CommandGroup.EXPENSE,
                readOnlyUserPrefs) instanceof ListExpensesCommand);
        assertTrue(parser.parseCommand(
                ListExpensesCommand.COMMAND_WORD + " 3", "",
                readOnlyUserPrefs) instanceof ListExpensesCommand);
    }


    // ------- primary budget commands--------

    @Test
    public void parseCommand_editExpenseFromBudget() throws Exception {
        Expense expense = new ExpenseBuilder().build();
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(expense).build();
        Command command = parser.parseCommand(EditExpenseFromBudgetCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + " "
                        + ExpenseUtil.getEditExpenseDescriptorDetails(descriptor),
                CommandGroup.EXPENSE, readOnlyUserPrefs);
        assertTrue(command instanceof EditExpenseFromBudgetCommand);
        assertEquals(new EditExpenseFromBudgetCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_deleteExpenseFromBudget() throws Exception {
        assertTrue(parser.parseCommand(
                DeleteExpenseFromBudgetCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                CommandGroup.PRIMARY_BUDGET, readOnlyUserPrefs) instanceof DeleteExpenseFromBudgetCommand);
        assertEquals(parser.parseCommand(
                DeleteExpenseFromBudgetCommand.COMMAND_WORD + " " + INDEX_THIRD.getOneBased(),
                CommandGroup.PRIMARY_BUDGET, readOnlyUserPrefs), new DeleteExpenseFromBudgetCommand(INDEX_THIRD));
    }


    // ---------- event commands --------

    @Test
    public void parseCommand_addEvent() throws Exception {
        Command command = parser.parseCommand(
                AddEventCommand.COMMAND_WORD + " "
                        + PREFIX_DESCRIPTION + "d "
                        + PREFIX_CATEGORY + "food "
                        + PREFIX_PRICE + "123 "
                        + PREFIX_TIMESTAMP + "tomorrow",
                CommandGroup.EVENT, readOnlyUserPrefs);
        assertTrue(command instanceof AddEventCommand);
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                CommandGroup.EVENT, readOnlyUserPrefs);
        assertEquals(new DeleteEventCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_listEvent() throws Exception {
        assertTrue(parser.parseCommand(
                ListEventsCommand.COMMAND_WORD, CommandGroup.EVENT, readOnlyUserPrefs) instanceof ListEventsCommand);
        assertTrue(parser.parseCommand(
                ListEventsCommand.COMMAND_WORD + " 3", "",
                readOnlyUserPrefs) instanceof ListEventsCommand);
    }

    @Test
    public void parseCommand_editEvent() throws Exception {
        Event event = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(event).build();
        EditEventCommand command = (EditEventCommand) parser.parseCommand(EditEventCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + " "
                        + EventUtil.getEditEventDescriptorDetails(descriptor),
                CommandGroup.EVENT, readOnlyUserPrefs);

        assertEquals(new EditEventCommand(INDEX_FIRST, descriptor), command);
    }


    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        Stream.of(
                //empty input with command group
                CommandGroup.ALIAS,
                CommandGroup.BUDGET,
                CommandGroup.EVENT,
                CommandGroup.EXPENSE,
                CommandGroup.STATISTIC,
                CommandGroup.PRIMARY_BUDGET,
                CommandGroup.GENERAL,
                // empty input with empty command group
                ""
        ).forEach(commandGroup -> {
            assertThrows(
                    ParseException.class,
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), () ->
                            parser.parseCommand("", commandGroup, readOnlyUserPrefs));
                }
        );
    }

    // ------ budget commands ----------

    @Test
    public void parseCommand_addBudget() throws Exception {
        assertTrue(parser.parseCommand(
                AddBudgetCommand.COMMAND_WORD + " "
                + PREFIX_DESCRIPTION + "abc "
                + PREFIX_START_DATE + "today "
                + PREFIX_PERIOD + "day "
                + PREFIX_PRICE + "100", CommandGroup.BUDGET, readOnlyUserPrefs) instanceof AddBudgetCommand);
    }

    @Test
    public void parseCommand_switchBudget() throws Exception {
        assertTrue(parser.parseCommand(
                SwitchBudgetCommand.COMMAND_WORD + " " + PREFIX_DESCRIPTION + "abc ",
                CommandGroup.BUDGET, readOnlyUserPrefs) instanceof SwitchBudgetCommand);
    }

    @Test
    public void parseCommand_editBudget() throws Exception {
        assertTrue(parser.parseCommand(
                EditBudgetCommand.COMMAND_WORD + " 1 " + PREFIX_DESCRIPTION + "abc ",
                CommandGroup.BUDGET, readOnlyUserPrefs) instanceof EditBudgetCommand);
    }

    @Test
    public void parseCommand_listBudgets() throws Exception {
        assertTrue(parser.parseCommand(
                ListBudgetsCommand.COMMAND_WORD,
                CommandGroup.BUDGET, readOnlyUserPrefs) instanceof ListBudgetsCommand);
    }

    @Test
    public void parseCommand_deleteBudgetByIndex() throws Exception {
        assertTrue(parser.parseCommand(
                DeleteBudgetByIndexCommand.COMMAND_WORD + " 1",
                CommandGroup.BUDGET, readOnlyUserPrefs) instanceof DeleteBudgetByIndexCommand);
    }

    @Test
    public void parseCommand_deleteBudgetByName() throws Exception {
        assertTrue(parser.parseCommand(
                DeleteBudgetByNameCommand.COMMAND_WORD + " " + PREFIX_DESCRIPTION + "some name",
                CommandGroup.BUDGET, readOnlyUserPrefs) instanceof DeleteBudgetByNameCommand);
    }

    @Test
    public void parseCommand_switchPeriod() throws Exception {
        assertTrue(parser.parseCommand(
                SwitchPeriodCommand.COMMAND_WORD + " " + PREFIX_TIMESTAMP + "tomorrow",
                CommandGroup.BUDGET, readOnlyUserPrefs) instanceof SwitchPeriodCommand);
    }

    @Test
    public void parseCommand_clearBudget() throws Exception {
        assertTrue(parser.parseCommand(
                ClearBudgetsCommand.COMMAND_WORD,
                CommandGroup.BUDGET, readOnlyUserPrefs) instanceof ClearBudgetsCommand);
    }


    // ------ stats command --------
    @Test
    void parseCommand_stats() throws Exception {
        Command command = parser.parseCommand(
                String.format("%s %s01-10-2019 %s31-10-2019",
                        StatsCommand.COMMAND_WORD,
                        PREFIX_START_DATE,
                        PREFIX_END_DATE),
                CommandGroup.STATISTIC, readOnlyUserPrefs);
        assertTrue(command instanceof StatsCommand);
        assertEquals(command, new StatsCommand(
                CommandTestUtil.OCTOBER_FIRST,
                CommandTestUtil.OCTOBER_LAST));
    }

    @Test
    void parseCommand_statsTrend() throws Exception {
        Command command = parser.parseCommand(
            String.format("%s %s%s",
                    StatsTrendCommand.COMMAND_WORD,
                    PREFIX_MODE ,
                    "budget"),
            CommandGroup.STATISTIC, readOnlyUserPrefs);
        assertTrue(command instanceof StatsTrendCommand);
        // equals not included yet
    }

    @Test
    void parseCommand_statsCompare() throws Exception {
        Command command = parser.parseCommand(
                String.format("%s %s01-10-2019 %s31-10-2019",
                        StatsCompareCommand.COMMAND_WORD,
                        PREFIX_FIRST_START_DATE ,
                        PREFIX_SECOND_START_DATE),
                CommandGroup.STATISTIC, readOnlyUserPrefs);
        assertTrue(command instanceof StatsCompareCommand);
        assertEquals(command, new StatsCompareCommand(
                CommandTestUtil.OCTOBER_FIRST, CommandTestUtil.OCTOBER_LAST));
    }


    // ----- command is alias -------

    @Test
    public void parseCommand_commandIsAlias() throws Exception {
        assertTrue(
                parser.parseCommand(AliasTestUtil.ALIAS_ADD_WITH_ARGUMENTS.getAliasName(),
                        "ignoredValue",
                        readOnlyUserPrefs)
                        instanceof AddExpenseCommand);

    }

    // ----- generic command ------

    @Test
    public void parseCommand_commandIsGeneric() throws Exception {
        assertTrue(
                parser.parseCommand(
                        GenericCommandWord.LIST,
                        CommandGroup.ALIAS,
                        readOnlyUserPrefs) instanceof ListAliasesCommand);
        assertTrue(
                parser.parseCommand(
                        GenericCommandWord.LIST,
                        CommandGroup.BUDGET,
                        readOnlyUserPrefs) instanceof ListBudgetsCommand);
        assertTrue(
                parser.parseCommand(
                        GenericCommandWord.LIST,
                        CommandGroup.EVENT,
                        readOnlyUserPrefs) instanceof ListEventsCommand);
        assertTrue(
                parser.parseCommand(
                        GenericCommandWord.LIST,
                        CommandGroup.EXPENSE,
                        readOnlyUserPrefs) instanceof ListExpensesCommand);
    }

    // ----- other tests ------

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Stream.of(
                //unknown input with command group
                CommandGroup.ALIAS,
                CommandGroup.BUDGET,
                CommandGroup.EVENT,
                CommandGroup.EXPENSE,
                CommandGroup.STATISTIC,
                CommandGroup.PRIMARY_BUDGET,
                CommandGroup.GENERAL,
                // unknown input with empty command group
                ""
        ).forEach(commandGroup -> {
            assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                    parser.parseCommand("unknownCommand", "", readOnlyUserPrefs));
        });
    }
}
