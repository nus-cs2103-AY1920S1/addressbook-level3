package calofit.testutil;

import calofit.logic.commands.EditCommand;
import calofit.model.meal.Meal;
import calofit.model.meal.Name;
import calofit.model.tag.Tag;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A utility class to help with building EditMealDescriptor objects.
 */
public class EditMealDescriptorBuilder {

    private EditCommand.EditMealDescriptor descriptor;

    public EditMealDescriptorBuilder() {
        descriptor = new EditCommand.EditMealDescriptor();
    }

    public EditMealDescriptorBuilder(EditCommand.EditMealDescriptor descriptor) {
        this.descriptor = new EditCommand.EditMealDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMealDescriptor} with fields containing {@code meal}'s details
     */
    public EditMealDescriptorBuilder(Meal meal) {
        descriptor = new EditCommand.EditMealDescriptor();
        descriptor.setName(meal.getName());
        descriptor.setTags(meal.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditMealDescriptor} that we are building.
     */
    public EditMealDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditMealDescriptor}
     * that we are building.
     */
    public EditMealDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditMealDescriptor build() {
        return descriptor;
    }
}
