package seedu.pluswork.logic.parser;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_STATUS;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.pluswork.logic.commands.AddTaskCommand;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.TaskStatus;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_NAME, PREFIX_TASK_STATUS,
                        PREFIX_TASK_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_TASK_NAME).get());

        TaskStatus taskStatus = TaskStatus.UNBEGUN;
        if (argMultimap.getValue(PREFIX_TASK_STATUS).isPresent()) {
            taskStatus = ParserUtil.parseStatus((argMultimap.getValue(PREFIX_TASK_STATUS).get()));
        }


        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TASK_TAG));

        Task task = new Task(name, taskStatus, tagList);

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
