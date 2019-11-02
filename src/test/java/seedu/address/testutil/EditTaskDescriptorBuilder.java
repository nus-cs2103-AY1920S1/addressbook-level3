package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.calendar.commands.EditCommand;
import seedu.address.model.calendar.tag.TaskTag;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskDay;
import seedu.address.model.calendar.task.TaskDeadline;
import seedu.address.model.calendar.task.TaskDescription;
import seedu.address.model.calendar.task.TaskTime;
import seedu.address.model.calendar.task.TaskTitle;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditCommand.EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditCommand.EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditCommand.EditTaskDescriptor descriptor) {
        this.descriptor = new EditCommand.EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code person}'s details
     */
    public EditTaskDescriptorBuilder(Task person) {
        descriptor = new EditCommand.EditTaskDescriptor();
        descriptor.setTaskTitle(person.getTaskTitle());
        descriptor.setTaskTime(person.getTaskTime());
        descriptor.setTaskDescription(person.getTaskDescription());
        descriptor.setTaskDay(person.getTaskDay());
        descriptor.setTaskDeadline(person.getTaskDeadline());
        descriptor.setTaskTags(person.getTaskTags());
    }

    /**
     * Sets the {@code TaskTitle} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskTitle(String name) {
        descriptor.setTaskTitle(new TaskTitle(name));
        return this;
    }

    /**
     * Sets the {@code TaskTime} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskTime(String phone) {
        descriptor.setTaskTime(new TaskTime(phone));
        return this;
    }

    /**
     * Sets the {@code TaskDeadline} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskDeadline(String deadline) {
        descriptor.setTaskDeadline(new TaskDeadline(deadline));
        return this;
    }

    /**
     * Sets the {@code TaskDescription} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskDescription(String email) {
        descriptor.setTaskDescription(new TaskDescription(email));
        return this;
    }

    /**
     * Sets the {@code TaskDay} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withTaskDay(String taskDay) {
        descriptor.setTaskDay(new TaskDay(taskDay));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<TaskTag>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditTaskDescriptorBuilder withTaskTags(String... tags) {
        Set<TaskTag> tagSet = Stream.of(tags).map(TaskTag::new).collect(Collectors.toSet());
        descriptor.setTaskTags(tagSet);
        return this;
    }

    public EditCommand.EditTaskDescriptor build() {
        return descriptor;
    }
}
