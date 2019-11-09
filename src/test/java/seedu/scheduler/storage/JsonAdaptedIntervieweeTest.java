package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_FACULTY_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NUS_WORK_EMAIL_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PERSONAL_EMAIL_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_YEAR_OF_STUDY_AMY;
import static seedu.scheduler.model.person.EmailType.NUS;
import static seedu.scheduler.model.person.EmailType.PERSONAL;
import static seedu.scheduler.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.exceptions.IllegalValueException;
import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Email;
import seedu.scheduler.model.person.EmailType;
import seedu.scheduler.model.person.Emails;
import seedu.scheduler.model.person.Faculty;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Phone;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.model.tag.Tag;

class JsonAdaptedIntervieweeTest {

    private List<JsonAdaptedDepartment> jsonDepartmentChoices;
    private List<JsonAdaptedSlot> jsonAvailableTimeslots;
    private HashMap<String, List<String>> jsonEmails;
    private List<JsonAdaptedTag> jsonTagged;

    @BeforeEach
    public void setUp() {
        jsonDepartmentChoices = Arrays.asList(new JsonAdaptedDepartment(VALID_DEPARTMENT_AMY));
        jsonAvailableTimeslots = Arrays.asList(new JsonAdaptedSlot(VALID_SLOT_AMY));
        jsonEmails = new HashMap<>();
        jsonEmails.put(PERSONAL.toString(), Arrays.asList(VALID_PERSONAL_EMAIL_AMY));
        jsonEmails.put(NUS.toString(), Arrays.asList(VALID_NUS_WORK_EMAIL_AMY));
        jsonTagged = Arrays.asList(new JsonAdaptedTag(VALID_TAG_FRIEND));
    }

    @Test
    public void toModelType_validIntervieweeObject_success() throws Exception {
        JsonAdaptedInterviewee interviewee = new JsonAdaptedInterviewee(ALICE_INTERVIEWEE);
        assertEquals(ALICE_INTERVIEWEE, interviewee.toModelType());
    }

    @Test
    public void toModelType_validIntervieweeDetails_success() throws Exception {
        Set<Tag> tagged = new HashSet<>();
        tagged.add(new Tag(VALID_TAG_FRIEND));
        List<Department> departmentChoices = Arrays.asList(new Department(VALID_DEPARTMENT_AMY));
        List<Slot> availableTimeslots = Arrays.asList(Slot.fromString(VALID_SLOT_AMY));
        HashMap<EmailType, List<Email>> hashEmails = new HashMap<>();
        hashEmails.put(PERSONAL, Arrays.asList(new Email(VALID_PERSONAL_EMAIL_AMY)));
        hashEmails.put(NUS, Arrays.asList(new Email(VALID_NUS_WORK_EMAIL_AMY)));
        Emails emails = new Emails(hashEmails);

        Interviewee interviewee = new Interviewee.IntervieweeBuilder(new Name(VALID_NAME_AMY),
                new Phone(VALID_PHONE_AMY), tagged)
                .faculty(new Faculty(VALID_FACULTY_AMY))
                .yearOfStudy(Integer.parseInt(VALID_YEAR_OF_STUDY_AMY))
                .departmentChoices(departmentChoices)
                .availableTimeslots(availableTimeslots)
                .emails(emails)
                .build();

        JsonAdaptedInterviewee jsonInterviewee = new JsonAdaptedInterviewee(VALID_FACULTY_AMY,
                Integer.parseInt(VALID_YEAR_OF_STUDY_AMY), jsonDepartmentChoices, jsonAvailableTimeslots,
                jsonEmails, VALID_NAME_AMY, VALID_PHONE_AMY, jsonTagged);

        assertEquals(interviewee, jsonInterviewee.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedInterviewee jsonInterviewee = new JsonAdaptedInterviewee(VALID_FACULTY_AMY,
                Integer.parseInt(VALID_YEAR_OF_STUDY_AMY), jsonDepartmentChoices, jsonAvailableTimeslots,
                jsonEmails, null, VALID_PHONE_AMY, jsonTagged);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());

        assertThrows(IllegalValueException.class, expectedMessage, jsonInterviewee::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedInterviewee jsonInterviewee = new JsonAdaptedInterviewee(VALID_FACULTY_AMY,
                Integer.parseInt(VALID_YEAR_OF_STUDY_AMY), jsonDepartmentChoices, jsonAvailableTimeslots,
                jsonEmails, "R@chel", VALID_PHONE_AMY, jsonTagged);

        assertThrows(IllegalValueException.class, Name.MESSAGE_CONSTRAINTS, jsonInterviewee::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedInterviewee jsonInterviewee = new JsonAdaptedInterviewee(VALID_FACULTY_AMY,
                Integer.parseInt(VALID_YEAR_OF_STUDY_AMY), jsonDepartmentChoices, jsonAvailableTimeslots,
                jsonEmails, VALID_NAME_AMY, null, jsonTagged);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());

        assertThrows(IllegalValueException.class, expectedMessage, jsonInterviewee::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedInterviewee jsonInterviewee = new JsonAdaptedInterviewee(VALID_FACULTY_AMY,
                Integer.parseInt(VALID_YEAR_OF_STUDY_AMY), jsonDepartmentChoices, jsonAvailableTimeslots,
                jsonEmails, VALID_NAME_AMY, "+65 9999 1111", jsonTagged);

        assertThrows(IllegalValueException.class, Phone.MESSAGE_CONSTRAINTS, jsonInterviewee::toModelType);
    }

    @Test
    public void toModelType_nullFaculty_throwsIllegalValueException() {
        JsonAdaptedInterviewee jsonInterviewee = new JsonAdaptedInterviewee(null,
                Integer.parseInt(VALID_YEAR_OF_STUDY_AMY), jsonDepartmentChoices, jsonAvailableTimeslots,
                jsonEmails, VALID_NAME_AMY, VALID_PHONE_AMY, jsonTagged);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Faculty.class.getSimpleName());

        assertThrows(IllegalValueException.class, expectedMessage, jsonInterviewee::toModelType);
    }

    @Test
    public void toModelType_nullYearOfStudy_throwsIllegalValueException() {
        JsonAdaptedInterviewee jsonInterviewee = new JsonAdaptedInterviewee(VALID_FACULTY_AMY,
                null, jsonDepartmentChoices, jsonAvailableTimeslots,
                jsonEmails, VALID_NAME_AMY, VALID_PHONE_AMY, jsonTagged);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Integer.class.getSimpleName());

        assertThrows(IllegalValueException.class, expectedMessage, jsonInterviewee::toModelType);
    }
}
