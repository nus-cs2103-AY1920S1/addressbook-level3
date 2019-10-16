package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.StatisticsCommand;
import seedu.address.logic.commands.AddExerciseCommand;
import seedu.address.logic.commands.AddHealthCommand;
import seedu.address.logic.commands.AddProfileCommand;
import seedu.address.logic.commands.ClearExerciseCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteExerciseCommand;
import seedu.address.logic.commands.EditExerciseCommand;
import seedu.address.logic.commands.EditProfileCommand;
import seedu.address.logic.commands.AddDiaryCommand;
import seedu.address.logic.commands.AddPageCommand;
import seedu.address.logic.commands.DeleteDiaryCommand;
import seedu.address.logic.commands.DeletePageCommand;
import seedu.address.logic.commands.EditDiaryCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindExerciseCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListExerciseCommand;
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
