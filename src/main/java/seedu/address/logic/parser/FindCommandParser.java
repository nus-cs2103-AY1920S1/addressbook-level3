package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.exercise.FindExerciseByIntensityCommand;
import seedu.address.logic.commands.exercise.FindExerciseByMuscleCommand;
import seedu.address.logic.commands.exercise.FindExerciseCommand;
import seedu.address.logic.commands.recipe.FindRecipeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exercise.FindExerciseByIntensityCommandParser;
import seedu.address.logic.parser.exercise.FindExerciseByMuscleCommandParser;
import seedu.address.logic.parser.exercise.FindExerciseCommandParser;
import seedu.address.logic.parser.recipe.FindRecipeCommandParser;

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

        case FindRecipeCommand.VARIANT_WORD:
            return new FindRecipeCommandParser().parse(arguments);

        case FindExerciseCommand.VARIANT_WORD:
            return new FindExerciseCommandParser().parse(arguments);

        case FindExerciseByIntensityCommand.VARIANT_WORD:
            return new FindExerciseByIntensityCommandParser().parse(arguments);

        case FindExerciseByMuscleCommand.VARIANT_WORD:
            return new FindExerciseByMuscleCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
