package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.*;
import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Finance;
import seedu.address.model.person.Person;
import seedu.address.model.project.*;
import seedu.address.model.timetable.TimeRange;
import seedu.address.model.timetable.Timetable;
import seedu.address.testutil.MeetingBuilder;

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
        public boolean hasBudget(Budget budget) {
            return false;
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
