package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.TreeMap;
import java.util.TreeSet;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEarningsCommand;
import seedu.address.logic.commands.AssignClassCommand;
import seedu.address.logic.commands.AutoAddEarningsCommand;
import seedu.address.logic.commands.AutoCommand;
import seedu.address.logic.commands.ChangeTabCommand;
import seedu.address.logic.commands.ClaimEarningsCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteCustomCommand;
import seedu.address.logic.commands.DeleteEarningsCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEarningsCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListClassCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.commands.MarkParticipationCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SetPictureCommand;
import seedu.address.logic.commands.TotalEarningsCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateEarningsCommand;
import seedu.address.logic.commands.calendar.AddTaskCommand;
import seedu.address.logic.commands.calendar.DeleteTaskCommand;
import seedu.address.logic.commands.calendar.EditTaskCommand;
import seedu.address.logic.commands.calendar.FindTaskCommand;
import seedu.address.logic.commands.calendar.ListTasksBasedOnDateCommand;
import seedu.address.logic.commands.calendar.ListTasksCommand;
import seedu.address.logic.commands.note.AddNotesCommand;
import seedu.address.logic.commands.note.DeleteNotesCommand;
import seedu.address.logic.commands.note.EditNotesCommand;
import seedu.address.logic.commands.note.FindNotesCommand;
import seedu.address.logic.commands.note.ListNotesCommand;
import seedu.address.logic.commands.reminder.AddReminderCommand;
import seedu.address.logic.commands.reminder.DeleteReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.WindowView;
import seedu.address.model.account.Username;
import seedu.address.model.classid.ClassId;
import seedu.address.model.commands.CommandAction;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.commands.CommandWord;
import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Claim;
import seedu.address.model.earnings.Count;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Type;
import seedu.address.model.note.ClassType;
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
        TreeMap<String, String> commandList = TutorAidParser.getCommandList();
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
     * Parses a {@code String content} into an {@code Content}.
     * @param type String of class type.
     * @return ClassType.
     * @throws ParseException if the given {@code code} is invalid.
     */
    public static ClassType parseClassType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (!ClassType.isValidClassType(trimmedType)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new ClassType(trimmedType);
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

    /**
     * Parses a {@code String claim} into an {@code Claim}.
     * @param claim String of claim.
     * @return Claim.
     * @throws ParseException if the given {@code claim} is invalid.
     */
    public static Claim parseClaim(String claim) throws ParseException {
        requireNonNull(claim);
        String trimmedClaim = claim.trim();
        if (!Claim.isValidClaim(trimmedClaim)) {
            throw new ParseException(Claim.MESSAGE_CONSTRAINTS);
        }
        return new Claim(trimmedClaim);
    }

    /**
     * Parses a {@code String count} into an {@code Count}.
     * @param count String of count.
     * @return Count.
     * @throws ParseException if the given {@code count} is invalid.
     */
    public static Count parseCount(String count) throws ParseException {
        requireNonNull(count);
        String trimmedCount = count.trim();
        if (!Count.isValidCount(trimmedCount)) {
            throw new ParseException(Count.MESSAGE_CONSTRAINTS);
        }
        return new Count(trimmedCount);
    }

    /**
     * Helps fill command list with all the basic in built commands.
     * @param commandList A {@code TreeMap} that is used to store all commands and their actions.
     */
    public static void fillBasicCommands(TreeMap<String, String> commandList) {
        commandList.put(AddCommand.COMMAND_WORD, AddCommand.COMMAND_WORD);
        commandList.put(EditCommand.COMMAND_WORD, EditCommand.COMMAND_WORD);
        commandList.put(ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD);
        commandList.put(DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD);
        commandList.put(ListCommand.COMMAND_WORD, ListCommand.COMMAND_WORD);
        commandList.put(FindCommand.COMMAND_WORD, FindCommand.COMMAND_WORD);
        commandList.put(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD);
        commandList.put(ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD);
        commandList.put(AddEarningsCommand.COMMAND_WORD, AddEarningsCommand.COMMAND_WORD);
        commandList.put(DeleteCustomCommand.COMMAND_WORD, DeleteCustomCommand.COMMAND_WORD);
        commandList.put(AddTaskCommand.COMMAND_WORD, AddTaskCommand.COMMAND_WORD);
        commandList.put(DeleteEarningsCommand.COMMAND_WORD, DeleteEarningsCommand.COMMAND_WORD);
        commandList.put(UpdateEarningsCommand.COMMAND_WORD, UpdateEarningsCommand.COMMAND_WORD);
        commandList.put(FindEarningsCommand.COMMAND_WORD, FindEarningsCommand.COMMAND_WORD);
        commandList.put(DeleteTaskCommand.COMMAND_WORD, DeleteTaskCommand.COMMAND_WORD);
        commandList.put(ListTasksCommand.COMMAND_WORD, ListTasksCommand.COMMAND_WORD);
        commandList.put(ChangeTabCommand.COMMAND_WORD, ChangeTabCommand.COMMAND_WORD);
        commandList.put(AddReminderCommand.COMMAND_WORD, AddReminderCommand.COMMAND_WORD);
        commandList.put(DeleteReminderCommand.COMMAND_WORD, DeleteReminderCommand.COMMAND_WORD);
        commandList.put(ListTasksBasedOnDateCommand.COMMAND_WORD,
                ListTasksBasedOnDateCommand.COMMAND_WORD);
        commandList.put(AddNotesCommand.COMMAND_WORD, AddNotesCommand.COMMAND_WORD);
        commandList.put(DeleteNotesCommand.COMMAND_WORD, DeleteNotesCommand.COMMAND_WORD);
        commandList.put(EditNotesCommand.COMMAND_WORD, EditNotesCommand.COMMAND_WORD);
        commandList.put(FindNotesCommand.COMMAND_WORD, FindNotesCommand.COMMAND_WORD);
        commandList.put(ListNotesCommand.COMMAND_WORD, ListNotesCommand.COMMAND_WORD);
        commandList.put(EditTaskCommand.COMMAND_WORD, EditTaskCommand.COMMAND_WORD);
        commandList.put(FindTaskCommand.COMMAND_WORD, FindTaskCommand.COMMAND_WORD);
        commandList.put(LoginCommand.COMMAND_WORD, LoginCommand.COMMAND_WORD);
        commandList.put(LogoutCommand.COMMAND_WORD, LogoutCommand.COMMAND_WORD);
        commandList.put(TotalEarningsCommand.COMMAND_WORD, TotalEarningsCommand.COMMAND_WORD);
        commandList.put(ListClassCommand.COMMAND_WORD, ListClassCommand.COMMAND_WORD);
        commandList.put(AssignClassCommand.COMMAND_WORD, AssignClassCommand.COMMAND_WORD);
        commandList.put(MarkAttendanceCommand.COMMAND_WORD, MarkAttendanceCommand.COMMAND_WORD);
        commandList.put(MarkParticipationCommand.COMMAND_WORD, MarkParticipationCommand.COMMAND_WORD);
        commandList.put(SetPictureCommand.COMMAND_WORD, SetPictureCommand.COMMAND_WORD);
        commandList.put(ClaimEarningsCommand.COMMAND_WORD, ClaimEarningsCommand.COMMAND_WORD);
        commandList.put(AutoAddEarningsCommand.COMMAND_WORD, AutoAddEarningsCommand.COMMAND_WORD);
        commandList.put(AutoCommand.COMMAND_WORD, AutoCommand.COMMAND_WORD);
        commandList.put(UndoCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD);
        commandList.put(RedoCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD);
    }
}
