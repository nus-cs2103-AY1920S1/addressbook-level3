package seedu.address.testutil;

import seedu.address.model.ModulesInfo;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;
import seedu.address.model.tag.DefaultTag;
import seedu.address.model.tag.DefaultTagType;
import seedu.address.model.tag.UserTag;

/**
 * A utility class to help with building Tag objects.
 */
public class TagBuilder {

    /**
     * Builds a UserTag with the name "testTag".
     */
    public static UserTag buildUserTag() {
        return new UserTag("testTag");
    }

    /**
     * Builds a UserTag with the specified name.
     */
    public static UserTag buildUserTag(String name) {
        return new UserTag(name);
    }

    /**
     * Builds a DefaultTag of type CORE.
     */
    public static DefaultTag buildDefaultTag() {
        return new DefaultTag(DefaultTagType.CORE);
    }

}
