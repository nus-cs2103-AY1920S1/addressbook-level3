package seedu.address.financialtracker.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.financialtracker.model.expense.Amount;
import seedu.address.financialtracker.model.expense.Country;
import seedu.address.financialtracker.model.expense.Description;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.PageType;

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
    public static Amount parseAmount(String name) throws ParseException {
        requireNonNull(name);
        String trimmedAmount = name.trim();
        if (!Amount.isValidAmount(trimmedAmount)) {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        }
        return new Amount(trimmedAmount);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedPhone = description.trim();
        if (!Description.isValidDescription(trimmedPhone)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedPhone);
    }

    /**
     * Parses a {@code String country} into an {@code Country}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Country parseCountry(String country) throws ParseException {
        requireNonNull(country);
        String trimmedCountry = country.trim();
        if (!Country.isValidCountry(trimmedCountry)) {
            throw new ParseException(Country.MESSAGE_CONSTRAINTS);
        }
        return new Country(trimmedCountry);
    }

    public static PageType parsePageType(String pageType) throws ParseException {
        requireNonNull(pageType);
        String trimmedPageType = pageType.trim();
        try {
            PageType requestedPage = PageType.valueOf(trimmedPageType.toUpperCase());
            return requestedPage;
        } catch (IllegalArgumentException e) {
            throw new ParseException(PageType.MESSAGE_CONSTRAINTS);
        }
    }
}
