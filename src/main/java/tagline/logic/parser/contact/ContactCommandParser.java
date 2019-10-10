package tagline.logic.parser.contact;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tagline.logic.commands.Command;
import tagline.logic.commands.HelpCommand;
import tagline.logic.commands.contact.AddContactCommand;
import tagline.logic.commands.contact.ClearContactCommand;
import tagline.logic.commands.contact.DeleteContactCommand;
import tagline.logic.commands.contact.EditContactCommand;
import tagline.logic.commands.contact.FindContactCommand;
import tagline.logic.commands.contact.ListContactCommand;
import tagline.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ContactCommandParser {

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
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.stripLeading());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddContactCommand.COMMAND_WORD:
            return new AddContactParser().parse(arguments);

        case EditContactCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteContactCommand.COMMAND_WORD:
            return new DeleteContactParser().parse(arguments);

        case ClearContactCommand.COMMAND_WORD:
            return new ClearContactCommand();

        case FindContactCommand.COMMAND_WORD:
            return new FindContactParser().parse(arguments);

        case ListContactCommand.COMMAND_WORD:
            return new ListContactCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
