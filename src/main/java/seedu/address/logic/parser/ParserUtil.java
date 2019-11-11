package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.body.BodyStatus;
import seedu.address.model.entity.body.Nric;
import seedu.address.model.entity.fridge.FridgeStatus;
import seedu.address.model.entity.worker.Photo;
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
    public static final String MESSAGE_INVALID_FLAG = "Flag is invalid.";
    public static final String MESSAGE_INVALID_DATE = "Wrong date format";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        if (oneBasedIndex.isEmpty()) {
            return null;
        }
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
        if (name.isEmpty()) {
            return null;
        }
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
     * Parses a {@code String phone} into a {@code PhoneNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phoneNumber} is invalid.
     */
    public static PhoneNumber parsePhoneNumber(String phone) throws ParseException {
        requireNonNull(phone);
        if (phone.isEmpty()) {
            return null;
        }
        String trimmedPhone = phone.trim();
        if (!PhoneNumber.isValidPhoneNumber(trimmedPhone)) {
            throw new ParseException(PhoneNumber.MESSAGE_CONSTRAINTS);
        }
        return new PhoneNumber(trimmedPhone);
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

    //@@author shaoyi1997
    /**
     * Parses {@code String date} into a {@code Date date}.
     *
     * @throws ParseException if the given {@code String date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        if (date.isEmpty()) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try {
            return format.parse(date);
        } catch (java.text.ParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }

    /**
     * Parses {@code String sex} into a {@code Sex sex}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Nric} is invalid.
     */
    public static Sex parseSex(String sex) throws ParseException {
        requireNonNull(sex);
        String trimmedSex = sex.trim();
        if (Sex.isValidSex(trimmedSex)) {
            if (Sex.isMale(trimmedSex)) {
                return Sex.MALE;
            } else {
                return Sex.FEMALE;
            }
        } else {
            throw new ParseException(Sex.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String nric} into an {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        if (nric.isEmpty()) {
            return null;
        }
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.VALID_NRIC);
        }
        return new Nric(trimmedNric);
    }

    /**
     * Parses a {@code String id} into an {@code IdentificationNumber}.
     *
     * @throws ParseException if the given {@code id} is invalid.
     */
    public static IdentificationNumber parseIdentificationNumber(String id) throws ParseException {
        requireNonNull(id);
        if (id.isEmpty()) {
            return null;
        }
        String trimmedId = id.trim();
        if (!IdentificationNumber.isValidIdentificationNumber(trimmedId)) {
            throw new ParseException(IdentificationNumber.MESSAGE_CONSTRAINTS);
        }
        return IdentificationNumber.customGenerateId(trimmedId.charAt(0) + "",
                Integer.parseInt(trimmedId.substring(1)));
    }

    /**
     * Parses a {@code String id} into an {@code IdentificationNumber}.
     */
    public static List<String> parseOrgansForDonation(String stringOfOrgans) {
        requireNonNull(stringOfOrgans);
        if (stringOfOrgans.isEmpty()) {
            return Arrays.asList(new String[0]);
        }
        String trimmedOrgans = stringOfOrgans.trim();
        String[] arrayOforgans = trimmedOrgans.split(" ");
        return Arrays.asList(arrayOforgans);
    }

    /**
     * Parses {@code String status} to return the corresponding {@code Status}.
     */
    public static BodyStatus parseBodyStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (status.isEmpty()) {
            return BodyStatus.ARRIVED;
        }
        return BodyStatus.parseBodyStatus(trimmedStatus);
    }

    //@@author ambervoong
    /**
     * Parses {@code String status} to return the corresponding {@code FridgeStatus}.
     * @param status a string representing the current status of the fridge
     * @return FridgeStatus
     * @throws ParseException if the given string does not represent a valid fridge status
     */
    public static FridgeStatus parseFridgeStatus(String status) throws ParseException {
        try {
            FridgeStatus result = FridgeStatus.valueOf(status.toUpperCase());
            return result;
        } catch (IllegalArgumentException e) {
            throw new ParseException(status + " is not a valid FridgeStatus.");
        }
    }
    //@@author

    /**
     * Parses {@code String field} to return null if empty
     */
    public static String parseStringFields(String field) {
        if (field.isEmpty()) {
            return null;
        }
        return field;
    }

    /**
     * Parses a {@code String pathToPhoto} into a {@code Photo}.
     */
    public static Photo parsePhoto(String pathToPhoto) throws ParseException {
        requireNonNull(pathToPhoto);
        if (pathToPhoto.isEmpty()) {
            return null;
        }
        String trimmedPath = pathToPhoto.trim();
        if (Photo.isValidPhoto(trimmedPath)) {
            try {
                return new Photo(trimmedPath);
            } catch (IllegalArgumentException e) {
                throw new ParseException(Photo.MESSAGE_CONSTRAINTS);
            }
        } else {
            throw new ParseException(Photo.MESSAGE_CONSTRAINTS);
        }
    }
    //@@author
}
