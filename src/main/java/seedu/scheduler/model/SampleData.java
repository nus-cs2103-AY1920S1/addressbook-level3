package seedu.scheduler.model;

import java.util.Arrays;
import java.util.HashSet;

import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Email;
import seedu.scheduler.model.person.Emails;
import seedu.scheduler.model.person.Faculty;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Person;
import seedu.scheduler.model.person.Phone;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.model.tag.Tag;

/**
 * Utility methods for generating a sample IntervieweeList and InterviewerList.
 */
public class SampleData {

    // Sample Persons to be used as either Interviewees or Interviewers
    // As sample data, females are Interviewees whereas males are Interviewers
    private static final Person SAMPLE_PERSON_ALICE = new Person(new Name("Alice Pauline"), new Phone("94351253"),
            new HashSet<>(Arrays.asList(new Tag("friends"))));
    private static final Person SAMPLE_PERSON_BETTY = new Person(new Name("Betty Smith"), new Phone("81535190"),
            new HashSet<>());
    private static final Person SAMPLE_PERSON_CHLOE = new Person(new Name("Chloe Brown"), new Phone("92731418"),
            new HashSet<>(Arrays.asList(new Tag("friends"), new Tag("important"))));
    private static final Person SAMPLE_PERSON_AARON = new Person(new Name("Aaron Davis"), new Phone("92651735"),
            new HashSet<>(Arrays.asList(new Tag("friends"))));
    private static final Person SAMPLE_PERSON_BENNY = new Person(new Name("Benny Jones"), new Phone("92635143"),
            new HashSet<>());
    private static final Person SAMPLE_PERSON_CALEB = new Person(new Name("Caleb Williams"), new Phone("82632413"),
            new HashSet<>(Arrays.asList(new Tag("friends"), new Tag("important"))));

    // Sample Interviewees
    private static final Interviewee SAMPLE_INTERVIEWEE_ALICE = new Interviewee.IntervieweeBuilder(SAMPLE_PERSON_ALICE)
            .faculty(new Faculty("Engineering")).yearOfStudy(2)
            .departmentChoices(Arrays.asList(new Department("Publicity")))
            .emails(new Emails().addPersonalEmail(new Email("alice@example.com"))
                    .addNusEmail(new Email("alice@u.nus.edu")))
            .availableTimeslots(Arrays.asList(Slot.fromString("03/03/2019 16:00-16:30"))).build();
    private static final Interviewee SAMPLE_INTERVIEWEE_BETTY = new Interviewee.IntervieweeBuilder(SAMPLE_PERSON_BETTY)
            .faculty(new Faculty("Computing")).yearOfStudy(1)
            .departmentChoices(Arrays.asList(new Department("Marketing")))
            .emails(new Emails().addPersonalEmail(new Email("betty@example.com"))
                    .addNusEmail(new Email("betty@u.nus.edu")))
            .availableTimeslots(Arrays.asList(Slot.fromString("03/03/2019 16:00-16:30"),
                    Slot.fromString("03/03/2019 16:30-17:00"))).build();
    private static final Interviewee SAMPLE_INTERVIEWEE_CHLOE = new Interviewee.IntervieweeBuilder(SAMPLE_PERSON_CHLOE)
            .faculty(new Faculty("Science")).yearOfStudy(1)
            .departmentChoices(Arrays.asList(new Department("Marketing")))
            .emails(new Emails().addPersonalEmail(new Email("chloe@example.com"))
                    .addNusEmail(new Email("chloe@u.nus.edu")))
            .availableTimeslots(Arrays.asList(Slot.fromString("03/03/2019 16:00-16:30"),
                    Slot.fromString("03/03/2019 16:30-17:00"), Slot.fromString("03/03/2019 17:00-17:30"))).build();

    // Sample Interviewers
    private static final Interviewer SAMPLE_INTERVIEWER_AARON = new Interviewer.InterviewerBuilder(SAMPLE_PERSON_AARON)
            .department(new Department("Publicity")).email(new Email("aaron@example.com"))
            .availabilities(Arrays.asList(Slot.fromString("03/03/2019 16:30-17:00"),
                    Slot.fromString("03/03/2019 17:00-17:30")))
            .build();
    private static final Interviewer SAMPLE_INTERVIEWER_BENNY = new Interviewer.InterviewerBuilder(SAMPLE_PERSON_BENNY)
            .department(new Department("Marketing")).email(new Email("benny@example.com"))
            .availabilities(Arrays.asList(Slot.fromString("03/03/2019 16:30-17:00"),
                    Slot.fromString("03/03/2019 17:00-17:30")))
            .build();
    private static final Interviewer SAMPLE_INTERVIEWER_CALEB = new Interviewer.InterviewerBuilder(SAMPLE_PERSON_CALEB)
            .department(new Department("Publicity")).email(new Email("caleb@example.com"))
            .availabilities(Arrays.asList(Slot.fromString("03/03/2019 16:00-16:30"),
                    Slot.fromString("03/03/2019 16:30-17:00"), Slot.fromString("03/03/2019 17:00-17:30")))
            .build();

    public static ReadOnlyList<Interviewee> getSampleIntervieweeList() {
        IntervieweeList intervieweeList = new IntervieweeList();
        intervieweeList.addEntity(SAMPLE_INTERVIEWEE_ALICE);
        intervieweeList.addEntity(SAMPLE_INTERVIEWEE_BETTY);
        intervieweeList.addEntity(SAMPLE_INTERVIEWEE_CHLOE);
        return intervieweeList;
    }

    public static ReadOnlyList<Interviewer> getSampleInterviewerList() {
        InterviewerList interviewerList = new InterviewerList();
        interviewerList.addEntity(SAMPLE_INTERVIEWER_AARON);
        interviewerList.addEntity(SAMPLE_INTERVIEWER_BENNY);
        interviewerList.addEntity(SAMPLE_INTERVIEWER_CALEB);
        return interviewerList;
    }

}
