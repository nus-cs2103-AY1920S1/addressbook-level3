package seedu.guilttrip.testutil;

import seedu.guilttrip.logic.commands.editcommands.EditCategoryCommand.EditCategoryDescriptor;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.util.CategoryType;

/**
 * A utility class to help with building EditCategoryDescriptor objects.
 */
public class EditCategoryDescriptorBuilder {

    private EditCategoryDescriptor descriptor;

    public EditCategoryDescriptorBuilder() {
        descriptor = new EditCategoryDescriptor();
    }

    public EditCategoryDescriptorBuilder(EditCategoryDescriptor descriptor) {
        this.descriptor = new EditCategoryDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code entry}'s details
     */
    public EditCategoryDescriptorBuilder(Category category) {
        descriptor = new EditCategoryDescriptor();
        descriptor.setCategoryType(category.getCategoryType());
        descriptor.setCategoryName(category.getCategoryName());
    }

    /**
     * Sets the {@code Category Type} of the {@code EditCategoryDescriptor} that we are building.
     */
    public EditCategoryDescriptorBuilder withCategoryType(String categoryType) {
        CategoryType catType = CategoryType.parse(categoryType);
        descriptor.setCategoryType(catType);
        return this;
    }

    /**
     * Sets the {@code Category Name} of the {@code EditCategoryDescriptor} that we are building.
     */
    public EditCategoryDescriptorBuilder withCategoryName(String categoryName) {
        descriptor.setCategoryName(categoryName);
        return this;
    }

    public EditCategoryDescriptor build() {
        return descriptor;
    }

}
