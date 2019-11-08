package seedu.guilttrip.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.guilttrip.logic.commands.editcommands.EditIncomeCommand.EditIncomeDescriptor;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.tag.Tag;

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
        descriptor.setDesc(income.getDesc());
        descriptor.setAmount(income.getAmount());
        descriptor.setCategory(income.getCategory());
        descriptor.setTags(income.getTags());
        descriptor.setDate(income.getDate());
    }

    /**
     * Sets the {@code desc} of the {@code EditIncomeDescriptorBuilder} that we are building.
     *
     * @param desc amount to be added
     * @return the descriptor with new description
     */
    public EditIncomeDescriptorBuilder withDescription(String desc) {
        descriptor.setDesc(new Description(desc));
        return this;
    }

    /**
     * Sets the {@code amt} of the {@code EditIncomeDescriptorBuilder} that we are building.
     *
     * @param amt amount to be added
     * @return the descriptor with new amount
     */
    public EditIncomeDescriptorBuilder withAmount(String amt) {
        descriptor.setAmount(new Amount(amt));
        return this;
    }

    /**
     * Sets the {@code catName} of the {@code EditIncomeDescriptorBuilder} that we are building.
     *
     * @param catName category name to be added
     * @return the descriptor with new category name
     */
    public EditIncomeDescriptorBuilder withCategory(String catName) {
        descriptor.setCategory(new Category(catName, "Income"));
        return this;
    }

    /**
     * Sets the {@code date} of the {@code EditIncomeDescriptorBuilder} that we are building.
     *
     * @param date new date to be changed.
     * @return the descriptor with new date
     */
    public EditIncomeDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditIncomeDescriptorBuilder}
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

