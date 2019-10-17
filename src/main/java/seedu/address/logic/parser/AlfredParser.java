package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.addcommand.AddMentorCommand;
import seedu.address.logic.commands.addcommand.AddParticipantCommand;
import seedu.address.logic.commands.addcommand.AddTeamCommand;
import seedu.address.logic.commands.deletecommand.DeleteCommand;
import seedu.address.logic.commands.findcommand.FindMentorCommand;
import seedu.address.logic.commands.findcommand.FindParticipantCommand;
import seedu.address.logic.commands.findcommand.FindTeamCommand;
import seedu.address.logic.commands.listcommand.ListCommand;
import seedu.address.logic.commands.undocommand.UndoCommand;
import seedu.address.logic.commands.viewcommand.ViewCommand;
import seedu.address.logic.parser.addcommandparser.AddMentorCommandParser;
import seedu.address.logic.parser.addcommandparser.AddParticipantCommandParser;
import seedu.address.logic.parser.addcommandparser.AddTeamCommandParser;
import seedu.address.logic.parser.deletecommandparser.DeleteCommandAllocator;
import seedu.address.logic.parser.editcommandparser.EditCommandAllocator;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.findcommandparser.FindMentorCommandParser;
import seedu.address.logic.parser.findcommandparser.FindParticipantCommandParser;
import seedu.address.logic.parser.findcommandparser.FindTeamCommandParser;
import seedu.address.logic.parser.listcommandparser.ListCommandParser;
import seedu.address.logic.parser.viewcommandparser.ViewCommandAllocator;

/**
 * Parses user input.
 */
public class AlfredParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final Logger logger = LogsCenter.getLogger(AlfredParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full   input string
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

        logger.info("Finding command type of " + commandWord);
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            logger.info("Add a new person(in old AddressBook...");
            return new AddCommandParser().parse(arguments);
        case AddParticipantCommand.COMMAND_WORD:
            logger.info("Adding a new Participant...");
            return new AddParticipantCommandParser().parse(arguments);

        case AddMentorCommand.COMMAND_WORD:
            logger.info("Adding a new Mentor...");
            return new AddMentorCommandParser().parse(arguments);

        case AddTeamCommand.COMMAND_WORD:
            logger.info("Adding a new Team...");
            return new AddTeamCommandParser().parse(arguments);

        case FindParticipantCommand.COMMAND_WORD:
            return new FindParticipantCommandParser().parse(arguments);

        case FindMentorCommand.COMMAND_WORD:
            return new FindMentorCommandParser().parse(arguments);

        case FindTeamCommand.COMMAND_WORD:
            return new FindTeamCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            logger.info("Deleting an existing Participant...");
            return new DeleteCommandAllocator().getDeleteCommand(arguments);
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandAllocator().getViewCommand(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case EditCommand.COMMAND_WORD:
            logger.info("Editing an existing Entity...");
            return new EditCommandAllocator().getEditCommand(arguments);

        default:
            logger.info("Unknown command type: " + commandWord);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
