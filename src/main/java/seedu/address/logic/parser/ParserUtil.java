package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.profile.medical.MedicalHistory;
import seedu.address.profile.person.BloodType;
import seedu.address.profile.person.DoB;
import seedu.address.profile.person.Gender;
import seedu.address.profile.person.Height;
import seedu.address.profile.person.Name;
import seedu.address.profile.person.Weight;
import seedu.address.profile.records.Timestamp;
import seedu.address.profile.records.Type;
import seedu.address.profile.records.Value;


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
     * Parses a {@code String dateOfBirth} into a {@code DoB}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateOfBirth} is invalid.
     */
    public static DoB parseDoB(String dateOfBirth) throws ParseException {
        requireNonNull(dateOfBirth);
        String trimmedDoB = dateOfBirth.trim();
        if (!DoB.isValidDate(trimmedDoB)) {
            throw new ParseException(DoB.MESSAGE_CONSTRAINTS);
        }
        return new DoB(dateOfBirth);
    }

    /**
     * Parses a {@code String bloodGroup} into a {@code BloodType}.
     * Leading and trailing whitespaces will be trimmed.
     * String will be in upper case.
     *
     * @throws ParseException if the given {@code bloodGroup} is invalid.
     */
    public static BloodType parseBloodType(String bloodGroup) throws ParseException {
        requireNonNull(bloodGroup);
        String trimmedBloodGroup = bloodGroup.trim();
        String upperBloodGroup = trimmedBloodGroup.toUpperCase();
        if (!BloodType.isValidBloodType(upperBloodGroup)) {
            throw new ParseException(BloodType.MESSAGE_CONSTRAINTS);
        }
        return new BloodType(upperBloodGroup);
    }

    /**
     * Parses a {@code String gender} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     * String will be in lower case.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        String lowerGender = trimmedGender.toLowerCase();
        if (!Gender.isValidGender(lowerGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(lowerGender);
    }

    /**
     * Parses a {@code String weight} into a {@code Weight}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weight} is invalid.
     */
    public static Weight parseWeight(String weight) throws ParseException {
        requireNonNull(weight);
        String trimmedWeight = weight.trim();
        if (!Weight.isValidNumber(trimmedWeight)) {
            throw new ParseException(Weight.MESSAGE_CONSTRAINTS);
        }
        return new Weight(trimmedWeight, DateParser.getCurrentTimestamp());
    }

    /**
     * Parses a {@code String height} into a {@code Height}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code height} is invalid.
     */
    public static Height parseHeight(String height) throws ParseException {
        requireNonNull(height);
        String trimmedHeight = height.trim();
        if (!Height.isValidNumber(trimmedHeight)) {
            throw new ParseException(Height.MESSAGE_CONSTRAINTS);
        }
        return new Height(trimmedHeight, DateParser.getCurrentTimestamp());
    }

    /**
     * Parses a {@code String medicalHistory} into a {@code MedicalHistory}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code medicalHistory} is invalid.
     */
    public static MedicalHistory parseMedicalHistory(String medicalHistory) throws ParseException {
        requireNonNull(medicalHistory);
        String trimmedTag = medicalHistory.trim();
        if (!MedicalHistory.isValidMedicalHistoryName(trimmedTag)) {
            throw new ParseException(MedicalHistory.MESSAGE_CONSTRAINTS);
        }
        return new MedicalHistory(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> medicalHistories} into a {@code Set<MedicalHistory>}.
     */
    public static Set<MedicalHistory> parseMedicalHistories(Collection<String> medicalHistories) throws ParseException {
        requireNonNull(medicalHistories);
        final Set<MedicalHistory> medicalHistorySet = new HashSet<>();
        for (String tagName : medicalHistories) {
            medicalHistorySet.add(parseMedicalHistory(tagName));
        }
        return medicalHistorySet;
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
     * Parses a {@code String value} into a {@code Value}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code value} is invalid.
     */
    public static Value parseValue(String value) throws ParseException {
        requireNonNull(value);
        String trimmedValue = value.trim();
        if (!Value.isValidNumber(trimmedValue)) {
            throw new ParseException(Value.MESSAGE_CONSTRAINTS);
        }
        return new Value(value);
    }

    /**
     * Parses a {@code String timestamp} into a {@code Timestamp}.
     * Leading and trailing whitespaces will be trimmed.
     * String will be in upper case.
     *
     * @throws ParseException if the given {@code timestamp} is invalid.
     */
    public static Timestamp parseTimestamp(String timestamp) throws ParseException {
        requireNonNull(timestamp);
        String trimmedTimestamp = timestamp.trim();
        String upperTimestamp = trimmedTimestamp.toUpperCase();
        if (!Timestamp.isValidDateTime(upperTimestamp)) {
            throw new ParseException(Timestamp.MESSAGE_CONSTRAINTS);
        }
        return new Timestamp(upperTimestamp);
    }
}
