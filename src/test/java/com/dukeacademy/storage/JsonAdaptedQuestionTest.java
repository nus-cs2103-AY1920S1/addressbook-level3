package com.dukeacademy.storage;

import static com.dukeacademy.testutil.Assert.assertThrows;
import static com.dukeacademy.testutil.TypicalQuestions.BENSON;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.model.question.Difficulty;
import com.dukeacademy.model.question.Status;
import com.dukeacademy.model.question.Title;
import com.dukeacademy.model.question.Topic;

public class JsonAdaptedQuestionTest {
    private static final String INVALID_TITLE = "R@chel";
    private static final String INVALID_TOPIC = "+651234";
    private static final String INVALID_DIFFICULTY = " ";
    private static final String INVALID_STATUS = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TITLE = BENSON.getTitle().toString();
    private static final String VALID_TOPIC = BENSON.getTopic().toString();
    private static final String VALID_STATUS = BENSON.getStatus().toString();
    private static final String VALID_DIFFICULTY = BENSON.getDifficulty().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedQuestion question = new JsonAdaptedQuestion(BENSON);
        assertEquals(BENSON, question.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedQuestion question =
                new JsonAdaptedQuestion(INVALID_TITLE, VALID_TOPIC, VALID_STATUS, VALID_DIFFICULTY, VALID_TAGS);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedQuestion
            question = new JsonAdaptedQuestion(null, VALID_TOPIC, VALID_STATUS,
                VALID_DIFFICULTY, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedQuestion.MISSING_FIELD_MESSAGE_FORMAT,
                Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedQuestion question =
                new JsonAdaptedQuestion(VALID_TITLE, INVALID_TOPIC, VALID_STATUS, VALID_DIFFICULTY, VALID_TAGS);
        String expectedMessage = Topic.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedQuestion
            question = new JsonAdaptedQuestion(VALID_TITLE, null, VALID_STATUS, VALID_DIFFICULTY,
                VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedQuestion.MISSING_FIELD_MESSAGE_FORMAT,
                Topic.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedQuestion question =
                new JsonAdaptedQuestion(VALID_TITLE, VALID_TOPIC, INVALID_STATUS, VALID_DIFFICULTY, VALID_TAGS);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedQuestion
            question = new JsonAdaptedQuestion(VALID_TITLE, VALID_TOPIC, null, VALID_DIFFICULTY,
                VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedQuestion.MISSING_FIELD_MESSAGE_FORMAT,
                Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedQuestion question =
                new JsonAdaptedQuestion(VALID_TITLE, VALID_TOPIC, VALID_STATUS, INVALID_DIFFICULTY, VALID_TAGS);
        String expectedMessage = Difficulty.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedQuestion
            question = new JsonAdaptedQuestion(VALID_TITLE, VALID_TOPIC, VALID_STATUS, null,
                VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedQuestion.MISSING_FIELD_MESSAGE_FORMAT,
                Difficulty.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, question::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedQuestion question =
                new JsonAdaptedQuestion(VALID_TITLE, VALID_TOPIC, VALID_STATUS, VALID_DIFFICULTY, invalidTags);
        assertThrows(IllegalValueException.class, question::toModelType);
    }

}
