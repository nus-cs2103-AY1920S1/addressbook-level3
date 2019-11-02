package dukecooks.logic.parser;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukecooks.logic.commands.PushCommand;
import dukecooks.logic.commands.exercise.PushExerciseCommand;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.logic.parser.exercise.PushExerciseCommandParser;

/**
 * Parses input arguments and creates a new PushCommand object
 */
public class PushCommandParser implements Parser<PushCommand> {


    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the PushCommand
     * and returns the appropriate PushCommand-variant object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PushCommand parse(String args) throws ParseException {
        final Matcher matcher = BASIC_VARIANT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PushCommand.MESSAGE_USAGE));
        }

        final String variant = matcher.group("variant");
        final String arguments = matcher.group("arguments");

        switch (variant) {

        case PushExerciseCommand.VARIANT_WORD:
            return new PushExerciseCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PushCommand.MESSAGE_USAGE));
        }
    }
}

