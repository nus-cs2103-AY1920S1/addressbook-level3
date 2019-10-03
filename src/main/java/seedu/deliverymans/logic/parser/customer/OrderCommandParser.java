package seedu.deliverymans.logic.parser.customer;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_ORDER;

import seedu.deliverymans.logic.commands.customer.OrderCommand;
import seedu.deliverymans.logic.parser.ArgumentMultimap;
import seedu.deliverymans.logic.parser.ArgumentTokenizer;
import seedu.deliverymans.logic.parser.Parser;
import seedu.deliverymans.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code OrderCommand} object
 */
public class OrderCommandParser implements Parser<OrderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code OrderCommand}
     * and returns a {@code OrderCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public OrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ORDER);

        try {
        } catch (Exception ive) {
            throw new ParseException("");
        }

        return new OrderCommand();
    }
}
