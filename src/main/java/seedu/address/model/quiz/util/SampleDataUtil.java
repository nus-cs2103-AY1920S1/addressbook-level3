package seedu.address.model.quiz.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.ReadOnlyAddressBook;
import seedu.address.model.quiz.person.Answer;
import seedu.address.model.quiz.person.Category;
import seedu.address.model.quiz.person.Name;
import seedu.address.model.quiz.person.Question;
import seedu.address.model.quiz.person.Type;
import seedu.address.model.quiz.tag.Tag;

/**
 * Contains utility methods for populating {@code TypeBook} with sample data.
 */
public class SampleDataUtil {
    public static Question[] getSampleQuestions() {
        return new Question[] {
            new Question(new Name("Alex Yeoh"), new Answer("87438807"), new Category("alexyeoh@example.com"),
                new Type("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Question(new Name("Bernice Yu"), new Answer("99272758"), new Category("berniceyu@example.com"),
                new Type("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Question(new Name("Charlotte Oliveiro"), new Answer("93210283"), new Category("charlotte@example.com"),
                new Type("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Question(new Name("David Li"), new Answer("91031282"), new Category("lidavid@example.com"),
                new Type("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Question(new Name("Irfan Ibrahim"), new Answer("92492021"), new Category("irfan@example.com"),
                new Type("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Question(new Name("Roy Balakrishnan"), new Answer("92624417"), new Category("royb@example.com"),
                new Type("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
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
