package seedu.travezy.financialtracker.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.travezy.financialtracker.commands.AddFinCommand;
import seedu.travezy.financialtracker.commands.Command;
import seedu.travezy.financialtracker.commands.DeleteFinCommand;
import seedu.travezy.financialtracker.commands.ExitCommand;
import seedu.travezy.financialtracker.commands.SummaryCommand;
import seedu.travezy.logic.commands.GoToCommand;
import seedu.travezy.address.logic.commands.HelpCommand;
import seedu.travezy.logic.parser.exceptions.ParseException;
import seedu.travezy.commons.core.Messages;

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

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case AddFinCommand.COMMAND_WORD:
            return new AddFinCommandParser().parse(arguments);

        case DeleteFinCommand.COMMAND_WORD:
            return new DeleteFinCommandParser().parse(arguments);

        case SummaryCommand.COMMAND_WORD:
            return new SummaryCommand();

        case GoToCommand.COMMAND_WORD:
            return new GoToCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
