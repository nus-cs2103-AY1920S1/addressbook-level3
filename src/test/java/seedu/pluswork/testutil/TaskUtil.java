package seedu.pluswork.testutil;

import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_STATUS;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_TAG;

import java.util.Set;

import seedu.pluswork.logic.commands.task.AddTaskCommand;
import seedu.pluswork.logic.commands.task.EditTaskCommand;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Task;

/**
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddTaskCommand(Task task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TASK_NAME + task.getName().fullName + " ");
        sb.append(PREFIX_TASK_STATUS + task.getTaskStatus().toString().toLowerCase() + " ");
        task.getTags().stream().forEach(s -> sb.append(PREFIX_TASK_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTaskDescriptor}'s details.
     */
    public static String getEditTaskDescriptorDetails(EditTaskCommand.EditTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_TASK_NAME).append(name.fullName).append(" "));
        descriptor.getTaskStatus().ifPresent(taskStatus -> sb.append(PREFIX_TASK_STATUS).append(taskStatus.toString()
                .toLowerCase()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TASK_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TASK_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
