package seedu.pluswork.testutil;

import seedu.pluswork.model.ProjectDashboard;
import seedu.pluswork.model.task.Task;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code ProjectDashboard ab = new ProjectDashboardBuilder().withTask("John", "Doe").build();}
 */
public class ProjectDashboardBuilder {

    private ProjectDashboard projectDashboard;

    public ProjectDashboardBuilder() {
        projectDashboard = new ProjectDashboard();
    }

    public ProjectDashboardBuilder(ProjectDashboard projectDashboard) {
        this.projectDashboard = projectDashboard;
    }

    /**
     * Adds a new {@code Task} to the {@code ProjectDashboard} that we are building.
     */
    public ProjectDashboardBuilder withTask(Task task) {
        projectDashboard.addTask(task);
        return this;
    }

    public ProjectDashboard build() {
        return projectDashboard;
    }
}
