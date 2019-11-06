package dukecooks.testutil.diary;

import dukecooks.logic.commands.diary.EditPageCommand.EditPageDescriptor;
import dukecooks.model.Image;
import dukecooks.model.diary.components.Page;
import dukecooks.model.diary.components.PageDescription;
import dukecooks.model.diary.components.PageType;
import dukecooks.model.diary.components.Title;

/**
 * A utility class to help with building EditPageDescriptor objects.
 */
public class EditPageDescriptorBuilder {

    private EditPageDescriptor descriptor;

    public EditPageDescriptorBuilder() {
        descriptor = new EditPageDescriptor();
    }

    public EditPageDescriptorBuilder(EditPageDescriptor descriptor) {
        this.descriptor = new EditPageDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPageDescriptor} with fields containing {@code page}'s details
     */
    public EditPageDescriptorBuilder(Page page) {
        descriptor = new EditPageDescriptor();
        descriptor.setTitle(page.getTitle());
        descriptor.setPageType(page.getPageType());
        descriptor.setPageDescription(page.getDescription());
        descriptor.setImage(page.getImage());
    }

    /**
     * Sets the {@code Title} of the {@code EditPageDescriptor} that we are building.
     */
    public EditPageDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code PageType} of the {@code EditPageDescriptor} that we are building.
     */
    public EditPageDescriptorBuilder withPageType(String type) {
        descriptor.setPageType(new PageType(type));
        return this;
    }

    /**
     * Sets the {@code PageDescription} of the {@code EditPageDescriptor} that we are building.
     */
    public EditPageDescriptorBuilder withPageDescription(String description) {
        descriptor.setPageDescription(new PageDescription(description));
        return this;
    }

    /**
     * Sets the {@code Image} of the {@code EditPageDescriptor} that we are building.
     */
    public EditPageDescriptorBuilder withImage(String image) {
        descriptor.setImage(new Image(image));
        return this;
    }

    public EditPageDescriptor build() {
        return descriptor;
    }
}
