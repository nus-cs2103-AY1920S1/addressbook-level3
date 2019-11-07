package seedu.address.logic.finance.parser;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.finance.parser.exceptions.ParseException;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.GroupByAttr;
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.Place;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
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
     * Parses a {@code String tDate} into a {@code TransactionDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tDate} is invalid.
     */
    public static TransactionDate parseTransactionDate(String tDate) throws ParseException {
        requireNonNull(tDate);
        String trimmedTDate = tDate.trim();
        if (!TransactionDate.isValidTransactionDate(trimmedTDate)) {
            throw new ParseException(TransactionDate.MESSAGE_CONSTRAINTS);
        }
        return new TransactionDate(trimmedTDate);
    }

    /**
     * Parses a {@code String d} into an {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code d} is invalid.
     */
    public static Description parseDescription(String d) throws ParseException {
        requireNonNull(d);
        String trimmedDesc = d.trim();
        if (!Description.isValidDescription(trimmedDesc)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDesc);
    }

    /**
     * Parses a {@code String tMethod} into an {@code TransactionMethod}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tMethod} is invalid.
     */
    public static TransactionMethod parseTransactionMethod(String tMethod) throws ParseException {
        requireNonNull(tMethod);
        String trimmedTMethod = tMethod.trim();
        if (!TransactionMethod.isValidTransactionMet(trimmedTMethod)) {
            throw new ParseException(TransactionMethod.MESSAGE_CONSTRAINTS);
        }
        return new TransactionMethod(trimmedTMethod);
    }

    /**
     * Parses a {@code String cat} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code cat} is invalid.
     */
    public static Category parseCategory(String cat) throws ParseException {
        requireNonNull(cat);
        String trimmedCat = cat.trim();
        if (!Category.isValidCatName(trimmedCat)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedCat);
    }

    /**
     * Parses {@code Collection<String> cats} into a {@code Set<Category>}.
     */
    public static Set<Category> parseCategories(Collection<String> cats) throws ParseException {
        requireNonNull(cats);
        final Set<Category> catSet = new HashSet<>();
        for (String catName : cats) {
            catSet.add(parseCategory(catName));
        }
        return catSet;
    }

    /**
     * Parses a {@code String p} into an {@code Place}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code p} is invalid.
     */
    public static Place parsePlace(String p) throws ParseException {
        requireNonNull(p);
        String trimmedPlace = p.trim();
        if (!Place.isValidPlace(trimmedPlace)) {
            throw new ParseException(Place.MESSAGE_CONSTRAINTS);
        }
        return new Place(trimmedPlace);
    }

    /**
     * Parses a {@code String name} into an {@code Person}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Person parsePerson(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Person.isValidName(trimmedName)) {
            throw new ParseException(Person.MESSAGE_CONSTRAINTS);
        }
        return new Person(trimmedName);
    }

    /**
     * Parses {@code String attr} into a {@code GroupByAttr}
     */
    public static GroupByAttr parseGroupByAttr(String attr) throws ParseException {
        requireNonNull(attr);
        String trimmedAttr = attr.trim();
        if (!GroupByAttr.isValidGroupByAttr(trimmedAttr)) {
            throw new ParseException(GroupByAttr.MESSAGE_CONSTRAINTS);
        }
        return new GroupByAttr(trimmedAttr);
    }

    /**
     * Parses a {@code String summariseAttr}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code summariseAttr} is invalid.
     */
    public static String parseSummariseAttr(String summariseAttr) throws ParseException {
        requireNonNull(summariseAttr);
        String processedAttr = summariseAttr.trim().toLowerCase();
        if (!processedAttr.equals("amt") && !processedAttr.equals("freq")) {
            throw new ParseException("Attributes to summarise by are only: "
                    + "\"amt\" and \"freq\".");
        }
        return processedAttr;
    }

    /**
     * Parses a {@code String date} into a {@code date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        SimpleDateFormat validFormat = new SimpleDateFormat("dd-MM-yyyy");
        validFormat.setLenient(false); // date has to exist in calendar (i.e. not 31 Feb)
        try {
            Date parsedDate = validFormat.parse(trimmedDate);
            return parsedDate;
        } catch (java.text.ParseException e) {
            throw new ParseException("Date should be in the form DD-MM-YYYY.");
        }
    }

    /**
     * Parses a {@code String dur} of days and converts to milli-seconds
     *
     * @throws ParseException if the given {@code dur} is invalid.
     */
    public static long parseDur(String dur) throws ParseException {
        requireNonNull(dur);
        String trimmedDur = dur.trim();
        long numMilliSecondsInADay = 1000 * 60 * 60 * 24;
        try {
            int numDays = Integer.parseInt(trimmedDur);
            if (numDays <= 0) {
                throw new NumberFormatException();
            }
            return numMilliSecondsInADay * numDays;
        } catch (NumberFormatException e) {
            throw new ParseException("Duration should be positive integers.");
        }
    }

    /**
     * Parses a {@code String date} and returns first day of month.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseMonthStart(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        SimpleDateFormat validFormat = new SimpleDateFormat("MM-yyyy");
        try {
            Date parsedDate = validFormat.parse(trimmedDate);
            return parsedDate;
        } catch (java.text.ParseException e) {
            throw new ParseException("Month should be in the form MM-YYYY.");
        }
    }

    /**
     * Parses a {@code String date} and returns last day of month.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseMonthEnd(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        SimpleDateFormat validFormat = new SimpleDateFormat("MM-yyyy");
        String monthYearRegex = "^((0?[1-9])|10|11|12)-[\\d]{4}$";
        try {
            if (!trimmedDate.matches(monthYearRegex)) {
                throw new NumberFormatException();
            }
            Date parsedDate = validFormat.parse(trimmedDate);
            return getLastDayOfMonth(parsedDate);
        } catch (java.text.ParseException | NumberFormatException e) {
            throw new ParseException("Month should be in the form MM-YYYY.");
        }
    }

    private static Date getLastDayOfMonth(Date date) {
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(date);
        int lastDay = endDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        endDate.set(Calendar.DAY_OF_MONTH, lastDay);
        return endDate.getTime();
    }
}
