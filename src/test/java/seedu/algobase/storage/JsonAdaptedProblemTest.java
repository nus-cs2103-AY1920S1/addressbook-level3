package seedu.algobase.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.algobase.storage.JsonAdaptedProblem.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblems.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.WebLink;

public class JsonAdaptedProblemTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_AUTHOR = "+651234";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_WEBLINK = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_AUTHOR = BENSON.getAuthor().toString();
    private static final String VALID_WEBLINK = BENSON.getWebLink().toString();
    private static final String VALID_DESCRIPTION = BENSON.getDescription().toString();
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
                new JsonAdaptedProblem(INVALID_NAME, VALID_AUTHOR, VALID_WEBLINK, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedProblem problem = new JsonAdaptedProblem(null, VALID_AUTHOR, VALID_WEBLINK, VALID_DESCRIPTION,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_invalidAuthor_throwsIllegalValueException() {
        JsonAdaptedProblem problem =
                new JsonAdaptedProblem(VALID_NAME, INVALID_AUTHOR, VALID_WEBLINK, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Author.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_nullAuthor_throwsIllegalValueException() {
        JsonAdaptedProblem problem = new JsonAdaptedProblem(VALID_NAME, null, VALID_WEBLINK, VALID_DESCRIPTION,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_invalidWeblink_throwsIllegalValueException() {
        JsonAdaptedProblem problem =
                new JsonAdaptedProblem(VALID_NAME, VALID_AUTHOR, INVALID_WEBLINK, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = WebLink.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_nullWeblink_throwsIllegalValueException() {
        JsonAdaptedProblem problem = new JsonAdaptedProblem(VALID_NAME, VALID_AUTHOR, null, VALID_DESCRIPTION,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WebLink.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedProblem problem =
                new JsonAdaptedProblem(VALID_NAME, VALID_AUTHOR, VALID_WEBLINK, INVALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedProblem problem = new JsonAdaptedProblem(VALID_NAME, VALID_AUTHOR, VALID_WEBLINK, null,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, problem::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedProblem problem =
                new JsonAdaptedProblem(VALID_NAME, VALID_AUTHOR, VALID_WEBLINK, VALID_DESCRIPTION, invalidTags);
        assertThrows(IllegalValueException.class, problem::toModelType);
    }

}
