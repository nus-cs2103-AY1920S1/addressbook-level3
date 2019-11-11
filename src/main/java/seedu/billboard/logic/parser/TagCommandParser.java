package seedu.billboard.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.billboard.logic.commands.AddTagCommand;
import seedu.billboard.logic.commands.FilterTagCommand;
import seedu.billboard.logic.commands.HelpCommand;
import seedu.billboard.logic.commands.ListTagCommand;
import seedu.billboard.logic.commands.RemoveTagCommand;
import seedu.billboard.logic.commands.TagCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;

//@@author waifonglee
/**
 * Parses user input.
 */
public class TagCommandParser implements Parser<TagCommand> {

    public static final String MESSAGE_TAG_COMMANDS = "Invalid command format! \n"
            + "Command format: tag (tag command) (parameters)" + "\nSupported tag commands:\n"
            + "add \nrm \nlist \nfilter\nFor more information:\n" + HelpCommand.MESSAGE_USAGE;

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
    public TagCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_TAG_COMMANDS);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case AddTagCommand.COMMAND_WORD:
            return new AddTagCommandParser().parse(arguments);
        case FilterTagCommand.COMMAND_WORD:
            return new FilterTagCommandParser().parse(arguments);
        case RemoveTagCommand.COMMAND_WORD:
            return new RemoveTagCommandParser().parse(arguments);
        case ListTagCommand.COMMAND_WORD:
            return new ListTagCommand();
        default:
            throw new ParseException(MESSAGE_TAG_COMMANDS);
        }

    }
}
