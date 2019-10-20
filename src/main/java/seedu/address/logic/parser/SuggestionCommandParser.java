package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMAND_WORD;

import seedu.address.logic.commands.SuggestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SuggestionCommand object
 */
public class SuggestionCommandParser implements Parser<SuggestionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SuggestionCommand
     * and returns an SuggestionCommand object for execution.
     * @throws seedu.address.logic.parser.exceptions.ParseException if the user input does not conform the expected
     * format
     */
    public SuggestionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMMAND_WORD, PREFIX_ARGUMENTS);

        String originalCommand = argMultimap.getValue(PREFIX_COMMAND_WORD).get();
        String arguments = argMultimap.getValue(PREFIX_ARGUMENTS).get();
        String suggestedCommand = ParserUtil.parseCommand(originalCommand, " " + arguments);
        return new SuggestionCommand(originalCommand, suggestedCommand, arguments);
    }
}
