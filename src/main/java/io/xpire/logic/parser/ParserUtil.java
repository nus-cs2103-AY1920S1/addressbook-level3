package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import io.xpire.commons.core.index.Index;
import io.xpire.commons.util.StringUtil;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.ReminderThreshold;
import io.xpire.model.item.sort.MethodOfSorting;
import io.xpire.model.item.sort.XpireMethodOfSorting;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNumeric(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
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

    //@@author febee99
    /**
     * Parses a {@code String expiryDate} into an {@code ExpiryDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static ExpiryDate parseExpiryDate(String expiryDate) throws ParseException {
        requireNonNull(expiryDate);
        String trimmedDate = expiryDate.trim();
        if (expiryDate.isEmpty()) {
            throw new ParseException(ExpiryDate.MESSAGE_CONSTRAINTS_FORMAT);
        }
        if (!ExpiryDate.isValidFormatExpiryDate(trimmedDate)) {
            throw new ParseException(ExpiryDate.MESSAGE_CONSTRAINTS_FORMAT);
        }
        if (!ExpiryDate.isValidExpiryDate(trimmedDate)) {
            throw new ParseException(ExpiryDate.MESSAGE_CONSTRAINTS_NO_SUCH_DATE);
        }
        if (!ExpiryDate.isValidLowerRangeExpiryDate(trimmedDate)) {
            throw new ParseException(ExpiryDate.MESSAGE_CONSTRAINTS_LOWER);
        }
        if (!ExpiryDate.isValidUpperRangeExpiryDate(trimmedDate)) {
            throw new ParseException(ExpiryDate.MESSAGE_CONSTRAINTS_UPPER);
        }
        return new ExpiryDate(trimmedDate);
    }

    //@@author liawsy
    /**
     * Parses a {@code String quantity} into an {@code Quantity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code quantity} is invalid.
     */
    public static Quantity parseQuantity(String quantity) throws ParseException {
        requireNonNull(quantity);
        String trimmedQuantity = quantity.trim();
        if (!StringUtil.isUnsignedNumericWithoutLeadingZeroes(trimmedQuantity)) {
            throw new ParseException(Quantity.MESSAGE_CONSTRAINTS);
        }
        if (Quantity.isNumericButExceedQuantity(trimmedQuantity)) {
            throw new ParseException(Quantity.MESSAGE_QUANTITY_LIMIT);
        }
        if (!Quantity.isValidQuantity(trimmedQuantity)) {
            throw new ParseException(Quantity.MESSAGE_QUANTITY_LIMIT);
        }
        return new Quantity(trimmedQuantity);
    }

    //@@author Kalsyc
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
        String sentenceCaseTag = StringUtil.convertToSentenceCase(trimmedTag);
        return new Tag(sentenceCaseTag);
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

    //@@author febee99
    /**
     * Parses a {@code String key} into a {@code MethodOfSorting}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code key} is invalid.
     */
    public static XpireMethodOfSorting parseMethodOfSorting(String key) throws ParseException {
        requireNonNull(key);
        String trimmedMethodOfSorting = key.trim();
        if (!MethodOfSorting.isValidMethodOfSorting(trimmedMethodOfSorting)) {
            Set<String> allowedArgs = new TreeSet<>(Arrays.asList(
                    XpireMethodOfSorting.SORT_NAME, XpireMethodOfSorting.SORT_DATE));
            String output = StringUtil.findSimilar(key, allowedArgs, 1);
            throw new ParseException(XpireMethodOfSorting.MESSAGE_CONSTRAINTS + output);
        }
        return new XpireMethodOfSorting(trimmedMethodOfSorting);
    }

    //@@author JermyTan
    /**
     * Parses a {@code String reminderThreshold} into an {@code ReminderThreshold} object.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param reminderThreshold A number in string.
     * @return A valid reminder threshold.
     * @throws ParseException If the given {@code reminderThreshold} is invalid.
     */
    public static ReminderThreshold parseReminderThreshold(String reminderThreshold) throws ParseException {
        requireNonNull(reminderThreshold);
        String trimmedReminderThreshold = reminderThreshold.trim();
        if (!ReminderThreshold.isValidReminderThreshold(trimmedReminderThreshold)) {
            throw new ParseException(ReminderThreshold.MESSAGE_CONSTRAINTS);
        }
        return new ReminderThreshold(trimmedReminderThreshold);
    }

    //@@author Kalsyc
    /**
     * Parses tag from user input in the form of "#Tag1 #Tag2"
     *
     * @param arg Argument that contains tags.
     * @return Set containing parsed tags from user input.
     * @throws ParseException if tags cannot be parsed properly.
     */
    public static Set<Tag> parseTagsFromInput(String arg) throws ParseException {
        Set<Tag> set;
        String tagInput = arg.trim();
        String[] tags = tagInput.split("#");
        String[] copiedTags;
        try {
            copiedTags = Arrays.copyOfRange(tags, 1, tags.length); //to get rid of empty string
        } catch (IllegalArgumentException e) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        set = ParserUtil.parseTags(Arrays.asList(copiedTags));
        return set;
    }
}
