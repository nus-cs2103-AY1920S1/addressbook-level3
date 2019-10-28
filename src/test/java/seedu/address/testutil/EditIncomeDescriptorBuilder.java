package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditIncomeCommand.EditIncomeDescriptor;
import seedu.address.model.claim.Amount;
import seedu.address.model.claim.Description;
import seedu.address.model.commonvariables.Date;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.commonvariables.Phone;
import seedu.address.model.income.Income;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditIncomeDescriptor objects.
 */
public class EditIncomeDescriptorBuilder {

    private EditIncomeDescriptor descriptor;

    public EditIncomeDescriptorBuilder() {
        descriptor = new EditIncomeDescriptor();
    }

    public EditIncomeDescriptorBuilder(EditIncomeDescriptor descriptor) {
        this.descriptor = new EditIncomeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditIncomeDescriptor} with fields containing {@code income}'s details
     */
    public EditIncomeDescriptorBuilder(Income income) {
        descriptor = new EditIncomeDescriptor();
        descriptor.setDescription(income.getDescription());
        descriptor.setAmount(income.getAmount());
        descriptor.setDate(income.getDate());
        descriptor.setName(income.getName());
        descriptor.setPhone(income.getPhone());
        descriptor.setTags(income.getTags());
    }

    /**
     * Sets the {@code Description} of the {@code EditIncomeDescriptor} that we are building.
     */
    public EditIncomeDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditIncomeDescriptor} that we are building.
     */
    public EditIncomeDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditIncomeDescriptor} that we are building.
     */
    public EditIncomeDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditIncomeDescriptor} that we are building.
     */
    public EditIncomeDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditIncomeDescriptor} that we are building.
     */
    public EditIncomeDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditIncomeDescriptor}
     * that we are building.
     */
    public EditIncomeDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditIncomeDescriptor build() {
        return descriptor;
    }
}
