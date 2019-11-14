package organice.logic.parser;

import static java.util.Objects.requireNonNull;

import organice.commons.core.index.Index;
import organice.commons.util.StringUtil;
import organice.logic.parser.exceptions.ParseException;
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.DoctorInCharge;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.TissueType;
import organice.model.person.Type;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses a {@code String nric} into a {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(trimmedNric);
    }

    /**
     * Parses a {@code String type} into a {@code Type}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code type} is invalid.
     */
    public static Type parseType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (!Type.isValidType(trimmedType)) {
            throw new ParseException(Type.MESSAGE_CONSTRAINTS);
        }
        return new Type(trimmedType);
    }

    /**
     * Parses a {@code String age} into a {@code Age}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code age} is invalid.
     */
    public static Age parseAge(String age) throws ParseException {
        requireNonNull(age);
        String trimmedAge = age.trim();

        if (!Age.isValidAge(trimmedAge)) {
            throw new ParseException(Age.MESSAGE_CONSTRAINTS);
        }

        return new Age(trimmedAge);
    }

    /**
     * Parses a {@code String priority} into a {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }
        return new Priority(trimmedPriority);
    }

    /**
     * Parses a {@code String bloodType} into a {@code BloodType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code bloodType} is invalid.
     */
    public static BloodType parseBloodType(String bloodType) throws ParseException {
        requireNonNull(bloodType);
        String trimmedBloodType = bloodType.trim();
        if (!BloodType.isValidBloodType(trimmedBloodType)) {
            throw new ParseException(BloodType.MESSAGE_CONSTRAINTS);
        }
        return new BloodType(trimmedBloodType);
    }

    /**
     * Parses a {@code String tissueType} into a {@code TissueType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tissueType} is invalid.
     */
    public static TissueType parseTissueType(String tissueType) throws ParseException {
        requireNonNull(tissueType);
        String trimmedTissueType = tissueType.trim();
        if (!TissueType.isValidTissueType(trimmedTissueType)) {
            throw new ParseException(TissueType.MESSAGE_CONSTRAINTS);
        }
        return new TissueType(trimmedTissueType);
    }

    /**
     * Parses a {@code String organ} into a {@code Organ}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code organ} is invalid.
     */
    public static Organ parseOrgan(String organ) throws ParseException {
        requireNonNull(organ);
        String trimmedOrgan = organ.trim();
        if (!Organ.isValidOrgan(trimmedOrgan)) {
            throw new ParseException(Organ.MESSAGE_CONSTRAINTS);
        }
        return new Organ(trimmedOrgan);
    }

    /**
     * Parses a {@code String doctorInCharge} into a {@code DoctorInCharge}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code doctorInCharge} is invalid.
     */
    public static DoctorInCharge parseDoctorInCharge(String doctorInCharge) throws ParseException {
        requireNonNull(doctorInCharge);
        String trimmedDoctorInCharge = doctorInCharge.trim();
        if (!DoctorInCharge.isValidDoctorInCharge(trimmedDoctorInCharge)) {
            throw new ParseException(DoctorInCharge.MESSAGE_CONSTRAINTS);
        }
        return new DoctorInCharge(trimmedDoctorInCharge);
    }

    /**
     * Parses a {@code String organExpiryDate} into a {@code OrganExpiryDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code organExpiryDate} is invalid.
     */
    public static OrganExpiryDate parseOrganExpiryDate(String organExpiryDate) throws ParseException {
        requireNonNull(organExpiryDate);
        String trimmedOrganExpiryDate = organExpiryDate.trim();
        if (!OrganExpiryDate.isValidExpiryDate(trimmedOrganExpiryDate)) {
            throw new ParseException(OrganExpiryDate.MESSAGE_CONSTRAINTS);
        }
        return new OrganExpiryDate(trimmedOrganExpiryDate);
    }
}
