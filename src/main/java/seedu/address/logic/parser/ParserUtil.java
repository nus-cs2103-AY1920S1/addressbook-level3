package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.TreeMap;
import java.util.TreeSet;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.WindowView;
import seedu.address.model.account.Username;
import seedu.address.model.classid.ClassId;
import seedu.address.model.commands.CommandAction;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.commands.CommandWord;
import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Type;
import seedu.address.model.note.Content;
import seedu.address.model.note.ModuleCode;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.person.Participation;
import seedu.address.model.person.Picture;
import seedu.address.model.person.Result;
import seedu.address.model.reminder.ReminderDescription;
import seedu.address.model.reminder.ReminderTime;
import seedu.address.model.task.Marking;
import seedu.address.model.task.TaskTime;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_COMMAND = "No such command to be deleted!";

    private static int viewIndexNumber;
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
     * Parses {@code commandToCheck} into an {@code CommandObject} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the command does not exist.
     */
    public static CommandObject parseCommand(String commandToCheck) throws ParseException {
        String trimmedCommand = commandToCheck.trim();
        TreeMap<String, String> commandList = AddressBookParser.getCommandList();
        if (!commandList.containsKey(trimmedCommand)) {
            throw new ParseException((MESSAGE_INVALID_COMMAND));
        } else {
            return new CommandObject(new CommandWord(trimmedCommand),
                    new CommandAction(commandList.get(trimmedCommand)));
        }
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
     * Parses a {@code String destination} into a {@code WindowView}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code destination} is invalid.
     */
    public static WindowView parseWindowView(String destination) throws ParseException {
        requireNonNull(destination);
        String trimmedDestination = destination.trim();
        if (!WindowView.isValidWindowView(trimmedDestination)) {
            throw new ParseException(WindowView.MESSAGE_CONSTRAINTS);
        }
        return new WindowView(trimmedDestination);
    }

    /**
     * Parses a {@code String picture} into a {@code Picture}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Picture} is invalid.
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
     * Parses a {@code String result} into an {@code Result}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code result} is invalid.
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
     * Parses a {@code String attendance} into an {@code Attendance}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code attendance} is invalid.
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
     * Parses a {@code String classId} into an {@code ClassId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code classId} is invalid.
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
     * Parses a {@code String participation} into an {@code Participation}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code participation} is invalid.
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
    public static TreeSet<TaskTime> parseTaskTimes(Collection<String> taskTimes) throws ParseException {
        requireNonNull(taskTimes);
        final TreeSet<TaskTime> taskTimeList = new TreeSet<>(TaskTime::compareTo);
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

    /**
     * Parses a {@code String amount} into an {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Amount parseAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        if (!Amount.isValidAmount(trimmedAmount)) {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        }
        return new Amount(trimmedAmount);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDateNum(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String reminderDescription} into a {@code ReminderDescription}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static ReminderDescription parseReminderDescription(String reminderDescription) {
        requireNonNull(reminderDescription);
        String trimmedReminderDescription = reminderDescription.trim();
        return new ReminderDescription(trimmedReminderDescription);
    }

    /**
     * Parses a {@code String reminderTime} into a {@code ReminderTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code taskTime} is invalid.
     */
    public static ReminderTime parseReminderTime(String reminderTime) throws ParseException {
        requireNonNull(reminderTime);
        String trimmedReminderTime = reminderTime.trim();
        if (!ReminderTime.isValidReminderTime(trimmedReminderTime)) {
            throw new ParseException(ReminderTime.MESSAGE_CONSTRAINTS);
        }
        return new ReminderTime(trimmedReminderTime);
    }

    /**
     * Parses {@code Collection<String> reminderTimes} into a {@code Set<ReminderTime>}.
     */
    public static TreeSet<ReminderTime> parseReminderTimes(Collection<String> reminderTimes) throws ParseException {
        requireNonNull(reminderTimes);
        final TreeSet<ReminderTime> reminderTimeList = new TreeSet<>();
        for (String reminderTime : reminderTimes) {
            reminderTimeList.add(parseReminderTime(reminderTime));
        }
        return reminderTimeList;
    }

    /**
     * Parses a {@code String username} into an {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code username} is invalid.
     */
    public static Username parseUsername(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(trimmedUsername)) {
            throw new ParseException(Username.MESSAGE_CONSTRAINTS);
        }
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code String code} into an {@code ModuleCode}.
     * @param code String of module code.
     * @return ModuleCode.
     * @throws ParseException if the given {@code code} is invalid.
     */
    public static ModuleCode parseModuleCode(String code) throws ParseException {
        requireNonNull(code);
        String trimmedCode = code.trim();
        if (!ModuleCode.isValidModuleCode(trimmedCode)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedCode);
    }

    /**
     * Parses a {@code String content} into an {@code Content}.
     * @param content String of content.
     * @return Content.
     * @throws ParseException if the given {@code code} is invalid.
     */
    public static Content parseContent(String content) throws ParseException {
        requireNonNull(content);
        String trimmedContent = content.trim();
        if (!Content.isValidContent(trimmedContent)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Content(trimmedContent);
    }

    /**
     * Parses a {@code String type} into an {@code Type}.
     * @param type String of type.
     * @return Type.
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
}
