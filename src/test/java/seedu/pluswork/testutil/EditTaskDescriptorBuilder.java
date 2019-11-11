package seedu.pluswork.testutil;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.pluswork.logic.commands.task.EditTaskCommand;
import seedu.pluswork.logic.commands.task.EditTaskCommand.EditTaskDescriptor;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.TaskStatus;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskCommand.EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        requireNonNull(task);
        descriptor = new EditTaskDescriptor();
        descriptor.setName(task.getName());
        descriptor.setTaskStatus(task.getTaskStatus());
        descriptor.setTags(task.getTags());
        if (task.hasDeadline()) {
            descriptor.setDeadline(task.getDeadline());
        }
    }

    /**
     * Sets the {@code Name} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code TaskStatus} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withStatus(TaskStatus taskStatus) {
        descriptor.setTaskStatus(TaskStatus.valueOf(taskStatus.toString()));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditTaskDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code LocalDateTime deadline} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDeadline(LocalDateTime dateTime) {
        descriptor.setDeadline(dateTime);
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
