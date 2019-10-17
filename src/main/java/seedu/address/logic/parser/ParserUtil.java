package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Grade;
import seedu.address.model.Lesson.ClassName;
import seedu.address.model.Lesson.Time;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.MedicalCondition;
import seedu.address.model.student.Name;
import seedu.address.model.student.ParentPhone;
import seedu.address.model.student.Phone;
import seedu.address.model.tag.Tag;

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
     * Parses a {@code String name} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static AssignmentName parseAssignmentName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new AssignmentName(trimmedName);
    }

    /**
     * Parses a {@code String grades} into a {@code List<Integer>}.
     * Leading and trailing whitespaces will be trimmed.
     * String grades will be split by whitespace, converted to Integer and stored in List.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static List<Grade> parseAssignmentGrades(String grades) throws ParseException {
        requireNonNull(grades);
        List<Grade> output = new ArrayList<>();
        String trimmedGrades = grades.trim();
        String[] individualGrades = trimmedGrades.split(" ");
        for (String grade: individualGrades) {
            Integer integerGrade = Integer.parseInt(grade);
            output.add(new Grade(integerGrade.toString()));
        }
        return output;
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
     * Parses a {@code String parentPhone} into a {@code ParentPhone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code parentPhone} is invalid.
     */
    public static ParentPhone parseParentPhone(String parentPhone) throws ParseException {
        requireNonNull(parentPhone);
        String trimmedParentPhone = parentPhone.trim();
        if (!ParentPhone.isValidParentPhone(trimmedParentPhone)) {
            throw new ParseException(ParentPhone.MESSAGE_CONSTRAINTS);
        }
        return new ParentPhone(trimmedParentPhone);
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
     * Parses a {@code String medicalCondition} into an {@code MedicalCondition}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code medicalCondition} is invalid.
     */
    public static MedicalCondition parseMedicalCondition(String medicalCondition) throws ParseException {
        requireNonNull(medicalCondition);
        String trimmedMedicalCondition = medicalCondition.trim();
        if (!MedicalCondition.isValidMedicalCondition(trimmedMedicalCondition)) {
            throw new ParseException(MedicalCondition.MESSAGE_CONSTRAINTS);
        }
        return new MedicalCondition(trimmedMedicalCondition);
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
     * Parses a {@code String className} into a {@code ClassName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code className} is invalid.
     */
    public static ClassName parseClassName(String className) throws ParseException {
        requireNonNull(className);
        String trimmedClassName = className.trim();
        if (!ClassName.isValidClassName(trimmedClassName)) {
            throw new ParseException(ClassName.MESSAGE_CONSTRAINTS);
        }
        return new ClassName(trimmedClassName);
    }

    /**
     * Parses a {@code String time} into a {@code Time}.
     * @param time String representing time.
     * @return Time object.
     */
    public static Time parseTime(String time) {
        requireNonNull(time);
        String trimmedTime = time.trim();
        String[] dateAndTime = trimmedTime.split(" ");
        String[] date = dateAndTime[0].split("/");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]) - 1;
        int year = Integer.parseInt(date[2]);
        int hour = Integer.parseInt(dateAndTime[1].substring(0, 2));
        int min = Integer.parseInt(dateAndTime[1].substring(2));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, min);
        return new Time(calendar);
    }
}
