package seedu.ifridge.testutil;

import seedu.ifridge.logic.commands.templatelist.EditTemplateListCommand.EditTemplateListDescriptor;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * A utility class to help with building EditTemplateListDescriptor objects.
 */
public class EditTemplateListDescriptorBuilder {

    private EditTemplateListDescriptor descriptor;

    public EditTemplateListDescriptorBuilder() {
        descriptor = new EditTemplateListDescriptor();
    }

    public EditTemplateListDescriptorBuilder(EditTemplateListDescriptor descriptor) {
        this.descriptor = new EditTemplateListDescriptor(descriptor);
    }

    public EditTemplateListDescriptorBuilder(UniqueTemplateItems template) {
        descriptor = new EditTemplateListDescriptor();
        descriptor.setName(template.getName());
        descriptor.setTemplateItems(template);
    }

    /**
     * Sets the {@code Name} of the {@code EditTemplateListDescriptor} that we are building.
     */
    public EditTemplateListDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code TemplateItem} of the {@code EditTemplateListDescriptor} that we are building.
     */
    public EditTemplateListDescriptorBuilder withTemplateItem(TemplateItem templateItem) {
        descriptor.setTemplateItem(templateItem);
        return this;
    }

    /**
     * Sets the {@code UniqueTemplateItems} of the {@code EditTemplateListDescriptor} that we are building.
     */
    public EditTemplateListDescriptorBuilder withTemplateItems(UniqueTemplateItems template) {
        descriptor.setTemplateItems(template);
        return this;
    }

    public EditTemplateListDescriptor build() {
        return this.descriptor;
    }
}
