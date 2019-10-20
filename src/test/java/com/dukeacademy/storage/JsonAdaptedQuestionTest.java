package com.dukeacademy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dukeacademy.model.program.UserProgram;
import com.dukeacademy.model.question.Difficulty;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.Status;
import com.dukeacademy.model.question.TestCase;
import com.dukeacademy.model.question.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonAdaptedQuestionTest {
    private final String validTitle = "Test question";
    private final String validStatus = Status.ATTEMPTED.toString();
    private final String validDifficulty = Difficulty.EASY.toString();
    private final List<String> validTopics = new ArrayList<>();
    private final List<JsonAdaptedTestCase> validTestCases = new ArrayList<>();
    private final JsonAdaptedUserProgram validUserProgram = new JsonAdaptedUserProgram("Test", "");

    @BeforeEach
    void populateTopicsAndTestCases() {
        this.validTopics.add(Topic.TREE.toString());
        this.validTopics.add(Topic.DYNAMIC_PROGRAMMING.toString());

        this.validTestCases.add(new JsonAdaptedTestCase("1", "1"));
        this.validTestCases.add(new JsonAdaptedTestCase("2", "2"));
        this.validTestCases.add(new JsonAdaptedTestCase("3", "3"));
    }

    @Test
    public void toModel() throws Exception {
        JsonAdaptedQuestion jsonQuestion = new JsonAdaptedQuestion(validTitle, validStatus, validDifficulty, validTopics, validTestCases, validUserProgram);
        Question question = jsonQuestion.toModel();

        assertEquals(this.validTitle, question.getTitle());
        assertEquals(Status.valueOf(this.validStatus), question.getStatus());
        assertEquals(Difficulty.valueOf(this.validDifficulty), question.getDifficulty());

        Set<Topic> expectedTopics = new HashSet<>(this.validTopics.stream().map(Topic::valueOf).collect(Collectors.toList()));
        assertEquals(expectedTopics, question.getTopics());
        List<TestCase> expectedTestCases = this.validTestCases.stream().map(JsonAdaptedTestCase::toModel).collect(Collectors.toList());
        assertEquals(expectedTestCases, question.getTestCases());
        UserProgram expectedUserProgram = this.validUserProgram.toModel();
        assertEquals(expectedUserProgram, question.getUserProgram());
    }

    @Test
    public void toModelInvalid() {
        assertThrows(NullPointerException.class, () -> new JsonAdaptedQuestion(null, validStatus, validDifficulty, validTopics, validTestCases, validUserProgram));
        assertThrows(NullPointerException.class, () -> new JsonAdaptedQuestion(validTitle, null, validDifficulty, validTopics, validTestCases, validUserProgram));
        assertThrows(NullPointerException.class, () -> new JsonAdaptedQuestion(validTitle, validStatus, null, validTopics, validTestCases, validUserProgram));
        assertThrows(NullPointerException.class, () -> new JsonAdaptedQuestion(validTitle, validStatus, validDifficulty, null, validTestCases, validUserProgram));
        assertThrows(NullPointerException.class, () -> new JsonAdaptedQuestion(validTitle, validStatus, validDifficulty, validTopics, null, validUserProgram));
        assertThrows(NullPointerException.class, () -> new JsonAdaptedQuestion(validTitle, validStatus, validDifficulty, validTopics, validTestCases, null));
    }
}
