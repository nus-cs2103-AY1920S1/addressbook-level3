package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.DateTime;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.record.Concentration;
import seedu.address.model.record.Height;
import seedu.address.model.record.RecordType;
import seedu.address.model.record.Weight;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses a {@code String recordType} into a {@code RecordType}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code recordType} does not match any of the enums.
     */
    public static RecordType parseRecordType(String recordType) throws ParseException {
        requireNonNull(recordType);
        String trimmedRType = recordType.split(" ")[0].trim();
        try {
            return RecordType.valueOf(trimmedRType);
        } catch (IllegalArgumentException e) {
            throw new ParseException("System does not accommodate such a record type.");
        }
    }

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
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String address} into an {@code Address}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String email} into an {@code Email}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String dateTime} into an {@code DateTime}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static DateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!DateTime.isValidDateTime(trimmedDateTime)) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS);
        }
        LocalDate ld = LocalDate.of(1970, Month.JANUARY, 1);
        LocalTime lt = LocalTime.of(8, 0, 0);
        return new DateTime(ld, lt);
    }

    /**
     * Parses a {@code String concentration} into an {@code Concentration}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code concentration} is invalid.
     */
    public static Concentration parseConcentration(String concentration) throws ParseException {
        String trimmedConcentration = concentration.trim();
        if (!Concentration.isValidConcentration(trimmedConcentration)) {
            throw new ParseException(Concentration.MESSAGE_CONSTRAINTS);
        }
        return new Concentration(trimmedConcentration);
    }

    /**
     * Parses a {@code String height} into an {@code Height}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code height} is invalid.
     */
    public static Height parseHeight(String height) throws ParseException {
        String trimmedHeight = height.trim();
        if (!Height.isValidHeight(trimmedHeight)) {
            throw new ParseException(Height.MESSAGE_CONSTRAINTS);
        }
        return new Height(trimmedHeight);
    }

    /**
     * Parses a {@code String weight} into an {@code Weight}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weight} is invalid.
     */
    public static Weight parseWeight(String weight) throws ParseException {
        String trimmedWeight = weight.trim();
        if (!Weight.isValidWeight(trimmedWeight)) {
            throw new ParseException(Weight.MESSAGE_CONSTRAINTS);
        }
        return new Weight(trimmedWeight);
    }
}
