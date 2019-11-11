package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.company.GstRegistrationNumber.isEmptyRepresentation;
import static seedu.address.model.company.GstRegistrationNumber.isValidGstRegistrationNumber;
import static seedu.address.model.company.RegistrationNumber.isValidRegistrationNumber;
import static seedu.address.model.pdfmanager.PdfManager.isValidDocument;
import static seedu.address.model.task.Task.DATE_FORMAT;
import static seedu.address.model.task.Task.DATE_FORMATTER_FOR_USER_INPUT;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Description;
import seedu.address.model.EventTime;
import seedu.address.model.company.GstRegistrationNumber;
import seedu.address.model.company.RegistrationNumber;
import seedu.address.model.pdfmanager.PdfManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final int MINUTES_IN_AN_HOUR = 60;
    public static final String HHMM_REGEX = "([0-9]?[0-9]):[0-5][0-9]";

    public static final String MESSAGE_INVALID_DATE_FORMAT =
            "Invalid Date format. Date format should be " + DATE_FORMAT + ".";

    public static final String MESSAGE_INVALID_ID = "ID should be a integer number and more than 0.";
    public static final String MESSAGE_INVALID_DURATION = "The format of the number of hours is either a decimal "
            + "number (e.g. 1.5) or in hh:MM (e.g. 1:30)";

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
     * Parses a {@code String description} into an {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(description)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String date} into an {@code LocalDate}.
     *
     * @throws ParseException if date format is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!isValidDate(trimmedDate)) {
            throw new ParseException(MESSAGE_INVALID_DATE_FORMAT);
        }
        return getDate(trimmedDate);
    }

    /**
     * Checks if {@code string date} is a valid date according to our date format standard.
     */
    public static boolean isValidDate(String date) {
        try {
            LocalDate tempDateTime = LocalDate.parse(date, DATE_FORMATTER_FOR_USER_INPUT);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

    private static LocalDate getDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER_FOR_USER_INPUT);
    }

    /**
     * Parse a number in string into an integer.
     *
     * @throws ParseException if the string cannot be parse into an integer.
     */
    public static int parseId(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        if (!isValidId(trimmedId)) {
            throw new ParseException(MESSAGE_INVALID_ID);
        }

        return Integer.parseInt(trimmedId);
    }

    /**
     * Checks if string can be parse into an integer and must be more than 0.
     */
    private static boolean isValidId(String id) {
        try {
            int tempId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return false;
        }

        return (Integer.parseInt(id) > 0);
    }

    /**
     * Checks if {@code String duration} is valid with a start and end time.
     */
    public static EventTime parseEventTime(String duration) throws ParseException {
        requireNonNull(duration);
        if (!duration.matches(EventTime.VALIDATION_REGEX)) {
            throw new ParseException(EventTime.MESSAGE_CONSTRAINTS);
        }

        EventTime candidate;
        //split string into 3 parts to get start time, "-" and end time
        List<String> times = Stream.of(duration.split("-")).map(String::trim).collect(Collectors.toList());

        try {
            candidate = EventTime.parse(times.get(0), times.get(1));
            if (candidate.getStart().compareTo(candidate.getEnd()) >= 0) {
                throw new ParseException(EventTime.MESSAGE_END_BEFORE_START);
            }
        } catch (NumberFormatException | DateTimeParseException nfe) {
            throw new ParseException(EventTime.MESSAGE_CONSTRAINTS);
        }

        return candidate;

    }

    /**
     * Parses a duration in hours, either 1.5 (a decimal), or 1:30 (in hh:MM) format.
     * @param input the string to be parsed
     * @return the parsed Duration
     * @throws ParseException when the String doesn't fit the required format
     */
    public static Duration parseDuration(String input) throws ParseException {
        input = input.trim();
        try {
            // either 1.5 or 1:30
            if (Pattern.matches(HHMM_REGEX, input)) {
                String[] hourMinute = input.split(":");
                long hour = Long.parseLong(hourMinute[0]);
                long minute = Long.parseLong(hourMinute[1]);

                if ((minute < 0 || hour < 0) || (minute == 0 && hour == 0)) {
                    throw new ParseException(MESSAGE_INVALID_DURATION);
                }

                return Duration.ofMinutes(hour * MINUTES_IN_AN_HOUR + minute);
            } else {
                long minutes = Math.round(Double.parseDouble(input) * MINUTES_IN_AN_HOUR);

                if (minutes <= 0) {
                    throw new ParseException(MESSAGE_INVALID_DURATION);
                }

                return Duration.ofMinutes(minutes);
            }
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }
    }

    /**
     * Parses a {@code String registrationNumber} into an {@code RegistrationNumber}.
     */
    public static RegistrationNumber parseRegistrationNumber(String registrationNumber) throws ParseException {
        requireNonNull(registrationNumber);
        String trimmedNo = registrationNumber.trim();
        if (!isValidRegistrationNumber(trimmedNo)) {
            throw new ParseException(RegistrationNumber.MESSAGE_CONSTRAINTS);
        }
        return new RegistrationNumber(trimmedNo);
    }

    /**
     * Parses a {@code String gstRegistrationNumber} into an {@code GstRegistrationNumber}.
     */
    public static GstRegistrationNumber parseGstRegistrationNumber(String gstRegistrationNumber) throws ParseException {
        requireNonNull(gstRegistrationNumber);
        String trimmedNo = gstRegistrationNumber.trim();
        if (!isValidGstRegistrationNumber(trimmedNo) && !isEmptyRepresentation(trimmedNo)) {
            throw new ParseException(GstRegistrationNumber.MESSAGE_CONSTRAINTS);
        }
        return new GstRegistrationNumber(trimmedNo);
    }

    /**
     * Parses a {@code String documentType} and checks if it is representing a valid document type.
     */
    public static String parsePdfDocumentType(String documentType) throws ParseException {
        requireNonNull(documentType);
        String trimmedDocumentType = documentType.trim().toLowerCase();
        if (!isValidDocument(trimmedDocumentType)) {
            throw new ParseException(PdfManager.MESSAGE_CONSTRAINTS);
        }

        return trimmedDocumentType;
    }


    public static int getNoOfPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return (int) Stream.of(prefixes).filter(prefix -> argumentMultimap.getValue(prefix).isPresent()).count();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Check if any Prefix is present. Return true if at least 1 Prefix is present.
     */
    public static boolean isAnyPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return (getNoOfPrefixesPresent(argumentMultimap, prefixes) > 0);
    }
}
