package io.xpire.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import io.xpire.commons.core.index.Index;
import io.xpire.commons.util.StringUtil;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Name;
import io.xpire.model.item.ReminderThreshold;
import io.xpire.model.item.sort.MethodOfSorting;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;

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
     * Parses a {@code String expiryDate} into an {@code ExpiryDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static ExpiryDate parseExpiryDate(String expiryDate) throws ParseException {
        requireNonNull(expiryDate);
        String trimmedDate = expiryDate.trim();
        if (!ExpiryDate.isValidExpiryDate(trimmedDate)) {
            throw new ParseException(ExpiryDate.MESSAGE_CONSTRAINTS);
        }
        return new ExpiryDate(trimmedDate);
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
        final Set<Tag> tagSet = new TreeSet<>(new TagComparator());
        for (String tagName : tags) {
            String trimmedTag = tagName.trim();
            if (!trimmedTag.isEmpty()) {
                tagSet.add(parseTag(trimmedTag));
            }
        }
        return tagSet;
    }

    /**
     * Parses a {@code String key} into a {@code MethodOfSorting}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code key} is invalid.
     */
    public static MethodOfSorting parseMethodOfSorting(String key) throws ParseException {
        requireNonNull(key);
        String trimmedMethodOfSorting = key.trim();
        if (!MethodOfSorting.isValidMethodOfSorting(trimmedMethodOfSorting)) {
            throw new ParseException(MethodOfSorting.MESSAGE_CONSTRAINTS);
        }
        return new MethodOfSorting(trimmedMethodOfSorting);
    }

    /**
     * Parses a {@code String reminderThreshold} into an {@code }.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code reminderThreshold} is invalid.
     */
    public static ReminderThreshold parseReminderThreshold(String reminderThreshold) throws ParseException {
        requireNonNull(reminderThreshold);
        String trimmedReminderThreshold = reminderThreshold.trim();
        if (!ReminderThreshold.isValidReminderThreshold(trimmedReminderThreshold)) {
            throw new ParseException(ReminderThreshold.MESSAGE_CONSTRAINTS);
        }
        return new ReminderThreshold(trimmedReminderThreshold);
    }
}
