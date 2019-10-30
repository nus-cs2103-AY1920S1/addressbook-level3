package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.commons.core.Alias;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.alias.AddAliasCommand;
import seedu.address.logic.commands.alias.DeleteAliasCommand;
import seedu.address.logic.commands.alias.ListAliasCommand;
import seedu.address.logic.commands.budget.AddBudgetCommand;
import seedu.address.logic.commands.budget.DeleteBudgetCommand;
import seedu.address.logic.commands.budget.DeleteExpenseFromBudgetCommand;
import seedu.address.logic.commands.budget.EditBudgetCommand;
import seedu.address.logic.commands.budget.EditExpenseFromBudgetCommand;
import seedu.address.logic.commands.budget.ListBudgetCommand;
import seedu.address.logic.commands.budget.PastPeriodCommand;
import seedu.address.logic.commands.budget.SwitchBudgetCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.ListEventsCommand;
import seedu.address.logic.commands.expense.AddExpenseCommand;
import seedu.address.logic.commands.expense.ClearCommand;
import seedu.address.logic.commands.expense.DeleteExpenseCommand;
import seedu.address.logic.commands.expense.EditExpenseCommand;
import seedu.address.logic.commands.expense.FindExpenseCommand;
import seedu.address.logic.commands.expense.ListExpenseCommand;
import seedu.address.logic.commands.general.ExitCommand;
import seedu.address.logic.commands.general.HelpCommand;
import seedu.address.logic.commands.statistics.StatsCommand;
import seedu.address.logic.commands.statistics.StatsCompareCommand;
import seedu.address.logic.commands.ui.ViewPanelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyUserPrefs;

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

        String commandWord;
        if (input.isGeneric()) {
            commandWord = input.getCommandWord() + commandGroup;
        } else {
            commandWord = input.getCommandWord();
        }
        String arguments = input.getArguments();

        switch (commandWord) {
        //expense
        case AddExpenseCommand.COMMAND_WORD:
            return new AddExpenseCommandParser().parse(arguments);
        case EditExpenseCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);
        case EditExpenseFromBudgetCommand.COMMAND_WORD:
            return new EditExpenseFromBudgetCommandParser().parse(arguments);
        case DeleteExpenseCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);
        case DeleteExpenseFromBudgetCommand.COMMAND_WORD:
            return new DeleteExpenseFromBudgetCommandParser().parse(arguments);
        case FindExpenseCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);
        case ListExpenseCommand.COMMAND_WORD:
            return new ListExpenseCommand();
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        //event
        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);
        case ListEventsCommand.COMMAND_WORD:
            return new ListEventsCommand();

        //budget
        case AddBudgetCommand.COMMAND_WORD:
            return new AddBudgetCommandParser().parse(arguments);
        case EditBudgetCommand.COMMAND_WORD:
            return new EditBudgetCommandParser().parse(arguments);
        case SwitchBudgetCommand.COMMAND_WORD:
            return new SwitchBudgetCommandParser().parse(arguments);
        case ListBudgetCommand.COMMAND_WORD:
            return new ListBudgetCommand();
        case DeleteBudgetCommand.COMMAND_WORD:
            return new DeleteBudgetCommandParser().parse(arguments);
        case PastPeriodCommand.COMMAND_WORD:
            return new PastPeriodCommandParser().parse(arguments);

        //stats
        case StatsCommand.COMMAND_WORD:
            return new StatsCommandParser().parse(arguments);
        case StatsCompareCommand.COMMAND_WORD:
            return new StatsCompareCommandParser().parse(arguments);

        //alias
        case AddAliasCommand.COMMAND_WORD:
            return new AddAliasCommandParser().parse(arguments);
        case DeleteAliasCommand.COMMAND_WORD:
            return new DeleteAliasCommandParser().parse(arguments);
        case ListAliasCommand.COMMAND_WORD:
            return new ListAliasCommand();

        //general
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
        case ViewPanelCommand.COMMAND_WORD:
            return new ViewPanelCommandParser().parse(arguments);

        default:
            // check if alias exists
            if (readOnlyUserPrefs.hasAlias(commandWord)) {
                Alias alias = readOnlyUserPrefs.getAlias(commandWord);
                return parseCommand(alias.getInput() + arguments, commandGroup, readOnlyUserPrefs);
            }
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
