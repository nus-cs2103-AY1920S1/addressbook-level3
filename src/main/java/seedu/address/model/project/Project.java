package seedu.address.model.project;

import java.util.HashSet;
import java.util.List;
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
    private Set<Meeting> meeting = new HashSet<>();

    public Project(Title name, Description description) {
        requireAllNonNull(name, description);
        this.description = description;
        this.title = name;
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public Set<Meeting> getListOfMeeting() { return meeting;}

    public void setListOfMeeting(Set<Meeting> meetings) {
        this.meeting.addAll(meetings);
    }

    public void addNewMeeting(Meeting meeting) {
        this.meeting.add(meeting);
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
