package seedu.ichifund.logic.parser;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ichifund.logic.commands.AddCommand;
import seedu.ichifund.logic.commands.ClearCommand;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.DeleteCommand;
import seedu.ichifund.logic.commands.EditCommand;
import seedu.ichifund.logic.commands.ExitCommand;
import seedu.ichifund.logic.commands.FindCommand;
import seedu.ichifund.logic.commands.HelpCommand;
import seedu.ichifund.logic.commands.ListCommand;
import seedu.ichifund.logic.commands.budget.AddBudgetCommand;
import seedu.ichifund.logic.commands.budget.DeleteBudgetCommand;
import seedu.ichifund.logic.commands.repeater.AddRepeaterCommand;
import seedu.ichifund.logic.commands.transaction.AddTransactionCommand;
import seedu.ichifund.logic.commands.transaction.FilterTransactionCommand;
import seedu.ichifund.logic.parser.budget.AddBudgetCommandParser;
import seedu.ichifund.logic.parser.budget.DeleteBudgetCommandParser;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.logic.parser.repeater.AddRepeaterCommandParser;
import seedu.ichifund.logic.parser.transaction.AddTransactionCommandParser;
import seedu.ichifund.logic.parser.transaction.FilterTransactionCommandParser;

/**
 * Parses user input.
 */
public class IchiFundParser {

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
    public Command parseCommand(String userInput) throws ParseException {
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

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddTransactionCommand.COMMAND_WORD:
            return new AddTransactionCommandParser().parse(arguments);

        case FilterTransactionCommand.COMMAND_WORD:
            return new FilterTransactionCommandParser().parse(arguments);

        case AddRepeaterCommand.COMMAND_WORD:
            return new AddRepeaterCommandParser().parse(arguments);

        case AddBudgetCommand.COMMAND_WORD:
            return new AddBudgetCommandParser().parse(arguments);

        case DeleteBudgetCommand.COMMAND_WORD:
            return new DeleteBudgetCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

    }

}
