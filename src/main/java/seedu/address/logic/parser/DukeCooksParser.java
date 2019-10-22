package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.dashboard.AddTaskCommand;
import seedu.address.logic.commands.dashboard.DeleteTaskCommand;
import seedu.address.logic.commands.dashboard.EditTaskCommand;
import seedu.address.logic.commands.dashboard.FindTaskCommand;
import seedu.address.logic.commands.diary.AddDiaryCommand;
import seedu.address.logic.commands.diary.AddPageCommand;
import seedu.address.logic.commands.diary.DeleteDiaryCommand;
import seedu.address.logic.commands.diary.DeletePageCommand;
import seedu.address.logic.commands.diary.EditDiaryCommand;
import seedu.address.logic.commands.exercise.AddExerciseCommand;
import seedu.address.logic.commands.exercise.ClearExerciseCommand;
import seedu.address.logic.commands.exercise.DeleteExerciseCommand;
import seedu.address.logic.commands.exercise.EditExerciseCommand;
import seedu.address.logic.commands.exercise.FindExerciseCommand;
import seedu.address.logic.commands.exercise.ListExerciseCommand;
import seedu.address.logic.commands.health.AddHealthCommand;
import seedu.address.logic.commands.profile.AddProfileCommand;
import seedu.address.logic.commands.profile.EditProfileCommand;
import seedu.address.logic.commands.stats.StatisticsCommand;

import seedu.address.logic.parser.dashboard.AddTaskCommandParser;
import seedu.address.logic.parser.dashboard.DeleteTaskCommandParser;
import seedu.address.logic.parser.dashboard.EditTaskCommandParser;
import seedu.address.logic.parser.dashboard.FindTaskCommandParser;
import seedu.address.logic.parser.diary.AddDiaryCommandParser;
import seedu.address.logic.parser.diary.AddPageCommandParser;
import seedu.address.logic.parser.diary.DeleteDiaryCommandParser;
import seedu.address.logic.parser.diary.DeletePageCommandParser;
import seedu.address.logic.parser.diary.EditDiaryCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

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

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
