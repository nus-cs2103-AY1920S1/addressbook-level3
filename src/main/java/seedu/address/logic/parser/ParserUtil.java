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
import seedu.address.model.performance.Event;
import seedu.address.model.performance.Timing;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.tag.Tag;
import seedu.address.ui.feature.Feature;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be trimmed.
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
     * Parses {@code oneBasedIndexes} into a {@code List<Index>} and returns it.
     * Leading and trailing whitespaces will be trimmed.
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
     * Parses {@code featureName} into a {@code Feature} and returns it. Leading and
     * trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified feature name is invalid (not calendar/attendance/performance/records).
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
     * Parses {@code date} into a {@code AthletickDate} and returns it. Leading and
     * trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified date is invalid (not length of 6 or 8).
     */
    public static AthletickDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        switch (trimmedDate.length()) {
        case 6:
            return parseDateTypeTwo(trimmedDate);
        case 8:
            return parseDateTypeOne(trimmedDate);
        default:
            throw new ParseException(
                    String.format(AthletickDate.MESSAGE_CONSTRAINTS, AthletickDate.DATE_FORMAT_GENERAL));
        }
    }

    /**
     * Parses {@code date} into a {@code AthletickDate} and returns it. Leading and
     * trailing whitespaces will be trimmed. Used when date is in format DDMMYYYY.
     *
     * @throws ParseException if the specified date is invalid.
     */
    public static AthletickDate parseDateTypeOne(String date) throws ParseException {
        try {
            date = date.trim();
            if (date.length() != 8) {
                throw new java.text.ParseException("Incorrect date length", 8);
            }
            return constructAthletickDateTypeOne(date);
        } catch (java.text.ParseException e) {
            throw new ParseException(AthletickDate.WRONG_DATE_FORMAT + " "
                    + String.format(AthletickDate.MESSAGE_CONSTRAINTS, AthletickDate.DATE_FORMAT_TYPE_ONE) + "\n"
                    + AthletickDate.MONTH_CONSTRAINTS + "\n" + AthletickDate.YEAR_CONSTRAINTS);
        }
    }

    /**
     * Constructs an {@code AthletickDate} of type 1 from {@code date}.
     * @param date Specified date
     * @return AthletickDate Date used by application
     * @throws java.text.ParseException if specified date is invalid
     */
    private static AthletickDate constructAthletickDateTypeOne(String date) throws java.text.ParseException {
        SimpleDateFormat fullDate = new SimpleDateFormat("ddMMyyyy");
        fullDate.setLenient(false);
        Date d = fullDate.parse(date);
        int day = Integer.parseInt(new SimpleDateFormat("d").format(d));
        int month = Integer.parseInt(new SimpleDateFormat("M").format(d));
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(d));
        String mth = new SimpleDateFormat("MMMM").format(d);
        return new AthletickDate(day, month, year, 1, mth);
    }

    /**
     * Parses {@code date} into a {@code AthletickDate} and returns it. Leading and
     * trailing whitespaces will be trimmed. Used when date is in format MMYYYY.
     *
     * @throws ParseException if the specified date is invalid.
     */
    public static AthletickDate parseDateTypeTwo(String date) throws ParseException {
        try {
            date = date.trim();
            if (date.length() != 6) {
                throw new java.text.ParseException("Incorrect date length", 6);
            }
            return constructAthletickDateTypeTwo(date);
        } catch (java.text.ParseException e) {
            throw new ParseException(AthletickDate.WRONG_DATE_FORMAT + " "
                    + String.format(AthletickDate.MESSAGE_CONSTRAINTS, AthletickDate.DATE_FORMAT_TYPE_TWO) + "\n"
                    + AthletickDate.MONTH_CONSTRAINTS + "\n" + AthletickDate.YEAR_CONSTRAINTS);
        }
    }

    /**
     * Constructs an {@code AthletickDate} of type 2 from {@code date}.
     * @param date Specified date
     * @return AthletickDate Date used by application
     * @throws java.text.ParseException if specified date is invalid
     */
    private static AthletickDate constructAthletickDateTypeTwo(String date) throws java.text.ParseException {
        SimpleDateFormat monthYear = new SimpleDateFormat("MMyyyy");
        monthYear.setLenient(false);
        Date d = monthYear.parse(date);
        int month = Integer.parseInt(new SimpleDateFormat("M").format(d));
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(d));
        String mth = new SimpleDateFormat("MMMM").format(d);
        return new AthletickDate(0, month, year, 2, mth);
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing
     * whitespaces will be trimmed.
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
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing
     * whitespaces will be trimmed.
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
     * Parses a {@code String address} into an {@code Address}. Leading and trailing
     * whitespaces will be trimmed.
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
     * Parses a {@code String email} into an {@code Email}. Leading and trailing
     * whitespaces will be trimmed.
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
     * Parses a {@code String gender} into an {@code Gender}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
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
     * Parses {@code String event} into a {@code Event}
     */
    public static String parseEvent(String event) throws ParseException {
        requireNonNull(event);
        String trimmedEvent = event.trim().toLowerCase();
        if (!Event.isValidName(trimmedEvent)) {
            throw new ParseException(Event.MESSAGE_CONSTRAINTS);
        }
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
     * Parses a {@code String photo} into an {@code Photo}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code photo} is invalid.
     */
    public static Photo parsePhoto(String imageFilePath) throws ParseException {
        requireNonNull(imageFilePath);
        String trimmedPath = imageFilePath.trim();
        if (!Photo.isValidFilePath(trimmedPath)) {
            throw new ParseException(Photo.MESSAGE_CONSTRAINTS);
        }
        if (!Photo.isFileExist(trimmedPath)) {
            throw new ParseException(Photo.NO_FILE_EXIST_MESSAGE);
        }
        return new Photo(trimmedPath);
    }
}
