package seedu.moolah.testutil;

import static seedu.moolah.logic.commands.budget.EditBudgetCommand.EditBudgetDescriptor;

import seedu.moolah.logic.commands.budget.EditBudgetCommand;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;

/**
 * A utility class to help with building EditBudgetDescriptor objects.
 */
public class EditBudgetDescriptorBuilder {
    private EditBudgetDescriptor descriptor;

    public EditBudgetDescriptorBuilder() {
        descriptor = new EditBudgetDescriptor();
    }

    public EditBudgetDescriptorBuilder(EditBudgetDescriptor descriptor) {
        this.descriptor = new EditBudgetDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBudgetDescriptor} with fields containing {@code budget}'s details
     */
    public EditBudgetDescriptorBuilder(Budget budget) {
        descriptor = new EditBudgetCommand.EditBudgetDescriptor();
        descriptor.setDescription(budget.getDescription());
        descriptor.setAmount(budget.getAmount());
        descriptor.setStartDate(budget.getWindowStartDate());
        descriptor.setPeriod(budget.getBudgetPeriod());
    }

    /**
     * Sets the {@code Description} of the {@code EditBudgetDescriptor} that we are building.
     */
    public EditBudgetDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditBudgetDescriptor} that we are building.
     */
    public EditBudgetDescriptorBuilder withAmount(String price) {
        descriptor.setAmount(new Price(price));
        return this;
    }

    /**
     * Sets the {@code Start Date} of the {@code EditBudgetDescriptor} that we are building.
     */
    public EditBudgetDescriptorBuilder withStartDate(String startDate) {
        descriptor.setStartDate(Timestamp.createTimestampIfValid(startDate).get());
        return this;
    }

    /**
     * Sets the {@code Period} of the {@code EditBudgetDescriptor} that we are building.
     */
    public EditBudgetDescriptorBuilder withPeriod(String period) {
        try {
            descriptor.setPeriod(ParserUtil.parsePeriod(period));
            return this;
        } catch (ParseException e) {
            return this;
        }
    }

    public EditBudgetDescriptor build() {
        return descriptor;
    }
}
