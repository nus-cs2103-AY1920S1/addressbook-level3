package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Amount;
import seedu.address.model.person.Date;
import seedu.address.model.person.Description;
import seedu.address.model.person.PanelName;
import seedu.address.model.person.SortSequence;
import seedu.address.model.person.SortType;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.Frequency;
import seedu.address.ui.FontManager;
import seedu.address.ui.FontName;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be trimmed. if the specified index is invalid
     * (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Description parseDescription(String desc) throws ParseException {
        requireNonNull(desc);
        String trimmedDesc = desc.trim();
        if (!Description.isValidDescription(trimmedDesc)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDesc);
    }

    /**
     * Parses a stringAmt into an Amount.
     *
     * @param stringAmt the amount as a String.
     * @return an Amount.
     */
    public static Amount parseAmount(String stringAmt) {
        requireNonNull(stringAmt);
        double amt = Double.parseDouble(stringAmt);
        return new Amount(amt);
    }

    /**
     * Parses a date in String to Date.
     *
     * @param date the date as a String.
     * @return the specified date as Date.
     */
    public static Date parseDate(String date) {
        requireNonNull(date);
        return new Date(date);
    }

    /**
     * Parses a time in {@code Optional}
     *
     * @param time the time as a String.
     * @return the specified time as Time.
     */
    public static Date parseTime(Optional<String> time) {
        requireNonNull(time);
        return time.isPresent() ? new Date(time.get()) : Date.now();
    }

    /**
     * Parses a time in String to ArrayList.
     *
     * @param period the time as a String.
     * @return the specified time as Date.
     */
    public static Date parsePeriod(String period) {
        Date dateToParse = new Date(period, true);
        return dateToParse;
    }

    /**
     * Parses a frequency from String to Frequency.
     *
     * @param stringFreq the frequency as a String.
     * @return the specified frequency as Frequency.
     */
    public static Frequency parseFrequency(String stringFreq) {
        requireNonNull(stringFreq);
        return Frequency.parse(stringFreq);
    }

    /**
     * Parses a type of sorting in String to SortType.
     *
     * @param type the time as a String.
     * @return the specified time as SortType.
     */
    public static SortType parseSortType(String type) throws IllegalArgumentException {
        requireNonNull(type);
        return new SortType(type);
    }

    /**
     * Parses a type of sequencesorting in String to SortSequence.
     *
     * @param sequence the sequence of sorting as a String.
     * @return the specified time as SortSequence.
     */
    public static SortSequence parseSortSequence(String sequence) {
        requireNonNull(sequence);
        return new SortSequence(sequence);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing
     * whitespaces will be trimmed.
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
     * Parses {@code String panelName} into a {@code PanelName}.
     */
    public static PanelName parsePanelName(String panelName) throws ParseException {
        requireNonNull(panelName);
        String trimmedPanelName = panelName.trim();
        if (!PanelName.isValidPanelName(trimmedPanelName)) {
            throw new ParseException(PanelName.MESSAGE_CONSTRAINTS);
        }

        return PanelName.parse(trimmedPanelName);
    }

    /**
     * Parses {@code String fontName} into a {@code FontName}.
     */
    public static FontName parseFontName(String fontName) throws ParseException {
        requireNonNull(fontName);
        String trimmedFontName = fontName.trim();
        if (!FontManager.isValidFontName(trimmedFontName)) {
            throw new ParseException(FontManager.MESSAGE_CONSTRAINTS);
        }
        return new FontName(fontName);
    }

}
