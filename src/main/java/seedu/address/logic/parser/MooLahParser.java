package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Alias;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.alias.AliasCommand;
import seedu.address.logic.commands.budget.AddBudgetCommand;
import seedu.address.logic.commands.budget.DeleteBudgetCommand;
import seedu.address.logic.commands.budget.DeleteExpenseFromBudgetCommand;
import seedu.address.logic.commands.budget.EditBudgetCommand;
import seedu.address.logic.commands.budget.EditExpenseFromBudgetCommand;
import seedu.address.logic.commands.budget.ListBudgetCommand;
import seedu.address.logic.commands.budget.PastPeriodCommand;
import seedu.address.logic.commands.budget.SwitchBudgetCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.event.ListEventsCommand;
import seedu.address.logic.commands.expense.ClearCommand;
import seedu.address.logic.commands.expense.DeleteCommand;
import seedu.address.logic.commands.expense.EditCommand;
import seedu.address.logic.commands.expense.FindCommand;
import seedu.address.logic.commands.expense.ListCommand;
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
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @param readOnlyUserPrefs read only user preferences to check for aliases
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, ReadOnlyUserPrefs readOnlyUserPrefs) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);
        case AliasCommand.COMMAND_WORD:
            return new AliasCommandParser().parse(arguments);
        case AddBudgetCommand.COMMAND_WORD:
            return new AddBudgetCommandParser().parse(arguments);
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);
        case EditExpenseFromBudgetCommand.COMMAND_WORD:
            return new EditExpenseFromBudgetCommandParser().parse(arguments);
        case EditEventCommand.COMMAND_WORD:
            return new EditEventCommandParser().parse(arguments);
        case EditBudgetCommand.COMMAND_WORD:
            return new EditBudgetCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);
        case DeleteExpenseFromBudgetCommand.COMMAND_WORD:
            return new DeleteExpenseFromBudgetCommandParser().parse(arguments);
        case DeleteEventCommand.COMMAND_WORD:
            return new DeleteEventCommandParser().parse(arguments);
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case ListEventsCommand.COMMAND_WORD:
            return new ListEventsCommand();
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();
        case StatsCommand.COMMAND_WORD:
            return new StatsCommandParser().parse(arguments);
        case StatsCompareCommand.COMMAND_WORD:
            return new StatsCompareCommandParser().parse(arguments);
        case SwitchBudgetCommand.COMMAND_WORD:
            return new SwitchBudgetCommandParser().parse(arguments);
        case ViewPanelCommand.COMMAND_WORD:
            return new ViewPanelCommandParser().parse(arguments);
        case ListBudgetCommand.COMMAND_WORD:
            return new ListBudgetCommand();
        case DeleteBudgetCommand.COMMAND_WORD:
            return new DeleteBudgetCommandParser().parse(arguments);
        case PastPeriodCommand.COMMAND_WORD:
            return new PastPeriodCommandParser().parse(arguments);
        default:
            // check if alias exists
            if (readOnlyUserPrefs.hasAlias(commandWord)) {
                Alias alias = readOnlyUserPrefs.getAlias(commandWord);
                return parseCommand(alias.getInput() + arguments, readOnlyUserPrefs);
            }
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
