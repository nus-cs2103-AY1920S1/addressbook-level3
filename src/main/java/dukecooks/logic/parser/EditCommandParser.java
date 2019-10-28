package dukecooks.logic.parser;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukecooks.logic.commands.EditCommand;
import dukecooks.logic.commands.dashboard.EditTaskCommand;
import dukecooks.logic.commands.diary.EditDiaryCommand;
import dukecooks.logic.commands.exercise.EditExerciseCommand;
import dukecooks.logic.commands.health.EditRecordCommand;
import dukecooks.logic.commands.mealplan.EditMealPlanCommand;
import dukecooks.logic.commands.profile.EditProfileCommand;
import dukecooks.logic.commands.recipe.EditRecipeCommand;
import dukecooks.logic.parser.dashboard.EditTaskCommandParser;
import dukecooks.logic.parser.diary.EditDiaryCommandParser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.logic.parser.exercise.EditExerciseCommandParser;
import dukecooks.logic.parser.health.EditRecordCommandParser;
import dukecooks.logic.parser.mealplan.EditMealPlanCommandParser;
import dukecooks.logic.parser.profile.EditProfileCommandParser;
import dukecooks.logic.parser.recipe.EditRecipeCommandParser;

/**
 * Parses input arguments and creates a new EditRecipeCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the EditRecipeCommand
     * and returns an EditRecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_VARIANT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        final String variant = matcher.group("variant");
        final String arguments = matcher.group("arguments");

        switch (variant) {

        case EditTaskCommand.VARIANT_WORD:
            return new EditTaskCommandParser().parse(arguments);

        case EditRecipeCommand.VARIANT_WORD:
            return new EditRecipeCommandParser().parse(arguments);

        case EditMealPlanCommand.VARIANT_WORD:
            return new EditMealPlanCommandParser().parse(arguments);

        case EditRecordCommand.VARIANT_WORD:
            return new EditRecordCommandParser().parse(arguments);

        case EditDiaryCommand.VARIANT_WORD:
            return new EditDiaryCommandParser().parse(arguments);

        case EditExerciseCommand.VARIANT_WORD:
            return new EditExerciseCommandParser().parse(arguments);

        case EditProfileCommand.VARIANT_WORD:
            return new EditProfileCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
    }
}
