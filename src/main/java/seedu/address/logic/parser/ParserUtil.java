package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.customer.ContactNumber;
import seedu.address.model.customer.CustomerName;
import seedu.address.model.customer.Email;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.schedule.Venue;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_CALENDAR = "Calendar is not in the correct format.";


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
     * Parses a {@code String phone} into a {@code phone}.
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
     * Parses a {@code String contactNumber} into a {@code contactNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code contactNumber} is invalid.
     */
    public static ContactNumber parseContactNumber(String contactNumber) throws ParseException {
        requireNonNull(contactNumber);
        String trimmedContactNumber = contactNumber.trim();
        if (!ContactNumber.isValidContactNumber(trimmedContactNumber)) {
            throw new ParseException(ContactNumber.MESSAGE_CONSTRAINTS);
        }
        return new ContactNumber(trimmedContactNumber);
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
     * Parses a {@code String customerName} into a {@code customerName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code customerName} is invalid.
     */
    public static CustomerName parseCustomerName(String customerName) throws ParseException {
        requireNonNull(customerName);
        String trimmedCustomerName = customerName.trim();
        if (!CustomerName.isValidCustomerName(trimmedCustomerName)) {
            throw new ParseException(CustomerName.MESSAGE_CONSTRAINTS);
        }
        return new CustomerName(trimmedCustomerName);
    }

    /**
     * Parses a {@code String calendar} into a {@code Calendar}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code calendar} is invalid.
     */
    public static Calendar parseCalendar(String calendar) throws ParseException {
        requireNonNull(calendar);
        String trimmedCalendar = calendar.trim();

        String[] stringCalendar = trimmedCalendar.split("\\.");
        if (stringCalendar.length != 5) {
            throw new ParseException(MESSAGE_INVALID_CALENDAR);
        }

        int[] input = new int[5];
        LocalDateTime localDateTime;
        try {
            for (int index = 0; index < 5; index++) {
                input[index] = Integer.parseInt(stringCalendar[index]);
            }
            localDateTime = LocalDateTime.of(input[0], input[1], input[2], input[3], input[4]);
        } catch (NumberFormatException | DateTimeException e) {
            throw new ParseException(MESSAGE_INVALID_CALENDAR);
        }

        return new Calendar.Builder().setDate(input[0], input[1], input[2])
                .setTimeOfDay(input[3], input[4], 0).build();
    }

    /**
     * Parses a {@code String venue} into a {@code Venue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code venue} is invalid.
     */
    public static Venue parseVenue(String venue) throws ParseException {
        requireNonNull(venue);
        String trimmedVenue = venue.trim();
        if (!Venue.isValidVenue(trimmedVenue)) {
            throw new ParseException(Venue.MESSAGE_CONSTRAINTS);
        }
        return new Venue(trimmedVenue);
    }

}
