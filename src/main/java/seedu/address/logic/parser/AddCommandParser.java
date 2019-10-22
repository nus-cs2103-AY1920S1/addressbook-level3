package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.diary.AddDiaryCommand;
import seedu.address.logic.commands.diary.AddPageCommand;
import seedu.address.logic.commands.exercise.AddExerciseCommand;
import seedu.address.logic.commands.health.AddHealthCommand;
import seedu.address.logic.commands.profile.AddProfileCommand;
import seedu.address.logic.commands.recipe.AddRecipeCommand;
import seedu.address.logic.parser.diary.AddDiaryCommandParser;
import seedu.address.logic.parser.diary.AddPageCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exercise.AddExerciseCommandParser;
import seedu.address.logic.parser.health.AddHealthCommandParser;
import seedu.address.logic.parser.profile.AddProfileCommandParser;
import seedu.address.logic.parser.recipe.AddRecipeCommandParser;

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

        case AddRecipeCommand.VARIANT_WORD:
            return new AddRecipeCommandParser().parse(arguments);

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
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }
}
