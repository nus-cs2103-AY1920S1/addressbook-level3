package dukecooks.testutil.dashboard;

import dukecooks.logic.commands.dashboard.EditTaskCommand;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.dashboard.components.DashboardName;
import dukecooks.model.dashboard.components.TaskDate;

/**
 * A utility class to help with building EditDashboardDescriptor objects.
 */
public class EditDashboardDescriptorBuilder {

    private EditTaskCommand.EditTaskDescriptor descriptor;

    public EditDashboardDescriptorBuilder() {
        descriptor = new EditTaskCommand.EditTaskDescriptor();
    }

    public EditDashboardDescriptorBuilder(EditTaskCommand.EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskCommand.EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDashboardDescriptor} with fields containing {@code dashboard}'s details
     */
    public EditDashboardDescriptorBuilder(Dashboard dashboard) {
        descriptor = new EditTaskCommand.EditTaskDescriptor();
        descriptor.setDashboardName(dashboard.getDashboardName());
        descriptor.setTaskDate(dashboard.getTaskDate());
    }

    /**
     * Sets the {@code DashboardName} of the {@code EditDashboardDescriptor} that we are building.
     */
    public EditDashboardDescriptorBuilder withDashboardName(String name) {
        descriptor.setDashboardName(new DashboardName(name));
        return this;
    }

    /**
     * Sets the {@code TaskDate} of the {@code EditDashboardDescriptor} that we are building.
     */
    public EditDashboardDescriptorBuilder withTaskDate(String date) {
        descriptor.setTaskDate(new TaskDate(date));
        return this;
    }


    public EditTaskCommand.EditTaskDescriptor build() {
        return descriptor;
    }
}
