package seedu.revision.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.revision.model.AddressBook;
import seedu.revision.model.ReadOnlyAddressBook;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.answerable.Saq;
import seedu.revision.model.category.Category;


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
            new Saq(new Question("David Li"), defaultCorrectAnswerSet,
                    new Difficulty("91031282"), getCategorySet("family")),
            new Saq(new Question("Irfan Ibrahim"), defaultCorrectAnswerSet,
                    new Difficulty("92492021"), getCategorySet("classmates")),
            new Saq(new Question("Roy Balakrishnan"), defaultCorrectAnswerSet,
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
