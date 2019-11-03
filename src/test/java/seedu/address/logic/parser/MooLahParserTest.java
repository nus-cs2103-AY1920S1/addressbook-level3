package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_INPUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_ALIAS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIRST_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECOND_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandGroup;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.alias.AddAliasCommand;
import seedu.address.logic.commands.alias.DeleteAliasCommand;
import seedu.address.logic.commands.alias.ListAliasesCommand;
import seedu.address.logic.commands.budget.DeleteExpenseFromBudgetCommand;
import seedu.address.logic.commands.budget.EditExpenseFromBudgetCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.event.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.commands.event.ListEventsCommand;
import seedu.address.logic.commands.expense.AddExpenseCommand;
import seedu.address.logic.commands.expense.DeleteExpenseCommand;
import seedu.address.logic.commands.expense.EditExpenseCommand;
import seedu.address.logic.commands.expense.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.logic.commands.expense.FindExpenseCommand;
import seedu.address.logic.commands.expense.ListExpensesCommand;
import seedu.address.logic.commands.general.ClearCommand;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.statistics.StatsCommand;
import seedu.address.logic.commands.statistics.StatsCompareCommand;
import seedu.address.logic.commands.statistics.StatsTrendCommand;
import seedu.address.logic.commands.ui.ViewPanelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.DescriptionContainsKeywordsPredicate;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Expense;
import seedu.address.model.statistics.Mode;
import seedu.address.testutil.AliasTestUtil;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditExpenseDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;
import seedu.address.testutil.ExpenseBuilder;
import seedu.address.testutil.ExpenseUtil;
import seedu.address.ui.panel.PanelName;

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
                ListExpensesCommand.COMMAND_WORD, CommandGroup.EXPENSE, readOnlyUserPrefs) instanceof ListExpensesCommand);
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
        ).forEach( commandGroup -> {
            assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                    -> parser.parseCommand("", commandGroup, readOnlyUserPrefs));
                }
        );
    }

    // ------ budget commands ----------



    // ------ stats command --------
    @Test
    void parseCommand_stats() throws Exception {
        Command command =  parser.parseCommand(
                String.format("%s %s01-10-2019 %s31-10-2019",
                        StatsCommand.COMMAND_WORD,
                        PREFIX_START_DATE,
                        PREFIX_END_DATE),
                CommandGroup.STATISTIC, readOnlyUserPrefs);
        assertTrue(command instanceof  StatsCommand);
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
                String.format("%s %s10-10 %s11-10",
                        StatsCompareCommand.COMMAND_WORD,
                        PREFIX_FIRST_START_DATE ,
                        PREFIX_SECOND_START_DATE),
                CommandGroup.STATISTIC, readOnlyUserPrefs);
        assertTrue(command instanceof StatsCompareCommand);
        // equals not included yet
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
        ).forEach( commandGroup -> {
            assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
                    -> parser.parseCommand("unknownCommand", "", readOnlyUserPrefs));
        });
    }





}
