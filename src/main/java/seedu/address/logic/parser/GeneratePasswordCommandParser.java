package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LENGTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UPPER;

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

        try {
            PasswordGeneratorDescriptor description = new PasswordGeneratorDescriptor();
            if (argMultimap.getValue(PREFIX_LENGTH).isPresent()) {
                description.setLength(Integer.parseInt(argMultimap.getValue(PREFIX_LENGTH).get()));
            }
            if (argMultimap.getValue(PREFIX_LOWER).isPresent()) {
                description.setLower(Boolean.valueOf(argMultimap.getValue(PREFIX_LOWER).get()));
            }
            if (argMultimap.getValue(PREFIX_UPPER).isPresent()) {
                description.setUpper(Boolean.valueOf(argMultimap.getValue(PREFIX_UPPER).get()));
            }
            if (argMultimap.getValue(PREFIX_NUM).isPresent()) {
                description.setNum(Boolean.valueOf(argMultimap.getValue(PREFIX_NUM).get()));
            }
            if (argMultimap.getValue(PREFIX_SPECIAL).isPresent()) {
                description.setSpecial(Boolean.valueOf(argMultimap.getValue(PREFIX_SPECIAL).get()));
            }
            if (!description.isAnyFieldChecked()) {
                throw new ParseException(GeneratePasswordCommand.MESSAGE_REQUIRE_CHECK_AT_LEAST_ONE + "\n"
                                            + GeneratePasswordCommand.MESSAGE_USAGE);
            }

            return new GeneratePasswordCommand(description.getLength(), description.getLower(), description.getUpper(),
                    description.getNum(), description.getSpecial());
        } catch (NumberFormatException e) { //check if length is an integer, and booleans are passed to other prefixes.
            throw new ParseException(GeneratePasswordCommand.MESSAGE_REQUIRE_INTEGER_LENGTH + "\n"
                    + GeneratePasswordCommand.MESSAGE_USAGE);
        }
    }
}
