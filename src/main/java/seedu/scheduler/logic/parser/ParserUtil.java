package seedu.scheduler.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.commons.util.StringUtil;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.FilePath;
import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Email;
import seedu.scheduler.model.person.Faculty;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Phone;
import seedu.scheduler.model.person.Role;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.model.tag.Tag;

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
     * Parses {@code filePath} into an {@code FilePath} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified filePath is invalid.
     */
    public static FilePath parseFilePath(String filePath) throws ParseException {
        requireNonNull(filePath);
        String trimmedFilePath = filePath.trim();
        if (!FilePath.isValidFilePath(trimmedFilePath)) {
            throw new ParseException(FilePath.MESSAGE_CONSTRAINTS);
        }
        return new FilePath(trimmedFilePath);
    }

    /**
     * Parses a {@code String} role into a {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!Role.isValidRole(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        return new Role(trimmedRole);
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
     * Parses a {@code String faculty} into a {@code Faculty}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code faculty} is invalid.
     */
    public static Faculty parseFaculty(String faculty) throws ParseException {
        requireNonNull(faculty);
        String trimmedFaculty = faculty.trim();
        if (!Faculty.isValidFaculty(trimmedFaculty)) {
            throw new ParseException(Faculty.MESSAGE_CONSTRAINTS);
        }
        return new Faculty(trimmedFaculty);
    }

    /**
     * Parses a {@code String yearOfStudy} into a {@code Integer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code yearOfStudy} is invalid.
     */
    public static Integer parseYearOfStudy(String yearOfStudy) throws ParseException {
        requireNonNull(yearOfStudy);
        String trimmedYearOfStudy = yearOfStudy.trim();
        Integer value;
        try {
            value = Integer.parseUnsignedInt(trimmedYearOfStudy);
            if (value < 0 || value > 5) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_YEAR_OF_STUDY);
        }
        return value;
    }

    /**
     * Parses a {@code String department} into a {@code Department}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code department} is invalid.
     */
    public static Department parseDepartment(String department) throws ParseException {
        requireNonNull(department);
        String trimmedDepartment = department.trim();
        if (!Department.isValidDepartment(trimmedDepartment)) {
            throw new ParseException(Department.MESSAGE_CONSTRAINTS);
        }
        return new Department(trimmedDepartment);
    }

    /**
     * Parses {@code Collection<String> departments} into a {@code List<Department>}.
     */
    public static List<Department> parseDepartments(Collection<String> departments) throws ParseException {
        requireNonNull(departments);
        final List<Department> departmentList = new ArrayList<>();
        for (String d: departments) {
            departmentList.add(parseDepartment(d));
        }
        return departmentList;
    }

    /**
     * Parses a {@code String slot} into a {@code Slot}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code slot} is invalid.
     */
    public static Slot parseSlot(String slot) throws ParseException {
        requireNonNull(slot);
        String trimmedSlot = slot.trim();
        if (!Slot.isValidSlot(trimmedSlot)) {
            throw new ParseException(Slot.MESSAGE_CONSTRAINTS);
        }
        return Slot.fromString(trimmedSlot);
    }

    /**
     * Parses {@code Collection<String> slots} into a {@code List<Slot>}.
     */
    public static List<Slot> parseSlots(Collection<String> slots) throws ParseException {
        requireNonNull(slots);
        final List<Slot> slotList = new ArrayList<>();
        for (String s: slots) {
            slotList.add(parseSlot(s));
        }
        return slotList;
    }

}
