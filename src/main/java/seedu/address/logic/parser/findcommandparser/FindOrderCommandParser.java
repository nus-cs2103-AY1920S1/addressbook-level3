package seedu.address.logic.parser.findcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.findcommand.FindOrderCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.Order;
import seedu.address.model.order.predicates.CustomerContainsKeywordsPredicate;
import seedu.address.model.order.predicates.IdContainsKeywordsPredicate;
import seedu.address.model.order.predicates.OrderTagContainsKeywordsPredicate;
import seedu.address.model.order.predicates.PhoneContainsKeywordsPredicate;
import seedu.address.model.order.predicates.PriceContainsKeywordsPredicate;
import seedu.address.model.order.predicates.StatusContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindOrderCommand object
 */
public class FindOrderCommandParser implements Parser<FindOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindOrderCommand
     * and returns a FindOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindOrderCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PHONE, PREFIX_CUSTOMER, PREFIX_PRICE, PREFIX_TAG);

        //dummy predicate
        Predicate<Order> predicate = x -> true;

        if (!argMultimap.getValue(PREFIX_PHONE).isPresent()
                && !argMultimap.getValue(PREFIX_CUSTOMER).isPresent()
                && !argMultimap.getValue(PREFIX_PRICE).isPresent()
                && !argMultimap.getValue(PREFIX_TAG).isPresent()) {

            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
            }

            String[] keywords = trimmedArgs.split("\\s+");

            predicate = new IdContainsKeywordsPredicate(Arrays.asList(keywords))
                    .or(new PriceContainsKeywordsPredicate(Arrays.asList(keywords)))
                    .or(new StatusContainsKeywordsPredicate(Arrays.asList(keywords)))
                    .or(new CustomerContainsKeywordsPredicate(Arrays.asList(keywords)))
                    .or(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));

            return new FindOrderCommand(predicate);

        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_PHONE).get().split("\\s+");

            predicate = predicate.and(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_CUSTOMER).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_CUSTOMER).get().split("\\s+");

            predicate = predicate.and(new CustomerContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_PRICE).get().split("\\s+");

            predicate = predicate.and(new PriceContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_TAG).get().split("\\s+");
            predicate = predicate.and(new OrderTagContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        return new FindOrderCommand(predicate);
    }

}
