package seedu.ifridge.testutil;

import seedu.ifridge.logic.commands.templatelist.template.EditTemplateItemCommand;
import seedu.ifridge.logic.commands.templatelist.template.EditTemplateItemCommand.EditTemplateItemDescriptor;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.TemplateItem;

/**
 * A utility class to help with building EditTemplateItemDescriptor objects.
 */
public class EditTemplateItemDescriptorBuilder {

    private EditTemplateItemCommand.EditTemplateItemDescriptor descriptor;

    public EditTemplateItemDescriptorBuilder() {
        descriptor = new EditTemplateItemCommand.EditTemplateItemDescriptor();
    }

    public EditTemplateItemDescriptorBuilder(EditTemplateItemDescriptor descriptor) {
        this.descriptor = new EditTemplateItemDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTemplateItemDescriptor} with fields containing {@code TemplateItem}'s details
     */
    public EditTemplateItemDescriptorBuilder(TemplateItem templateItem) {
        descriptor = new EditTemplateItemDescriptor();
        descriptor.setName(templateItem.getName());
        descriptor.setAmount(templateItem.getAmount());
    }

    /**
     * Sets the {@code Amount} of the {@code EditTemplateItemDescriptor} that we are building.
     */
    public EditTemplateItemDescriptorBuilder withAmount(String name) {
        descriptor.setAmount(new Amount(name));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditTemplateItemDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    public EditTemplateItemDescriptor build() {
        return descriptor;
    }
}
