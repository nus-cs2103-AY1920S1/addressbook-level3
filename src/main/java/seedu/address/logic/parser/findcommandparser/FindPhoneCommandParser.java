package seedu.address.logic.parser.findcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITYNUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIALNUM;

import java.util.Arrays;

import java.util.function.Predicate;
import seedu.address.logic.commands.findcommand.FindPhoneCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.predicates.BrandContainsKeywordsPredicate;
import seedu.address.model.phone.predicates.CapacityContainsKeywordsPredicate;
import seedu.address.model.phone.predicates.ColourContainsKeywordsPredicate;
import seedu.address.model.phone.predicates.CostContainsKeywordsPredicate;
import seedu.address.model.phone.predicates.IdentityNumberContainsKeywordsPredicate;
import seedu.address.model.phone.predicates.PhoneNameContainsKeywordsPredicate;
import seedu.address.model.phone.predicates.SerialNumberContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPhoneCommand object
 */
public class FindPhoneCommandParser implements Parser<FindPhoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPhoneCommand
     * and returns a FindPhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPhoneCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IDENTITYNUM, PREFIX_SERIALNUM, PREFIX_PHONENAME,
                        PREFIX_BRAND, PREFIX_CAPACITY, PREFIX_COLOUR, PREFIX_COST);

        //dummy predicate
        Predicate<Phone> predicate = x -> true;
        if (! argMultimap.getValue(PREFIX_IDENTITYNUM).isPresent()
                && ! argMultimap.getValue(PREFIX_SERIALNUM).isPresent()
                && ! argMultimap.getValue(PREFIX_PHONENAME).isPresent()
                && ! argMultimap.getValue(PREFIX_BRAND).isPresent()
                && ! argMultimap.getValue(PREFIX_CAPACITY).isPresent()
                && ! argMultimap.getValue(PREFIX_COLOUR).isPresent()
                && ! argMultimap.getValue(PREFIX_COST).isPresent()) {

            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
            }

            String[] nameKeywords = trimmedArgs.split("\\s+");

            predicate = new PhoneNameContainsKeywordsPredicate(Arrays.asList(nameKeywords))
                            .or(new BrandContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                            .or(new CapacityContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                            .or(new ColourContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                            .or(new CostContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                            .or(new IdentityNumberContainsKeywordsPredicate(Arrays.asList(nameKeywords)))
                            .or(new SerialNumberContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

            return new FindPhoneCommand(predicate);

        }

        if (argMultimap.getValue(PREFIX_IDENTITYNUM).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_IDENTITYNUM).get().split("\\s+");

            predicate = predicate.and(new IdentityNumberContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_SERIALNUM).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_SERIALNUM).get().split("\\s+");

            predicate = predicate.and(new SerialNumberContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_PHONENAME).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_PHONENAME).get().split("\\s+");

            predicate = predicate.and(new PhoneNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_BRAND).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_BRAND).get().split("\\s+");

            predicate = predicate.and(new BrandContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_CAPACITY).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_CAPACITY).get().split("\\s+");

            predicate = predicate.and(new CapacityContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_COLOUR).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_COLOUR).get().split("\\s+");

            predicate = predicate.and(new ColourContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_COST).get().split("\\s+");

            predicate = predicate.and(new CostContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        return new FindPhoneCommand(predicate);
    }

}
