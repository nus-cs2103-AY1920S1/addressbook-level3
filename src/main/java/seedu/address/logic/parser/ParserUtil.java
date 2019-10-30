package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.feature.Feature;
import seedu.address.model.performance.Timing;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses {@code oneBasedIndexes} into an {@code List<Index>} and returns it. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws ParseException if any index is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndexes(String oneBasedIndexes) throws ParseException {
        String[] indexes = oneBasedIndexes.trim().split("\\s+");
        List<Index> listOfIndexes = new ArrayList<>();
        for (String indexString : indexes) {
            Index parsedIndex = parseIndex(indexString);
            listOfIndexes.add(parsedIndex);
        }
        return listOfIndexes;
    }

    /**
     * Parses {@code featureName} into a {@code Feature} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the specified feature name is invalid (not calendar / attendance
     *                        / performance).
     */
    public static Feature parseFeature(String input) throws ParseException {
        requireNonNull(input);
        String[] inputArray = input.split("\\s+", 2);
        if (!(inputArray[0].equals("calendar")
            | inputArray[0].equals("attendance")
            | inputArray[0].equals("performance")
            | inputArray[0].equals("records"))) {
            throw new ParseException(Feature.MESSAGE_CONSTRAINTS);
        }
        if (inputArray[0].equals("records")) {
            if (inputArray.length == 1) {
                throw new ParseException(Feature.MESSAGE_NO_EVENT);
            }
            return new Feature(inputArray[0], parseEvent(inputArray[1]));
        }
        return new Feature(inputArray[0]);
    }

    /**
     * Parses {@code date} into a {@code AthletickDate} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the specified date is invalid (not length of 6 or 8).
     */
    public static AthletickDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (trimmedDate.length() == 6 || trimmedDate.length() == 8) {
            SimpleDateFormat fullDate = new SimpleDateFormat("ddMMyyyy");
            fullDate.setLenient(false);
            SimpleDateFormat monthYear = new SimpleDateFormat("MMyyyy");
            monthYear.setLenient(false);
            try {
                if (trimmedDate.length() == 8) {
                    Date d = fullDate.parse(trimmedDate);
                    int day = Integer.parseInt(new SimpleDateFormat("d").format(d));
                    int month = Integer.parseInt(new SimpleDateFormat("M").format(d));
                    int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(d));
                    int type = 1;
                    String mth = new SimpleDateFormat("MMMM").format(d);
                    return new AthletickDate(day, month, year, type, mth);
                } else if (date.length() == 6) {
                    Date d2 = monthYear.parse(trimmedDate);
                    int day = Integer.parseInt("0");
                    int month = Integer.parseInt(new SimpleDateFormat("M").format(d2));
                    int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(d2));
                    int type = 2;
                    String mth = new SimpleDateFormat("MMMM").format(d2);
                    return new AthletickDate(day, month, year, type, mth);
                }
            } catch (java.text.ParseException pe) {
                throw new ParseException(AthletickDate.WRONG_DATE_FORMAT + " " + AthletickDate.MESSAGE_CONSTRAINTS);
            }
        } else {
            throw new ParseException(AthletickDate.MESSAGE_CONSTRAINTS);
        }
        // should not reach here
        return null;
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
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
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
     * Parses {@code String event} into a {@code Event}
     */
    public static String parseEvent(String event) {
        requireNonNull(event);
        String trimmedEvent = event.trim().toLowerCase();
        return trimmedEvent;
    }

    /**
     * Parses {@code String timing} into a {@code Timing}
     */
    public static Timing parseTiming(String timing) throws ParseException {
        requireNonNull(timing);
        String trimmedTiming = timing.trim();
        if (!Timing.isValidTiming(trimmedTiming)) {
            throw new ParseException(Timing.MESSAGE_CONSTRAINTS);
        }
        return new Timing(trimmedTiming);
    }

    /**
     * Parses a {@code String photo} into an {@code Photo}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code photo} is invalid.
     */
    public static Photo parsePhoto(String imageFilePath) throws ParseException {
        requireNonNull(imageFilePath);
        String trimmedPath = imageFilePath.trim();
        if (!Photo.isValidFilePath(trimmedPath)) {
            throw new ParseException(Photo.MESSAGE_CONSTRAINTS);
        }
        return new Photo(trimmedPath);
    }
}
