package dukecooks.logic.parser;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukecooks.logic.commands.DeleteCommand;
import dukecooks.logic.commands.dashboard.DeleteTaskCommand;
import dukecooks.logic.commands.diary.DeleteDiaryCommand;
import dukecooks.logic.commands.diary.DeletePageCommand;
import dukecooks.logic.commands.exercise.DeleteExerciseCommand;
import dukecooks.logic.commands.health.DeleteRecordCommand;
import dukecooks.logic.commands.mealplan.DeleteMealPlanCommand;
import dukecooks.logic.commands.profile.DeleteProfileCommand;
import dukecooks.logic.commands.recipe.DeleteRecipeCommand;
import dukecooks.logic.commands.workout.DeleteWorkoutCommand;
import dukecooks.logic.parser.dashboard.DeleteTaskCommandParser;
import dukecooks.logic.parser.diary.DeleteDiaryCommandParser;
import dukecooks.logic.parser.diary.DeletePageCommandParser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.logic.parser.exercise.DeleteExerciseCommandParser;
import dukecooks.logic.parser.health.DeleteRecordCommandParser;
import dukecooks.logic.parser.mealplan.DeleteMealPlanCommandParser;
import dukecooks.logic.parser.recipe.DeleteRecipeCommandParser;
import dukecooks.logic.parser.workout.DeleteWorkoutCommandParser;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRecipeCommand
     * and returns a DeleteRecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_VARIANT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        final String variant = matcher.group("variant");
        final String arguments = matcher.group("arguments");

        switch (variant) {

        case DeleteTaskCommand.VARIANT_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case DeleteRecipeCommand.VARIANT_WORD:
            return new DeleteRecipeCommandParser().parse(arguments);

        case DeleteMealPlanCommand.VARIANT_WORD:
            return new DeleteMealPlanCommandParser().parse(arguments);

        case DeleteProfileCommand.VARIANT_WORD:
            return new DeleteProfileCommand();

        case DeleteRecordCommand.VARIANT_WORD:
            return new DeleteRecordCommandParser().parse(arguments);

        case DeleteDiaryCommand.VARIANT_WORD:
            return new DeleteDiaryCommandParser().parse(arguments);

        case DeletePageCommand.VARIANT_WORD:
            return new DeletePageCommandParser().parse(arguments);

        case DeleteExerciseCommand.VARIANT_WORD:
            return new DeleteExerciseCommandParser().parse(arguments);

        case DeleteWorkoutCommand.VARIANT_WORD:
            return new DeleteWorkoutCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
