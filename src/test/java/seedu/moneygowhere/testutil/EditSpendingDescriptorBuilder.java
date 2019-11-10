package seedu.moneygowhere.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.moneygowhere.logic.commands.EditCommand.EditSpendingDescriptor;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Name;
import seedu.moneygowhere.model.spending.Remark;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditSpendingDescriptorBuilder {

    private EditSpendingDescriptor descriptor;

    public EditSpendingDescriptorBuilder() {
        descriptor = new EditSpendingDescriptor();
    }

    public EditSpendingDescriptorBuilder(EditSpendingDescriptor descriptor) {
        this.descriptor = new EditSpendingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code Spending}'s details
     */
    public EditSpendingDescriptorBuilder(Spending spending) {
        descriptor = new EditSpendingDescriptor();
        descriptor.setName(spending.getName());
        descriptor.setDate(spending.getDate());
        descriptor.setRemark(spending.getRemark());
        descriptor.setCost(spending.getCost());
        descriptor.setTags(spending.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditSpendingDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditSpendingDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditSpendingDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code Cost} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditSpendingDescriptorBuilder withCost(String cost) {
        descriptor.setCost(new Cost(cost));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditSpendingDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditSpendingDescriptor build() {
        return descriptor;
    }
}
