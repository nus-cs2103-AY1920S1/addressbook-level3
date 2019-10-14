package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.calendar.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Marking;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDescription;
import seedu.address.model.task.TaskTime;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class AddTaskCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX__TASK_DESCRIPTION, PREFIX_MARKING, PREFIX_TASK_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX__TASK_DESCRIPTION, PREFIX_MARKING, PREFIX_TASK_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        TaskDescription taskDescription = ParserUtil.parseTaskDescription(argMultimap.getValue(PREFIX__TASK_DESCRIPTION).get());
        Marking marking = ParserUtil.parseMarking(argMultimap.getValue(PREFIX_MARKING).get());
        Set<TaskTime> taskTimeList = ParserUtil.parseTaskTimes(argMultimap.getAllValues(PREFIX_TASK_TIME));

        Task task = new Task(taskDescription, taskTimeList, marking);

        return new AddTaskCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
