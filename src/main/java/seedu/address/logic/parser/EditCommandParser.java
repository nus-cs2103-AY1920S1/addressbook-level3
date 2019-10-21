package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.diary.EditDiaryCommand;
import seedu.address.logic.commands.exercise.EditExerciseCommand;
import seedu.address.logic.commands.profile.EditProfileCommand;
import seedu.address.logic.commands.recipe.EditRecipeCommand;
import seedu.address.logic.parser.diary.EditDiaryCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exercise.EditExerciseCommandParser;
import seedu.address.logic.parser.profile.EditProfileCommandParser;
import seedu.address.logic.parser.recipe.EditRecipeCommandParser;

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

        case EditRecipeCommand.VARIANT_WORD:
            return new EditRecipeCommandParser().parse(arguments);

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
