package seedu.guilttrip.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.guilttrip.logic.commands.editcommands.EditExpenseCommand.EditExpenseDescriptor;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.tag.Tag;
import seedu.guilttrip.model.util.CategoryType;

/**
 * A utility class to help with building EditExpenseDescriptor objects.
 */
public class EditExpenseDescriptorBuilder {

    private EditExpenseDescriptor descriptor;

    public EditExpenseDescriptorBuilder() {
        descriptor = new EditExpenseDescriptor();
    }

    public EditExpenseDescriptorBuilder(EditExpenseDescriptor descriptor) {
        this.descriptor = new EditExpenseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditExpenseDescriptor} with fields containing {@code entry}'s details
     */
    public EditExpenseDescriptorBuilder(Expense expense) {
        descriptor = new EditExpenseDescriptor();
        descriptor.setDesc(expense.getDesc());
        descriptor.setAmount(expense.getAmount());
        descriptor.setCategory(expense.getCategory());
        descriptor.setTags(expense.getTags());
        descriptor.setTime(expense.getDate());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withDescription(String desc) {
        descriptor.setDesc(new Description(desc));
        return this;
    }

    /**
     * Sets the Amount of the EditEntryDescriptor that we are building.
     *
     * @param amt amount to be added
     * @return
     */
    public EditExpenseDescriptorBuilder withAmount(String amt) {
        descriptor.setAmount(new Amount(amt));
        return this;
    }

    /**
     * Sets the Category of the EditEntryDescriptor that we are building.
     *
     * @param catName category name to be added
     * @return
     */
    public EditExpenseDescriptorBuilder withCategory(String catName) {
        descriptor.setCategory(new Category(catName, CategoryType.EXPENSE));
        return this;
    }

    /**
     * Sets the Date of the EditEntryDescriptor that we are building.
     *
     * @param date new date to be changed.
     * @return
     */
    public EditExpenseDescriptorBuilder withDate(String date) {
        descriptor.setTime(new Date(date));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditExpenseDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditExpenseDescriptor build() {
        return descriptor;
    }
}

