package dukecooks.logic.parser;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukecooks.logic.commands.ViewCommand;
import dukecooks.logic.commands.diary.ViewDiaryCommand;
import dukecooks.logic.commands.exercise.ViewExerciseCommand;
import dukecooks.logic.commands.mealplan.ViewMealPlanCommand;
import dukecooks.logic.commands.workout.ViewWorkoutCommand;
import dukecooks.logic.parser.diary.ViewDiaryCommandParser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.logic.parser.exercise.ViewExerciseCommandParser;
import dukecooks.logic.parser.mealplan.ViewMealPlanCommandParser;
import dukecooks.logic.parser.workout.ViewWorkoutCommandParser;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the ViewDiaryCommand
     * and returns a ViewDiaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_VARIANT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        final String variant = matcher.group("variant");
        final String arguments = matcher.group("arguments");

        switch (variant) {

        case ViewDiaryCommand.VARIANT_WORD:
            return new ViewDiaryCommandParser().parse(arguments);

        case ViewMealPlanCommand.VARIANT_WORD:
            return new ViewMealPlanCommandParser().parse(arguments);

        case ViewWorkoutCommand.VARIANT_WORD:
            return new ViewWorkoutCommandParser().parse(arguments);

        case ViewExerciseCommand.VARIANT_WORD:
            return new ViewExerciseCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }
    }

}
