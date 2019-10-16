package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * A class which gives sample interviewers.
 */
public class SampleInterviewers {
    public static Interviewer getInterviewerOneValidAvailability() {
        String[] availabilities = new String[]{"10/9/2019 18:00 - 18:30"};
        return getAlicePauline(availabilities);
    }

    public static Interviewer getInterviewerMultipleValidAvailabilities() {
        String[] availabilities = new String[]{"10/9/2019 18:30 - 19:00", "10/9/2019 19:00 - 19:30",
            "10/9/2019 20:00 - 20:30"};
        return getAlicePauline(availabilities);
    }

    /**
     * Invalid date and invalid time.
     */
    public static Interviewer getInterviewerMultipleInvalidAvailabilities() {
        String[] availabilities = new String[]{"20/9/2019 18:30 - 19:00", "10/9/2019 22:00 - 23:30"};
        return getAlicePauline(availabilities);
    }

    public static Interviewer getInterviewerMultipleAvailabilitiesSomeInvalid() {
        String[] availabilities = new String[]{"8/9/2019 18:30 - 19:00", "10/9/2019 19:00 - 19:30",
            "10/9/2019 20:00 - 20:30", "10/9/2019 23:00 - 23:30"};
        return getAlicePauline(availabilities);
    }

    private static Interviewer getAlicePauline(String[] availabilities) {
        Person alice = TypicalPersons.ALICE;
        Department department = new Department("Technical");
        Name name = alice.getName();
        Phone phone = alice.getPhone();
        Email email = alice.getEmail();
        Address address = alice.getAddress();
        Set<Tag> tags = alice.getTags();

        Interviewer alicePauline = new Interviewer(department, name, phone, email, address, tags);
        alicePauline.setAvailabilities(Arrays.asList(availabilities));
        return alicePauline;
    }

    public static Interviewer getHazel() {
        Interviewer hazel = getInterviewer("Hazel", "Welfare");

        String[] availabilitiesAsArray = new String[]{"10/9/2019 18:30 - 19:00, 10/9/2019 19:00 - 19:30",
            "10/9/2019 20:00 - 20:30", "10/9/2019 20:30 - 21:00"};
        List<String> availabilities = Arrays.asList(availabilitiesAsArray);
        hazel.setAvailabilities(availabilities);

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
        Email email = alice.getEmail();
        Address address = alice.getAddress();
        Set<Tag> tags = alice.getTags();

        return new Interviewer(department, name, phone, email, address, tags);
    }
}
