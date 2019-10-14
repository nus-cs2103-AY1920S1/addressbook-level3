package com.dukeacademy.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.dukeacademy.model.QuestionBank;
import com.dukeacademy.model.ReadOnlyQuestionBank;
import com.dukeacademy.model.question.Difficulty;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.Status;
import com.dukeacademy.model.question.Title;
import com.dukeacademy.model.question.Topic;
import com.dukeacademy.model.tag.Tag;

/**
 * Contains utility methods for populating {@code QuestionBank} with sample data.
 */
public class SampleDataUtil {
    public static Question[] getSampleQuestions() {
        return new Question[] {
            new Question(new Title("Two Sum"), new Topic("Arrays"),
                new Status("New"),
                new Difficulty("Easy"),
                getTagSet("java")),
            new Question(new Title("Search Insert Position"), new Topic("Binary Search"), new Status("New"),
                new Difficulty("Easy"),
                getTagSet("java", "Google2019")),
            new Question(new Title("Linked List Cycle"), new Topic("Linked List"), new Status("Passed"),
                new Difficulty("Easy"),
                getTagSet("java", "CS2040")),
            new Question(new Title("Happy Number"), new Topic("Hash Table"), new Status("Passed"),
                new Difficulty("Easy"),
                getTagSet("java")),
            new Question(new Title("Triangle"), new Topic("Dynamic Programming"), new Status("Attempted"),
                new Difficulty("Medium"),
                getTagSet("java", "100 days of Java")),
            new Question(new Title("Freedom Trial"), new Topic("Divide and Conquer"), new Status("New"),
                new Difficulty("Hard"),
                getTagSet("C++", "Java"))
        };
    }

    public static ReadOnlyQuestionBank getSampleQuestionBank() {
        QuestionBank sampleAb = new QuestionBank();
        for (Question sampleQuestion : getSampleQuestions()) {
            sampleAb.addQuestion(sampleQuestion);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
