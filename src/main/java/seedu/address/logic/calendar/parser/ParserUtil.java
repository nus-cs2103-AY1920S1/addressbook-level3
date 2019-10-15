package seedu.address.logic.calendar.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.calendar.parser.exceptions.ParseException;
import seedu.address.model.calendar.person.TaskDescription;
import seedu.address.model.calendar.person.TaskPlace;
import seedu.address.model.calendar.person.TaskTime;
import seedu.address.model.calendar.person.TaskTitle;
import seedu.address.model.calendar.tag.TaskTag;

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
     * Parses a {@code String name} into a {@code TaskTitle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static TaskTitle parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!TaskTitle.isValidName(trimmedName)) {
            throw new ParseException(TaskTitle.MESSAGE_CONSTRAINTS);
        }
        return new TaskTitle(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code TaskTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static TaskTime parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!TaskTime.isValidPhone(trimmedPhone)) {
            throw new ParseException(TaskTime.MESSAGE_CONSTRAINTS);
        }
        return new TaskTime(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code TaskPlace}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static TaskPlace parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!TaskPlace.isValidAddress(trimmedAddress)) {
            throw new ParseException(TaskPlace.MESSAGE_CONSTRAINTS);
        }
        return new TaskPlace(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code TaskDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static TaskDescription parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!TaskDescription.isValidEmail(trimmedEmail)) {
            throw new ParseException(TaskDescription.MESSAGE_CONSTRAINTS);
        }
        return new TaskDescription(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code TaskTag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static TaskTag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!TaskTag.isValidTagName(trimmedTag)) {
            throw new ParseException(TaskTag.MESSAGE_CONSTRAINTS);
        }
        return new TaskTag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<TaskTag>}.
     */
    public static Set<TaskTag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<TaskTag> taskTagSet = new HashSet<>();
        for (String tagName : tags) {
            taskTagSet.add(parseTag(tagName));
        }
        return taskTagSet;
    }
}
