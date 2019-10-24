package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.eatery.Address;
import seedu.address.model.eatery.Category;
import seedu.address.model.eatery.Name;
import seedu.address.model.eatery.Review;
import seedu.address.model.eatery.Tag;


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
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
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
        if (!Tag.isValidTag(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return Tag.create(trimmedTag);
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
     * Parses {@code String category} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code category} is invalid.
     */
    public static Category parseCategory(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCat = category.trim();
        if (!Category.isValidCategory(trimmedCat)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return Category.create(trimmedCat);
    }

    /**
     *Trims leading and trailing white spaces.
     *
     * @param reviewDescription
     * @throws ParseException if the given {@code reviewDescription} is invalid.
     */
    public static String parseReviewDescription(String reviewDescription) throws ParseException {
        requireNonNull(reviewDescription);
        String trimmedDescription = reviewDescription.trim();
        if (!Review.isValidDescription(trimmedDescription)) {
            throw new ParseException(Review.REVIEW_CONSTRAINTS);
        }
        return trimmedDescription;
    }

    /**
     * Parses {@code reviewCost} to double value.
     *
     * @throws ParseException if the given {@code reviewCost} is invalid.
     */
    public static double parseReviewCost(String reviewCost) throws ParseException {
        requireNonNull(reviewCost);
        String trimmedCost = reviewCost.trim();
        if (!Review.isValidCost(Double.valueOf(trimmedCost))) {
            throw new ParseException(Review.REVIEW_CONSTRAINTS);
        }
        return Double.valueOf(trimmedCost);
    }

    /**
     * Parses {@code reviewRating} to integer value corresponding to rating.
     *
     * @throws ParseException if the given {@code reviewRating} is invalid.
     */
    public static int parseReviewRating(String reviewRating) throws ParseException {
        requireNonNull(reviewRating);
        String trimmedRating = reviewRating.trim();
        if (!Review.isValidRating(reviewRating)) {
            throw new ParseException(Review.REVIEW_CONSTRAINTS);
        }
        return Integer.valueOf(trimmedRating);
    }

    /**
     * Parses {@code reviewDate} to corresponding date.
     */
    public static Date parseReviewDate(String reviewDate) throws java.text.ParseException {
        requireNonNull(reviewDate);
        String trimmedDate = reviewDate.trim();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.parse(trimmedDate);
    }
}
