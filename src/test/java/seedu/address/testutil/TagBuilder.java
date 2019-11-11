package seedu.address.testutil;

import seedu.address.model.tag.DefaultTag;
import seedu.address.model.tag.DefaultTagType;
import seedu.address.model.tag.PriorityTag;
import seedu.address.model.tag.PriorityTagType;
import seedu.address.model.tag.UserTag;

/**
 * A utility class to help with building Tag objects.
 */
public class TagBuilder {

    /**
     * Builds a UserTag with the name "testUserTag".
     */
    public UserTag buildTestUserTag() {
        return new UserTag("testUserTag");
    }

    /**
     * Builds a UserTag with the specified name.
     */
    public UserTag buildUserTag(String name) {
        return new UserTag(name);
    }

    /**
     * Builds a DefaultTag of type CORE.
     */
    public DefaultTag buildDefaultCoreTag() {
        return new DefaultTag(DefaultTagType.CORE);
    }

    /**
     * Builds a DefaultTag of the given DefaultTagType
     */
    public DefaultTag buildDefaultTag(DefaultTagType defaultTagType) {
        return new DefaultTag(defaultTagType);
    }

    /**
     * Builds a PriorityTag of priority high
     */
    public PriorityTag buildPriorityHighTag() {
        return new PriorityTag(PriorityTagType.HIGH);
    }

    /**
     * Builds a PriorityTag of the given PriorityTagType
     */
    public PriorityTag buildPriorityTag(PriorityTagType priorityTagType) {
        return new PriorityTag(priorityTagType);
    }

}
