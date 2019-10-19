package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.diary.AddDiaryCommand;
import seedu.address.logic.commands.diary.AddPageCommand;
import seedu.address.logic.commands.diary.DeleteDiaryCommand;
import seedu.address.logic.commands.diary.DeletePageCommand;
import seedu.address.logic.commands.diary.EditDiaryCommand;
import seedu.address.logic.commands.exercise.AddExerciseCommand;
import seedu.address.logic.commands.exercise.ClearExerciseCommand;
import seedu.address.logic.commands.exercise.DeleteExerciseCommand;
import seedu.address.logic.commands.exercise.EditExerciseCommand;
import seedu.address.logic.commands.exercise.FindExerciseByIntensityCommand;
import seedu.address.logic.commands.exercise.FindExerciseByMuscleCommand;
import seedu.address.logic.commands.exercise.FindExerciseCommand;
import seedu.address.logic.commands.exercise.ListExerciseCommand;
import seedu.address.logic.commands.health.AddHealthCommand;
import seedu.address.logic.commands.profile.AddProfileCommand;
import seedu.address.logic.commands.profile.EditProfileCommand;
import seedu.address.logic.commands.stats.StatisticsCommand;

import seedu.address.logic.parser.diary.AddDiaryCommandParser;
import seedu.address.logic.parser.diary.AddPageCommandParser;
import seedu.address.logic.parser.diary.DeleteDiaryCommandParser;
import seedu.address.logic.parser.diary.DeletePageCommandParser;
import seedu.address.logic.parser.diary.EditDiaryCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exercise.AddExerciseCommandParser;
import seedu.address.logic.parser.exercise.DeleteExerciseCommandParser;
import seedu.address.logic.parser.exercise.EditExerciseCommandParser;
import seedu.address.logic.parser.exercise.FindExerciseByIntensityCommandParser;
import seedu.address.logic.parser.exercise.FindExerciseByMuscleCommandParser;
import seedu.address.logic.parser.exercise.FindExerciseCommandParser;
import seedu.address.logic.parser.health.AddHealthCommandParser;
import seedu.address.logic.parser.profile.AddProfileCommandParser;
import seedu.address.logic.parser.profile.EditProfileCommandParser;

/**
 * Parses user input.
 */
public class DukeCooksParser {

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
            return new AddExerciseCommandParser().parse(arguments);

        case EditExerciseCommand.COMMAND_WORD:
            return new EditExerciseCommandParser().parse(arguments);

        case AddProfileCommand.COMMAND_WORD:
            return new AddProfileCommandParser().parse(arguments);

        case DeleteExerciseCommand.COMMAND_WORD:
            return new DeleteExerciseCommandParser().parse(arguments);

        case EditProfileCommand.COMMAND_WORD:
            return new EditProfileCommandParser().parse(arguments);

        case ClearExerciseCommand.COMMAND_WORD:
            return new ClearExerciseCommand();

        case FindExerciseCommand.COMMAND_WORD:
            return new FindExerciseCommandParser().parse(arguments);

        case FindExerciseByMuscleCommand.COMMAND_WORD:
            return new FindExerciseByMuscleCommandParser().parse(arguments);

        case FindExerciseByIntensityCommand.COMMAND_WORD:
            return new FindExerciseByIntensityCommandParser().parse(arguments);

        case AddHealthCommand.COMMAND_WORD:
            return new AddHealthCommandParser().parse(arguments);

        case ListExerciseCommand.COMMAND_WORD:
            return new ListExerciseCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommand();

        case AddDiaryCommand.COMMAND_WORD:
            return new AddDiaryCommandParser().parse(arguments);

        case EditDiaryCommand.COMMAND_WORD:
            return new EditDiaryCommandParser().parse(arguments);

        case DeleteDiaryCommand.COMMAND_WORD:
            return new DeleteDiaryCommandParser().parse(arguments);

        case AddPageCommand.COMMAND_WORD:
            return new AddPageCommandParser().parse(arguments);

        case DeletePageCommand.COMMAND_WORD:
            return new DeletePageCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
