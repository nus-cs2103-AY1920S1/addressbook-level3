package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STRONG;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AnalysePasswordCommand;
import seedu.address.logic.commands.AnalyseStrongPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AnalysePasswordCommand object
 */
public class AnalysePasswordCommandParser implements Parser {

    /**
     * Parses the given {@code String} of arguments in the context of the AnalysePasswordCommand
     * and returns a AnalysePasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AnalysePasswordCommand parse(String userInput) throws ParseException {
        if (userInput.equals("")) {
            return new AnalysePasswordCommand();
        }
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_STRONG);

        if (argMultimap.getAllValues(PREFIX_STRONG).size() > 1) {
            throw new ParseException(String.format(MESSAGE_DUPLICATE_FIELDS, AnalysePasswordCommand.MESSAGE_USAGE));
        }

        if (!isPrefixPresent(argMultimap, PREFIX_STRONG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                AnalysePasswordCommand.MESSAGE_USAGE));
        }
        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STRONG).get());
            return new AnalyseStrongPasswordCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AnalysePasswordCommand.MESSAGE_USAGE), pe);
        }

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
