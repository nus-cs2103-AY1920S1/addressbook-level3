package dukecooks.logic.parser;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukecooks.logic.commands.ClearCommand;
import dukecooks.logic.commands.exercise.ClearExerciseCommand;
import dukecooks.logic.commands.health.ClearRecordCommand;
import dukecooks.logic.commands.mealplan.ClearMealPlanCommand;
import dukecooks.logic.commands.recipe.ClearRecipeCommand;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns the appropriate AddCommand-variant object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_VARIANT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }

        final String variant = matcher.group("variant");
        final String arguments = matcher.group("arguments");

        switch (variant) {

        case ClearRecipeCommand.VARIANT_WORD:
            return new ClearRecipeCommand();

        case ClearMealPlanCommand.VARIANT_WORD:
            return new ClearMealPlanCommand();

        case ClearExerciseCommand.VARIANT_WORD:
            return new ClearExerciseCommand();

        case ClearRecordCommand.VARIANT_WORD:
            return new ClearRecordCommand();

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }
    }
}
