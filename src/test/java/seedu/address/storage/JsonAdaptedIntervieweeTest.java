package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.ANSON;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.DefaultValues;
import seedu.address.model.person.Department;
import seedu.address.model.person.Emails;
import seedu.address.model.person.Faculty;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Slot;

class JsonAdaptedIntervieweeTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TAG = "#friend";

    private static final Faculty VALID_FACULTY = DefaultValues.DEFAULT_FACULTY;
    private static final Integer VALID_YEAR_OF_STUDY = DefaultValues.DEFAULT_YEAR_OF_STUDY;
    private static final List<Department> VALID_DEPARTMENTS = DefaultValues.DEFAULT_DEPARTMENTS;
    private static final List<Slot> VALID_SLOTS = DefaultValues.DEFAULT_TIMESLOTS;
    private static final Emails VALID_EMAILS = DefaultValues.DEFAULT_EMAILS;
    private static final Name VALID_NAME = DefaultValues.DEFAULT_NAME;
    private static final Phone VALID_PHONE = DefaultValues.DEFAULT_PHONE;
    private static final Address VALID_ADDRESS = DefaultValues.DEFAULT_ADDRESS;
    private static final List<JsonAdaptedTag> VALID_TAGS = ANSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validIntervieweeDetails_returnsInterviewee() throws Exception {
        JsonAdaptedInterviewee interviewee = new JsonAdaptedInterviewee(ANSON);
        assertEquals(ANSON, interviewee.toModelType());
    }

    @Test void toModelType_invalidName_throwsIllegalValueException() {

    }
}
