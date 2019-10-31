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
    // Persons
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

    // Typical Interviewees
    public static final Interviewee ALICE_INTERVIEWEE = new IntervieweeBuilder(ALICE).withFaculty("Engineering")
            .withYearOfStudy("2019").withDepartmentChoices("Logistics").withPersonalEmail("HiThere@gmail.com")
            .withNusWorkEmail("Heyo@u.nus.edu").withTimeslots("03/03/2019 16:00-17:00").build();
    public static final Interviewee BENSON_INTERVIEWEE = new IntervieweeBuilder(BENSON).withFaculty("Engineering")
            .withYearOfStudy("2019").withDepartmentChoices("Logistics").withPersonalEmail("HiThere@gmail.com")
            .withNusWorkEmail("Heyo@u.nus.edu").withTimeslots("03/03/2019 16:00-17:00").build();
    public static final Interviewee CARL_INTERVIEWEE = new IntervieweeBuilder(CARL).withFaculty("Engineering")
            .withYearOfStudy("2019").withDepartmentChoices("Logistics").withPersonalEmail("HiThere@gmail.com")
            .withNusWorkEmail("Heyo@u.nus.edu").withTimeslots("03/03/2019 16:00-17:00").build();
    public static final Interviewee DANIEL_INTERVIEWEE = new IntervieweeBuilder(DANIEL).withFaculty("Engineering")
            .withYearOfStudy("2019").withDepartmentChoices("Logistics").withPersonalEmail("HiThere@gmail.com")
            .withNusWorkEmail("Heyo@u.nus.edu").withTimeslots("03/03/2019 16:00-17:00").build();
    public static final Interviewee ELLE_INTERVIEWEE = new IntervieweeBuilder(ELLE).withFaculty("Engineering")
            .withYearOfStudy("2019").withDepartmentChoices("Logistics").withPersonalEmail("HiThere@gmail.com")
            .withNusWorkEmail("Heyo@u.nus.edu").withTimeslots("03/03/2019 16:00-17:00").build();
    public static final Interviewee FIONA_INTERVIEWEE = new IntervieweeBuilder(FIONA).withFaculty("Engineering")
            .withYearOfStudy("2019").withDepartmentChoices("Logistics").withPersonalEmail("HiThere@gmail.com")
            .withNusWorkEmail("Heyo@u.nus.edu").withTimeslots("03/03/2019 16:00-17:00").build();
    public static final Interviewee GEORGE_INTERVIEWEE = new IntervieweeBuilder(GEORGE).withFaculty("Engineering")
            .withYearOfStudy("2019").withDepartmentChoices("Logistics").withPersonalEmail("HiThere@gmail.com")
            .withNusWorkEmail("Heyo@u.nus.edu").withTimeslots("03/03/2019 16:00-17:00").build();

    // Typical Interviewers
    public static final Interviewer ALICE_INTERVIEWER = new InterviewerBuilder(ALICE).withDepartment("Logistics")
            .withEmail("interviewer@gmail.com").withAvailabilities("05/05/2019 16:00-17:00").build();
    public static final Interviewer BENSON_INTERVIEWER = new InterviewerBuilder(BENSON).withDepartment("Logistics")
            .withEmail("interviewer@gmail.com").withAvailabilities("05/05/2019 16:00-17:00").build();
    public static final Interviewer CARL_INTERVIEWER = new InterviewerBuilder(CARL).withDepartment("Logistics")
            .withEmail("interviewer@gmail.com").withAvailabilities("05/05/2019 16:00-17:00").build();
    public static final Interviewer DANIEL_INTERVIEWER = new InterviewerBuilder(DANIEL).withDepartment("Logistics")
            .withEmail("interviewer@gmail.com").withAvailabilities("05/05/2019 16:00-17:00").build();
    public static final Interviewer ELLE_INTERVIEWER = new InterviewerBuilder(ELLE).withDepartment("Logistics")
            .withEmail("interviewer@gmail.com").withAvailabilities("05/05/2019 16:00-17:00").build();
    public static final Interviewer FIONA_INTERVIEWER = new InterviewerBuilder(FIONA).withDepartment("Logistics")
            .withEmail("interviewer@gmail.com").withAvailabilities("05/05/2019 16:00-17:00").build();
    public static final Interviewer GEORGE_INTERVIEWER = new InterviewerBuilder(GEORGE).withDepartment("Logistics")
            .withEmail("interviewer@gmail.com").withAvailabilities("05/05/2019 16:00-17:00").build();

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

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code IntervieweeList} with all the typical interviewees.
     * @return
     */
    public static IntervieweeList getTypicalIntervieweeList() {
        IntervieweeList ib = new IntervieweeList();
        for (Interviewee i: getTypicalInterviewees()) {
            ib.addEntity(i);
        }
        return ib;
    }

    public static List<Interviewee> getTypicalInterviewees() {
        return new ArrayList<>(Arrays.asList(ALICE_INTERVIEWEE, BENSON_INTERVIEWEE, CARL_INTERVIEWEE,
                DANIEL_INTERVIEWEE, ELLE_INTERVIEWEE, FIONA_INTERVIEWEE, GEORGE_INTERVIEWEE));
    }

    public static InterviewerList getTypicalInterviewerList() {
        InterviewerList ib = new InterviewerList();
        for (Interviewer i: getTypicalInterviewers()) {
            ib.addEntity(i);
        }
        return ib;
    }

    public static List<Interviewer> getTypicalInterviewers() {
        return new ArrayList<>(Arrays.asList(ALICE_INTERVIEWER, BENSON_INTERVIEWER, CARL_INTERVIEWER,
                DANIEL_INTERVIEWER, ELLE_INTERVIEWER, FIONA_INTERVIEWER, GEORGE_INTERVIEWER));
    }
}
