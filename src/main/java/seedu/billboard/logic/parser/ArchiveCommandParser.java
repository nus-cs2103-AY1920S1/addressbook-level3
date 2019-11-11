package seedu.billboard.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.billboard.logic.commands.AddArchiveCommand;
import seedu.billboard.logic.commands.ArchiveCommand;
import seedu.billboard.logic.commands.DeleteArchiveCommand;
import seedu.billboard.logic.commands.HelpCommand;
import seedu.billboard.logic.commands.ListArchiveCommand;
import seedu.billboard.logic.commands.ListArchiveNamesCommand;
import seedu.billboard.logic.commands.RevertArchiveCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;

//@@author davidcwh
/**
 * Parses user input regarding Archive commands.
 */
public class ArchiveCommandParser implements Parser<ArchiveCommand> {

    public static final String MESSAGE_ARCHIVE_COMMANDS = "Invalid archive command format! \nCommand format: "
            + "archive (archive command) (parameters if needed)\nSupported archive commands:\n"
            + "add \ndelete \nrevert \nlist\nlistall \nFor more information:\n" + HelpCommand.MESSAGE_USAGE;

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
    @Override
    public ArchiveCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_ARCHIVE_COMMANDS);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case ListArchiveNamesCommand.COMMAND_WORD:
            return new ListArchiveNamesCommand();

        case ListArchiveCommand.COMMAND_WORD:
            return new ListArchiveCommandParser().parse(arguments);

        case AddArchiveCommand.COMMAND_WORD:
            return new AddArchiveCommandParser().parse(arguments);

        case RevertArchiveCommand.COMMAND_WORD:
            return new RevertArchiveCommandParser().parse(arguments);

        case DeleteArchiveCommand.COMMAND_WORD:
            return new DeleteArchiveCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_ARCHIVE_COMMANDS);
        }
    }
}
