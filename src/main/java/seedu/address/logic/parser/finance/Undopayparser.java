package seedu.address.logic.parser.finance;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARYPAID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.finance.Undopay;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new Pay object
 */
public class Undopayparser implements Parser<Undopay> {

    /**
     * Parses the given {@code String} of arguments in the context of the Pay
     * and returns an Pay object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public Undopay parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SALARYPAID);

        Index index;
        double salaryToPay;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            salaryToPay = ParserUtil.parseMoney(argMultimap.getValue(PREFIX_SALARYPAID).get());

        } catch (ParseException pe) {
            throw new ParseException(String.format(("Invalid input. Please call the Pay command in this format:\n"
                    + "undo_pay 1 s/100")));
        }

        return new Undopay(index, salaryToPay);
    }
}


