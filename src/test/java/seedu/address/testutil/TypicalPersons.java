package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACULTY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUS_WORK_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUS_WORK_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERSONAL_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERSONAL_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SLOT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SLOT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_OF_STUDY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_OF_STUDY_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.IntervieweeList;
import seedu.address.model.InterviewerList;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Interviewee ANSON = new IntervieweeBuilder(new PersonBuilder().withName("Anson Pauline")
            .withPhone("94351253").withTags("friends").build())
            .withFaculty("School of computing")
            .withYearOfStudy("2019")
            .withDepartmentChoices("Marketing")
            .withTimeslots("16/10/2019 00:00-23:59")
            .withPersonalEmail("anson@gmail.com")
            .withNusWorkEmail("anson@u.nus.edu")
            .build();

    public static final Interviewer IAN = new InterviewerBuilder(new PersonBuilder().withName("Ian Scotch")
            .withPhone("91234567").withTags("senior").build())
            .withDepartment("Technical")
            .withEmail("test@example.com")
            .withAvailabilities("23/10/2019 00:00-23:59")
            .build();

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withPhone("94351253").withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withPhone("98765432").withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY_PERSON = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Interviewee AMY_INTERVIEWEE = new IntervieweeBuilder(AMY_PERSON).withFaculty(VALID_FACULTY_AMY)
            .withYearOfStudy(VALID_YEAR_OF_STUDY_AMY).withDepartmentChoices(VALID_DEPARTMENT_AMY)
            .withTimeslots(VALID_SLOT_AMY).withPersonalEmail(VALID_PERSONAL_EMAIL_AMY)
            .withNusWorkEmail(VALID_NUS_WORK_EMAIL_AMY).build();
    public static final Person BOB_PERSON = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    public static final Interviewee BOB_INTERVIEWEE = new IntervieweeBuilder(BOB_PERSON).withFaculty(VALID_FACULTY_BOB)
            .withYearOfStudy(VALID_YEAR_OF_STUDY_BOB).withDepartmentChoices(VALID_DEPARTMENT_BOB)
            .withTimeslots(VALID_SLOT_BOB).withPersonalEmail(VALID_PERSONAL_EMAIL_BOB)
            .withNusWorkEmail(VALID_NUS_WORK_EMAIL_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code IntervieweeList} with all the typical Interviewees.
     */
    public static IntervieweeList getTypicalIntervieweeList() {
        IntervieweeList iveelist = new IntervieweeList();

        for (Interviewee interviewee : getTypicalInterviewees()) {
            iveelist.addInterviewee(interviewee);
        }

        return iveelist;
    }

    /**
     * Returns an {@code InterviewerList} with all the typical Interviewers.
     */
    public static InterviewerList getTypicalInterviewerList() {
        InterviewerList iverlist = new InterviewerList();

        for (Interviewer interviewer : getTypicalInterviewers()) {
            iverlist.addInterviewer(interviewer);
        }

        return iverlist;
    }

    public static List<Interviewee> getTypicalInterviewees() {
        return new ArrayList<>(Arrays.asList(ANSON));
    }

    public static List<Interviewer> getTypicalInterviewers() {
        return new ArrayList<>(Arrays.asList(IAN));
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
