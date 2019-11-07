package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HomeCommand;
import seedu.address.logic.commands.addcommand.AddCommand;
import seedu.address.logic.commands.assigncommand.AssignCommand;
import seedu.address.logic.commands.csvcommand.ExportCommand;
import seedu.address.logic.commands.csvcommand.ImportCommand;
import seedu.address.logic.commands.deletecommand.DeleteCommand;
import seedu.address.logic.commands.findcommand.FindCommand;
import seedu.address.logic.commands.historycommand.HistoryCommand;
import seedu.address.logic.commands.historycommand.RedoCommand;
import seedu.address.logic.commands.historycommand.UndoCommand;
import seedu.address.logic.commands.leaderboardcommand.SimpleLeaderboardCommand;
import seedu.address.logic.commands.listcommand.ListCommand;
import seedu.address.logic.commands.removecommand.RemoveCommand;
import seedu.address.logic.commands.scorecommand.ScoreCommand;
import seedu.address.logic.commands.topteamscommand.SimpleTopTeamsCommand;
import seedu.address.logic.commands.viewcommand.ViewCommand;
import seedu.address.logic.parser.addcommandparser.AddCommandAllocator;
import seedu.address.logic.parser.assigncommandparser.AssignCommandAllocator;
import seedu.address.logic.parser.csvcommandparser.ExportCommandParser;
import seedu.address.logic.parser.csvcommandparser.ImportCommandParser;
import seedu.address.logic.parser.deletecommandparser.DeleteCommandAllocator;
import seedu.address.logic.parser.editcommandparser.EditCommandAllocator;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.findcommandparser.FindCommandAllocator;
import seedu.address.logic.parser.historycommandparser.HistoryCommandParser;
import seedu.address.logic.parser.historycommandparser.RedoCommandParser;
import seedu.address.logic.parser.historycommandparser.UndoCommandParser;
import seedu.address.logic.parser.listcommandparser.ListCommandParser;
import seedu.address.logic.parser.removecommandparser.RemoveCommandAllocator;
import seedu.address.logic.parser.scorecommandparser.ScoreCommandAllocator;
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
     * @param userInput full input string
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
        Command c;
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            logger.info("Allocating add command to appropriate parser.");
            c = new AddCommandAllocator().allocate(arguments);
            break;

        case FindCommand.COMMAND_WORD:
            c = new FindCommandAllocator().allocate(arguments);
            break;

        case DeleteCommand.COMMAND_WORD:
            logger.info("Allocating delete command to appropriate parser.");
            c = new DeleteCommandAllocator().allocate(arguments);
            break;

        case ScoreCommand.COMMAND_WORD:
            c = new ScoreCommandAllocator().allocate(arguments);
            break;

        case ListCommand.COMMAND_WORD:
            logger.info("Showing list of a particular entity...");
            c = new ListCommandParser().parse(arguments);
            break;

        case SimpleLeaderboardCommand.COMMAND_WORD:
            logger.info("Executing leaderboard command...");
            c = new LeaderboardCommandParser().parse(arguments);
            break;

        case SimpleTopTeamsCommand.COMMAND_WORD:
            c = new TopTeamsCommandParser().parse(arguments);
            break;

        case ViewCommand.COMMAND_WORD:
            c = new ViewCommandAllocator().allocate(arguments);
            break;

        case ExitCommand.COMMAND_WORD:
            c = new ExitCommand();
            break;

        case HelpCommand.COMMAND_WORD:
            c = new HelpCommand();
            break;

        case ImportCommand.COMMAND_WORD:
            c = new ImportCommandParser().parse(arguments);
            break;

        case ExportCommand.COMMAND_WORD:
            c = new ExportCommandParser().parse(arguments);
            break;

        case UndoCommand.COMMAND_WORD:
            c = new UndoCommandParser().parse(arguments);
            break;

        case RedoCommand.COMMAND_WORD:
            c = new RedoCommandParser().parse(arguments);
            break;

        case HistoryCommand.COMMAND_WORD:
            c = new HistoryCommandParser().parse(arguments);
            break;

        case EditCommand.COMMAND_WORD:
            logger.info("Editing an existing Entity...");
            c = new EditCommandAllocator().allocate(arguments);
            break;

        case AssignCommand.COMMAND_WORD:
            logger.info("Assigning Entity(Mentor/Participant) to a Team");
            c = new AssignCommandAllocator().allocate(arguments);
            break;

        case RemoveCommand.COMMAND_WORD:
            logger.info("Removing Entity(Mentor/Participant) from a Team");
            c = new RemoveCommandAllocator().allocate(arguments);
            break;

        case HomeCommand.COMMAND_WORD:
            return new HomeCommand();
        default:
            logger.info("Unknown command type: " + commandWord);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
        c.setCommandInputString(userInput);
        return c;
    }
}
