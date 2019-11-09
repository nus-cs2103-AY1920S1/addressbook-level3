package dukecooks.logic.parser;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dukecooks.logic.commands.CreateCommand;
import dukecooks.logic.commands.diary.CreatePageCommand;
import dukecooks.logic.parser.diary.CreatePageCommandParser;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CreatePageCommand object
 */
public class CreateCommandParser implements Parser<CreatePageCommand> {

    /**
     * Used for initial separation of variant and args.
     */
    private static final Pattern BASIC_VARIANT_FORMAT = Pattern.compile("(?<variant>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the CreateCommand
     * and returns the appropriate CreateCommand-variant object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreatePageCommand parse(String args) throws ParseException {

        final Matcher matcher = BASIC_VARIANT_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
        }

        final String variant = matcher.group("variant");
        final String arguments = matcher.group("arguments");

        switch (variant) {

        case CreatePageCommand.VARIANT_WORD:
            return new CreatePageCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
        }
    }
}
