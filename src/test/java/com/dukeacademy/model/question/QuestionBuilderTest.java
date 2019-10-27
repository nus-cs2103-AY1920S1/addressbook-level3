package com.dukeacademy.model.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dukeacademy.model.question.entities.Difficulty;
import com.dukeacademy.model.question.entities.Status;
import com.dukeacademy.model.question.entities.TestCase;
import com.dukeacademy.model.question.entities.Topic;

class QuestionBuilderTest {
    private final String validTitle = "Test question";
    private final Status validStatus = Status.ATTEMPTED;
    private final Difficulty validDifficulty = Difficulty.EASY;
    private final Set<Topic> validTopics = new HashSet<>();
    private final List<TestCase> validTestCases = new ArrayList<>();
    private final UserProgram validUserProgram = new UserProgram("Test", "");
    private final String validDescription = "description";
    private final Boolean validIsBookmarked = true;

    @BeforeEach
    void populateTopicsAndTestCases() {
        this.validTopics.add(Topic.TREE);
        this.validTopics.add(Topic.DYNAMIC_PROGRAMMING);

        this.validTestCases.add(new TestCase("1", "1"));
        this.validTestCases.add(new TestCase("2", "2"));
        this.validTestCases.add(new TestCase("3", "3"));
    }

    @Test
    void build() {
        assertThrows(IllegalArgumentException.class, () -> new QuestionBuilder().build());

        assertThrows(IllegalArgumentException.class, () -> new QuestionBuilder()
                .withTitle("")
                .withStatus(validStatus)
                .withDifficulty(validDifficulty)
                .withTopics(validTopics.toArray(Topic[]::new))
                .withTestCases(validTestCases.toArray(TestCase[]::new))
                .withUserProgram(validUserProgram)
                .withDescription(validDescription)
                .withIsBookmarked(validIsBookmarked)
                .build());

        assertThrows(IllegalArgumentException.class, () -> new QuestionBuilder()
                .withTitle(validTitle)
                .withDifficulty(validDifficulty)
                .withTopics(validTopics.toArray(Topic[]::new))
                .withTestCases(validTestCases.toArray(TestCase[]::new))
                .withUserProgram(validUserProgram)
                .withDescription(validDescription)
                .withIsBookmarked(validIsBookmarked)
                .build());

        assertThrows(IllegalArgumentException.class, () -> new QuestionBuilder()
                .withStatus(validStatus)
                .withDifficulty(validDifficulty)
                .withTopics(validTopics.toArray(Topic[]::new))
                .withTestCases(validTestCases.toArray(TestCase[]::new))
                .withUserProgram(validUserProgram)
                .withDescription(validDescription)
                .withIsBookmarked(validIsBookmarked)
                .build());

        assertThrows(IllegalArgumentException.class, () -> new QuestionBuilder()
                .withTitle(validTitle)
                .withStatus(validStatus)
                .withTopics(validTopics.toArray(Topic[]::new))
                .withTestCases(validTestCases.toArray(TestCase[]::new))
                .withUserProgram(validUserProgram)
                .withDescription(validDescription)
                .withIsBookmarked(validIsBookmarked)
                .build());

        Question question = new QuestionBuilder()
                .withTitle(validTitle)
                .withStatus(validStatus)
                .withDifficulty(validDifficulty)
                .withTopics(validTopics.toArray(Topic[]::new))
                .withTestCases(validTestCases.toArray(TestCase[]::new))
                .withUserProgram(validUserProgram)
                .withDescription(validDescription)
                .withIsBookmarked(validIsBookmarked)
                .build();

        assertEquals(validTitle, question.getTitle());
        assertEquals(validStatus, question.getStatus());
        assertEquals(validDifficulty, question.getDifficulty());
        assertEquals(validTopics, question.getTopics());
        assertEquals(validTestCases, question.getTestCases());
        assertEquals(validUserProgram, question.getUserProgram());
        assertEquals(validDescription, question.getDescription());
        assertEquals(validIsBookmarked, question.isBookmarked());
    }
}
