package dukecooks.testutil.mealplan;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dukecooks.logic.commands.mealplan.EditMealPlanCommand;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.model.recipe.components.RecipeName;


/**
 * A utility class to help with building EditMealPlanDescriptor objects.
 */
public class EditMealPlanDescriptorBuilder {

    private EditMealPlanCommand.EditMealPlanDescriptor descriptor;

    public EditMealPlanDescriptorBuilder() {
        descriptor = new EditMealPlanCommand.EditMealPlanDescriptor();
    }

    public EditMealPlanDescriptorBuilder(EditMealPlanCommand.EditMealPlanDescriptor descriptor) {
        this.descriptor = new EditMealPlanCommand.EditMealPlanDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMealPlanDescriptor} with fields containing {@code mealPlan}'s details
     */
    public EditMealPlanDescriptorBuilder(MealPlan mealPlan) {
        descriptor = new EditMealPlanCommand.EditMealPlanDescriptor();
        descriptor.setName(mealPlan.getName());
        descriptor.addDay1(mealPlan.getDay1().stream().collect(Collectors.toList()));
        descriptor.addDay2(mealPlan.getDay2().stream().collect(Collectors.toList()));
        descriptor.addDay3(mealPlan.getDay3().stream().collect(Collectors.toList()));
        descriptor.addDay4(mealPlan.getDay4().stream().collect(Collectors.toList()));
        descriptor.addDay5(mealPlan.getDay5().stream().collect(Collectors.toList()));
        descriptor.addDay6(mealPlan.getDay6().stream().collect(Collectors.toList()));
        descriptor.addDay7(mealPlan.getDay7().stream().collect(Collectors.toList()));
    }

    /**
     * Returns an {@code EditMealPlanDescriptor} with fields containing {@code mealPlan}'s details
     */
    public EditMealPlanDescriptorBuilder(MealPlan mealPlanFrom, MealPlan mealPlanTo) {
        descriptor = new EditMealPlanCommand.EditMealPlanDescriptor();
        descriptor.setName(mealPlanTo.getName());
        descriptor.addDay1(mealPlanTo.getDay1().stream().collect(Collectors.toList()));
        descriptor.removeDay1(mealPlanFrom.getDay1().stream().collect(Collectors.toList()));
        descriptor.addDay2(mealPlanTo.getDay2().stream().collect(Collectors.toList()));
        descriptor.removeDay2(mealPlanFrom.getDay2().stream().collect(Collectors.toList()));
        descriptor.addDay3(mealPlanTo.getDay3().stream().collect(Collectors.toList()));
        descriptor.removeDay3(mealPlanFrom.getDay3().stream().collect(Collectors.toList()));
        descriptor.addDay4(mealPlanTo.getDay4().stream().collect(Collectors.toList()));
        descriptor.removeDay4(mealPlanFrom.getDay4().stream().collect(Collectors.toList()));
        descriptor.addDay5(mealPlanTo.getDay5().stream().collect(Collectors.toList()));
        descriptor.removeDay5(mealPlanFrom.getDay5().stream().collect(Collectors.toList()));
        descriptor.addDay6(mealPlanTo.getDay6().stream().collect(Collectors.toList()));
        descriptor.removeDay6(mealPlanFrom.getDay6().stream().collect(Collectors.toList()));
        descriptor.addDay7(mealPlanTo.getDay7().stream().collect(Collectors.toList()));
        descriptor.removeDay7(mealPlanFrom.getDay7().stream().collect(Collectors.toList()));
    }

    /**
     * Sets the {@code Name} of the {@code EditMealPlanDescriptor} that we are building.
     */
    public EditMealPlanDescriptorBuilder withMealPlanName(String name) {
        descriptor.setName(new MealPlanName(name));
        return this;
    }

    /**
     * Parses the {@code recipes} to be added into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 1 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay1ToAdd(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.addDay1(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} for removal into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 1 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay1ToRemove(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.removeDay1(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} to be added into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 2 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay2ToAdd(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.addDay2(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} for removal into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 2 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay2ToRemove(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.removeDay2(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} to be added into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 3 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay3ToAdd(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.addDay3(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} for removal into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 3 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay3ToRemove(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.removeDay3(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} to be added into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 4 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay4ToAdd(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.addDay4(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} for removal into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 4 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay4ToRemove(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.removeDay4(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} to be added into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 5 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay5ToAdd(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.addDay5(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} for removal into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 5 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay5ToRemove(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.removeDay5(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} to be added into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 6 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay6ToAdd(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.addDay6(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} for removal into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 6 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay6ToRemove(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.removeDay6(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} to be added into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 7 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay7ToAdd(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.addDay7(recipeList);
        return this;
    }

    /**
     * Parses the {@code recipes} for removal into a {@code List<RecipeName>}
     * and set it to the {@code EditMealPlanDescriptor} Day 7 recipe list
     * that we are building.
     */
    public EditMealPlanDescriptorBuilder withDay7ToRemove(String... recipes) {
        List<RecipeName> recipeList = Stream.of(recipes).map(RecipeName::new).collect(Collectors.toList());
        descriptor.removeDay7(recipeList);
        return this;
    }

    public EditMealPlanCommand.EditMealPlanDescriptor build() {
        return descriptor;
    }
}
