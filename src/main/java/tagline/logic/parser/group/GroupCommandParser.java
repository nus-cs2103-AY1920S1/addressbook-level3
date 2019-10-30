//@@author e0031374
package tagline.logic.parser.group;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tagline.logic.commands.Command;
import tagline.logic.commands.HelpCommand;
import tagline.logic.commands.group.AddMemberToGroupCommand;
import tagline.logic.commands.group.CreateGroupCommand;
import tagline.logic.commands.group.DeleteGroupCommand;
import tagline.logic.commands.group.FindGroupCommand;
import tagline.logic.commands.group.ListGroupCommand;
import tagline.logic.commands.group.RemoveMemberFromGroupCommand;
import tagline.logic.parser.exceptions.ParseException;

/**
 * Parses user input for group commands.
 */
public class GroupCommandParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?s)(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into group command for execution.
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

        case CreateGroupCommand.COMMAND_WORD:
            return new CreateGroupParser().parse(arguments);

        case AddMemberToGroupCommand.COMMAND_WORD:
            return new AddMemberToGroupParser().parse(arguments);

        case FindGroupCommand.COMMAND_WORD:
            return new FindGroupParser().parse(arguments);

        case RemoveMemberFromGroupCommand.COMMAND_WORD:
            return new RemoveMemberFromGroupParser().parse(arguments);

        case DeleteGroupCommand.COMMAND_WORD:
            return new DeleteGroupParser().parse(arguments);

        case ListGroupCommand.COMMAND_WORD:
            return new ListGroupParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
