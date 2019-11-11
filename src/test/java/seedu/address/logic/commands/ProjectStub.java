package seedu.address.logic.commands;

import seedu.address.model.finance.Finance;
import seedu.address.model.person.Person;
import seedu.address.model.project.*;
import seedu.address.model.timetable.Timetable;

import java.util.List;

/**
 * a default Project stub that has all the methods failing
 */
public class ProjectStub extends Project {

    public ProjectStub(Title name, Description description, List<String> members, List<Task> tasks, Finance finance, Timetable generatedTimetable) {
        super(name, description, members, tasks, finance, generatedTimetable);
    }

    @Override
    public Title getTitle() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Description getDescription() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Meeting> getListOfMeeting() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setListOfMeeting(List<Meeting> meetings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addNewMeeting(Meeting meeting) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<Task> getTasks() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTask(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Finance getFinance() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isSameProject(Project project) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<String> getMemberNames() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteMember(String member) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasMember(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String toString() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Timetable getGeneratedTimetable() {
        throw new AssertionError("This method should not be called.");
    }

}
