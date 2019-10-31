package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.SortFilter;
import seedu.address.model.View;
import seedu.address.model.claim.Amount;
import seedu.address.model.claim.Description;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.commanditem.CommandTask;
import seedu.address.model.commanditem.CommandWord;
import seedu.address.model.commonvariables.Date;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.commonvariables.Phone;
import seedu.address.model.contact.Email;
import seedu.address.model.help.SecondaryCommand;
import seedu.address.model.help.Type;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final String MESSAGE_INVALID_VIEW = "View is not recognised.";

    public static final String MESSAGE_INVALID_FILTER = "Filter is not recognised";

    public static final String MESSAGE_INVALID_SHORTCUT = "Shortcut is not recognised.";

    private static int viewIndex;

    private static int filterIndex;
    /**
     * Checks if the parsed argument is a valid view
     * @param view
     * @return
     */
    public static boolean checkView(String view) {

        if (view.equals("contacts")) {
            viewIndex = 1;
            return true;
        } else if (view.equals("claims")) {
            viewIndex = 2;
            return true;
        } else if (view.equals("incomes")) {
            viewIndex = 3;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the parsed argument is a valid filter
     * @param filter
     * @return
     */
    public static boolean checkFilter(String filter) {

        if (filter.equals("name")) {
            filterIndex = 1;
            return true;
        } else if (filter.equals("date")) {
            filterIndex = 2;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Parsers a View.
     * @param view View command
     * @return Trimmed view command
     * @throws ParseException If the command cannot be parsed.
     */
    public static View parseView(String view) throws ParseException {
        String trimmedView = view.trim();
        if (checkView(trimmedView)) {
            return new View(view, viewIndex);
        } else {
            throw new ParseException(MESSAGE_INVALID_VIEW);
        }
    }

    /**
     * Parses a Filter
     */
    public static SortFilter parseFilter(String input) throws ParseException {
        String trimmedFilter = input.trim();
        if (checkFilter(trimmedFilter)) {
            return new SortFilter(input, filterIndex);
        } else {
            throw new ParseException(MESSAGE_INVALID_FILTER);
        }
    }

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
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String command} into an {@code Command}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code command} is invalid.
     */
    public static SecondaryCommand parseCommand(String command) throws ParseException {
        requireNonNull(command);
        String trimmedCommand = command.trim();


        if (!SecondaryCommand.isValidSecondaryCommand(trimmedCommand)) {
            throw new ParseException(SecondaryCommand.MESSAGE_CONSTRAINTS);
        }
        return new SecondaryCommand(trimmedCommand);
    }

    /**
     * Parses a {@code String shortcut} into an {@code CommandItem}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given shortcut does not exist.
     */
    public static CommandItem parseShortcut(String shortcut) throws ParseException {
        requireNonNull(shortcut);
        String trimmedShortcut = shortcut.trim();
        TreeMap<String, String> commands = FinSecParser.getCommandList();
        if (!commands.containsKey(trimmedShortcut)) {
            throw new ParseException(MESSAGE_INVALID_SHORTCUT);
        } else {
            return new CommandItem(new CommandWord(trimmedShortcut), new CommandTask(commands.get(trimmedShortcut)));
        }
    }

    /**
     * Parses a {@code String command} into an {@code Command}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code command} is invalid.
     */
    public static Type parseType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();


        if (!Type.isValidType(trimmedType)) {
            throw new ParseException(Type.MESSAGE_CONSTRAINTS);
        }
        return new Type(trimmedType);
    }

    /**
     * Parses a {@code String description} into an {@code Description}.
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
     * Parses an {@code String amount} into an {@code Amount}.
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
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailling whitespaces will be trimmed.
     *
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
}
