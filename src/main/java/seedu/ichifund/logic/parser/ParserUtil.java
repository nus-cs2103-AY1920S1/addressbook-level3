package seedu.ichifund.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.commons.util.StringUtil;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.loan.Name;
import seedu.ichifund.model.repeater.MonthOffset;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;

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
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
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
     * Parses a {@code String amount} into a {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid or negative.
     */
    public static Amount parsePositiveAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        if (!Amount.isValidAmount(trimmedAmount)) {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        } else if (Amount.isNegative(trimmedAmount)) {
            throw new ParseException(Amount.POSITIVE_AMOUNT_CONSTRAINT);
        } else if (Amount.isZero(trimmedAmount)) {
            throw new ParseException(Amount.POSITIVE_AMOUNT_CONSTRAINT);
        }
        return new Amount(trimmedAmount);
    }

    /**
     * Parses a {@code String category} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code category} is invalid.
     */
    public static Category parseCategory(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (!Category.isValidCategory(trimmedCategory)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }

    /**
     * Parses a {@code String category} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     * Allows for parsing of {@code Category.CATEGORY_ALL}
     *
     * @throws ParseException if the given {@code category} is invalid and is not "!all".
     */
    public static Category parseCategoryWithAll(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        if (trimmedCategory.equals(Category.CATEGORY_ALL.toString())) {
            return Category.CATEGORY_ALL;
        } else if (!Category.isValidCategory(trimmedCategory)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }


    /**
     * Parses a {@code String day} into a {@code Day}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code day} is invalid.
     */
    public static Day parseDay(String day) throws ParseException {
        requireNonNull(day);
        String trimmedDay = day.trim();
        if (!Day.isValidDay(trimmedDay)) {
            throw new ParseException(Day.MESSAGE_CONSTRAINTS);
        }
        return new Day(trimmedDay);
    }

    /**
     * Parses a {@code String month} into a {@code Month}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code month} is invalid.
     */
    public static Month parseMonth(String month) throws ParseException {
        requireNonNull(month);
        String trimmedMonth = month.trim();
        if (!Month.isValidMonth(trimmedMonth)) {
            throw new ParseException(Month.MESSAGE_CONSTRAINTS);
        }
        return new Month(trimmedMonth);
    }

    /**
     * Parses a {@code String year} into a {@code Year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.trim();
        if (!Year.isValidYear(trimmedYear)) {
            throw new ParseException(Year.MESSAGE_CONSTRAINTS);
        }
        return new Year(trimmedYear);
    }

    /**
     * Parses a {@code String transactionType} into a {@code TransactionType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code transactionType} is invalid.
     */
    public static TransactionType parseTransactionType(String transactionType) throws ParseException {
        requireNonNull(transactionType);
        String trimmedTransactionType = transactionType.trim();
        if (!TransactionType.isValidTransactionType(trimmedTransactionType)) {
            throw new ParseException(TransactionType.MESSAGE_CONSTRAINTS);
        }
        return new TransactionType(trimmedTransactionType);
    }

    /**
     * Parses a {@code String transactionType} into a {@code TransactionType}.
     * Leading and trailing whitespaces will be trimmed.
     * Allows for parsing of {@code TransactionType.TRANSACTION_TYPE_ALL}
     *
     * @throws ParseException if the given {@code transactionType} is invalid.
     */
    public static TransactionType parseTransactionTypeWithAll(String transactionType) throws ParseException {
        requireNonNull(transactionType);
        String trimmedTransactionType = transactionType.trim();
        if (trimmedTransactionType.equals(TransactionType.TRANSACTION_TYPE_ALL.toString())) {
            return TransactionType.TRANSACTION_TYPE_ALL;
        } else if (!TransactionType.isValidTransactionType(trimmedTransactionType)) {
            throw new ParseException(TransactionType.MESSAGE_CONSTRAINTS);
        }
        return new TransactionType(trimmedTransactionType);
    }

    /**
     * Parses a {@code String monthOffset} into a {@code MonthOffset}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code transactionType} is invalid.
     */
    public static MonthOffset parseMonthOffset(String monthOffset) throws ParseException {
        requireNonNull(monthOffset);
        String trimmedMonthOffset = monthOffset.trim();
        if (!MonthOffset.isValidMonthOffset(trimmedMonthOffset)) {
            throw new ParseException(MonthOffset.MESSAGE_CONSTRAINTS);
        }
        return new MonthOffset(trimmedMonthOffset);
    }
}
