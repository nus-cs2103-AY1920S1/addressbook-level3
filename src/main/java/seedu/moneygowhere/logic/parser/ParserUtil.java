package seedu.moneygowhere.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.moneygowhere.commons.core.index.Index;
import seedu.moneygowhere.commons.util.StringUtil;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Name;
import seedu.moneygowhere.model.spending.Remark;
import seedu.moneygowhere.model.tag.Tag;

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
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses {@code Collection<String> dates} into a {@code List<Dates>}.
     */
    public static List<Date> parseDates(Collection<String> dates) throws ParseException {
        requireNonNull(dates);
        final List<Date> dateList = new ArrayList<>();
        for (String date : dates) {
            dateList.add(parseDate(date));
        }
        return dateList;
    }

    /**
     * Parses a {@code String cost} into an {@code cost}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code cost} is invalid.
     */
    public static Cost parseCost(String cost) throws ParseException {
        requireNonNull(cost);
        String trimmedCost = cost.trim();
        if (!Cost.isValidCost(trimmedCost)) {
            throw new ParseException(Cost.MESSAGE_CONSTRAINTS);
        }
        return new Cost(trimmedCost);
    }

    /**
     * Parses {@code String cost} delimited by a '-' into an {@code cost}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code cost} is invalid.
     */
    public static List<Cost> parseCosts(Collection<String> costs) throws ParseException {
        requireNonNull(costs);

        List<Cost> result = new ArrayList<>();
        for (String tempCost : costs) {
            String[] costTokens = tempCost.split("-");
            for (String cost : costTokens) {
                if (!Cost.isValidCost(cost)) {
                    throw new ParseException(Cost.MESSAGE_CONSTRAINTS);
                }

                result.add(new Cost(cost));
            }
        }

        return result;
    }

    /**
     * Parses a {@code String remark} into an {@code remark}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Remark parseRemark(String remark) {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
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

    /**
     * Parses {@code budget} into an {@code Budget} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified budget is invalid (negative integer).
     */
    public static Budget parseBudget(String budget) throws ParseException {
        String trimmedBudget = budget.trim();
        try {
            double temp = Double.parseDouble(trimmedBudget);
            if (temp < 0) {
                throw new ParseException(Budget.MESSAGE_CONSTRAINTS);
            }
            return new Budget(temp);
        } catch (NumberFormatException e) {
            throw new ParseException(Budget.MESSAGE_CONSTRAINTS);
        }
    }
}
