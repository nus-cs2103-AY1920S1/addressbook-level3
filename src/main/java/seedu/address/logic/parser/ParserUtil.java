package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.classid.ClassId;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.person.Participation;
import seedu.address.model.person.Picture;
import seedu.address.model.person.Result;
import seedu.address.model.task.Marking;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskTime;


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
    public static Picture parsePicture(String picture) throws ParseException {
        requireNonNull(picture);
        String trimmedPicture = picture.trim();
        if (!Picture.isValidPicture(trimmedPicture)) {
            throw new ParseException(Picture.MESSAGE_CONSTRAINTS);
        }
        return new Picture(trimmedPicture);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Result parseResult(String result) throws ParseException {
        requireNonNull(result);
        String trimmedResult = result.trim();
        if (!Result.isValidResult(trimmedResult)) {
            throw new ParseException(Result.MESSAGE_CONSTRAINTS);
        }
        return new Result(trimmedResult);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Attendance parseAttendance(String attendance) throws ParseException {
        requireNonNull(attendance);
        String trimmedAttendance = attendance.trim();
        if (!Attendance.isValidAttendance(trimmedAttendance)) {
            throw new ParseException(Attendance.MESSAGE_CONSTRAINTS);
        }
        return new Attendance(trimmedAttendance);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static ClassId parseClassId(String classId) throws ParseException {
        requireNonNull(classId);
        String trimmedId = classId.trim();
        if (!ClassId.isValidClassId(trimmedId)) {
            throw new ParseException(ClassId.MESSAGE_CONSTRAINTS);
        }
        return new ClassId(trimmedId);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Participation parseParticipation(String participation) throws ParseException {
        requireNonNull(participation);
        String trimmedParticipation = participation.trim();
        if (!Participation.isValidParticipation(trimmedParticipation)) {
            throw new ParseException(Participation.MESSAGE_CONSTRAINTS);
        }
        return new Participation(trimmedParticipation);
    }


    /**
     * Parses a {@code String taskDescription} into a {@code TaskDescription}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static TaskDescription parseTaskDescription(String taskDescription) {
        requireNonNull(taskDescription);
        String trimmedTaskDescription = taskDescription.trim();
        return new TaskDescription(trimmedTaskDescription);
    }

    /**
     * Parses a {@code String taskTime} into a {@code TaskTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code taskTime} is invalid.
     */
    public static TaskTime parseTaskTime(String taskTime) throws ParseException {
        requireNonNull(taskTime);
        String trimmedTaskTime = taskTime.trim();
        if (!TaskTime.isValidTaskTime(trimmedTaskTime)) {
            throw new ParseException(TaskTime.MESSAGE_CONSTRAINTS);
        }
        return new TaskTime(trimmedTaskTime);
    }

    /**
     * Parses {@code Collection<String> taskTimes} into a {@code Set<TaskTime>}.
     */
    public static Set<TaskTime> parseTaskTimes(Collection<String> taskTimes) throws ParseException {
        requireNonNull(taskTimes);
        final Set<TaskTime> taskTimeList = new HashSet<>();
        for (String taskTime : taskTimes) {
            taskTimeList.add(parseTaskTime(taskTime));
        }
        return taskTimeList;
    }

    /**
     * Parses a {@code String marking} into a {@code Marking}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code marking} is invalid.
     */
    public static Marking parseMarking(String marking) throws ParseException {
        requireNonNull(marking);
        String trimmedMarking = marking.trim();
        if (!Marking.isValidMark(trimmedMarking)) {
            throw new ParseException(Marking.MESSAGE_CONSTRAINTS);
        }
        return new Marking(trimmedMarking);
    }

}
