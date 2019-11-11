package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_EXCESS_CARD_FIELDS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CVC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReadCardCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Cvc;
import seedu.address.model.card.Description;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ReadCardCommandParser implements Parser<ReadCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReadCardCommand
     * and returns an ReadCardCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReadCardCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CVC, PREFIX_DESCRIPTION);

        // runs if no cvc is provided
        if (!arePrefixesPresent(argMultimap, PREFIX_CVC)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReadCardCommand.MESSAGE_USAGE));
        }

        // runs if description and preamble are both present
        if (arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION)
                && !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_EXCESS_CARD_FIELDS, ReadCardCommand.MESSAGE_USAGE));
        }
        // runs if description and preamble are both absent
        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION)
                && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReadCardCommand.MESSAGE_USAGE));
        }

        Cvc cvc = ParserUtil.parseCvc(argMultimap.getValue(PREFIX_CVC).get());
        if (arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION)) {
            Description description = ParserUtil.parseCardDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            return new ReadCardCommand(cvc, description);
        } else {
            try {
                Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
                return new ReadCardCommand(cvc, index);
            } catch (ParseException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReadCardCommand.MESSAGE_USAGE));
            }
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
