package dukecooks.logic.parser;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.dashboard.AddTaskCommand;
import dukecooks.logic.commands.diary.AddDiaryCommand;
import dukecooks.logic.commands.diary.AddPageCommand;
import dukecooks.logic.commands.exercise.AddExerciseCommand;
import dukecooks.logic.commands.health.AddHealthCommand;
import dukecooks.logic.commands.mealplan.AddMealPlanCommand;
import dukecooks.logic.commands.profile.AddProfileCommand;
import dukecooks.logic.commands.recipe.AddRecipeCommand;
import dukecooks.logic.commands.workout.AddWorkoutCommand;
import dukecooks.logic.parser.dashboard.AddTaskCommandParser;
import dukecooks.logic.parser.diary.AddDiaryCommandParser;
import dukecooks.logic.parser.diary.AddPageCommandParser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.logic.parser.exercise.AddExerciseCommandParser;
import dukecooks.logic.parser.health.AddHealthCommandParser;
import dukecooks.logic.parser.mealplan.AddMealPlanCommandParser;
import dukecooks.logic.parser.profile.AddProfileCommandParser;
import dukecooks.logic.parser.recipe.AddRecipeCommandParser;
import dukecooks.logic.parser.workout.AddWorkoutCommandParser;

/**
 * Parses input arguments and creates a new AddRecipeCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns the appropriate AddCommand-variant object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_VARIANT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        final String variant = matcher.group("variant");
        final String arguments = matcher.group("arguments");

        switch (variant) {

        case AddTaskCommand.VARIANT_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case AddRecipeCommand.VARIANT_WORD:
            return new AddRecipeCommandParser().parse(arguments);

        case AddMealPlanCommand.VARIANT_WORD:
            return new AddMealPlanCommandParser().parse(arguments);

        case AddDiaryCommand.VARIANT_WORD:
            return new AddDiaryCommandParser().parse(arguments);

        case AddPageCommand.VARIANT_WORD:
            return new AddPageCommandParser().parse(arguments);

        case AddExerciseCommand.VARIANT_WORD:
            return new AddExerciseCommandParser().parse(arguments);

        case AddHealthCommand.VARIANT_WORD:
            return new AddHealthCommandParser().parse(arguments);

        case AddProfileCommand.VARIANT_WORD:
            return new AddProfileCommandParser().parse(arguments);

        case AddWorkoutCommand.VARIANT_WORD:
            return new AddWorkoutCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }
}
