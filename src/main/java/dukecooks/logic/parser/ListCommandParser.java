package dukecooks.logic.parser;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukecooks.logic.commands.ListCommand;
import dukecooks.logic.commands.dashboard.ListTaskCommand;
import dukecooks.logic.commands.dashboard.ListTaskDoneCommand;
import dukecooks.logic.commands.dashboard.ListTaskNotDoneCommand;
import dukecooks.logic.commands.diary.ListDiaryCommand;
import dukecooks.logic.commands.exercise.ListExerciseCommand;
import dukecooks.logic.commands.health.ListHealthCommand;
import dukecooks.logic.commands.mealplan.ListMealPlanCommand;
import dukecooks.logic.commands.recipe.ListRecipeCommand;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddRecipeCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns the appropriate AddCommand-variant object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_VARIANT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        final String variant = matcher.group("variant");
        final String arguments = matcher.group("arguments");

        switch (variant) {

        case ListTaskCommand.VARIANT_WORD:
            return new ListTaskCommand();

        case ListTaskDoneCommand.VARIANT_WORD:
            return new ListTaskDoneCommand();

        case ListTaskNotDoneCommand.VARIANT_WORD:
            return new ListTaskNotDoneCommand();

        case ListRecipeCommand.VARIANT_WORD:
            return new ListRecipeCommand();

        case ListMealPlanCommand.VARIANT_WORD:
            return new ListMealPlanCommand();

        case ListHealthCommand.VARIANT_WORD:
            return new ListHealthCommand();

        case ListExerciseCommand.VARIANT_WORD:
            return new ListExerciseCommand();

        case ListDiaryCommand.VARIANT_WORD:
            return new ListDiaryCommand();

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
}
