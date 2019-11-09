package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENCY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.exchangedata.ExchangeDataSingleton;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String amount} into a {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Amount parseAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        if (!Amount.isValidAmount(trimmedAmount)) {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        }
        return new Amount(trimmedAmount);
    }

    /**
     * Parses a {@code String currency} into a {@code Currency}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code currency} is invalid.
     */
    public static Currency parseCurrency(String currency) throws ParseException {
        requireNonNull(currency);
        String trimmedCurrency = currency.trim();
        if (!Currency.isValidCurrency(trimmedCurrency)) {
            throw new ParseException(Currency.MESSAGE_CONSTRAINTS);
        }
        return new Currency(trimmedCurrency, ExchangeDataSingleton.getInstance().getRates().getRate(trimmedCurrency));
    }

    /**
     * Parses a {@code ArgumentMultiMap argMultiMap} into an {@code Currency}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code currency} is invalid.
     */
    public static Currency parseCurrency(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> currencyField = argMultimap.getValue(PREFIX_CURRENCY);
        String currency;
        if (!currencyField.isPresent()) {
            currency = "SGD";
        } else {
            currency = currencyField.get();
        }
        return parseCurrency(currency);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            if (!Date.isValidDate(trimmedDate)) {
                throw new ParseException(Date.MESSAGE_CONSTRAINTS);
            }
        } catch (DateTimeParseException e) {
            throw new ParseException("Input date contains " + e.getCause().getMessage());
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code ArgumentMultiMap argMultiMap} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> dateField = argMultimap.getValue(PREFIX_DATE);
        String date;
        if (!dateField.isPresent()) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy Hmm");
            date = currentDateTime.format(formatter);
        } else {
            date = dateField.get();
        }
        return parseDate(date);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
