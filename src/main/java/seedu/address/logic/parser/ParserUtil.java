package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.Amount;
import seedu.address.model.person.Date;
import seedu.address.model.person.Description;
import seedu.address.model.person.PanelName;
import seedu.address.model.person.Period;
import seedu.address.model.person.SortSequence;
import seedu.address.model.person.SortType;
import seedu.address.model.reminders.Reminder;
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
     * Parses {@code String indexes} into a {@code List<Index}.
     */
    public static List<Index> parseIndexes(String indexes) throws ParseException {
        String[] indexArr = indexes.split(",");
        List<Index> indexList = new ArrayList<>();
        for (String indexString : indexArr) {
            indexList.add(parseIndex(indexString));
        }
        return indexList;
    }

    /**
<<<<<<< HEAD
     * Parses {@code Collection<String> tags} into a {@code List<Index>}.
     */
    public static List<Index> parseIndexes(Collection<String> indexes) throws ParseException {
        requireNonNull(indexes);
        final List<Index> indexList = new ArrayList<>();
        for (String indexString : indexes) {
            indexList.add(parseIndex(indexString));
        }
        return indexList;
    }
    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
=======
     * Parses a {@code String name} into a {@code Name}. Leading and trailing
     * whitespaces will be trimmed.
>>>>>>> b2ec7d64249172d26ceccb183976364b8bb21fc9
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
     * Parses a string with 2 dates
     * @param dates
     * @return List of length 2.
     */
    public static List<Date> parseStartAndEndDate(String dates) {
        String[] dateArr = dates.split(",");
        List<Date> startAndEnd = Arrays
                .stream(dateArr).map(dateString -> new Date(dateString.trim())).collect(Collectors.toList());
        return startAndEnd;
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
     * Parses a time in String to ArrayList.
     * @param period the time as a String.
     * @return the specified time as Date.
     */
    public static Period parsePeriods(String period) throws ParseException {
        requireNonNull(period);
        String trimmedPeriod = period.trim();

        long duration;
        char interval;

        duration = Long.parseLong(period.substring(0, period.length() - 1));
        interval = period.charAt(period.length() - 1);

        return new Period(duration, interval);
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
<<<<<<< HEAD
     * Parses {@code String tags} into a {@code List<Tag>}.
     */
    public static List<Tag> parseTags(String tags) throws ParseException {
        requireNonNull(tags);
        final List<String> tagNames = Arrays.asList(tags.trim().split(","));
        final List<Tag> tagList = new ArrayList();
        for (String tagName : tagNames) {
            tagList.add(parseTag(tagName));
        }
        return tagList;
    }


    /**
     * Parses {@code String keywords} into a {@code List<String>}.
     */
    public static List<String> parseKeyWords(String keyWords) throws ParseException {
        requireNonNull(keyWords);
        final List<String> keyWordList = Arrays.asList(keyWords.trim().split(","));
        return keyWordList;
    }
    /**
     * Parses {@code String panelNamee} into a {@code PanelName}.
=======
     * Parses {@code String panelName} into a {@code PanelName}.
>>>>>>> b2ec7d64249172d26ceccb183976364b8bb21fc9
     */
    public static PanelName parsePanelName(String panelName) throws ParseException {
        requireNonNull(panelName);
        String trimmedPanelName = panelName.trim();
        if (!PanelName.isValidPanelName(trimmedPanelName)) {
            throw new ParseException(PanelName.MESSAGE_CONSTRAINTS);
        }

        // TODO rewrite this
        // standardise panel names
        ArrayList<String> aliasesForWishlist = new ArrayList<>(Arrays.asList("wishlist", "wish", "wishes", "w"));
        if (aliasesForWishlist.contains(trimmedPanelName)) {
            trimmedPanelName = "wishlist";
        }

        ArrayList<String> aliasesForBudget = new ArrayList<>(Arrays.asList("budget", "budgets", "b"));
        if (aliasesForBudget.contains(trimmedPanelName)) {
            trimmedPanelName = "budget";
        }

        ArrayList<String> aliasesForReminder = new ArrayList<>(Arrays.asList("reminder", "reminders", "r"));
        if (aliasesForReminder.contains(trimmedPanelName)) {
            trimmedPanelName = "reminder";
        }

        return new PanelName(trimmedPanelName);
    }

    /**
<<<<<<< HEAD
     * Parses {@code String trackerType} into {@code TrackerType}
     * @param trackerType
     * @return
     * @throws ParseException
     */
    public static Reminder.TrackerType parseTrackerType(String trackerType) throws ParseException {
        requireNonNull(trackerType);
        return Reminder.TrackerType.parse(trackerType);
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
