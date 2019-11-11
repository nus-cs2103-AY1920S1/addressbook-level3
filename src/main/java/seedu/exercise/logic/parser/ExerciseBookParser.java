package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.ClearCommand;
import seedu.exercise.logic.commands.Command;
import seedu.exercise.logic.commands.CustomCommand;
import seedu.exercise.logic.commands.DeleteExerciseCommand;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.logic.commands.ExitCommand;
import seedu.exercise.logic.commands.HelpCommand;
import seedu.exercise.logic.commands.ListCommand;
import seedu.exercise.logic.commands.RedoCommand;
import seedu.exercise.logic.commands.ResolveCommand;
import seedu.exercise.logic.commands.ScheduleCommand;
import seedu.exercise.logic.commands.SelectCommand;
import seedu.exercise.logic.commands.SuggestCommand;
import seedu.exercise.logic.commands.UndoCommand;
import seedu.exercise.logic.commands.ViewCustomCommand;
import seedu.exercise.logic.commands.statistic.StatsCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ExerciseBookParser {

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

        case AddExerciseCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteExerciseCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ScheduleCommand.COMMAND_WORD:
            return new ScheduleCommandParser().parse(arguments);

        case CustomCommand.COMMAND_WORD:
            return new CustomCommandParser().parse(arguments);

        case ViewCustomCommand.COMMAND_WORD:
            return new ViewCustomCommand();

        case SuggestCommand.COMMAND_WORD:
            return new SuggestCommandParser().parse(arguments);

        case StatsCommand.COMMAND_WORD:
            return new StatsCommandParser().parse(arguments);

        case ResolveCommand.COMMAND_WORD:
            return new ResolveCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
