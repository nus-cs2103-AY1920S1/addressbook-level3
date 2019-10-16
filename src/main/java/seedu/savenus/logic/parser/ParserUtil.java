package seedu.savenus.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.savenus.commons.core.index.Index;
import seedu.savenus.commons.util.StringUtil;
import seedu.savenus.logic.commands.BudgetCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.OpeningHours;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Restrictions;
import seedu.savenus.model.tag.Tag;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;

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
     * Parses a {@code String} into a {@code Wallet}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String} is invalid.
     */
    public static Wallet parseWallet(String walletString) throws ParseException {
        String trimmedWalletString = walletString.trim();
        String[] splitWalletString = trimmedWalletString.split("\\s+");
        if (splitWalletString.length != 2
                || !RemainingBudget.isValidRemainingBudget(splitWalletString[0])
                || !DaysToExpire.isValidDaysToExpire(splitWalletString[1])) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
        }
        BigDecimal budgetAmount = new BigDecimal(splitWalletString[0].contains(".")
                ? splitWalletString[0]
                : String.format("%s.00", splitWalletString[0]));

        int parsedDaysToExpire;
        try {
            parsedDaysToExpire = Integer.parseInt(splitWalletString[1]);
        } catch (NumberFormatException e) {
            throw new ParseException(DaysToExpire.INTEGER_CONSTRAINTS);
        }

        return new Wallet(new RemainingBudget(budgetAmount.toString()),
                new DaysToExpire(splitWalletString[1]));
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
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
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
     * Parses a {@code String description} into an {@code description}.
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
     * Parses a {@code String location} into an {@code location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Parses a {@code String openingHours} into an {@code openingHours}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code openingHours} is invalid.
     */
    public static OpeningHours parseOpeningHours(String openingHours) throws ParseException {
        requireNonNull(openingHours);
        String trimmedOpeningHours = openingHours.trim();
        if (openingHours.equals(OpeningHours.DEFAULT_VALUE)) {
            return new OpeningHours(trimmedOpeningHours);
        }
        if (!OpeningHours.isValidOpeningHours(trimmedOpeningHours)) {
            throw new ParseException(OpeningHours.MESSAGE_CONSTRAINTS);
        }
        return new OpeningHours(trimmedOpeningHours);
    }

    /**
     * Parse a {@code String restrictions} into an {@code restrictions}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code restrictions} is invalid.
     */
    public static Restrictions parseRestrictions(String restrictions) throws ParseException {
        requireNonNull(restrictions);
        String trimmedRestrictions = restrictions.trim();
        if (!Restrictions.isValidRestrictions(trimmedRestrictions)) {
            throw new ParseException(Restrictions.MESSAGE_CONSTRAINTS);
        }
        return new Restrictions(trimmedRestrictions);
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
     * Parses {@code Collection<String> categories} into a {@code Set<Category>}.
     */
    public static Set<Category> parseCategories(Collection<String> categories) throws ParseException {
        requireNonNull(categories);
        final Set<Category> categoriesSet = new HashSet<>();
        for (String categoryName : categories) {
            categoriesSet.add(parseCategory(categoryName));
        }
        return categoriesSet;
    }

    /**
     * Parses {@code Collection<String> locations} into a {@code Set<Location>}.
     */
    public static Set<Location> parseLocations(Collection<String> locations) throws ParseException {
        requireNonNull(locations);
        final Set<Location> locationsSet = new HashSet<>();
        for (String locationName : locations) {
            locationsSet.add(parseLocation(locationName));
        }
        return locationsSet;
    }
}
