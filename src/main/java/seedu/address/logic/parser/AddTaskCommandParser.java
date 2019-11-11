package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARKING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TIME;

import java.util.TreeSet;
import java.util.stream.Stream;

import seedu.address.logic.commands.calendar.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.classid.ClassId;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;
import seedu.address.model.reminder.ReminderTime;
import seedu.address.model.task.Marking;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskTime;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        System.out.println(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASSID, PREFIX_MARKING, PREFIX_TASK_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLASSID, PREFIX_MARKING, PREFIX_TASK_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        ClassId classId = ParserUtil.parseClassId(argMultimap.getValue(PREFIX_CLASSID).get());
        Marking marking = ParserUtil.parseMarking(argMultimap.getValue(PREFIX_MARKING).get());
        TreeSet<TaskTime> taskTimeList = ParserUtil.parseTaskTimes(argMultimap.getAllValues(PREFIX_TASK_TIME));

        Task task = new Task(classId, taskTimeList, marking);

        if (marking.equals(new Marking("Y"))) {
            System.out.println("yesy");
            ReminderDescription rd = ParserUtil.parseReminderDescription(argMultimap.getValue(PREFIX_CLASSID).get());
            TreeSet<ReminderTime> reminderTimeList =
                    ParserUtil.parseReminderTimes(argMultimap.getAllValues(PREFIX_TASK_TIME));
            AddTaskCommand addtaskreminder = new AddTaskCommand(task);
            addtaskreminder.getReminder(new Reminder(rd, reminderTimeList));
            return addtaskreminder;
        } else {
            return new AddTaskCommand(task);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
