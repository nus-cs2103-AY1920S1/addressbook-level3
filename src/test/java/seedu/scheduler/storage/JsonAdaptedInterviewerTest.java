package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PERSONAL_EMAIL_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWER;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.exceptions.IllegalValueException;
import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Email;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Phone;
import seedu.scheduler.model.person.Slot;
import seedu.scheduler.model.tag.Tag;

public class JsonAdaptedInterviewerTest {

    private List<JsonAdaptedSlot> jsonAvailabilities;
    private List<JsonAdaptedTag> jsonTagged;

    @BeforeEach
    public void setUp() {
        jsonAvailabilities = Arrays.asList(new JsonAdaptedSlot(VALID_SLOT_AMY));
        jsonTagged = Arrays.asList(new JsonAdaptedTag(VALID_TAG_FRIEND));
    }

    @Test
    public void toModelType_validInterviewerObject_success() throws Exception {
        JsonAdaptedInterviewer interviewer = new JsonAdaptedInterviewer(ALICE_INTERVIEWER);
        assertEquals(ALICE_INTERVIEWER, interviewer.toModelType());
    }

    @Test
    public void toModelType_validInterviewerDetails_success() throws Exception {
        Set<Tag> tagged = new HashSet<>();
        tagged.add(new Tag(VALID_TAG_FRIEND));
        List<Slot> availabilities = Arrays.asList(Slot.fromString(VALID_SLOT_AMY));

        Interviewer interviewer = new Interviewer.InterviewerBuilder(new Name(VALID_NAME_AMY),
                new Phone(VALID_PHONE_AMY), tagged)
                .department(new Department(VALID_DEPARTMENT_AMY))
                .email(new Email(VALID_PERSONAL_EMAIL_AMY))
                .availabilities(availabilities)
                .build();

        JsonAdaptedInterviewer jsonInterviewer = new JsonAdaptedInterviewer(jsonAvailabilities,
                VALID_DEPARTMENT_AMY, VALID_PERSONAL_EMAIL_AMY, VALID_NAME_AMY, VALID_PHONE_AMY, jsonTagged);

        assertEquals(interviewer, jsonInterviewer.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedInterviewer jsonInterviewer = new JsonAdaptedInterviewer(jsonAvailabilities,
                VALID_DEPARTMENT_AMY, VALID_PERSONAL_EMAIL_AMY, null, VALID_PHONE_AMY, jsonTagged);
        assertThrows(IllegalValueException.class, jsonInterviewer::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedInterviewer jsonInterviewer = new JsonAdaptedInterviewer(jsonAvailabilities,
                VALID_DEPARTMENT_AMY, VALID_PERSONAL_EMAIL_AMY, "R@chel", VALID_PHONE_AMY, jsonTagged);
        assertThrows(IllegalValueException.class, jsonInterviewer::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedInterviewer jsonInterviewer = new JsonAdaptedInterviewer(jsonAvailabilities,
                VALID_DEPARTMENT_AMY, VALID_PERSONAL_EMAIL_AMY, VALID_NAME_AMY, null, jsonTagged);
        assertThrows(IllegalValueException.class, jsonInterviewer::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedInterviewer jsonInterviewer = new JsonAdaptedInterviewer(jsonAvailabilities,
                VALID_DEPARTMENT_AMY, VALID_PERSONAL_EMAIL_AMY, VALID_NAME_AMY, "+65 9999 1111", jsonTagged);
        assertThrows(IllegalValueException.class, jsonInterviewer::toModelType);
    }

    @Test
    public void toModelType_nullDepartment_throwsIllegalValueException() {
        JsonAdaptedInterviewer jsonInterviewer = new JsonAdaptedInterviewer(jsonAvailabilities,
                null, VALID_PERSONAL_EMAIL_AMY, VALID_NAME_AMY, VALID_PHONE_AMY, jsonTagged);
        assertThrows(IllegalValueException.class, jsonInterviewer::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedInterviewer jsonInterviewer = new JsonAdaptedInterviewer(jsonAvailabilities,
                VALID_DEPARTMENT_AMY, null, VALID_NAME_AMY, VALID_PHONE_AMY, jsonTagged);
        assertThrows(IllegalValueException.class, jsonInterviewer::toModelType);
    }
}
