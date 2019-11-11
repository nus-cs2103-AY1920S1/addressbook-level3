package seedu.address.logic.cap.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.cap.parser.exceptions.ParseException;
import seedu.address.model.cap.module.AcademicYear;
import seedu.address.model.cap.module.Credit;
import seedu.address.model.cap.module.Grade;
import seedu.address.model.cap.module.ModuleCode;
import seedu.address.model.cap.module.Semester;
import seedu.address.model.cap.module.SemesterPeriod;
import seedu.address.model.cap.module.Title;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_CONSTRAINTS =
            "Academic year and semester should be of correct format. "
                    + "The field should also not be left blank.";


    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static ModuleCode parseModuleCode(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        return new ModuleCode(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Credit parseCredit(int credit) throws ParseException {
        requireNonNull(credit);
        return new Credit(credit);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Grade parseGrade(String grade) {
        requireNonNull(grade);
        String trimmedGrade = grade.trim();
        return new Grade(trimmedGrade);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Semester parseSemester(String input) throws ParseException, ArrayIndexOutOfBoundsException {
        requireNonNull(input);
        String trimmedSemester = input.trim().toLowerCase();
        String[] yearSem = trimmedSemester.split("s");

        try {
            return new Semester(new SemesterPeriod(Integer.parseInt(yearSem[1])), new AcademicYear(yearSem[0]));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_CONSTRAINTS);
        }
    }
}
