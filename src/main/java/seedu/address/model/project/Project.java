package seedu.address.model.project;

import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;

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

    public Project(Title name, Description description) {
        requireAllNonNull(name, description);
        this.description = description;
        this.title = name;
    }

    public Title getTitle() { return title; }

    public Description getDescription() { return description; }

    public boolean isSameProject(Project project) {
        return this.title.equals(project.getTitle().title);
    }

    public List<String> getMembers() {
        return this.members;
    }

    public Project clone() {
        Project project = new Project(title, description);
        project.setMembers(this.members);
        return project;
    }

    private void setMembers(List<String> members) {
        this.members.addAll(members);
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
                .append(" Project Title: ")
                .append(getTitle())
                .append(" Description: ")
                .append(getDescription());

        for (String a : members) {
            builder.append(a + " ");
        }
        return builder.toString();
    }

}
