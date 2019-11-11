package seedu.deliverymans.logic.parser.customer;

import static seedu.deliverymans.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.deliverymans.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.customer.CustomerAddCommand;
import seedu.deliverymans.logic.commands.customer.CustomerDeleteCommand;
import seedu.deliverymans.logic.commands.customer.CustomerEditCommand;
import seedu.deliverymans.logic.commands.customer.CustomerHistoryCommand;
import seedu.deliverymans.logic.commands.customer.CustomerSortCommand;
import seedu.deliverymans.logic.commands.universal.HelpCommand;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * (to be added)
 */
public class CustomerParser {
    /**
     * Used for initial separation of command word and args.
     */
    public static final String COMMAND_WORD = "-customer";
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
        case CustomerAddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case CustomerDeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case CustomerEditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case CustomerHistoryCommand.COMMAND_WORD:
            return new HistoryCommandParser().parse(arguments);

        case CustomerSortCommand.COMMAND_WORD:
            return new CustomerSortCommand(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
