package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.alias.AddAliasCommand;
import seedu.address.logic.commands.alias.DeleteAliasCommand;
import seedu.address.logic.commands.alias.ListAliasesCommand;
import seedu.address.logic.commands.budget.AddBudgetCommand;
import seedu.address.logic.commands.budget.ClearBudgetsCommand;
import seedu.address.logic.commands.budget.DeleteBudgetByIndexCommand;
import seedu.address.logic.commands.budget.DeleteBudgetByNameCommand;
import seedu.address.logic.commands.budget.DeleteExpenseFromBudgetCommand;
import seedu.address.logic.commands.budget.EditBudgetCommand;
import seedu.address.logic.commands.budget.EditExpenseFromBudgetCommand;
import seedu.address.logic.commands.budget.ListBudgetsCommand;
import seedu.address.logic.commands.budget.SwitchBudgetCommand;
import seedu.address.logic.commands.budget.SwitchPeriodCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.event.ListEventsCommand;
import seedu.address.logic.commands.expense.AddExpenseCommand;
import seedu.address.logic.commands.expense.DeleteExpenseCommand;
import seedu.address.logic.commands.expense.EditExpenseCommand;
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
import seedu.address.model.alias.Alias;

/**
 * Parses user input.
 */
public class MooLahParser {


    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @param commandGroup
     * @param readOnlyUserPrefs read only user preferences to check for aliases
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, String commandGroup, ReadOnlyUserPrefs readOnlyUserPrefs)
            throws ParseException {
        Input input = ParserUtil.parseInput(userInput);

        String commandWord = getCommandWord(input, commandGroup);
        String arguments = input.getArguments();
        //expense
        if (AddExpenseCommand.COMMAND_WORD.equals(commandWord)) {
            return new AddExpenseCommandParser().parse(arguments);
        } else if (EditExpenseCommand.COMMAND_WORD.equals(commandWord)) {
            return new EditCommandParser().parse(arguments);
        } else if (EditExpenseFromBudgetCommand.COMMAND_WORD.equals(commandWord)) {
            return new EditExpenseFromBudgetCommandParser().parse(arguments);
        } else if (DeleteExpenseCommand.COMMAND_WORD.equals(commandWord)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (DeleteExpenseFromBudgetCommand.COMMAND_WORD.equals(commandWord)) {
            return new DeleteExpenseFromBudgetCommandParser().parse(arguments);
        } else if (ClearCommand.COMMAND_WORD.equals(commandWord)) {
            return new ClearCommand();
        } else if (FindExpenseCommand.COMMAND_WORD.equals(commandWord)) {
            return new FindCommandParser().parse(arguments);
        } else if (ListExpensesCommand.COMMAND_WORD.equals(commandWord)) {
            return new ListExpensesCommand();
            //event
        } else if (AddEventCommand.COMMAND_WORD.equals(commandWord)) {
            return new AddEventCommandParser().parse(arguments);
        } else if (ListEventsCommand.COMMAND_WORD.equals(commandWord)) {
            return new ListEventsCommand();
        } else if (EditEventCommand.COMMAND_WORD.equals(commandWord)) {
            return new EditEventCommandParser().parse(arguments);
        } else if (DeleteEventCommand.COMMAND_WORD.equals(commandWord)) {
            return new DeleteEventCommandParser().parse(arguments);
            //budget
        } else if (AddBudgetCommand.COMMAND_WORD.equals(commandWord)) {
            return new AddBudgetCommandParser().parse(arguments);
        } else if (EditBudgetCommand.COMMAND_WORD.equals(commandWord)) {
            return new EditBudgetCommandParser().parse(arguments);
        } else if (SwitchBudgetCommand.COMMAND_WORD.equals(commandWord)) {
            return new SwitchBudgetCommandParser().parse(arguments);
        } else if (ListBudgetsCommand.COMMAND_WORD.equals(commandWord)) {
            return new ListBudgetsCommand();
        } else if (DeleteBudgetByIndexCommand.COMMAND_WORD.equals(commandWord)) {
            return new DeleteBudgetByIndexCommandParser().parse(arguments);
        } else if (DeleteBudgetByNameCommand.COMMAND_WORD.equals(commandWord)) {
            return new DeleteBudgetByNameCommandParser().parse(arguments);
        } else if (SwitchPeriodCommand.COMMAND_WORD.equals(commandWord)) {
            return new SwitchPeriodCommandParser().parse(arguments);
        } else if (ClearBudgetsCommand.COMMAND_WORD.equals(commandWord)) {
            return new ClearBudgetsCommand();

            //stats
        } else if (StatsCommand.COMMAND_WORD.equals(commandWord)) {
            return new StatsCommandParser().parse(arguments);
        } else if (StatsCompareCommand.COMMAND_WORD.equals(commandWord)) {
            return new StatsCompareCommandParser().parse(arguments);
        } else if (StatsTrendCommand.COMMAND_WORD.equals(commandWord)) {
            return new StatsTrendCommandParser().parse(arguments);

            //alias
        } else if (AddAliasCommand.COMMAND_WORD.equals(commandWord)) {
            return new AddAliasCommandParser().parse(arguments);
        } else if (DeleteAliasCommand.COMMAND_WORD.equals(commandWord)) {
            return new DeleteAliasCommandParser().parse(arguments);
        } else if (ListAliasesCommand.COMMAND_WORD.equals(commandWord)) {
            return new ListAliasesCommand();

            //general
        } else if (ExitCommand.COMMAND_WORD.equals(commandWord)) {
            return new ExitCommand();
        } else if (HelpCommand.COMMAND_WORD.equals(commandWord)) {
            return new HelpCommand();
        } else if (UndoCommand.COMMAND_WORD.equals(commandWord)) {
            return new UndoCommand();
        } else if (RedoCommand.COMMAND_WORD.equals(commandWord)) {
            return new RedoCommand();
        } else if (ViewPanelCommand.COMMAND_WORD.equals(commandWord)) {
            return new ViewPanelCommandParser().parse(arguments);
        }
        // check if alias exists
        if (readOnlyUserPrefs.hasAlias(commandWord)) {
            Alias alias = readOnlyUserPrefs.getAlias(commandWord);
            return parseCommand(alias.getInput() + arguments, commandGroup, readOnlyUserPrefs);
        }
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
    }

    private String getCommandWord(Input input, String commandGroup) {
        if (input.isGeneric()) {
            return input.getCommandWord() + commandGroup;
        } else {
            return input.getCommandWord();
        }
    }

}
