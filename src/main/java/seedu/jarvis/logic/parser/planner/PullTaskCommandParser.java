package seedu.jarvis.logic.parser.planner;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_DATE;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_FREQ;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_PRIORITY;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TAG;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TASK_TYPE;
import static seedu.jarvis.logic.parser.ParserUtil.MESSAGE_INVALID_TASK_TYPE;
import static seedu.jarvis.logic.parser.ParserUtil.MESSAGE_MULTIPLE_SAME_PREFIX;

import java.time.LocalDate;
import java.util.function.Predicate;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.logic.commands.planner.PullTaskCommand;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.Prefix;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.enums.TaskType;
import seedu.jarvis.model.planner.predicates.TaskDateMatchesDatePredicate;
import seedu.jarvis.model.planner.predicates.TaskFrequencyMatchesFrequencyPredicate;
import seedu.jarvis.model.planner.predicates.TaskPriorityMatchesPriorityPredicate;
import seedu.jarvis.model.planner.predicates.TaskTagMatchesTagPredicate;
import seedu.jarvis.model.planner.predicates.TaskTypeMatchesTypePredicate;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * Parses input arguments and creates a new PullTaskCommand object
 */
public class PullTaskCommandParser implements Parser<PullTaskCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the PullTaskCommand
     * and returns a PullTaskCommand object for execution.
     * @param userInput one or more keywords to match the tasks to
     * @return a PullTaskCommand
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public PullTaskCommand parse(String userInput) throws ParseException {
        String args = userInput.trim();

        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PullTaskCommand.MESSAGE_USAGE));
        }

        if (args.split(" ").length > 1) {
            throw new ParseException(MESSAGE_MULTIPLE_SAME_PREFIX);
        }

        Predicate<Task> predicate = parsePrefix(args);

        return new PullTaskCommand(predicate);
    }

    /**
     * Parses {@code String} representation of taskType into {@code TaskType}
     * @param type the string given by the user to be parsed
     * @return {@code TaskType}
     * @throws ParseException if given string is not an event, todo or deadline.
     */
    private TaskType parseTaskType(String type) throws ParseException {
        String todo = "todo";
        String event = "event";
        String deadline = "deadline";

        if (type.equals(todo)) {
            return TaskType.TODO;
        } else if (type.equals(event)) {
            return TaskType.EVENT;
        } else if (type.equals(deadline)) {
            return TaskType.DEADLINE;
        } else {
            throw new ParseException(MESSAGE_INVALID_TASK_TYPE);
        }
    }

    /**
     * Retrieves the value of the attribute that the user wants to pull
     * @param args user input
     * @param prefix attribute that the task list will be sorted by
     * @return value of the attribute
     */
    private String getValue(String args, Prefix prefix) {
        return args.split(prefix.toString())[1];
    }


    /**
     * Parses the respective prefixes given by the user to return the corresponding
     * {@code Predicate}
     * @param arg {@code String} argument to be parsed
     * @return a {@code Predicate} to build the corresponding {@code PullTaskCommand}
     */
    private Predicate<Task> parsePrefix(String arg) throws ParseException {

        if (arg.startsWith(PREFIX_TAG.toString())) {
            Tag t = ParserUtil.parseTag(getValue(arg, PREFIX_TAG));
            return new TaskTagMatchesTagPredicate(t);

        } else if (arg.startsWith(PREFIX_PRIORITY.toString())) {
            Priority priority = ParserUtil.parsePriority(getValue(arg, PREFIX_PRIORITY));
            return new TaskPriorityMatchesPriorityPredicate(priority);

        } else if (arg.startsWith(PREFIX_FREQ.toString())) {
            Frequency frequency = ParserUtil.parseFrequency(getValue(arg, PREFIX_FREQ));
            return new TaskFrequencyMatchesFrequencyPredicate(frequency);

        } else if (arg.startsWith(PREFIX_DATE.toString())) {
            LocalDate date = ParserUtil.parseDate(getValue(arg, PREFIX_DATE))[0];
            return new TaskDateMatchesDatePredicate(date);

        } else if (arg.startsWith(PREFIX_TASK_TYPE.toString())) {
            TaskType type = parseTaskType(getValue(arg, PREFIX_TASK_TYPE));
            return new TaskTypeMatchesTypePredicate(type);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PullTaskCommand.MESSAGE_USAGE));
        }

    }


}
