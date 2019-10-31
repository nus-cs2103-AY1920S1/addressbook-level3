package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.DefaultValues;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;

/**
 * A class which gives sample interviewers.
 */
public class SampleInterviewer {
    public static Interviewer getInterviewerOneValidAvailability() {
        String[] availabilities = new String[]{"10/09/2019 18:00-18:30"};
        return getAlicePauline(availabilities);
    }

    public static Interviewer getInterviewerMultipleValidAvailabilities() {
        String[] availabilities = new String[]{"10/09/2019 18:30-19:00", "10/09/2019 19:00-19:30",
            "10/09/2019 20:00-20:30"};
        return getAlicePauline(availabilities);
    }

    /**
     * Invalid date and invalid time.
     */
    public static Interviewer getInterviewerMultipleInvalidAvailabilities() {
        String[] availabilities = new String[]{"20/09/2019 18:30-19:00", "10/09/2019 22:00-23:30"};
        return getAlicePauline(availabilities);
    }

    public static Interviewer getInterviewerMultipleAvailabilitiesSomeInvalid() {
        String[] availabilities = new String[]{"08/09/2019 18:30-19:00", "10/09/2019 19:00-19:30",
            "10/09/2019 20:00-20:30", "10/09/2019 23:00-23:30"};
        return getAlicePauline(availabilities);
    }

    private static Interviewer getAlicePauline(String[] availabilities) {
        Person alice = TypicalPersons.ALICE;
        Department department = new Department("Technical");
        Name name = alice.getName();
        Phone phone = alice.getPhone();
        Email email = DefaultValues.DEFAULT_PERSONAL_EMAIL;
        Set<Tag> tags = alice.getTags();

        Interviewer alicePauline = new Interviewer.InterviewerBuilder(name, phone, tags)
                .email(email)
                .department(department)
                .build();
        alicePauline.setAvailabilities(
                Arrays.stream(availabilities).map(Slot::fromString).collect(Collectors.toList()));
        return alicePauline;
    }

    public static Interviewer getHazel() {
        Interviewer hazel = getInterviewer("Hazel", "Welfare");

        String[] availabilitiesAsArray = new String[]{"10/09/2019 18:30-19:00", "10/09/2019 19:00-19:30",
            "10/09/2019 20:00-20:30", "10/09/2019 20:30-21:00"};
        List<String> availabilities = Arrays.asList(availabilitiesAsArray);
        hazel.setAvailabilities(availabilities.stream().map(Slot::fromString).collect(Collectors.toList()));

        return hazel;
    }

    public static Interviewer getBernard() {
        Interviewer bernard = getInterviewer("Bernard", "Presidential");
        return bernard;
    }

    private static Interviewer getInterviewer(String nameString, String departmentString) {
        Person alice = TypicalPersons.ALICE;
        Department department = new Department(departmentString);
        Name name = new Name(nameString);
        Phone phone = alice.getPhone();
        Email email = DefaultValues.DEFAULT_PERSONAL_EMAIL;
        Set<Tag> tags = alice.getTags();

        return new Interviewer.InterviewerBuilder(name, phone, tags)
                .department(department)
                .email(email)
                .build();
    }

    /**
     * Returns sample slots for the sample graph 1 in the sample graph data.
     */
    public static List<Interviewer> getSampleInterviewersForGraph1() {
        List<Slot> slots = SampleSlot.getSampleSlotsForGraph1();

        List<Slot> slots1 = new LinkedList<>();
        slots1.add(slots.get(0));
        slots1.add(slots.get(1));

        List<Slot> slots2 = new LinkedList<>();
        slots1.add(slots.get(2));
        slots1.add(slots.get(3));

        List<Slot> slots3 = new LinkedList<>();
        slots1.add(slots.get(4));

        Interviewer interviewer1 = new Interviewer.InterviewerBuilder(new Name("Chris"), new Phone("12345678"),
                new HashSet<>()).department(new Department("Technical")).availabilities(slots1).build();
        Interviewer interviewer2 = new Interviewer.InterviewerBuilder(new Name("John"), new Phone("12345678"),
                new HashSet<>()).department(new Department("Technical")).availabilities(slots2).build();
        Interviewer interviewer3 = new Interviewer.InterviewerBuilder(new Name("Barry"), new Phone("12345678"),
                new HashSet<>()).department(new Department("Technical")).availabilities(slots3).build();

        Interviewer[] interviewersArr = new Interviewer[]{interviewer1, interviewer2, interviewer3};
        return Arrays.asList(interviewersArr);
    }
}
