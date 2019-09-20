package seedu.algobase.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Problem.Address;
import seedu.algobase.model.Problem.Email;
import seedu.algobase.model.Problem.Name;
import seedu.algobase.model.Problem.Problem;
import seedu.algobase.model.Problem.Phone;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AlgoBase} with sample data.
 */
public class SampleDataUtil {
    public static Problem[] getSampleProblems() {
        return new Problem[] {
            new Problem(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Problem(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Problem(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Problem(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Problem(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Problem(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAlgoBase getSampleAlgoBase() {
        AlgoBase sampleAb = new AlgoBase();
        for (Problem sampleProblem : getSampleProblems()) {
            sampleAb.addProblem(sampleProblem);
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
