package dukecooks.logic.parser;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukecooks.logic.commands.GotoCommand;
import dukecooks.logic.commands.dashboard.GotoTaskCommand;
import dukecooks.logic.commands.diary.GotoDiaryCommand;
import dukecooks.logic.commands.exercise.GotoExerciseCommand;
import dukecooks.logic.commands.health.GotoHealthCommand;
import dukecooks.logic.commands.mealplan.GotoMealPlanCommand;
import dukecooks.logic.commands.recipe.GotoRecipeCommand;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GotoCommand object
 */
public class GotoCommandParser implements Parser<GotoCommand> {

    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)");

    /**
     * Parses the given {@code String} of arguments in the context of the GotoCommand
     * and returns the appropriate GotoCommand-variant object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GotoCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_VARIANT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GotoCommand.MESSAGE_USAGE));
        }

        final String variant = matcher.group("variant");

        switch (variant) {

        case GotoTaskCommand.VARIANT_WORD:
            return new GotoTaskCommand();

        case GotoRecipeCommand.VARIANT_WORD:
            return new GotoRecipeCommand();

        case GotoMealPlanCommand.VARIANT_WORD:
            return new GotoMealPlanCommand();

        case GotoExerciseCommand.VARIANT_WORD:
            return new GotoExerciseCommand();

        case GotoHealthCommand.VARIANT_WORD:
            return new GotoHealthCommand();

        case GotoDiaryCommand.VARIANT_WORD:
            return new GotoDiaryCommand();

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GotoCommand.MESSAGE_USAGE));
        }
    }
}
