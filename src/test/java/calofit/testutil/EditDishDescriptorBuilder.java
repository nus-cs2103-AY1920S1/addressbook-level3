package calofit.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import calofit.logic.commands.EditCommand;
import calofit.model.dish.Calorie;
import calofit.model.dish.Dish;
import calofit.model.dish.Name;
import calofit.model.tag.Tag;

/**
 * A utility class to help with building EditDishDescriptor objects.
 */
public class EditDishDescriptorBuilder {

    private EditCommand.EditDishDescriptor descriptor;

    public EditDishDescriptorBuilder() {
        descriptor = new EditCommand.EditDishDescriptor();
    }

    public EditDishDescriptorBuilder(EditCommand.EditDishDescriptor descriptor) {
        this.descriptor = new EditCommand.EditDishDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDishDescriptor} with fields containing {@code dish}'s details
     */
    public EditDishDescriptorBuilder(Dish dish) {
        descriptor = new EditCommand.EditDishDescriptor();
        descriptor.setName(dish.getName());
        descriptor.setCalories(dish.getCalories());
        descriptor.setTags(dish.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditDishDescriptor} that we are building.
     */
    public EditDishDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Calorie} of the {@code EditDishDescriptor} that we are building.
     */
    public EditDishDescriptorBuilder withCalories(int calories) {
        descriptor.setCalories(new Calorie(calories));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditDishDescriptor}
     * that we are building.
     */
    public EditDishDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditDishDescriptor}
     * that we are building.
     */
    public EditDishDescriptorBuilder withTagsToBeRemoved(Tag tags) {
        Set<Tag> tagSet = Stream.of(tags).collect(Collectors.toSet());
        descriptor.setTagsToRemove(tagSet);
        return this;
    }

    public EditCommand.EditDishDescriptor build() {
        return descriptor;
    }
}
