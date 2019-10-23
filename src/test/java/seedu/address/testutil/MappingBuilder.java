package seedu.address.testutil;

import seedu.address.model.mapping.Mapping;
import seedu.address.model.member.Member;
import seedu.address.model.task.Task;

public class MappingBuilder {
    public static final String DEFAULT_NAME = "Sample Mapping";

    private Member member;
    private Task task;

    public MappingBuilder() {
        task = new TaskBuilder().build();
        member = new MemberBuilder().build();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public MappingBuilder(Mapping mappingToCopy) {
        member = mappingToCopy.getMember();
        task = mappingToCopy.getTask();
    }

    /**
     * Sets the {@code Name} of the {@code Inventory} that we are building.
     */
    public MappingBuilder withMember(Member member) {
        this.member = member;
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Inventory} that we are building.
     */
    public MappingBuilder withTask(Task task) {
        this.task = task;
        return this;
    }


    public Mapping build() {
        return new Mapping(member, task);
    }
}
