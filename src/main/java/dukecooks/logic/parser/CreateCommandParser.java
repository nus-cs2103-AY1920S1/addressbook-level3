package dukecooks.logic.parser;

import dukecooks.logic.commands.CreateCommand;
import dukecooks.logic.commands.PushCommand;
import dukecooks.logic.commands.diary.CreatePageCommand;
import dukecooks.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new CreateCommand object
 */
public class CreateCommandParser implements Parser<CreateCommand> {


    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the CreateCommand
     * and returns the appropriate CreateCommand-variant object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateCommand parse(String args) throws ParseException {

        final Matcher matcher = BASIC_VARIANT_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PushCommand.MESSAGE_USAGE));
        }

        final String variant = matcher.group("variant");
        final String arguments = matcher.group("arguments");

        switch (variant) {

        case CreatePageCommand.VARIANT_WORD:
            return new CreatePageCommand();

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PushCommand.MESSAGE_USAGE));
        }
    }
}

