package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.answerable.Answer;
import seedu.address.model.answerable.Answerable;
import seedu.address.model.answerable.Category;
import seedu.address.model.answerable.Difficulty;
import seedu.address.model.answerable.Mcq;
import seedu.address.model.answerable.AnswerSet;
import seedu.address.model.answerable.Question;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Answerable[] getSampleAnswerables() {
        AnswerSet defaultAnswerSet = new AnswerSet(new HashSet<>(), new HashSet<>());
        //TODO: Implement actual answerable
        return new Answerable[] {
            new Mcq(new Question("Alex Yeoh"), defaultAnswerSet, new Difficulty("87438807"),
                    new Category("Blk 30 Geylang Street 29, #06-40"), getTagSet("friends")),
            new Mcq(new Question("Bernice Yu"), defaultAnswerSet, new Difficulty("99272758"),
                    new Category("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
            new Mcq(new Question("Charlotte Oliveiro"), defaultAnswerSet, new Difficulty("93210283"),
                    new Category("Blk 11 Ang Mo Kio Street 74, #11-04"), getTagSet("neighbours")),
            new Mcq(new Question("David Li"), defaultAnswerSet, new Difficulty("91031282"),
                    new Category("Blk 436 Serangoon Gardens Street 26, #16-43"), getTagSet("family")),
            new Mcq(new Question("Irfan Ibrahim"), defaultAnswerSet, new Difficulty("92492021"),
                    new Category("Blk 47 Tampines Street 20, #17-35"), getTagSet("classmates")),
            new Mcq(new Question("Roy Balakrishnan"), defaultAnswerSet, new Difficulty("92624417"),
                    new Category("Blk 45 Aljunied Street 85, #11-31"), getTagSet("colleagues"))
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
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
