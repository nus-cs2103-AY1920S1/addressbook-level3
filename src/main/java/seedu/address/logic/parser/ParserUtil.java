package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Tag;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.Timing;
import seedu.address.model.person.parameters.Address;
import seedu.address.model.person.parameters.Email;
import seedu.address.model.person.parameters.Name;
import seedu.address.model.person.parameters.PatientReferenceId;
import seedu.address.model.person.parameters.PersonReferenceId;
import seedu.address.model.person.parameters.Phone;
import seedu.address.model.person.parameters.StaffReferenceId;

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
     * Returns the element at the {@code oneBasedIndex} index of a given {@code listofEntries}
     *
     * @throws ParseException if the specified index is out of bounds of the list.
     */
    public static <T> T getEntryFromList(List<T> listOfEntries, Index oneBasedIndex) throws ParseException {

        if (oneBasedIndex.getZeroBased() >= listOfEntries.size()) {
            throw new ParseException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return listOfEntries.get(oneBasedIndex.getZeroBased());
    }

    /**
     * Parses a {@code String refId} into an {@code StaffReferenceId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code StaffReferenceId} is invalid.
     */
    public static StaffReferenceId parseStaffReferenceId(String staffRefId) throws ParseException {
        requireNonNull(staffRefId);
        String trimmedRefId = staffRefId.trim().toUpperCase();
        if (!PersonReferenceId.isValidId(trimmedRefId)) {
            throw new ParseException(PersonReferenceId.MESSAGE_CONSTRAINTS);
        } else if (!StaffReferenceId.isValidStaffId(trimmedRefId)) {
            throw new ParseException(StaffReferenceId.MESSAGE_CONSTRAINTS);
        }
        return new StaffReferenceId(trimmedRefId);
    }

    /**
     * Parses a {@code String refId} into an {@code PatientReferenceId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code PatientReferenceId} is invalid.
     */
    public static PatientReferenceId parsePatientReferenceId(String patientRefId) throws ParseException {
        requireNonNull(patientRefId);
        String trimmedRefId = patientRefId.trim().toUpperCase();
        if (!PersonReferenceId.isValidId(trimmedRefId)) {
            throw new ParseException(PersonReferenceId.MESSAGE_CONSTRAINTS);
        } else if (!PatientReferenceId.isValidPatientId(trimmedRefId)) {
            throw new ParseException(PatientReferenceId.MESSAGE_CONSTRAINTS);
        }
        return new PatientReferenceId(trimmedRefId);
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
     * checks the starting and ending time of the appointment is a valid time.
     *
     * @param start which the string startTime of the appointment.
     * @param end   which the string endTime of the appointment.
     * @return the valid Appointment object.
     * @throws ParseException If an error occurs during command parsering.
     */
    public static Timing parseTiming(String start, String end) throws ParseException {
        requireNonNull(start, end);
        DateTime startTiming = DateTime.tryParseSimpleDateFormat(start);
        if (startTiming == null) {
            throw new ParseException("The start " + DateTime.MESSAGE_CONSTRAINTS);
        }

        DateTime endTiming = DateTime.tryParseSimpleDateFormat(end);
        if (endTiming == null) {
            throw new ParseException("The end " + DateTime.MESSAGE_CONSTRAINTS);
        }

        if (!Timing.isValidTiming(startTiming, endTiming)) {
            throw new ParseException(Timing.MESSAGE_CONSTRAINTS);
        }
        return new Timing(startTiming, endTiming);
    }

}
