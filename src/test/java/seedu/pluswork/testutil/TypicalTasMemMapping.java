package seedu.pluswork.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.pluswork.model.mapping.TasMemMapping;

/**
 * A utility class containing a list of {@code TasMemMapping} objects to be used in tests.
 */
public class TypicalTasMemMapping {
    public static final TasMemMapping firstMemFirstTask = new MappingBuilder().withMember(1).withTask(1)
            .tasMemMappingBuild();
    public static final TasMemMapping firstMemSecondTask = new MappingBuilder().withMember(1).withTask(2)
            .tasMemMappingBuild();
    public static final TasMemMapping secondMemThirdTask = new MappingBuilder().withMember(2).withTask(3)
            .tasMemMappingBuild();
    public static final TasMemMapping thirdMemThirdTask = new MappingBuilder().withMember(3).withTask(3)
            .tasMemMappingBuild();
    public static final TasMemMapping fourthMemFirstTask = new MappingBuilder().withMember(4).withTask(1)
            .tasMemMappingBuild();
    public static final TasMemMapping fourthMemSecondTask = new MappingBuilder().withMember(4).withTask(2)
            .tasMemMappingBuild();
    public static final TasMemMapping fourthMemFifthTask = new MappingBuilder().withMember(4).withTask(5)
            .tasMemMappingBuild();
    public static final TasMemMapping fifthMemFirstTask = new MappingBuilder().withMember(5).withTask(1)
            .tasMemMappingBuild();

    public static List<TasMemMapping> getTypicalTasMemMapping() {
        return new ArrayList<>(Arrays.asList(firstMemFirstTask, firstMemSecondTask, secondMemThirdTask,
                thirdMemThirdTask, fourthMemFifthTask, fourthMemFirstTask, fourthMemSecondTask, fifthMemFirstTask));
    }
}

