package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditTitleCommand.EditTitleStudyPlanDescriptor;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.UserTag;

/**
 * A utility class to help with building EditTitleStudyPlanDescriptor objects.
 */
public class EditTitleStudyPlanDescriptorBuilder {

    private EditTitleStudyPlanDescriptor descriptor;

    public EditTitleStudyPlanDescriptorBuilder() {
        descriptor = new EditTitleStudyPlanDescriptor();
    }

    public EditTitleStudyPlanDescriptorBuilder(EditTitleStudyPlanDescriptor descriptor) {
        this.descriptor = new EditTitleStudyPlanDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTitleStudyPlanDescriptor} with fields containing {@code studyPlan}'s details
     */
    public EditTitleStudyPlanDescriptorBuilder(StudyPlan studyPlan) {
        /*
        descriptor = new EditTitleStudyPlanDescriptor();
        descriptor.setName(studyPlan.getName());
        descriptor.setPhone(studyPlan.getPhone());
        descriptor.setEmail(studyPlan.getEmail());
        descriptor.setAddress(studyPlan.getAddress());
        descriptor.setTags(studyPlan.getTags());
         */
    }

    /**
     * Sets the {@code Name} of the {@code EditTitleStudyPlanDescriptor} that we are building.
     */
    public EditTitleStudyPlanDescriptorBuilder withName(String name) {
        //descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTitleStudyPlanDescriptor}
     * that we are building.
     */
    public EditTitleStudyPlanDescriptorBuilder withTags(String... tags) {
        Set<UserTag> tagSet = Stream.of(tags).map(UserTag::new).collect(Collectors.toSet());
        //descriptor.setTags(tagSet);
        return this;
    }

    public EditTitleStudyPlanDescriptor build() {
        return descriptor;
    }
}
