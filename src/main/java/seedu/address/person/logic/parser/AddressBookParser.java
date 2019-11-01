package seedu.address.person.logic.parser;

import static seedu.address.person.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.person.commons.core.Messages.MESSAGE_NO_COMMAND;
import static seedu.address.person.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.MissingFormatArgumentException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.person.logic.commands.AddCommand;
import seedu.address.person.logic.commands.Command;
import seedu.address.person.logic.commands.DeleteCommand;
import seedu.address.person.logic.commands.EditCommand;
import seedu.address.person.logic.commands.FindCommand;
import seedu.address.person.logic.commands.ListCommand;
import seedu.address.person.logic.parser.exceptions.ParseException;

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
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        System.out.println("got here.");
        System.out.println(matcher.matches());

        if (!matcher.matches()) {
            String errorMsg;
            try {
                errorMsg = String.format(MESSAGE_INVALID_COMMAND_FORMAT);
            } catch (MissingFormatArgumentException e) {
                throw new ParseException(MESSAGE_NO_COMMAND);
            }
            throw new ParseException(errorMsg);
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

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
