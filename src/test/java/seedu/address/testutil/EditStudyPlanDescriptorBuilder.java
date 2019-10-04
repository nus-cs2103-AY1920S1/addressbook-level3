package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditStudyPlanDescriptor;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.UserTag;

/**
 * A utility class to help with building EditStudyPlanDescriptor objects.
 */
public class EditStudyPlanDescriptorBuilder {

    private EditStudyPlanDescriptor descriptor;

    public EditStudyPlanDescriptorBuilder() {
        descriptor = new EditStudyPlanDescriptor();
    }

    public EditStudyPlanDescriptorBuilder(EditStudyPlanDescriptor descriptor) {
        this.descriptor = new EditStudyPlanDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditStudyPlanDescriptor} with fields containing {@code studyPlan}'s details
     */
    public EditStudyPlanDescriptorBuilder(StudyPlan studyPlan) {
        descriptor = new EditStudyPlanDescriptor();
        descriptor.setName(studyPlan.getName());
        descriptor.setPhone(studyPlan.getPhone());
        descriptor.setEmail(studyPlan.getEmail());
        descriptor.setAddress(studyPlan.getAddress());
        descriptor.setTags(studyPlan.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditStudyPlanDescriptor} that we are building.
     */
    public EditStudyPlanDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditStudyPlanDescriptor} that we are building.
     */
    public EditStudyPlanDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditStudyPlanDescriptor} that we are building.
     */
    public EditStudyPlanDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditStudyPlanDescriptor} that we are building.
     */
    public EditStudyPlanDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditStudyPlanDescriptor}
     * that we are building.
     */
    public EditStudyPlanDescriptorBuilder withTags(String... tags) {
        Set<UserTag> tagSet = Stream.of(tags).map(UserTag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditStudyPlanDescriptor build() {
        return descriptor;
    }
}
