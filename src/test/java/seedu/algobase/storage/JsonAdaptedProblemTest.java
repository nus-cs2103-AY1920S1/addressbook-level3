package seedu.algobase.storage;

import org.junit.jupiter.api.Test;
import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.Problem.Author;
import seedu.algobase.model.Problem.Description;
import seedu.algobase.model.Problem.WebLink;
import seedu.algobase.model.Problem.Name;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.algobase.storage.JsonAdaptedProblem.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblems.BENSON;

public class JsonAdaptedProblemTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getAuthor().toString();
    private static final String VALID_EMAIL = BENSON.getWebLink().toString();
    private static final String VALID_ADDRESS = BENSON.getDescription().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validProblemDetails_returnsProblem() throws Exception {
        JsonAdaptedProblem problem = new JsonAdaptedProblem(BENSON);
        assertEquals(BENSON, problem.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedProblem problem =
                new JsonAdaptedProblem(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedProblem problem = new JsonAdaptedProblem(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_invalidAuthor_throwsIllegalValueException() {
        JsonAdaptedProblem problem =
                new JsonAdaptedProblem(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Author.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_nullAuthor_throwsIllegalValueException() {
        JsonAdaptedProblem problem = new JsonAdaptedProblem(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_invalidWeblink_throwsIllegalValueException() {
        JsonAdaptedProblem problem =
                new JsonAdaptedProblem(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = WebLink.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_nullWeblink_throwsIllegalValueException() {
        JsonAdaptedProblem problem = new JsonAdaptedProblem(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WebLink.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedProblem problem =
                new JsonAdaptedProblem(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedProblem problem = new JsonAdaptedProblem(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedProblem problem =
                new JsonAdaptedProblem(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags);
        assertThrows(IllegalValueException.class, problem::toModelType);
    }

}
