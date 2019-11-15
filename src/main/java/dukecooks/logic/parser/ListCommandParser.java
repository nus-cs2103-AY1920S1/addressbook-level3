package dukecooks.logic.parser;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukecooks.logic.commands.ListCommand;
import dukecooks.logic.commands.dashboard.ListTaskDoneCommand;
import dukecooks.logic.commands.dashboard.ListTaskNotDoneCommand;
import dukecooks.logic.commands.diary.ListDiaryCommand;
import dukecooks.logic.commands.exercise.ListExerciseCommand;
import dukecooks.logic.commands.health.ListHealthCommand;
import dukecooks.logic.commands.mealplan.ListMealPlanCommand;
import dukecooks.logic.commands.recipe.ListRecipeCommand;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.logic.parser.health.ListRecordCommandParser;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns the appropriate ListCommand-variant object for execution.
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

        case ListTaskDoneCommand.VARIANT_WORD:
            return new ListTaskDoneCommand();

        case ListTaskNotDoneCommand.VARIANT_WORD:
            return new ListTaskNotDoneCommand();

        case ListRecipeCommand.VARIANT_WORD:
            return new ListRecipeCommand();

        case ListMealPlanCommand.VARIANT_WORD:
            return new ListMealPlanCommand();

        case ListHealthCommand.VARIANT_WORD:
            return new ListRecordCommandParser().parse(arguments);

        case ListExerciseCommand.VARIANT_WORD:
            return new ListExerciseCommand();

        case ListDiaryCommand.VARIANT_WORD:
            return new ListDiaryCommand();

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
}
