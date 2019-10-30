package dukecooks.logic.parser;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukecooks.logic.commands.FindCommand;
import dukecooks.logic.commands.dashboard.FindTaskCommand;
import dukecooks.logic.commands.diary.FindDiaryCommand;
import dukecooks.logic.commands.exercise.FindExerciseByIntensityCommand;
import dukecooks.logic.commands.exercise.FindExerciseByMuscleCommand;
import dukecooks.logic.commands.exercise.FindExerciseCommand;
import dukecooks.logic.commands.recipe.FindRecipeCommand;
import dukecooks.logic.parser.dashboard.FindTaskCommandParser;
import dukecooks.logic.parser.diary.FindDiaryCommandParser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.logic.parser.exercise.FindExerciseByIntensityCommandParser;
import dukecooks.logic.parser.exercise.FindExerciseByMuscleCommandParser;
import dukecooks.logic.parser.exercise.FindExerciseCommandParser;
import dukecooks.logic.parser.recipe.FindRecipeCommandParser;

/**
 * Parses input arguments and creates a new FindRecipeCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the FindRecipeCommand
     * and returns a FindRecipeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_VARIANT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        final String variant = matcher.group("variant");
        final String arguments = matcher.group("arguments");

        switch (variant) {

        case FindTaskCommand.VARIANT_WORD:
            return new FindTaskCommandParser().parse(arguments);

        case FindRecipeCommand.VARIANT_WORD:
            return new FindRecipeCommandParser().parse(arguments);

        case FindExerciseCommand.VARIANT_WORD:
            return new FindExerciseCommandParser().parse(arguments);

        case FindExerciseByIntensityCommand.VARIANT_WORD:
            return new FindExerciseByIntensityCommandParser().parse(arguments);

        case FindExerciseByMuscleCommand.VARIANT_WORD:
            return new FindExerciseByMuscleCommandParser().parse(arguments);

        case FindDiaryCommand.VARIANT_WORD:
            return new FindDiaryCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
