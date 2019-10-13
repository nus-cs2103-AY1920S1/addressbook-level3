package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.ModulesInfo;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;
import seedu.address.model.tag.UserTag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building StudyPlan objects.
 */
public class StudyPlanBuilder {

    // TODO: implement this entire class

    private Set<UserTag> tags;

    public StudyPlanBuilder() {
        tags = new HashSet<>();
    }

    /**
     * Initializes the StudyPlanBuilder with the data of {@code studyPlanToCopy}.
     */
    public StudyPlanBuilder(StudyPlan studyPlanToCopy) {
        //tags = new HashSet<>(studyPlanToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code StudyPlan} that we are building.
     */
    /*
    public StudyPlanBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }
     */

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }


    /**
     * Builds a new studyplan.
     */
    public StudyPlan build() {
        //return new StudyPlan(name, phone, email, address, tags);
        return new StudyPlan(new Title("this is just a temporary holder"), new ModulesInfo());
    }

}
