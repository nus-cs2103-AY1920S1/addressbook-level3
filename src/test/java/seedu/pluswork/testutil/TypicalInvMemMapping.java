package seedu.pluswork.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.pluswork.model.mapping.InvMemMapping;

/**
 * A utility class containing a list of {@code InvMemMapping} objects to be used in tests.
 */
public class TypicalInvMemMapping {
    private static final InvMemMapping firstMemFirstTask = new MappingBuilder().withMember(1).withInv(1)
            .invMemMappingBuild();
    private static final InvMemMapping firstMemSecondTask = new MappingBuilder().withMember(1).withInv(2)
            .invMemMappingBuild();
    private static final InvMemMapping secondMemThirdTask = new MappingBuilder().withMember(2).withInv(3)
            .invMemMappingBuild();
    private static final InvMemMapping thirdMemThirdTask = new MappingBuilder().withMember(3).withInv(3)
            .invMemMappingBuild();
    private static final InvMemMapping fourthMemFirstTask = new MappingBuilder().withMember(4).withInv(1)
            .invMemMappingBuild();
    private static final InvMemMapping fourthMemSecondTask = new MappingBuilder().withMember(4).withInv(2)
            .invMemMappingBuild();
    private static final InvMemMapping fourthMemFifthTask = new MappingBuilder().withMember(4).withInv(5)
            .invMemMappingBuild();
    private static final InvMemMapping sixthMemFirstTask = new MappingBuilder().withMember(5).withInv(1)
            .invMemMappingBuild();

    public static List<InvMemMapping> getTypicalInvMemMapping() {
        return new ArrayList<>(Arrays.asList(firstMemFirstTask, firstMemSecondTask, secondMemThirdTask,
                thirdMemThirdTask, fourthMemFifthTask, fourthMemFirstTask, fourthMemSecondTask, sixthMemFirstTask));
    }
}
