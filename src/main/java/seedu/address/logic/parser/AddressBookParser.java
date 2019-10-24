package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddExpenseReminderCommand;
import seedu.address.logic.commands.BudgetListCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteBudgetCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteExpenseReminderCommand;
import seedu.address.logic.commands.DeleteWishCommand;
import seedu.address.logic.commands.EditBudgetCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditExpenseCommand;
import seedu.address.logic.commands.EditExpenseReminderCommand;
import seedu.address.logic.commands.EditIncomeCommand;
import seedu.address.logic.commands.EditWishCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindBudgetCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindWishCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.StatisticsCommand;
import seedu.address.logic.commands.TogglePanelCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.WishListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException, IllegalArgumentException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditExpenseCommand.COMMAND_WORD:
            return new EditExpenseCommandParser().parse(arguments);

        case EditIncomeCommand.COMMAND_WORD:
            return new EditIncomeCommandParser().parse(arguments);

        case EditWishCommand.COMMAND_WORD:
            return new EditWishCommandParser().parse(arguments);

        case EditBudgetCommand.COMMAND_WORD:
            return new EditBudgetCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteWishCommand.COMMAND_WORD:
            return new DeleteWishCommandParser().parse(arguments);

        case DeleteBudgetCommand.COMMAND_WORD:
            return new DeleteBudgetCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FindWishCommand.COMMAND_WORD:
            return new FindWishCommandParser().parse(arguments);

        case FindBudgetCommand.COMMAND_WORD:
            return new FindBudgetCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case WishListCommand.COMMAND_WORD:
            return new WishListCommand();

        case BudgetListCommand.COMMAND_WORD:
            return new BudgetListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddExpenseReminderCommand.COMMAND_WORD:
            return new AddExpenseReminderCommandParser().parse(arguments);

        case EditExpenseReminderCommand.COMMAND_WORD:
            return new EditExpenseReminderCommandParser().parse(arguments);

        case DeleteExpenseReminderCommand.COMMAND_WORD:
            return new DeleteExpenseReminderCommandParser().parse(arguments);

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommandParser().parse(arguments);

        case TogglePanelCommand.COMMAND_WORD:
            return new TogglePanelCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
