package seedu.tarence.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_INDEX_NON_POSITIVE;
import static seedu.tarence.logic.parser.ArgumentPatterns.PATTERN_WEEKRANGE;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.commons.exceptions.IllegalValueException;
import seedu.tarence.commons.util.StringUtil;
import seedu.tarence.logic.parser.exceptions.NegativeIndexException;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Week;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final String[] SHORT_FORM_DAYS = new String[]{"MON", "TUES", "WED", "THURS", "FRI", "SAT", "SUN"};

    public static final String[] NORMAL_FORM_DAYS = new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY",
        "FRIDAY", "SATURDAY", "SUNDAY"};

    public static final String MESSAGE_INVALID_WEEK_RANGE = "Week range must be valid eg 5-7";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (StringUtil.isNonPositiveInteger(trimmedIndex)) {
            throw new NegativeIndexException(MESSAGE_INVALID_INDEX_NON_POSITIVE);
        } else if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
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
     * Parses a {@code String matricNum} into an {@code MatricNum}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code matricNum} is invalid.
     */
    public static MatricNum parseMatricNum(String matricNum) throws ParseException {
        requireNonNull(matricNum);
        String trimmedMatricNum = matricNum.trim();
        if (!MatricNum.isValidMatricNum(trimmedMatricNum)) {
            throw new ParseException(MatricNum.MESSAGE_CONSTRAINTS);
        }
        return new MatricNum(trimmedMatricNum);
    }

    /**
     * Parses a {@code String nusnetId} into a {@code NusnetId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nusnetId} is invalid.
     */
    public static NusnetId parseNusnetId(String nusnetId) throws ParseException {
        requireNonNull(nusnetId);
        String trimmedNusnetId = nusnetId.trim();
        if (!NusnetId.isValidNusnetId(trimmedNusnetId)) {
            throw new ParseException(MatricNum.MESSAGE_CONSTRAINTS);
        }
        return new NusnetId(trimmedNusnetId);
    }

    /**
     * Parses a {@code String modCode} into a {@code ModCode}.
     *
     * @param modCode User string.
     * @return ModCode object.
     * @throws ParseException if the given {@code modCode} doesn't match the regex.
     */
    public static ModCode parseModCode(String modCode) throws ParseException {
        requireNonNull(modCode);
        String trimmedModCode = modCode.trim();
        if (!ModCode.isValidModCode(trimmedModCode)) {
            throw new ParseException(ModCode.MESSAGE_CONSTRAINTS);
        }
        return new ModCode(trimmedModCode.toUpperCase());
    }

    /**
     * Parses a {@code String modCode} into an {@code ModCode}.
     *
     * @param tutorialName User string.
     * @return TutName object.
     * @throws ParseException if the given {@code tutorialName} doesn't match the regex.
     */
    public static TutName parseTutorialName(String tutorialName) throws ParseException {
        requireNonNull(tutorialName);
        String trimmedTutorialName = tutorialName.trim();
        if (!TutName.isValidTutName(trimmedTutorialName)) {
            throw new ParseException(TutName.MESSAGE_CONSTRAINTS);
        }
        return new TutName(trimmedTutorialName);
    }

    /**
     * Parses a {@code String tutorialDay} into an {@code DayOfWeek}.
     * Leading and trailing whitespaces will be trimmed.
     * Accepts both normal spelling eg MONDAY, monday and short forms ie MON, mon
     *
     * @throws ParseException if the given {@code tutorialDay} is invalid.
     */
    public static DayOfWeek parseDayOfWeek(String tutorialDay) throws ParseException {
        requireNonNull(tutorialDay);
        String trimmedTutorialDay = tutorialDay.trim().toUpperCase();

        for (int i = 0; i < SHORT_FORM_DAYS.length; i++) {
            if (trimmedTutorialDay.equals(SHORT_FORM_DAYS[i])) {
                trimmedTutorialDay = NORMAL_FORM_DAYS[i];
            }
        }

        try {
            return DayOfWeek.valueOf(trimmedTutorialDay);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid day entered");
        }
    }

    /**
     * Parses a {@code String localTime} into a {@code LocalTime}.
     *
     * @param localTime User String.
     * @return LocalTime object.
     * @throws ParseException if user String is not 4 chars in length.
     */
    public static LocalTime parseLocalTime(String localTime) throws ParseException {
        requireNonNull(localTime);
        if (localTime.length() > 4) {
            throw new ParseException("Time entered should be in 24HR format eg 0900");
        }
        try {
            localTime = String.format("%04d", Integer.parseInt(localTime));
        } catch (NumberFormatException e) {
            throw new ParseException("Time entered should be in 24HR format eg 0900");
        }
        // Converts a string from '1200' to '12:00:00'.
        localTime = localTime.substring(0, 2) + ":" + localTime.substring(2, 4) + ":00";

        LocalTime result;
        try {
            result = LocalTime.parse(localTime, DateTimeFormatter.ISO_TIME);
        } catch (DateTimeException e) {
            throw new ParseException("Time entered should be in 24HR format eg 0900");
        }
        return result;
    }

    /**
     * Parses a {@code String weeks} into an Set of Weeks.
     *
     * @param weeks User string. Eg 1,2,7
     * @return Set of Weeks.
     * @throws ParseException if unable to parse the string into a Set of Weeks.
     */
    public static Set<Week> parseWeeks(String weeks) throws ParseException {
        requireNonNull(weeks);
        Set<Week> listOfWeeks = new TreeSet<>();

        if (weeks.toLowerCase().equals("every week")) {
            for (int i = 3; i <= 13; i += 1) {
                listOfWeeks.add(new Week(i));
            }
            return listOfWeeks;
        } else if (weeks.toLowerCase().equals("odd")
                || weeks.toLowerCase().equals("odd week")) { // weeks 3, 5, 7, 9, 11, 13
            for (int i = 3; i <= 13; i += 2) {
                listOfWeeks.add(new Week(i));
            }
            return listOfWeeks;
        } else if (weeks.toLowerCase().equals("even")
                || weeks.toLowerCase().equals("even week")) { // weeks 4, 6, 8, 10, 12
            for (int i = 4; i <= 12; i += 2) {
                listOfWeeks.add(new Week(i));
            }
            return listOfWeeks;
        }

        // Remove '[', ']' and blank spaces. For parsing from saved Json file.
        weeks = weeks.replace("[", "").replace("]", "").replace(" ", "");

        // check for user input of range "x-y"
        Matcher m = PATTERN_WEEKRANGE.matcher(weeks);
        if (m.find()) {
            String[] weekRange = m.group().split("-");
            int start = Integer.parseInt(weekRange[0]);
            int end = Integer.parseInt(weekRange[1]);
            if (start < 1 || end > 13) {
                throw new ParseException(Week.MESSAGE_CONSTRAINTS);
            } else if ((start > end) || (start == end)) {
                throw new ParseException((MESSAGE_INVALID_WEEK_RANGE));
            }
            for (int i = start; i <= end; i++) {
                listOfWeeks.add(new Week(i));
            }
            return listOfWeeks;
        }

        // default - assume user input of list of weeks
        String[] weekNumbers = weeks.split(",");

        for (String weekNumber : weekNumbers) {
            listOfWeeks.add(parseWeek(weekNumber));

        }
        return listOfWeeks;
    }

    /**
     * Parses a {@code String weekNumber} into a Week.
     *
     * @param weekNumber User string. Eg 1,2,7
     * @return Week.
     * @throws ParseException if unable to parse the string into a Week.
     */
    public static Week parseWeek(String weekNumber) throws ParseException {
        requireNonNull(weekNumber);
        Week week;
        try {
            week = new Week(Integer.parseInt(weekNumber));
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid weeks input. Must be 'even', 'odd', or 'every week', "
                    + "or numbers separated by commas.");
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid week number(s) entered. Should contain only numbers from 1 to 13.");
        }
        return week;
    }

    /**
     * Parses a {@code String weeks} into a Duration object.
     *
     * @param duration User string of the duration in minutes. Eg 120
     * @return Duration object.
     * @throws ParseException if unable to parse the string into Integers.
     */
    public static Duration parseDuration(String duration) throws ParseException {
        requireNonNull(duration);
        try {
            Integer minutes = Integer.parseInt(duration);
            return Duration.ofMinutes(minutes);
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid duration entered. Duration should contain only numbers");
        }
    }

    /**
     * Parses the string representation of a Semester start date (a Module attribute).
     *
     * @param semesterStartString String representation of a Semester start date obtained from Json.
     * @return Date object.
     * @throws IllegalValueException thrown if there is an error with parsing the String.
     */
    public static Date parseSemesterStartDateFromJson(String semesterStartString) throws IllegalValueException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date semesterStartDate = null;

        try {
            if (!semesterStartString.contains("null")) {
                semesterStartDate = dateFormatter.parse(semesterStartString);
            }
        } catch (java.text.ParseException e) {
            String errorMessage = "Error with parsing module's start date " + e.getMessage();
            throw new IllegalValueException(errorMessage);
        }
        return semesterStartDate;
    }

}
