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
            new Question(new Title("Alex Yeoh"), new Topic("87438807"), new Status("alexyeoh@example.com"),
                new Difficulty("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Question(new Title("Bernice Yu"), new Topic("99272758"), new Status("berniceyu@example.com"),
                new Difficulty("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Question(new Title("Charlotte Oliveiro"), new Topic("93210283"), new Status("charlotte@example.com"),
                new Difficulty("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Question(new Title("David Li"), new Topic("91031282"), new Status("lidavid@example.com"),
                new Difficulty("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Question(new Title("Irfan Ibrahim"), new Topic("92492021"), new Status("irfan@example.com"),
                new Difficulty("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Question(new Title("Roy Balakrishnan"), new Topic("92624417"), new Status("royb@example.com"),
                new Difficulty("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
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
