package seedu.jarvis.logic.parser.planner;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_DATE;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_FREQ;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_PRIORITY;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TAG;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TASK_DES;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TASK_TYPE;
import static seedu.jarvis.model.planner.tasks.Task.DEADLINE;
import static seedu.jarvis.model.planner.tasks.Task.EVENT;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import seedu.jarvis.logic.commands.planner.AddTaskCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.Prefix;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    @Override
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK_TYPE, PREFIX_TASK_DES, PREFIX_DATE,
                                                                    PREFIX_FREQ, PREFIX_PRIORITY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_TYPE, PREFIX_TASK_DES)
            || !argMultimap.getPreamble().isEmpty()
            || !isValidDeadline(argMultimap)
            || !isValidEvent(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        Priority priority = argMultimap.getValue(PREFIX_PRIORITY).isPresent()
                            ? ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get())
                            : null;

        Frequency frequency = argMultimap.getValue(PREFIX_FREQ).isPresent()
                              ? ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQ).get())
                              : null;

        String taskType = argMultimap.getValue(PREFIX_TASK_TYPE).get();
        String taskDes = argMultimap.getValue(PREFIX_TASK_DES).get();
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        LocalDate[] dates = new LocalDate[2];
        if (taskType.equals(DEADLINE) || taskType.equals(EVENT)) {
            dates = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        }

        Task task = ParserUtil.buildTask(taskType, taskDes, dates);
        task.addFrequency(frequency);
        task.addPriority(priority);
        for (Tag tag : tagList) {
            task.addTag(tag);
        }
        return new AddTaskCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks to see if given task is a valid event
     * @param argMultimap {@code ArgumentMultimap}
     * @return true if task is an event, and has two valid dates - one start date and one end date
     * @throws ParseException
     */
    private static boolean isValidEvent(ArgumentMultimap argMultimap) throws ParseException {
        String type = argMultimap.getValue(PREFIX_TASK_TYPE).get();
        if (type.equals(EVENT)) {

            if (!arePrefixesPresent(argMultimap, PREFIX_DATE)) {
                return false;
            }

            LocalDate[] dates;
            dates = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            return dates[1] != null;
        }

        return true;
    }

    /**
     * Checks to see if the given task is a valid deadline
     * @param argMultimap {@code ArgumentMultiMap}
     * @return true if task is a deadline & has a valid date, and false if it does not
     */
    private static boolean isValidDeadline(ArgumentMultimap argMultimap) {
        String type = argMultimap.getValue(PREFIX_TASK_TYPE).get();
        if (type.equals(DEADLINE)) {
            return arePrefixesPresent(argMultimap, PREFIX_DATE);
        }

        return true;
    }
}
