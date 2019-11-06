package seedu.jarvis.logic.parser.planner;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.logic.commands.planner.PullTaskCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
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

import java.time.LocalDate;
import java.util.function.Predicate;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_DATE;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_FREQ;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_PRIORITY;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TAG;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TASK_TYPE;
import static seedu.jarvis.logic.parser.ParserUtil.MESSAGE_INVALID_TASK_TYPE;
import static seedu.jarvis.logic.parser.ParserUtil.MESSAGE_MULTIPLE_SAME_PREFIX;

/**
 * Parses input arguments and creates a new PullTaskCommand object
 */
//TODO tests
public class PullTaskCommandParser implements Parser<PullTaskCommand> {

    private static String EVENT = "event";
    private static String DEADLINE = "deadline";
    private static String TODO = "todo";

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

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK_TYPE, PREFIX_DATE,
                                                                        PREFIX_FREQ, PREFIX_PRIORITY, PREFIX_TAG);

        if (argumentMultimap.getPrefixSet().size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PullTaskCommand.MESSAGE_USAGE));
        }

        Prefix prefixToBePulled = (Prefix) argumentMultimap.getPrefixSet().toArray()[0];

        if (argumentMultimap.getAllValues(prefixToBePulled).size() > 1) {
            throw new ParseException(MESSAGE_MULTIPLE_SAME_PREFIX);
        }

        Predicate<Task> predicate = parsePrefix(prefixToBePulled, argumentMultimap);

        return new PullTaskCommand(predicate);

    }


    /**
     * Parses the respective prefixes given by the user to return the corresponding
     * {@code Predicate} for {@code PullTaskCommand}
     * @param p the {@code Prefix} to be parsed
     * @return a {@code Predicate} to be applied to the {@code TaskList} during the
     * execution of {@code PullTaskCommand}
     */
    private Predicate<Task> parsePrefix(Prefix p, ArgumentMultimap argumentMultimap) throws ParseException {

        if (PREFIX_DATE.equals(p)) {
            LocalDate d = ParserUtil.parseDate(argumentMultimap.getValue(p).get())[0];
            return new TaskDateMatchesDatePredicate(d);
        } else if (PREFIX_FREQ.equals(p)) {
            Frequency f = ParserUtil.parseFrequency(argumentMultimap.getValue(p).get());
            return new TaskFrequencyMatchesFrequencyPredicate(f);
        } else if (PREFIX_PRIORITY.equals(p)) {
            Priority priority = ParserUtil.parsePriority(argumentMultimap.getValue(p).get());
            return new TaskPriorityMatchesPriorityPredicate(priority);
        } else if (PREFIX_TASK_TYPE.equals(p)) {
            TaskType type = parseTaskType(argumentMultimap.getValue(p).get());
            return new TaskTypeMatchesTypePredicate(type);
        } else if (PREFIX_TAG.equals(p)) {
            Tag tag = ParserUtil.parseTag(argumentMultimap.getValue(p).get());
            return new TaskTagMatchesTagPredicate(tag);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PullTaskCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses {@code String} representation of taskType into {@code TaskType}
     * @param type the string given by the user to be parsed
     * @return {@code TaskType}
     * @throws ParseException if given string is not an event, todo or deadline.
     */
    private TaskType parseTaskType(String type) throws ParseException {
        if (type.equals(TODO)) {
            return TaskType.TODO;
        } else if (type.equals(EVENT)) {
            return TaskType.EVENT;
        } else if (type.equals(DEADLINE)) {
            return TaskType.DEADLINE;
        } else {
            throw new ParseException(MESSAGE_INVALID_TASK_TYPE);
        }
    }


}
