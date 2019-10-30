package seedu.address.logic.calendar.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDAY;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDEADLINE;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDESCRIPTION;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTAG;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTIME;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTITLE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.calendar.commands.AddCommand;
import seedu.address.logic.calendar.commands.GoCommand;
import seedu.address.logic.calendar.parser.exceptions.ParseException;
import seedu.address.model.calendar.tag.TaskTag;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskDay;
import seedu.address.model.calendar.task.TaskDeadline;
import seedu.address.model.calendar.task.TaskDescription;
import seedu.address.model.calendar.task.TaskTime;
import seedu.address.model.calendar.task.TaskTitle;
import seedu.address.model.calendar.task.ToDoTask;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        seedu.address.logic.calendar.parser.ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASKTITLE, PREFIX_TASKDAY, PREFIX_TASKDESCRIPTION,
                        PREFIX_TASKDEADLINE, PREFIX_TASKTIME, PREFIX_TASKTAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASKTITLE, PREFIX_TASKTIME, PREFIX_TASKDAY)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        TaskTitle taskTitle = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TASKTITLE).get());
        TaskDay taskDay = ParserUtil.parseDay(argMultimap.getValue(PREFIX_TASKDAY).get());
        TaskDescription taskDescription =
                ParserUtil.parseDescription(argMultimap.getValue(PREFIX_TASKDESCRIPTION).get());
        TaskTime taskTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TASKTIME).get());
        TaskDeadline taskDeadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_TASKDEADLINE).get());
        Set<TaskTag> taskTagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TASKTAG));

        Task task = new ToDoTask(taskTitle, taskDay, taskDescription, taskDeadline, taskTime, taskTagList,
            GoCommand.getCurrentWeek());

        return new AddCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
