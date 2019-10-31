package seedu.deliverymans.logic.parser;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.commons.util.StringUtil;
import seedu.deliverymans.logic.parser.exceptions.ParseException;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.food.Food;
import seedu.deliverymans.model.location.Location;
import seedu.deliverymans.model.location.LocationMap;
import seedu.deliverymans.model.restaurant.Rating;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index provided is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_RATING = "Rating provided is not a non-negative unsigned integer.";

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
     * Parses {@code rating} into an {@code Rating} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified rating is invalid (not non-zero unsigned integer).
     */
    public static Rating parseRating(String rating) throws ParseException {
        String trimmedRating = rating.trim();
        if (!StringUtil.isNonNegativeUnsignedInteger(trimmedRating)) {
            throw new ParseException(MESSAGE_INVALID_RATING);
        }
        if (!Rating.isValidRating(trimmedRating)) {
            throw new ParseException(Rating.MESSAGE_CONSTRAINTS);
        }
        return new Rating(trimmedRating);
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
     * Parses {@code Collection<String> names} into a {@code Set<Name>}.
     */
    public static ArrayList<Name> parseNames(Collection<String> names) throws ParseException {
        requireNonNull(names);
        final ArrayList<Name> nameSet = new ArrayList<>();
        for (String tagName : names) {
            nameSet.add(parseName(tagName));
        }
        return nameSet;
    }

    /**
     * Parses a {@code String name} into a {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!LocationMap.isValidLocation(trimmedLocation)) {
            throw new ParseException(LocationMap.MESSAGE_CONSTRAINTS);
        }
        return LocationMap.getLocation(trimmedLocation).get();
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
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

    /**
     * Parses {@code String bool} into a {@code boolean}.
     */
    public static boolean parseBoolean(String bool) throws ParseException {
        requireNonNull(bool);
        String trimmedBool = bool.trim();
        boolean isCompleted = Boolean.parseBoolean(trimmedBool);
        if (!isCompleted && trimmedBool.equalsIgnoreCase("false")) {
            throw new ParseException("Completed should be true or false");
        }
        return isCompleted;
    }

    /**
     * Parses a {@code String price} into a {@code BigDecimal}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static BigDecimal parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        BigDecimal result;
        try {
            result = new BigDecimal(trimmedPrice);
        } catch (NumberFormatException e) {
            throw new ParseException("Price must be a non-negative number");
        }

        if (!Food.isValidPrice(result)) {
            throw new ParseException(Food.PRICE_CONSTRAINTS);
        }
        return result;
    }

    /**
     * Parses a {@code String quantity} into an {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code quantity} is invalid.
     */
    public static int parseQuantity(String quantity) throws ParseException {
        requireNonNull(quantity);
        String trimmedQuantity = quantity.trim();
        try {
            return Integer.parseInt(trimmedQuantity);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    /**
     * Parses a {@code Collection<String> quantity} in seconds into a {@code ArrayList<Integer>}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Collection<String> quantity} is invalid.
     */
    public static ArrayList<Integer> parseQuantity(Collection<String> quantity) throws ParseException {
        requireNonNull(quantity);
        final ArrayList<Integer> quantitySet = new ArrayList<>();
        for (String quantityName : quantity) {
            quantitySet.add(Integer.parseInt(quantityName));
        }
        return quantitySet;
    }
}
