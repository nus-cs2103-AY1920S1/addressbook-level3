package seedu.savenus.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.savenus.commons.core.index.Index;
import seedu.savenus.commons.util.StringUtil;
import seedu.savenus.logic.commands.BudgetCommand;
import seedu.savenus.logic.commands.SaveCommand;
import seedu.savenus.logic.commands.TopUpCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.OpeningHours;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Restrictions;
import seedu.savenus.model.food.Tag;
import seedu.savenus.model.util.Money;
import seedu.savenus.model.wallet.DaysToExpire;
import seedu.savenus.model.wallet.RemainingBudget;
import seedu.savenus.model.wallet.Wallet;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String DUPLICATE_FIELDS = "You have multiple instances of %s. \n"
            + "Please make sure you only have one instance of it.";

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
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndexes(String oneBasedIndex) throws ParseException {
        String trimmedIndexes = oneBasedIndex.trim();
        String[] indexes = trimmedIndexes.split("\\s+");
        List<Index> outputIndexes = new ArrayList<Index>();
        for (int i = 0; i < indexes.length; i++) {
            String trimmedIndex = indexes[i];
            if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
            outputIndexes.add(Index.fromOneBased(Integer.parseInt(trimmedIndex)));
        }
        return outputIndexes;
    }

    /**
     * Parses a {@code String} into a valid TopUpAmount.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String} is invalid.
     */
    public static BigDecimal parseTopUpAmount (String topUpAmountString) throws ParseException {
        String trimmedTopUpAmount = topUpAmountString.trim();
        if (!RemainingBudget.isValidRemainingBudget(trimmedTopUpAmount)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TopUpCommand.MESSAGE_USAGE));
        }
        return new BigDecimal(trimmedTopUpAmount);
    }

    /**
     * Parses a {@code String} into a {@code Saving}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if given {@code String} is invalid.
     */
    public static Money parseSavings(String savingsAmount) throws ParseException {
        String trimmedSavingsString = savingsAmount.trim();
        String[] splitSavingsString = trimmedSavingsString.split("\\s+");
        if (splitSavingsString.length != 1
            || !Money.isValidMoney(splitSavingsString[0])
            || splitSavingsString[0].contains("-")
            || splitSavingsString[0].equals("0")
            || splitSavingsString[0].equals("0.00")) { // catch negative savings and 0 values
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        /** if there is already a specified number of cents return the String with decimal
         * else have to manually add to the string to parse into BigDecimal.
         */


        return new Money(splitSavingsString[0]);
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
        String budgetAmountString;
        String budgetDurationString;
        if (splitWalletString.length == 1 && RemainingBudget.isValidRemainingBudget(splitWalletString[0])) {
            budgetAmountString = splitWalletString[0].contains(".")
                ? splitWalletString[0]
                : String.format("%s.00", splitWalletString[0]);
            budgetDurationString = "0";
        } else if (splitWalletString.length == 2
                    && RemainingBudget.isValidRemainingBudget(splitWalletString[0])
                    && DaysToExpire.isValidDaysToExpire(splitWalletString[1])) {
            budgetAmountString = splitWalletString[0].contains(".")
                    ? splitWalletString[0]
                    : String.format("%s.00", splitWalletString[0]);
            budgetDurationString = splitWalletString[1];
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BudgetCommand.MESSAGE_USAGE));
        }
        return new Wallet(budgetAmountString, budgetDurationString);
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
     * Parses a collection of {@code String names} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid or multiple names are present.
     */
    public static Name parseTheNames(Collection<String> names) throws ParseException {
        requireNonNull(names);
        if (names.size() > 1) {
            throw new ParseException(String.format(DUPLICATE_FIELDS, "Name"));
        }

        String theName = "";
        for (String name : names) {
            if (!Name.isValidName(name.trim())) {
                throw new ParseException(Name.MESSAGE_CONSTRAINTS);
            }

            theName = name.trim();
            break;
        }

        return new Name(theName);
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
     * Parses a collection of {@code String prices} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid or multiple prices are present.
     */
    public static Price parseThePrices(Collection<String> prices) throws ParseException {
        requireNonNull(prices);
        if (prices.size() > 1) {
            throw new ParseException(String.format(DUPLICATE_FIELDS, "Price"));
        }

        String thePrice = "";
        for (String price : prices) {
            if (!Price.isValidPrice(price)) {
                throw new ParseException(Price.MESSAGE_CONSTRAINTS);
            }

            thePrice = price;
            break;
        }

        return new Price(thePrice);
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
     * Parses a collection of {@code String categories} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid or multiple categories are present.
     */
    public static Category parseTheCategories(Collection<String> categories) throws ParseException {
        requireNonNull(categories);
        if (categories.size() > 1) {
            throw new ParseException(String.format(DUPLICATE_FIELDS, "Category"));
        }

        String theCategory = "";
        for (String category : categories) {
            if (!Category.isValidCategory(category)) {
                throw new ParseException(Category.MESSAGE_CONSTRAINTS);
            }

            theCategory = category;
            break;
        }

        return new Category(theCategory);
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
     * Parses a collection of {@code String descriptions} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid or multiple descriptions are present.
     */
    public static Description parseTheDescriptions(Collection<String> descriptions) throws ParseException {
        requireNonNull(descriptions);
        if (descriptions.size() > 1) {
            throw new ParseException(String.format(DUPLICATE_FIELDS, "Description"));
        }

        if (descriptions.size() == 0) {
            return new Description(Description.DEFAULT_VALUE);
        }

        String theDescription = "";

        for (String description : descriptions) {
            if (!Description.isValidDescription(description)) {
                throw new ParseException(Description.MESSAGE_CONSTRAINTS);
            }

            theDescription = description;
            break;
        }

        return new Description(theDescription);
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
     * Parses a collection of {@code String locations} into a {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid or multiple locations are present.
     */
    public static Location parseTheLocations(Collection<String> locations) throws ParseException {
        requireNonNull(locations);
        if (locations.size() > 1) {
            throw new ParseException(String.format(DUPLICATE_FIELDS, "Location"));
        }

        if (locations.size() == 0) {
            return new Location(Location.DEFAULT_VALUE);
        }

        String theLocation = "";

        for (String location : locations) {
            if (!Location.isValidLocation(location)) {
                throw new ParseException(Location.MESSAGE_CONSTRAINTS);
            }

            theLocation = location;
            break;
        }

        return new Location(theLocation);
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
            throw new ParseException(OpeningHours.FORMAT_CONSTRAINTS);
        }
        return new OpeningHours(trimmedOpeningHours);
    }

    /**
     * Parses a collection of {@code String openingHours} into a {@code OpeningHours}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid or multiple openingHours are present.
     */
    public static OpeningHours parseTheOpeningHours(Collection<String> openingHours) throws ParseException {
        requireNonNull(openingHours);
        if (openingHours.size() > 1) {
            throw new ParseException(String.format(DUPLICATE_FIELDS, "OpeningHours"));
        }

        if (openingHours.size() == 0) {
            return new OpeningHours(OpeningHours.DEFAULT_VALUE);
        }

        String theOpeningHours = "";

        for (String actualOpeningHours : openingHours) {
            if (!OpeningHours.isValidOpeningHours(actualOpeningHours)) {
                throw new ParseException(OpeningHours.FORMAT_CONSTRAINTS);
            }
            if (!OpeningHours.isValidComparison(actualOpeningHours)) {
                throw new ParseException(OpeningHours.COMPARISON_CONSTRAINTS);
            }

            theOpeningHours = actualOpeningHours;
            break;
        }

        return new OpeningHours(theOpeningHours);
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
     * Parses a collection of {@code String restrictions} into a {@code Restrictions}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid or multiple restrictions are present.
     */
    public static Restrictions parseTheRestrictions(Collection<String> restrictions) throws ParseException {
        requireNonNull(restrictions);
        if (restrictions.size() > 1) {
            throw new ParseException(String.format(DUPLICATE_FIELDS, "Restrictions"));
        }

        if (restrictions.size() == 0) {
            return new Restrictions(Restrictions.DEFAULT_VALUE);
        }

        String theRestriction = "";

        for (String restriction : restrictions) {
            if (!Restrictions.isValidRestrictions(restriction)) {
                throw new ParseException(Restrictions.MESSAGE_CONSTRAINTS);
            }

            theRestriction = restriction;
            break;
        }

        return new Restrictions(theRestriction);
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
