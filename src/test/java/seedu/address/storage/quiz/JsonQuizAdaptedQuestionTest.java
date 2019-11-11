package seedu.address.storage.quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.quiz.person.Type.MESSAGE_CONSTRAINTS;
import static seedu.address.storage.quiz.JsonQuizAdaptedQuestion.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.quiz.person.Answer;
import seedu.address.model.quiz.person.Category;
import seedu.address.model.quiz.person.Name;
import seedu.address.model.quiz.person.Question;
import seedu.address.testutil.QuestionBuilder;

public class JsonQuizAdaptedQuestionTest {
    public static final Question BENSON = new QuestionBuilder().withName("What is bob favourite fruit?")
            .withAnswer("Banana").withCategory("PrimarySch").withType("high").withTags("owesMoney", "friends").build();

    private static final String INVALID_NAME = "R@James<qns>";
    private static final String INVALID_ANSWER = "911<ans>4";
    private static final String INVALID_TYPE = "something";
    private static final String INVALID_CATEGORY = "";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_ANSWER = BENSON.getAnswer().toString();
    private static final String VALID_CATEGORY = BENSON.getCategory().toString();
    private static final String VALID_TYPE = BENSON.getType().toString();
    private static final List<JsonQuizAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonQuizAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validQuestionDetails_returnsQuestion() throws Exception {
        JsonQuizAdaptedQuestion question = new JsonQuizAdaptedQuestion(BENSON);
        assertEquals(BENSON, question.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonQuizAdaptedQuestion question = new JsonQuizAdaptedQuestion(INVALID_NAME, null,
                VALID_ANSWER, VALID_CATEGORY, VALID_TYPE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonQuizAdaptedQuestion question = new JsonQuizAdaptedQuestion(null, null,
                VALID_ANSWER, VALID_CATEGORY, VALID_TYPE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        JsonQuizAdaptedQuestion question = new JsonQuizAdaptedQuestion(VALID_NAME, null,
                INVALID_ANSWER, VALID_CATEGORY, VALID_TYPE, VALID_TAGS);
        String expectedMessage = Answer.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullAnswer_throwsIllegalValueException() {
        JsonQuizAdaptedQuestion question = new JsonQuizAdaptedQuestion(VALID_NAME, null, null,
                VALID_CATEGORY, VALID_TYPE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonQuizAdaptedQuestion question =
                new JsonQuizAdaptedQuestion(VALID_NAME, null, VALID_ANSWER, INVALID_CATEGORY, VALID_TYPE, VALID_TAGS);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonQuizAdaptedQuestion question = new JsonQuizAdaptedQuestion(VALID_NAME, null, VALID_ANSWER,
                null, VALID_TYPE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() {
        JsonQuizAdaptedQuestion question =
                new JsonQuizAdaptedQuestion(VALID_NAME, null, VALID_ANSWER, VALID_CATEGORY, INVALID_TYPE, VALID_TAGS);
        String expectedMessage = String.format(MESSAGE_CONSTRAINTS, Question.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonQuizAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonQuizAdaptedTag(INVALID_TAG));
        JsonQuizAdaptedQuestion question =
                new JsonQuizAdaptedQuestion(VALID_NAME, null, VALID_ANSWER, VALID_CATEGORY, VALID_TYPE, invalidTags);
        assertThrows(IllegalValueException.class, question::toModelType);
    }

}
