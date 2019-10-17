package seedu.address.model.quiz.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.ReadOnlyAddressBook;
import seedu.address.model.quiz.person.Question;
import seedu.address.model.quiz.tag.Tag;

/**
 * Contains utility methods for populating {@code TypeBook} with sample data.
 */
public class SampleDataUtil {
    public static Question[] getSampleQuestions() {
        return new Question[] {};
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressQuizBook sampleAb = new AddressQuizBook();
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
