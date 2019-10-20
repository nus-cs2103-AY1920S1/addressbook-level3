package com.dukeacademy.model.question;

import com.dukeacademy.model.program.UserProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuestionBuilder {
    private String title;
    private Status status;
    private Difficulty difficulty;
    private Set<Topic> topics = new HashSet<>();
    private List<TestCase> testCases = new ArrayList<>();
    private UserProgram userProgram = new UserProgram("Main", "");

    public QuestionBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public QuestionBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    public QuestionBuilder withDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public QuestionBuilder withTopics(Topic... topics) {
        this.topics.addAll(Arrays.asList(topics));
        return this;
    }

    public QuestionBuilder withTestCases(TestCase... testCases) {
        this.testCases.addAll(Arrays.asList(testCases));
        return this;
    }

    public QuestionBuilder withUserProgram(UserProgram userProgram) {
        this.userProgram = userProgram;
        return this;
    }

    public Question build() {
        if (this.title == null) {
            throw new IllegalArgumentException("Title cannot be null.");
        }

        if (this.status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }

        if (this.difficulty == null) {
            throw new IllegalArgumentException("Difficulty cannot be null.");
        }

        return new Question(title, status, difficulty, topics, testCases, userProgram);
    }
}
