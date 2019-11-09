package seedu.address.testutil;

import java.time.LocalDate;
import java.util.Optional;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.Description;
import seedu.address.model.EventTime;
import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code Task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setDescription(task.getDescription());
        descriptor.setDate(task.getDate());
        descriptor.setCustomer(task.getCustomer().getId());

        Optional<Driver> optionalDriver = task.getDriver();
        optionalDriver.ifPresent(driver -> descriptor.setDriver(driver.getId()));

        Optional<EventTime> optionalEventTime = task.getEventTime();
        optionalEventTime.ifPresent(eventTime -> descriptor.setEventTime(eventTime));
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }

    /**
     * Sets the {@code Description} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder changeDescriptionTo(Description description) {
        descriptor.setDescription(description);
        return this;
    }

    /**
     * Sets the {@code Customer} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder changeCustomerTo(int customerId) {
        descriptor.setCustomer(customerId);
        return this;
    }

    /**
     * Sets the {@code LocalDate} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder changeDateTo(LocalDate date) {
        descriptor.setDate(date);
        return this;
    }
}
