package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.ExitCommand;
import seedu.planner.logic.commands.FindCommand;
import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.LoadCommand;
import seedu.planner.logic.commands.NewCommand;
import seedu.planner.logic.commands.OptimiseCommand;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.SetCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.logic.commands.addcommand.AddCommand;
import seedu.planner.logic.commands.deletecommand.DeleteCommand;
import seedu.planner.logic.commands.editcommand.EditCommand;
import seedu.planner.logic.commands.listcommand.ListCommand;
import seedu.planner.logic.commands.schedulecommand.AutoScheduleCommand;
import seedu.planner.logic.commands.schedulecommand.ScheduleCommand;
import seedu.planner.logic.commands.schedulecommand.UnscheduleCommand;
import seedu.planner.logic.commands.viewcommand.ViewCommand;
import seedu.planner.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class PlannerParser {

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
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

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand(false);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommandParser().parse(arguments);

        case UnscheduleCommand.COMMAND_WORD:
            return new UnscheduleCommandParser().parse(arguments);

        case SetCommand.COMMAND_WORD:
            return new SetCommandParser().parse(arguments);

        case AutoScheduleCommand.COMMAND_WORD:
            return new AutoScheduleCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case NewCommand.COMMAND_WORD:
            return new NewCommandParser().parse(arguments);

        case LoadCommand.COMMAND_WORD:
            return new LoadCommandParser().parse(arguments);

        case OptimiseCommand.COMMAND_WORD:
            return new OptimiseBudgetCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
