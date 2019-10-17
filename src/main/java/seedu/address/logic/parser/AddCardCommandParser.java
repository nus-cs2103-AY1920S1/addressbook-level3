package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CARDNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CVC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCardCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Card;
import seedu.address.model.card.CardNumber;
import seedu.address.model.card.Cvc;
import seedu.address.model.card.Description;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCardCommand object
 */
public class AddCardCommandParser implements Parser<AddCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCardCommand
     * and returns an AddCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCardCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_CARDNUMBER, PREFIX_CVC, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_CARDNUMBER, PREFIX_CVC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        CardNumber cardNumber = ParserUtil.parseCardNumber(argMultimap.getValue(PREFIX_CARDNUMBER).get());
        Cvc cvc = ParserUtil.parseCvc(argMultimap.getValue(PREFIX_CVC).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Card card = new Card(description, cardNumber, cvc, tagList);

        return new AddCardCommand(card);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
