package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TAG;

import java.util.HashSet;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

public class AddTaskCommandParserStub implements Parser<AddTaskCommand> {

    public AddTaskCommand parse(String args) throws ParseException {
        Name taskStubName = new Name("finish homework ");
        Task task = new Task(taskStubName, TaskStatus.UNBEGUN, new HashSet<Tag>());

        return new AddTaskCommand(task);
    }
}
