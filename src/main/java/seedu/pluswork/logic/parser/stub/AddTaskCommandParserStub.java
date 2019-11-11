package seedu.pluswork.logic.parser.stub;

/*import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TAG;*/

import java.util.HashSet;

import seedu.pluswork.logic.commands.task.AddTaskCommand;
import seedu.pluswork.logic.parser.Parser;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.TaskStatus;

//import java.util.stream.Stream;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParserStub implements Parser<AddTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {
        Name taskStubName = new Name("finish homework ");
        Task task = new Task(taskStubName, TaskStatus.UNBEGUN, new HashSet<Tag>());

        return new AddTaskCommand(task);
    }
}
