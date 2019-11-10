package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.util.Date;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final char NEGATIVE_AMOUNT_SIGN = '-';
    public static final double ZERO_AMOUNT = 0;
    public static final double MAX_AMOUNT = 1000000;

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim().substring(1);
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code type} into an {@code String} and returns it. Leading
     * and trailing whitespaces will be trimmed.
     */
    public static String parseType(String type) throws ParseException {
        requireNonNull(type);
        try {
            String trimmedType = type.trim().substring(0, 1);
            return trimmedType;
        } catch (StringIndexOutOfBoundsException ex) {
            throw new ParseException(ex.getMessage());
        }
    }

    /**
     * Parses a {@code String descriptions} into a {@code Description}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Name.isValidName(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing
     * whitespaces will be trimmed.
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
     * Parses a {@code Collection<String> names} into a {@code List<Name>}
     *
     * @param names
     * @return
     * @throws ParseException
     */
    public static List<Name> parseNames(Collection<String> names) throws ParseException {
        requireNonNull(names);
        List<Name> nameList = new ArrayList<>();
        for (String name : names) {
            nameList.add(parseName(name));
        }
        return nameList;
    }

    /**
     * Parses a {@code String date} into an {@code Date}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!trimmedDate.matches(Date.DATE_FORMAT)) {
            throw new ParseException(Date.MESSAGE_FORMAT_CONSTRAINTS);
        }
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(String.format(Date.MESSAGE_DATE_INVALID, trimmedDate));
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String category} into a {@code Category}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code category} is invalid.
     */
    public static Category parseCategory(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (!Category.isValidCategoryName(trimmedCategory)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }

    /**
     * Parses {@code Collection<String> categories} into a {@code Set<Category>}.
     */
    public static Set<Category> parseCategories(Collection<String> categories) throws ParseException {
        requireNonNull(categories);
        final Set<Category> categorySet = new HashSet<>();
        for (String categoryName : categories) {
            categorySet.add(parseCategory(categoryName));
        }
        return categorySet;
    }

    /**
     * Parses {@code Collection<String> shares} into a {@code List<Integer>}.
     *
     * @param shares
     * @return
     * @throws ParseException
     */
    public static List<Integer> parseShares(Collection<String> shares) throws ParseException {
        requireNonNull(shares);
        final List<Integer> intShares = new ArrayList<>();
        for (String share : shares) {
            try {
                int shareInt = Integer.parseInt(share.trim());
                if (shareInt < 0) {
                    throw new ParseException(Amount.SHARE_CONSTRAINTS);
                }
                intShares.add(Integer.parseInt(share.trim()));
            } catch (NumberFormatException ex) {
                throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
            }

        }
        return intShares;
    }

    /**
     * Parse {@code String s} into an {@code Amount}
     *
     * @param s input
     * @return Amount
     */
    public static Amount parseAmount(String s) throws ParseException {
        requireNonNull(s);
        /* handles empty amount*/
        if (s.length() == ZERO_AMOUNT) {
            throw new ParseException(Messages.MESSAGE_AMOUNT_EMPTY);
        }

        /* handles negative amount*/
        char first = s.toCharArray()[0];
        if (first == NEGATIVE_AMOUNT_SIGN) {
            throw new ParseException(Messages.MESSAGE_AMOUNT_NEGATIVE);
        }

        try {
            /* handles 0 value */
            if (Double.parseDouble(s) == ZERO_AMOUNT) {
                throw new ParseException(Messages.MESSAGE_AMOUNT_ZERO);
            }

            /* handles overflow value */
            if (Double.parseDouble(s) >= MAX_AMOUNT) {
                throw new ParseException(String.format(Messages.MESSAGE_AMOUNT_OVERFLOW));
            }

            return new Amount(Double.parseDouble(s));
        } catch (NumberFormatException ex) {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(ex.getMessage());
        }
    }

    /**
     * Parse Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be trimmed.
     *
     * @return {@code Index} representing the budget's index
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseBudgetIndex(String oneBasedIndex) throws ParseException {
        requireNonNull(oneBasedIndex);
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code month} into an {@code Integer} and returns it. Leading
     * and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified month is invalid.
     */
    public static Integer parseMonth(String monthString) throws ParseException {
        requireNonNull(monthString);
        String trimmedMonth = monthString.trim();
        try {
            if (Date.isValidMonth(trimmedMonth)) {
                return Integer.parseInt(trimmedMonth);
            } else {
                throw new ParseException(Date.MESSAGE_DATE_INVALID);
            }
        } catch (NumberFormatException ex) {
            throw new ParseException(Date.MESSAGE_DATE_INVALID);
        }
    }

    /**
     * Parses {@code year} into an {@code Integer} and returns it. Leading
     * and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified year is invalid.
     */
    public static Integer parseYear(String yearString) throws ParseException {
        requireNonNull(yearString);
        String trimmedYear = yearString.trim();
        try {
            if (Date.isValidYear(trimmedYear)) {
                return Integer.parseInt(trimmedYear);
            } else {
                throw new ParseException(Date.MESSAGE_DATE_INVALID);
            }
        } catch (NumberFormatException ex) {
            throw new ParseException(Date.MESSAGE_DATE_INVALID);
        }
    }
}
