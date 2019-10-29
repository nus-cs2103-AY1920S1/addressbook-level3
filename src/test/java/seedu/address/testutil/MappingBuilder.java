package seedu.address.testutil;

import seedu.address.model.mapping.InvMemMapping;
import seedu.address.model.mapping.InvTasMapping;
import seedu.address.model.mapping.TasMemMapping;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;

public class MappingBuilder {
    public static final String DEFAULT_NAME = "Sample Mapping";

    private int memberIndex;
    private int taskIndex;

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


    public TasMemMapping build() {
        return new TasMemMapping(taskIndex, memberIndex);
    }
}
