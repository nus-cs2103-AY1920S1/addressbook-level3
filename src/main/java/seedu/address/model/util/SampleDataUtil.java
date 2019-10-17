package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.answerable.Answer;
import seedu.address.model.answerable.Answerable;
import seedu.address.model.answerable.Difficulty;
import seedu.address.model.answerable.Mcq;
import seedu.address.model.answerable.Question;
import seedu.address.model.category.Category;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Answerable[] getSampleAnswerables() {
        Answer defaultCorrectAnswer = new Answer("CORRECT");
        Set<Answer> defaultCorrectAnswerSet = new HashSet<>(Arrays.asList(defaultCorrectAnswer));
        Answer defaultWrongAnswer = new Answer("WRONG");
        Set<Answer> defaultWrongAnswerSet = new HashSet<>(Arrays.asList(defaultWrongAnswer));

        //TODO: Implement actual answerable
        return new Answerable[] {
            new Mcq(new Question("What is being shown?"), defaultCorrectAnswerSet, defaultWrongAnswerSet,
                    new Difficulty("3"), getCategorySet("Math")),
            new Mcq(new Question("Bernice Yu"), defaultCorrectAnswerSet, defaultWrongAnswerSet,
                    new Difficulty("99272758"), getCategorySet("colleagues", "friends")),
            new Mcq(new Question("Charlotte Oliveiro"), defaultCorrectAnswerSet, defaultWrongAnswerSet,
                    new Difficulty("93210283"), getCategorySet("neighbours")),
            new Mcq(new Question("David Li"), defaultCorrectAnswerSet, defaultWrongAnswerSet,
                    new Difficulty("91031282"), getCategorySet("family")),
            new Mcq(new Question("Irfan Ibrahim"), defaultCorrectAnswerSet, defaultWrongAnswerSet,
                    new Difficulty("92492021"), getCategorySet("classmates")),
            new Mcq(new Question("Roy Balakrishnan"), defaultCorrectAnswerSet, defaultWrongAnswerSet,
                    new Difficulty("92624417"), getCategorySet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Answerable sampleAnswerable : getSampleAnswerables()) {
            sampleAb.addAnswerable(sampleAnswerable);
        }
        return sampleAb;
    }

    /**
     * Returns a category set containing the list of strings given.
     */
    public static Set<Category> getCategorySet(String... strings) {
        return Arrays.stream(strings)
                .map(Category::new)
                .collect(Collectors.toSet());
    }

}
