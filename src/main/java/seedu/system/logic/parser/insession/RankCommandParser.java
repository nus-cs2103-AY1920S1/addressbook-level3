package seedu.system.logic.parser.insession;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.system.logic.commands.insession.RankCommand;
import seedu.system.logic.parser.ArgumentMultimap;
import seedu.system.logic.parser.ArgumentTokenizer;
import seedu.system.logic.parser.Parser;
import seedu.system.logic.parser.ParserUtil;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.person.Name;

/**
 * Parses input arguments and creates a new RankCommand object
 */
public class RankCommandParser implements Parser<RankCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RankCommand
     * and returns an RankCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public RankCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RankCommand.MESSAGE_USAGE));
        }

        Name athleteName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        return new RankCommand(athleteName);
    }

}
