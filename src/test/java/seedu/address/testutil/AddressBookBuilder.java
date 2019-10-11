package seedu.address.testutil;

import seedu.address.model.ProjectDashboard;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ProjectDashboard ab = new AddressBookBuilder().withTask("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private ProjectDashboard projectDashboard;

    public AddressBookBuilder() {
        projectDashboard = new ProjectDashboard();
    }

    public AddressBookBuilder(ProjectDashboard projectDashboard) {
        this.projectDashboard = projectDashboard;
    }

    /**
     * Adds a new {@code Task} to the {@code ProjectDashboard} that we are building.
     */
    public AddressBookBuilder withTask(Task task) {
        projectDashboard.addTask(task);
        return this;
    }

    public ProjectDashboard build() {
        return projectDashboard;
    }
}
