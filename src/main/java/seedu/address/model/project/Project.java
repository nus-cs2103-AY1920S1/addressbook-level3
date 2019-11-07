package seedu.address.model.project;

import seedu.address.model.finance.Finance;
import seedu.address.model.person.Person;
import seedu.address.model.timetable.Timetable;


import java.util.ArrayList;
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
    private final List<String> members = new ArrayList<>();
    private final Finance finance;
    private final List<Task> tasks = new ArrayList<>();
    private final Timetable generatedTimetable;
    private Set<Meeting> meeting = new HashSet<>();

    public Project(Title name, Description description, List<String> members, List<Task> tasks, Finance finance, Timetable generatedTimetable) {

        requireAllNonNull(name, description);
        this.description = description;
        this.title = name;
        this.members.addAll(members);
        this.tasks.addAll(tasks);
        this.finance = finance;
        this.generatedTimetable = generatedTimetable;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public boolean hasTask(Task task) {
        return tasks.contains(task);
    }

    public Finance getFinance() {
        return finance;
    }

    public boolean isSameProject(Project project) {
        return this.title.equals(project.getTitle().title);
    }

    public List<String> getMemberNames() {
        return this.members;
    }

    public void deleteMember(String member) {
        int index = members.indexOf(member);
        if (index != -1) {
            members.remove(index);
        }
    }

    public boolean hasMember(Person person) {
        return members.contains(person.getName().fullName);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append("\n")
                .append(getDescription());
        return builder.toString();
    }

    public Timetable getGeneratedTimetable() {
        return generatedTimetable;
    }
}
