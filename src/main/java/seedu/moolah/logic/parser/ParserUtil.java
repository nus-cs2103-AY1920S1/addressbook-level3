package seedu.moolah.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;

import seedu.moolah.commons.core.index.Index;
import seedu.moolah.commons.util.StringUtil;
import seedu.moolah.logic.commands.general.HelpCommand;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.model.budget.BudgetPeriod;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.menu.Menu;
import seedu.moolah.model.menu.MenuItem;
import seedu.moolah.model.statistics.Mode;


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
        if (!Category.isValidCategoryName(trimmedCategory)) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(trimmedCategory);
    }


    /**
     * Parses {@code String aliasName} and {@code String input} into a user defined {@code Alias}.
     *
     * @throws ParseException if the given {@code aliasName} is invalid.
     */
    public static Alias parseAlias(String aliasName, String input) throws ParseException {
        if (!Alias.isValidAliasName(aliasName)) {
            throw new ParseException(Alias.MESSAGE_NAME_CONSTRAINTS);
        }
        if (!Alias.isValidInput(input)) {
            throw new ParseException(Alias.MESSAGE_INPUT_CONSTRAINTS);
        }
        return new Alias(aliasName, input);
    }

    /**
     * Parses a {@code String timestamp} into a {@code Timestamp}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code timestamp} is invalid.
     */
    public static Timestamp parseTimestamp(String timestamp) throws ParseException {
        requireNonNull(timestamp);
        String trimmedTimestamp = timestamp.trim();
        Optional<Timestamp> potentialTimestamp = Timestamp.createTimestampIfValid(trimmedTimestamp);
        if (potentialTimestamp.isPresent()) {
            return potentialTimestamp.get();
        } else {
            throw new ParseException(Timestamp.MESSAGE_CONSTRAINTS_GENERAL);
        }
    }

    /**
     * Parses a {@code String mode} into a {@code Mode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code mode} is invalid.
     */
    public static Mode parseMode(String mode) throws ParseException {
        requireNonNull(mode);
        String trimmedMode = mode.trim();
        if (!Mode.isValidMode(trimmedMode)) {
            throw new ParseException(Mode.MESSAGE_CONSTRAINTS);
        }
        return new Mode(trimmedMode);
    }

    /**
     * Parses {@code String period} into a {@code BudgetPeriod}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code period} is invalid.
     */
    public static BudgetPeriod parsePeriod(String period) throws ParseException {
        String trimmedPeriod = period.trim();
        switch (trimmedPeriod) {
        case "day":
            return BudgetPeriod.DAY;
        case "week":
            return BudgetPeriod.WEEK;
        case "month":
            return BudgetPeriod.MONTH;
        case "year":
            return BudgetPeriod.YEAR;
        case "infinity":
            return BudgetPeriod.INFINITY;
        default:
            throw new ParseException(Timestamp.MESSAGE_CONSTRAINTS_PERIOD);
        }
    }

    /**
     * Parses user input into Input with a command word and argument attributes.
     * @param input the user input to parse
     * @return The Input with the command word and arguments
     * @throws ParseException if invalid format
     */
    public static Input parseInput(String input) throws ParseException {
        final Matcher matcher = Input.BASIC_COMMAND_FORMAT.matcher(input.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        return new Input(commandWord, arguments);
    }

    /**
     * Parses user input into a corresponding {@code MenuItem} that matches the description given.
     * @param input the user input to be parsed
     * @return the corresponding {@code MenuItem}
     * @throws ParseException if the given input is invalid
     */
    public static MenuItem parseMenuItem(String input) throws ParseException {
        requireNonNull(input);

        try {
            Description desc = parseDescription(input);
            return Menu.findMenuItemByDescription(desc);
        } catch (ParseException | NoSuchElementException e) {
            throw new ParseException(MenuItem.MESSAGE_CONSTRAINTS);
        }
    }

}
