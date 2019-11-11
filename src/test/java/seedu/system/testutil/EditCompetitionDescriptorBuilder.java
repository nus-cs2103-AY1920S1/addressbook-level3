package seedu.system.testutil;

import seedu.system.logic.commands.outofsession.EditCompetitionCommand.EditCompetitionDescriptor;
import seedu.system.model.competition.Competition;
import seedu.system.model.person.CustomDate;
import seedu.system.model.person.Name;

/**
 * A utility class to help with building EditCompetitionDescriptor objects.
 */
public class EditCompetitionDescriptorBuilder {
    private EditCompetitionDescriptor descriptor;

    public EditCompetitionDescriptorBuilder() {
        descriptor = new EditCompetitionDescriptor();
    }

    public EditCompetitionDescriptorBuilder(EditCompetitionDescriptor descriptor) {
        this.descriptor = new EditCompetitionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCompetitionDescriptor} with fields containing {@code competition}'s details
     */
    public EditCompetitionDescriptorBuilder(Competition competition) {
        descriptor = new EditCompetitionDescriptor();
        descriptor.setName(competition.getName());
        descriptor.setStartDate(competition.getStartDate());
        descriptor.setEndDate(competition.getEndDate());
    }

    /**
     * Sets the {@code Name} of the {@code EditCompetitionDescriptor} that we are building.
     */
    public EditCompetitionDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code startDate} of the {@code EditCompetitionDescriptor} that we are building.
     */
    public EditCompetitionDescriptorBuilder withStartDate(String startDate) {
        descriptor.setStartDate(new CustomDate(startDate));
        return this;
    }
    /**
     * Sets the {@code endDate} of the {@code EditCompetitionDescriptor} that we are building.
     */
    public EditCompetitionDescriptorBuilder withEndDate(String endDate) {
        descriptor.setEndDate(new CustomDate(endDate));
        return this;
    }


    public EditCompetitionDescriptor build() {
        return descriptor;
    }

}
