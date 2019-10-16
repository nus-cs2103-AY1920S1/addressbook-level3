package seedu.address.model.project;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a project in the app.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Project {

    // Identity fields
    private final Title title;
    private final Description description;
    private final Set<Task> tasks = new HashSet<>();
    private Set<Meeting> meeting = new HashSet<>();

    public Project(Title name, Description description, Set<Task> tasks) {
        requireAllNonNull(name, description);
        this.description = description;
        this.title = name;
        this.tasks.addAll(tasks);
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public Set<Meeting> getListOfMeeting() {
        return meeting;
    }

    public void setListOfMeeting(Set<Meeting> meetings) {
        this.meeting.addAll(meetings);
    }

    public void addNewMeeting(Meeting meeting) {
        this.meeting.add(meeting);
    }

    public Set<Task> getTasks() {
        return Collections.unmodifiableSet(tasks);
    }

    public boolean hasTask(Task task) {
        return tasks.contains(task);
    }


    public boolean isSameProject(Project project) {
        return this.title.equals(project.getTitle().title);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Project Title: ")
                .append(getTitle())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }

}
