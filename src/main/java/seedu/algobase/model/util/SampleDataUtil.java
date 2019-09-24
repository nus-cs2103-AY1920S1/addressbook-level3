package seedu.algobase.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.problem.*;
import seedu.algobase.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AlgoBase} with sample data.
 */
public class SampleDataUtil {
    public static Problem[] getSampleProblems() {
        // TODO: add fields difficulty, remark and source
        return new Problem[] {
            new Problem(new Name("Alex Yeoh"), new Author("87438807"), new WebLink("alexyeoh@example.com"),
                new Description("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends"),
                    Difficulty.DEFAULT_DIFFICULTY,
                    Remark.DEFAULT_REMARK,
                    Source.DEFAULT_SOURCE),
            new Problem(new Name("Bernice Yu"), new Author("99272758"), new WebLink("berniceyu@example.com"),
                new Description("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"),
                    Difficulty.DEFAULT_DIFFICULTY,
                    Remark.DEFAULT_REMARK,
                    Source.DEFAULT_SOURCE),
            new Problem(new Name("Charlotte Oliveiro"), new Author("93210283"), new WebLink("charlotte@example.com"),
                new Description("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours"),
                    Difficulty.DEFAULT_DIFFICULTY,
                    Remark.DEFAULT_REMARK,
                    Source.DEFAULT_SOURCE),
            new Problem(new Name("David Li"), new Author("91031282"), new WebLink("lidavid@example.com"),
                new Description("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family"),
                    Difficulty.DEFAULT_DIFFICULTY,
                    Remark.DEFAULT_REMARK,
                    Source.DEFAULT_SOURCE),
            new Problem(new Name("Irfan Ibrahim"), new Author("92492021"), new WebLink("irfan@example.com"),
                new Description("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates"),
                    Difficulty.DEFAULT_DIFFICULTY,
                    Remark.DEFAULT_REMARK,
                    Source.DEFAULT_SOURCE),
            new Problem(new Name("Roy Balakrishnan"), new Author("92624417"), new WebLink("royb@example.com"),
                new Description("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"),
                    Difficulty.DEFAULT_DIFFICULTY,
                    Remark.DEFAULT_REMARK,
                    Source.DEFAULT_SOURCE)
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
