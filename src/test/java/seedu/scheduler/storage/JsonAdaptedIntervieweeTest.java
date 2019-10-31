package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.scheduler.model.person.DefaultValues;
import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Emails;
import seedu.scheduler.model.person.Faculty;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Phone;
import seedu.scheduler.model.person.Slot;

class JsonAdaptedIntervieweeTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_TAG = "#friend";

    private static final Faculty VALID_FACULTY = DefaultValues.DEFAULT_FACULTY;
    private static final Integer VALID_YEAR_OF_STUDY = DefaultValues.DEFAULT_YEAR_OF_STUDY;
    private static final List<Department> VALID_DEPARTMENTS = DefaultValues.DEFAULT_DEPARTMENTS;
    private static final List<Slot> VALID_SLOTS = DefaultValues.DEFAULT_TIMESLOTS;
    private static final Emails VALID_EMAILS = DefaultValues.DEFAULT_EMAILS;
    private static final Name VALID_NAME = DefaultValues.DEFAULT_NAME;
    private static final Phone VALID_PHONE = DefaultValues.DEFAULT_PHONE;
    private static final List<JsonAdaptedTag> VALID_TAGS = ALICE_INTERVIEWEE.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validIntervieweeDetails_returnsInterviewee() throws Exception {
        JsonAdaptedInterviewee interviewee = new JsonAdaptedInterviewee(ALICE_INTERVIEWEE);
        assertEquals(ALICE_INTERVIEWEE, interviewee.toModelType());
    }

    @Test void toModelType_invalidName_throwsIllegalValueException() {

    }
}
