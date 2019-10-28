package seedu.address.logic.parser.findcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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
import seedu.address.model.phone.predicates.PhoneTagContainsKeywordsPredicate;
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
                ArgumentTokenizer.tokenize(args, PREFIX_IDENTITY_NUM, PREFIX_SERIAL_NUM, PREFIX_PHONE_NAME,
                        PREFIX_BRAND, PREFIX_CAPACITY, PREFIX_COLOUR, PREFIX_COST, PREFIX_TAG);

        //dummy predicate
        Predicate<Phone> predicate = x -> false;
        if (!argMultimap.getValue(PREFIX_IDENTITY_NUM).isPresent()
                && !argMultimap.getValue(PREFIX_SERIAL_NUM).isPresent()
                && !argMultimap.getValue(PREFIX_PHONE_NAME).isPresent()
                && !argMultimap.getValue(PREFIX_BRAND).isPresent()
                && !argMultimap.getValue(PREFIX_CAPACITY).isPresent()
                && !argMultimap.getValue(PREFIX_COLOUR).isPresent()
                && !argMultimap.getValue(PREFIX_COST).isPresent()
                && !argMultimap.getValue(PREFIX_TAG).isPresent()) {

            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
            }

            String[] keywords = trimmedArgs.split("\\s+");

            predicate = new PhoneNameContainsKeywordsPredicate(Arrays.asList(keywords))
                            .or(new BrandContainsKeywordsPredicate(Arrays.asList(keywords)))
                            .or(new CapacityContainsKeywordsPredicate(Arrays.asList(keywords)))
                            .or(new ColourContainsKeywordsPredicate(Arrays.asList(keywords)))
                            .or(new CostContainsKeywordsPredicate(Arrays.asList(keywords)))
                            .or(new IdentityNumberContainsKeywordsPredicate(Arrays.asList(keywords)))
                            .or(new SerialNumberContainsKeywordsPredicate(Arrays.asList(keywords)))
                            .or(new PhoneTagContainsKeywordsPredicate(Arrays.asList(keywords)));

            return new FindPhoneCommand(predicate);

        }

        if (argMultimap.getValue(PREFIX_IDENTITY_NUM).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_IDENTITY_NUM).get().split("\\s+");

            predicate = predicate.or(new IdentityNumberContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_SERIAL_NUM).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_SERIAL_NUM).get().split("\\s+");

            predicate = predicate.or(new SerialNumberContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_PHONE_NAME).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_PHONE_NAME).get().split("\\s+");

            predicate = predicate.or(new PhoneNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_BRAND).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_BRAND).get().split("\\s+");

            predicate = predicate.or(new BrandContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_CAPACITY).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_CAPACITY).get().split("\\s+");

            predicate = predicate.or(new CapacityContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_COLOUR).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_COLOUR).get().split("\\s+");

            predicate = predicate.or(new ColourContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_COST).get().split("\\s+");

            predicate = predicate.or(new CostContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String[] keywords = argMultimap.getValue(PREFIX_TAG).get().split("\\s+");
            predicate = predicate.or(new PhoneTagContainsKeywordsPredicate(Arrays.asList(keywords)));
        }

        return new FindPhoneCommand(predicate);
    }

}
