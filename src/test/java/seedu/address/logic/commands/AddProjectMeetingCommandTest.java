package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.*;
import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Finance;
import seedu.address.model.performanceoverview.PerformanceOverview;
import seedu.address.model.person.Person;
import seedu.address.model.project.*;
import seedu.address.model.timetable.TimeRange;
import seedu.address.model.timetable.Timetable;
import seedu.address.testutil.MeetingBuilder;

import java.nio.file.Path;
import java.text.ParseException;
import java.util.*;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;


public class AddProjectMeetingCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddProjectMeetingCommand(null));
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() throws ParseException {
        Meeting validMeeting = new MeetingBuilder().build();
        AddProjectMeetingCommand addProjectMeetingCommand = new AddProjectMeetingCommand(validMeeting);
        ModelStub modelStub = new ModelStubWithOneProjectStubWithOneMeeting(validMeeting);

        assertThrows(CommandException.class, AddProjectMeetingCommand.MESSAGE_DUPLICATE_MEETING, () -> addProjectMeetingCommand.execute(modelStub));
    }

    @Test
    public void equals() throws ParseException {
        Meeting meetingMilestone = new MeetingBuilder().withDescription("Milestone discussion").build();
        Meeting meetingDocumentation = new MeetingBuilder().withDescription("Finalising documentation").build();
        AddProjectMeetingCommand addDocumentationCommand = new AddProjectMeetingCommand(meetingDocumentation);
        AddProjectMeetingCommand addMilestoneCommand = new AddProjectMeetingCommand(meetingMilestone);

        // same object -> returns true
        assertTrue(addDocumentationCommand.equals(addDocumentationCommand));

        // same values -> returns true
        AddProjectMeetingCommand addDocumentationCommandCopy = new AddProjectMeetingCommand(meetingDocumentation);
        assertTrue(addDocumentationCommand.equals(addDocumentationCommandCopy));

        // different types -> returns false
        assertFalse(addDocumentationCommand.equals(1));

        // null -> returns false
        assertFalse(addDocumentationCommand.equals(null));

        // different person -> returns false
        assertFalse(addDocumentationCommand.equals(addMilestoneCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setWorkingProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeWorkingProject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Project> getWorkingProject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isCheckedOut() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Person> getMembers() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerformanceOverview(PerformanceOverview overview) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public PerformanceOverview getPerformanceOverview() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editTaskInAllPersons(Task taskToEdit, Task editedTask, Project currWorkingProject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTaskInAllPersons(Task task, Project currWorkingProject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMeetingInAllPersons(Meeting meeting, Project currWorkingProject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Person> getMembersOf(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isSignedIn() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void signIn(OwnerAccount ownerAccount) throws Exception {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public OwnerAccount getOwnerAccount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void logOut() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getProjectListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProjectListFilePath(Path projectListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProjectList(ReadOnlyProjectList projectList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProjectList getProjectList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteProject(Project target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProject(Project target, Project editedProject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMember(String name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void editInAllProjects(Person personToEdit, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Project> getFilteredProjectList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredProjectList(Predicate<Project> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * a default Project stub that has all the methods failing
     */
    private class ProjectStub extends Project {

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

    /**
     * A Model stub that contains one Project with a single meeting.
     */
    private class ModelStubWithOneProjectStubWithOneMeeting extends ModelStub {
        private final Title title = new Title("CS2103T");
        private final Description description = new Description("Software Engineering");
        private final List<String> members = new ArrayList<>();
        private final List<Task> tasks = new ArrayList<Task>();
        private final Finance finance = new Finance(new ArrayList<Budget>());
        private final Timetable timetable = new Timetable(new ArrayList<TimeRange>());

        private ProjectStubWithOneMeeting projectStubWithOneMeeting = new ProjectStubWithOneMeeting(title, description, members, tasks, finance, timetable);

        private final Meeting meeting;

        ModelStubWithOneProjectStubWithOneMeeting(Meeting meeting) throws ParseException {
            this.meeting = meeting;
            List<Meeting> meetingList = new ArrayList<>();
            meetingList.add(meeting);
            projectStubWithOneMeeting.setListOfMeeting(meetingList);
        }

        @Override
        public Optional<Project> getWorkingProject() {
            return Optional.of(this.projectStubWithOneMeeting);
        }

        @Override
        public boolean isCheckedOut() {
            return true;
        }

        @Override
        public void setProject(Project projectToEdit, Project editedProject) {
            projectToEdit = editedProject;
        }

        @Override
        public void setWorkingProject(Project editedProject) {
            requireNonNull(editedProject);
        }

        @Override
        public void updateFilteredProjectList(Predicate<Project> project) {
            requireNonNull(project);
        }

        public ProjectStubWithOneMeeting getProjectStubWithOneMeeting() {
            return this.projectStubWithOneMeeting;
        }
    }

    /**
     * Project Stub with one Meeting
     */

    public class ProjectStubWithOneMeeting extends ProjectStub {
        private Title title;
        private Description description;
        private List<String> members;
        private List<Task> tasks;
        private Finance finance;
        private Timetable timetable;
        private List<Meeting> meetingList = new ArrayList<>();

        public ProjectStubWithOneMeeting(Title title, Description description, List<String> members, List<Task> tasks, Finance finance, Timetable timetable) {
            super(title, description, members, tasks, finance, timetable);
        }


        @Override
        public List<Task> getTasks() {
            return this.tasks;
        }

        @Override
        public List<String> getMemberNames() {
            return this.members;
        }

        @Override
        public Finance getFinance() {
            return this.finance;
        }

        @Override
        public Timetable getGeneratedTimetable() {
            return this.timetable;
        }

        @Override
        public Description getDescription() {
            return this.description;
        }

        @Override
        public Title getTitle() {
            return this.title;
        }

        @Override
        public List<Meeting> getListOfMeeting() {
            return this.meetingList;
        }

        @Override
        public void setListOfMeeting(List<Meeting> meetingList) {
            this.meetingList = meetingList;
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}

