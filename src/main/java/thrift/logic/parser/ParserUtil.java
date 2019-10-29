package thrift.logic.parser;

import static java.util.Objects.requireNonNull;
import static thrift.model.transaction.Budget.BUDGET_DATE_FORMAT;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import thrift.commons.core.index.Index;
import thrift.commons.util.StringUtil;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.clone.Occurrence;
import thrift.model.tag.Tag;
import thrift.model.transaction.Budget;
import thrift.model.transaction.BudgetValue;
import thrift.model.transaction.Description;
import thrift.model.transaction.Remark;
import thrift.model.transaction.Value;
import thrift.model.util.CurrencyUtil;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_CURRENCY = "Currency entered is not valid. (Must exist)";

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
     * Parses a {@code String description} into a {@code Description} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Description parseDescription(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String monthYear} into a {@code Calendar} with {@code Calendar.MONTH} and {@code Calendar.YEAR}
     * set and returns it.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Calendar parseDate(String monthYear) throws ParseException {
        requireNonNull(monthYear);
        Calendar date = Calendar.getInstance();

        String trimmedMonthYear = monthYear.trim();
        if (!trimmedMonthYear.matches(Budget.VALIDATION_REGEX)) {
            throw new ParseException(Budget.DATE_CONSTRAINTS);
        }

        try {
            date.setTime(BUDGET_DATE_FORMAT.parse(trimmedMonthYear));
        } catch (java.text.ParseException pe) {
            throw new ParseException(Budget.DATE_CONSTRAINTS);
        }
        return date;
    }

    /**
     * Parses a {@code String value} into a {@code Value} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code value} is invalid.
     */
    public static Value parseValue(String value) throws ParseException {
        requireNonNull(value);
        String trimmedValue = value.trim();
        if (!Value.isValidValue(trimmedValue)) {
            throw new ParseException(Value.VALUE_CONSTRAINTS);
        }
        return new Value(trimmedValue);
    }

    /**
     * Parses a {@code String value} into a {@code BudgetValue} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code value} is invalid.
     */
    public static BudgetValue parseBudgetValue(String value) throws ParseException {
        requireNonNull(value);
        String trimmedValue = value.trim();
        if (!BudgetValue.isValidValue(trimmedValue)) {
            throw new ParseException(BudgetValue.VALUE_CONSTRAINTS);
        }
        return new BudgetValue(trimmedValue);
    }

    /**
     * Parses a {@code String remark} into a {@code Remark} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Remark parseRemark(String remark) {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag} and returns it.
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
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} and returns it.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Checks if the currencies are valid
     *
     * @param currencies The currencies
     * @return the same currencies (without modification)
     * @throws ParseException
     */
    public static List<String> parseCurrencies(Collection<String> currencies) throws ParseException {
        requireNonNull(currencies);
        final List<String> currencyList = new ArrayList<String>();
        for (String currency : currencies) {
            if (!currency.matches("[A-Za-z]+")) {
                throw new ParseException(MESSAGE_INVALID_CURRENCY);
            }
            if (!CurrencyUtil.getCurrencyMap().containsKey(currency.toUpperCase())) {
                throw new ParseException(MESSAGE_INVALID_CURRENCY);
            }

            currencyList.add(currency.split(" ")[0]);
        }
        return currencyList;
    }


    //this method may be used if i want to parse m/jan as a possible argument next time.
    /**
     * Parses a {@code String month} into a {@code Month}.
     *
     * @throws ParseException if the given {@code month} is invalid.
     */
    public static Month parseMonth(String month) throws ParseException {
        requireNonNull(month);
        try {
            String pattern = "MMMM";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String monthCaps = sdf.format(sdf.parse(month)).toUpperCase();
            return Month.valueOf(monthCaps);
        } catch (java.text.ParseException pe) {
            throw new ParseException(ListCommandParser.MESSAGE_INVALID_MONTH_FORMAT);
        }
    }

    /**
     * Parses a String representing occurrence into an {@code Occurrence}.
     *
     * @param inputOccurrence String to be parsed into Occurrence.
     * @throws ParseException if given {@code inputOccurrence} has invalid {@code frequency} or {@code numOccurrences}.
     */
    public static Occurrence parseOccurrence(String inputOccurrence) throws ParseException {
        requireNonNull(inputOccurrence);
        try {
            String[] occurrenceParts = inputOccurrence.split(":");
            String frequency = occurrenceParts[0].trim().toLowerCase();
            if (!Occurrence.isValidFrequency(frequency)) {
                throw new ParseException(Occurrence.OCCURRENCE_CONSTRAINTS);
            }

            int numOccurrences = Integer.parseInt(occurrenceParts[1].trim());
            if (numOccurrences <= 0 || numOccurrences > 12 || ("yearly".equals(frequency) && numOccurrences > 5)) {
                throw new ParseException(Occurrence.OCCURRENCE_CONSTRAINTS);
            }
            return new Occurrence(frequency, numOccurrences);
        } catch (ParseException | NumberFormatException e) {
            throw new ParseException(Occurrence.OCCURRENCE_CONSTRAINTS);
        }
    }
}
