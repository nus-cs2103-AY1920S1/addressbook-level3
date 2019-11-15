package seedu.pluswork.testutil;

import seedu.pluswork.model.mapping.InvMemMapping;
import seedu.pluswork.model.mapping.TasMemMapping;

/**
 * A utility class to help with building {@code Mapping} objects.
 */
public class MappingBuilder {
    public static final String DEFAULT_NAME = "Sample Mapping";

    private int memberIndex;
    private int taskIndex;
    private int invIndex;

    public MappingBuilder() {
        //Dummy constructor until Mapping Testing is decided
        taskIndex = 1;
        memberIndex = 1;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public MappingBuilder(TasMemMapping mappingToCopy) {
        memberIndex = mappingToCopy.getMemberIndex();
        taskIndex = mappingToCopy.getTaskIndex();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public MappingBuilder(InvMemMapping mappingToCopy) {
        memberIndex = mappingToCopy.getMemberIndex();
        invIndex = mappingToCopy.getInventoryIndex();
    }

    /**
     * Sets the {@code Name} of the {@code Inventory} that we are building.
     */
    public MappingBuilder withMember(int memberIndex) {
        this.memberIndex = memberIndex;
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Inventory} that we are building.
     */
    public MappingBuilder withTask(int taskIndex) {
        this.taskIndex = taskIndex;
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Inventory} that we are building.
     */
    public MappingBuilder withInv(int invIndex) {
        this.invIndex = invIndex;
        return this;
    }


    public TasMemMapping tasMemMappingBuild() {
        return new TasMemMapping(taskIndex, memberIndex);
    }

    public InvMemMapping invMemMappingBuild() {
        return new InvMemMapping(memberIndex, invIndex);
    }
}
