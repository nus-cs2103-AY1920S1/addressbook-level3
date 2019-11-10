package seedu.address.financialtracker.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Messages;
import seedu.address.financialtracker.logic.commands.AddFinCommand;
import seedu.address.financialtracker.logic.commands.ClearCommand;
import seedu.address.financialtracker.logic.commands.DeleteFinCommand;
import seedu.address.financialtracker.logic.commands.EditFinCommand;
import seedu.address.financialtracker.logic.commands.HelpCommand;
import seedu.address.financialtracker.logic.commands.SortFinCommand;
import seedu.address.financialtracker.logic.commands.SummaryCommand;
import seedu.address.financialtracker.logic.commands.SwitchCommand;
import seedu.address.financialtracker.logic.commands.UndoCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GoToCommand;
import seedu.address.logic.parser.GoToParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class FinancialTrackerParser {

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
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case AddFinCommand.COMMAND_WORD:
            return new AddFinCommandParser().parse(arguments);

        case DeleteFinCommand.COMMAND_WORD:
            return new DeleteFinCommandParser().parse(arguments);

        case SummaryCommand.COMMAND_WORD:
            return new SummaryCommand();

        case EditFinCommand.COMMAND_WORD:
            return new EditFinCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SortFinCommand.COMMAND_WORD:
            return new SortFinCommandParser().parse(arguments);

        case SwitchCommand.COMMAND_WORD:
            return new SwitchCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case GoToCommand.COMMAND_WORD:
            return new GoToParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            throw new ParseException("Unknown command, wanna try typing 'help'?");
        }
    }

}
