package com.dukeacademy.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.dukeacademy.model.QuestionBank;
import com.dukeacademy.model.ReadOnlyQuestionBank;
import com.dukeacademy.model.question.Address;
import com.dukeacademy.model.question.Email;
import com.dukeacademy.model.question.Phone;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.Title;
import com.dukeacademy.model.tag.Tag;

/**
 * Contains utility methods for populating {@code QuestionBank} with sample data.
 */
public class SampleDataUtil {
    public static Question[] getSamplePersons() {
        return new Question[] {
            new Question(new Title("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Question(new Title("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Question(new Title("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Question(new Title("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Question(new Title("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Question(new Title("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyQuestionBank getSampleAddressBook() {
        QuestionBank sampleAb = new QuestionBank();
        for (Question sampleQuestion : getSamplePersons()) {
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
