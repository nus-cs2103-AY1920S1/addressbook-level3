package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LENGTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UPPER;

import java.util.stream.Stream;

import seedu.address.logic.commands.GeneratePasswordCommand;
import seedu.address.logic.commands.GeneratePasswordCommand.PasswordGeneratorDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GeneratePasswordCommand object
 */
public class GeneratePasswordCommandParser implements Parser {

    @Override
    public GeneratePasswordCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_LENGTH, PREFIX_LOWER,
                                                    PREFIX_UPPER, PREFIX_NUM, PREFIX_SPECIAL);
        if (!anyPrefixesPresent(argMultimap, PREFIX_LENGTH, PREFIX_LOWER, PREFIX_UPPER, PREFIX_NUM, PREFIX_SPECIAL)
            && !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                                            GeneratePasswordCommand.MESSAGE_USAGE));
        }

        //returns default settings password generation settings if all empty
        if (!anyPrefixesPresent(argMultimap, PREFIX_LENGTH, PREFIX_LOWER, PREFIX_UPPER, PREFIX_NUM, PREFIX_SPECIAL)) {
            return new GeneratePasswordCommand(PasswordGeneratorDescriptor.getDefaultConfiguration());
        }

        PasswordGeneratorDescriptor description = new PasswordGeneratorDescriptor();
        if (argMultimap.getValue(PREFIX_LENGTH).isPresent()) {
            description.setLength(ParserUtil.parsePasswordLength(argMultimap.getValue(PREFIX_LENGTH).get()));
        }
        if (argMultimap.getValue(PREFIX_LOWER).isPresent()) {
            description.setLower(ParserUtil.parseBool(argMultimap.getValue(PREFIX_LOWER).get()));
        }
        if (argMultimap.getValue(PREFIX_UPPER).isPresent()) {
            description.setUpper(ParserUtil.parseBool(argMultimap.getValue(PREFIX_UPPER).get()));
        }
        if (argMultimap.getValue(PREFIX_NUM).isPresent()) {
            description.setNum(ParserUtil.parseBool(argMultimap.getValue(PREFIX_NUM).get()));
        }
        if (argMultimap.getValue(PREFIX_SPECIAL).isPresent()) {
            description.setSpecial(ParserUtil.parseBool(argMultimap.getValue(PREFIX_SPECIAL).get()));
        }
        return new GeneratePasswordCommand(description);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
