package dukecooks.logic.commands.mealplan;

import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY1;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY2;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY3;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY4;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY5;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY6;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DAY7;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.commons.util.CollectionUtil;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.EditCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.model.recipe.components.Calories;
import dukecooks.model.recipe.components.Carbs;
import dukecooks.model.recipe.components.Fats;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.Protein;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeName;

/**
 * Edits the details of an existing meal plan in Duke Cooks.
 */
public class EditMealPlanCommand extends EditCommand {

    public static final String VARIANT_WORD = "mealplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meal plan identified "
            + "by the index number used in the displayed meal plan list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "RECIPE NAME]... "
            + "[" + PREFIX_DAY1 + "RECIPE NAME]... "
            + "[" + PREFIX_DAY2 + "RECIPE NAME]... "
            + "[" + PREFIX_DAY3 + "RECIPE NAME]... "
            + "[" + PREFIX_DAY4 + "RECIPE NAME]... "
            + "[" + PREFIX_DAY5 + "RECIPE NAME]... "
            + "[" + PREFIX_DAY6 + "RECIPE NAME]... "
            + "[" + PREFIX_DAY7 + "RECIPE NAME]... \n";

    public static final String MESSAGE_EDIT_MEALPLAN_SUCCESS = "Edited MealPlanBook: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEALPLAN = "This meal plan already exists in the Duke Cooks.";

    private static final Set<Ingredient> DUMMY_INGREDIENTS = new HashSet<>(Arrays
            .asList(new Ingredient("DUMMY")));
    private static final Calories DUMMY_CALORIES = new Calories("0");
    private static final Carbs DUMMY_CARBS = new Carbs("0");
    private static final Fats DUMMY_FATS = new Fats("0");
    private static final Protein DUMMY_PROTEIN = new Protein("0");

    private static Event event;

    private final Index index;
    private final EditMealPlanDescriptor editMealPlanDescriptor;

    /**
     * @param index of the meal plan in the filtered meal plan list to edit
     * @param editMealPlanDescriptor details to edit the meal plan with
     */
    public EditMealPlanCommand(Index index, EditMealPlanDescriptor editMealPlanDescriptor) {
        requireNonNull(index);
        requireNonNull(editMealPlanDescriptor);

        this.index = index;
        this.editMealPlanDescriptor = new EditMealPlanDescriptor(editMealPlanDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<MealPlan> lastShownList = model.getFilteredMealPlanList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEALPLAN_DISPLAYED_INDEX);
        }

        MealPlan mealPlanToEdit = lastShownList.get(index.getZeroBased());
        MealPlan editedMealPlan = createEditedMealPlan(mealPlanToEdit, editMealPlanDescriptor, model);

        if (!mealPlanToEdit.isSameMealPlan(editedMealPlan) && model.hasMealPlan(editedMealPlan)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEALPLAN);
        }

        model.setMealPlan(mealPlanToEdit, editedMealPlan);
        model.updateFilteredMealPlanList(Model.PREDICATE_SHOW_ALL_MEALPLANS);

        event = Event.getInstance();
        event.set("mealPlan", "all");

        return new CommandResult(String.format(MESSAGE_EDIT_MEALPLAN_SUCCESS, editedMealPlan));
    }

    /**
     * Creates and returns a {@code MealPlanBook} with the details of {@code mealPlanToEdit}
     * edited with {@code editMealPlanDescriptor}.
     */
    private static MealPlan createEditedMealPlan(MealPlan mealPlanToEdit,
                                                 EditMealPlanDescriptor editMealPlanDescriptor,
                                                 Model model) {
        assert mealPlanToEdit != null;

        MealPlanName updatedName = editMealPlanDescriptor.getName().orElse(mealPlanToEdit.getName());
        List<RecipeName> updatedDay1;
        if (editMealPlanDescriptor.getDay1ToAdd().isPresent()
                || editMealPlanDescriptor.getDay1ToRemove().isPresent()) {

            updatedDay1 = new ArrayList<>(mealPlanToEdit.getDay1());

            if (editMealPlanDescriptor.getDay1ToAdd().isPresent()) {
                List<RecipeName> tempDay1ToAdd = editMealPlanDescriptor.getDay1ToAdd().get();

                for (RecipeName recipeName : tempDay1ToAdd) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay1.add(recipeName);
                    }
                }
            }

            if (editMealPlanDescriptor.getDay1ToRemove().isPresent()) {
                List<RecipeName> tempDay1ToRemove = editMealPlanDescriptor.getDay1ToRemove().get();

                for (RecipeName recipeName : tempDay1ToRemove) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay1.remove(recipeName);
                    }
                }
            }

        } else {
            updatedDay1 = mealPlanToEdit.getDay1();
        }

        List<RecipeName> updatedDay2;
        if (editMealPlanDescriptor.getDay2ToAdd().isPresent()
                || editMealPlanDescriptor.getDay2ToRemove().isPresent()) {

            updatedDay2 = new ArrayList<>(mealPlanToEdit.getDay2());

            if (editMealPlanDescriptor.getDay2ToAdd().isPresent()) {
                List<RecipeName> tempDay2ToAdd = editMealPlanDescriptor.getDay2ToAdd().get();

                for (RecipeName recipeName : tempDay2ToAdd) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay2.add(recipeName);
                    }
                }
            }

            if (editMealPlanDescriptor.getDay2ToRemove().isPresent()) {
                List<RecipeName> tempDay2ToRemove = editMealPlanDescriptor.getDay2ToRemove().get();

                for (RecipeName recipeName : tempDay2ToRemove) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay2.remove(recipeName);
                    }
                }
            }

        } else {
            updatedDay2 = mealPlanToEdit.getDay2();
        }

        List<RecipeName> updatedDay3;
        if (editMealPlanDescriptor.getDay3ToAdd().isPresent()
                || editMealPlanDescriptor.getDay3ToRemove().isPresent()) {

            updatedDay3 = new ArrayList<>(mealPlanToEdit.getDay3());

            if (editMealPlanDescriptor.getDay3ToAdd().isPresent()) {
                List<RecipeName> tempDay3ToAdd = editMealPlanDescriptor.getDay3ToAdd().get();

                for (RecipeName recipeName : tempDay3ToAdd) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay3.add(recipeName);
                    }
                }
            }

            if (editMealPlanDescriptor.getDay3ToRemove().isPresent()) {
                List<RecipeName> tempDay3ToRemove = editMealPlanDescriptor.getDay3ToRemove().get();

                for (RecipeName recipeName : tempDay3ToRemove) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay3.remove(recipeName);
                    }
                }
            }

        } else {
            updatedDay3 = mealPlanToEdit.getDay3();
        }

        List<RecipeName> updatedDay4;
        if (editMealPlanDescriptor.getDay4ToAdd().isPresent()
                || editMealPlanDescriptor.getDay4ToRemove().isPresent()) {

            updatedDay4 = new ArrayList<>(mealPlanToEdit.getDay4());

            if (editMealPlanDescriptor.getDay4ToAdd().isPresent()) {
                List<RecipeName> tempDay4ToAdd = editMealPlanDescriptor.getDay4ToAdd().get();

                for (RecipeName recipeName : tempDay4ToAdd) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay4.add(recipeName);
                    }
                }
            }

            if (editMealPlanDescriptor.getDay4ToRemove().isPresent()) {
                List<RecipeName> tempDay4ToRemove = editMealPlanDescriptor.getDay4ToRemove().get();

                for (RecipeName recipeName : tempDay4ToRemove) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay4.remove(recipeName);
                    }
                }
            }

        } else {
            updatedDay4 = mealPlanToEdit.getDay4();
        }

        List<RecipeName> updatedDay5;
        if (editMealPlanDescriptor.getDay5ToAdd().isPresent()
                || editMealPlanDescriptor.getDay5ToRemove().isPresent()) {

            updatedDay5 = new ArrayList<>(mealPlanToEdit.getDay5());

            if (editMealPlanDescriptor.getDay5ToAdd().isPresent()) {
                List<RecipeName> tempDay5ToAdd = editMealPlanDescriptor.getDay5ToAdd().get();

                for (RecipeName recipeName : tempDay5ToAdd) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay5.add(recipeName);
                    }
                }
            }

            if (editMealPlanDescriptor.getDay5ToRemove().isPresent()) {
                List<RecipeName> tempDay5ToRemove = editMealPlanDescriptor.getDay5ToRemove().get();

                for (RecipeName recipeName : tempDay5ToRemove) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay5.remove(recipeName);
                    }
                }
            }

        } else {
            updatedDay5 = mealPlanToEdit.getDay5();
        }

        List<RecipeName> updatedDay6;
        if (editMealPlanDescriptor.getDay6ToAdd().isPresent()
                || editMealPlanDescriptor.getDay6ToRemove().isPresent()) {

            updatedDay6 = new ArrayList<>(mealPlanToEdit.getDay6());

            if (editMealPlanDescriptor.getDay6ToAdd().isPresent()) {
                List<RecipeName> tempDay6ToAdd = editMealPlanDescriptor.getDay6ToAdd().get();

                for (RecipeName recipeName : tempDay6ToAdd) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay6.add(recipeName);
                    }
                }
            }

            if (editMealPlanDescriptor.getDay6ToRemove().isPresent()) {
                List<RecipeName> tempDay6ToRemove = editMealPlanDescriptor.getDay6ToRemove().get();

                for (RecipeName recipeName : tempDay6ToRemove) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay6.remove(recipeName);
                    }
                }
            }

        } else {
            updatedDay6 = mealPlanToEdit.getDay6();
        }

        List<RecipeName> updatedDay7;
        if (editMealPlanDescriptor.getDay7ToAdd().isPresent()
                || editMealPlanDescriptor.getDay7ToRemove().isPresent()) {

            updatedDay7 = new ArrayList<>(mealPlanToEdit.getDay7());

            if (editMealPlanDescriptor.getDay7ToAdd().isPresent()) {
                List<RecipeName> tempDay7ToAdd = editMealPlanDescriptor.getDay7ToAdd().get();

                for (RecipeName recipeName : tempDay7ToAdd) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay7.add(recipeName);
                    }
                }
            }

            if (editMealPlanDescriptor.getDay7ToRemove().isPresent()) {
                List<RecipeName> tempDay7ToRemove = editMealPlanDescriptor.getDay7ToRemove().get();

                for (RecipeName recipeName : tempDay7ToRemove) {
                    Recipe curr = new Recipe(recipeName, DUMMY_INGREDIENTS,
                            DUMMY_CALORIES, DUMMY_CARBS, DUMMY_FATS, DUMMY_PROTEIN);
                    if (model.hasRecipe(curr)) {
                        updatedDay7.remove(recipeName);
                    }
                }
            }

        } else {
            updatedDay7 = mealPlanToEdit.getDay7();
        }

        return new MealPlan(updatedName, updatedDay1, updatedDay2, updatedDay3,
                updatedDay4, updatedDay5, updatedDay6, updatedDay7);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMealPlanCommand)) {
            return false;
        }

        // state check
        EditMealPlanCommand e = (EditMealPlanCommand) other;
        return index.equals(e.index)
                && editMealPlanDescriptor.equals(e.editMealPlanDescriptor);
    }

    /**
     * Stores the details to edit the meal plan with. Each non-empty field value will replace the
     * corresponding field value of the meal plan.
     */
    public static class EditMealPlanDescriptor {
        private MealPlanName name;
        private List<RecipeName> day1ToAdd;
        private List<RecipeName> day1ToRemove;
        private List<RecipeName> day2ToAdd;
        private List<RecipeName> day2ToRemove;
        private List<RecipeName> day3ToAdd;
        private List<RecipeName> day3ToRemove;
        private List<RecipeName> day4ToAdd;
        private List<RecipeName> day4ToRemove;
        private List<RecipeName> day5ToAdd;
        private List<RecipeName> day5ToRemove;
        private List<RecipeName> day6ToAdd;
        private List<RecipeName> day6ToRemove;
        private List<RecipeName> day7ToAdd;
        private List<RecipeName> day7ToRemove;

        public EditMealPlanDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code ingredients} is used internally.
         */
        public EditMealPlanDescriptor(EditMealPlanDescriptor toCopy) {
            setName(toCopy.name);
            setAddDay1(toCopy.day1ToAdd);
            setRemoveDay1(toCopy.day1ToRemove);
            setAddDay2(toCopy.day2ToAdd);
            setRemoveDay2(toCopy.day2ToRemove);
            setAddDay3(toCopy.day3ToAdd);
            setRemoveDay3(toCopy.day3ToRemove);
            setAddDay4(toCopy.day4ToAdd);
            setRemoveDay4(toCopy.day4ToRemove);
            setAddDay5(toCopy.day5ToAdd);
            setRemoveDay5(toCopy.day5ToRemove);
            setAddDay6(toCopy.day6ToAdd);
            setRemoveDay6(toCopy.day6ToRemove);
            setAddDay7(toCopy.day7ToAdd);
            setRemoveDay7(toCopy.day7ToRemove);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, day1ToAdd, day1ToRemove,
                    day2ToAdd, day2ToRemove, day3ToAdd, day3ToRemove,
                    day4ToAdd, day4ToRemove, day5ToAdd, day5ToRemove,
                    day6ToAdd, day6ToRemove, day7ToAdd, day7ToRemove);
        }

        public void setName(MealPlanName name) {
            this.name = name;
        }

        public Optional<MealPlanName> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Set {@code recipes} to this object's {@code day1ToAdd}.
         * A defensive copy of {@code day1ToAdd} is used internally.
         */
        public void setAddDay1(List<RecipeName> recipes) {
            this.day1ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Adds {@code recipes} to this object's {@code day1ToAdd}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void addDay1(List<RecipeName> recipes) {
            this.day1ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Set {@code recipes} to this object's {@code day1ToRemove}.
         * A defensive copy of {@code day1ToRemove} is used internally.
         */
        public void setRemoveDay1(List<RecipeName> recipes) {
            this.day1ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Removes {@code recipes} to this object's {@code day1ToRemove}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void removeDay1(List<RecipeName> recipes) {
            this.day1ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Returns an unmodifiable Day 1 recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay1ToAdd() {
            return (day1ToAdd != null)
                   ? Optional.of(Collections.unmodifiableList(day1ToAdd))
                   : Optional.empty();
        }

        /**
         * Returns an unmodifiable Day 1 recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay1ToRemove() {
            return (day1ToRemove != null)
                   ? Optional.of(Collections.unmodifiableList(day1ToRemove))
                   : Optional.empty();
        }

        /**
         * Set {@code recipes} to this object's {@code day2ToAdd}.
         * A defensive copy of {@code day2ToAdd} is used internally.
         */
        public void setAddDay2(List<RecipeName> recipes) {
            this.day2ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Adds {@code recipes} to this object's {@code day2ToAdd}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void addDay2(List<RecipeName> recipes) {
            this.day2ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Set {@code recipes} to this object's {@code day2ToRemove}.
         * A defensive copy of {@code day2ToRemove} is used internally.
         */
        public void setRemoveDay2(List<RecipeName> recipes) {
            this.day2ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Removes {@code recipes} to this object's {@code day2ToRemove}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void removeDay2(List<RecipeName> recipes) {
            this.day2ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Returns an unmodifiable Day 2 recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay2ToAdd() {
            return (day2ToAdd != null)
                   ? Optional.of(Collections.unmodifiableList(day2ToAdd))
                   : Optional.empty();
        }

        /**
         * Returns an unmodifiable Day 2recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay2ToRemove() {
            return (day2ToRemove != null)
                   ? Optional.of(Collections.unmodifiableList(day2ToRemove))
                   : Optional.empty();
        }

        /**
         * Set {@code recipes} to this object's {@code day3ToAdd}.
         * A defensive copy of {@code day3ToAdd} is used internally.
         */
        public void setAddDay3(List<RecipeName> recipes) {
            this.day3ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Adds {@code recipes} to this object's {@code day3ToAdd}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void addDay3(List<RecipeName> recipes) {
            this.day3ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Set {@code recipes} to this object's {@code day3ToRemove}.
         * A defensive copy of {@code day3ToRemove} is used internally.
         */
        public void setRemoveDay3(List<RecipeName> recipes) {
            this.day3ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Removes {@code recipes} to this object's {@code day3ToRemove}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void removeDay3(List<RecipeName> recipes) {
            this.day3ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Returns an unmodifiable Day 3 recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay3ToAdd() {
            return (day3ToAdd != null)
                   ? Optional.of(Collections.unmodifiableList(day3ToAdd))
                   : Optional.empty();
        }

        /**
         * Returns an unmodifiable Day 3recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay3ToRemove() {
            return (day3ToRemove != null)
                   ? Optional.of(Collections.unmodifiableList(day3ToRemove))
                   : Optional.empty();
        }

        /**
         * Set {@code recipes} to this object's {@code day4ToAdd}.
         * A defensive copy of {@code day4ToAdd} is used internally.
         */
        public void setAddDay4(List<RecipeName> recipes) {
            this.day4ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Adds {@code recipes} to this object's {@code day4ToAdd}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void addDay4(List<RecipeName> recipes) {
            this.day4ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Set {@code recipes} to this object's {@code day4ToRemove}.
         * A defensive copy of {@code day4ToRemove} is used internally.
         */
        public void setRemoveDay4(List<RecipeName> recipes) {
            this.day4ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Removes {@code recipes} to this object's {@code day4ToRemove}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void removeDay4(List<RecipeName> recipes) {
            this.day4ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Returns an unmodifiable Day 4 recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay4ToAdd() {
            return (day4ToAdd != null)
                   ? Optional.of(Collections.unmodifiableList(day4ToAdd))
                   : Optional.empty();
        }

        /**
         * Returns an unmodifiable Day 4recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay4ToRemove() {
            return (day4ToRemove != null)
                   ? Optional.of(Collections.unmodifiableList(day4ToRemove))
                   : Optional.empty();
        }

        /**
         * Set {@code recipes} to this object's {@code day5ToAdd}.
         * A defensive copy of {@code day5ToAdd} is used internally.
         */
        public void setAddDay5(List<RecipeName> recipes) {
            this.day5ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Adds {@code recipes} to this object's {@code day5ToAdd}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void addDay5(List<RecipeName> recipes) {
            this.day5ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Set {@code recipes} to this object's {@code day5ToRemove}.
         * A defensive copy of {@code day5ToRemove} is used internally.
         */
        public void setRemoveDay5(List<RecipeName> recipes) {
            this.day5ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Removes {@code recipes} to this object's {@code day5ToRemove}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void removeDay5(List<RecipeName> recipes) {
            this.day5ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Returns an unmodifiable Day 5 recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay5ToAdd() {
            return (day5ToAdd != null)
                   ? Optional.of(Collections.unmodifiableList(day5ToAdd))
                   : Optional.empty();
        }

        /**
         * Returns an unmodifiable Day 5recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay5ToRemove() {
            return (day5ToRemove != null)
                   ? Optional.of(Collections.unmodifiableList(day5ToRemove))
                   : Optional.empty();
        }

        /**
         * Set {@code recipes} to this object's {@code day6ToAdd}.
         * A defensive copy of {@code day6ToAdd} is used internally.
         */
        public void setAddDay6(List<RecipeName> recipes) {
            this.day6ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Adds {@code recipes} to this object's {@code day6ToAdd}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void addDay6(List<RecipeName> recipes) {
            this.day6ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Set {@code recipes} to this object's {@code day6ToRemove}.
         * A defensive copy of {@code day6ToRemove} is used internally.
         */
        public void setRemoveDay6(List<RecipeName> recipes) {
            this.day6ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Removes {@code recipes} to this object's {@code day6ToRemove}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void removeDay6(List<RecipeName> recipes) {
            this.day6ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Returns an unmodifiable Day 6 recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay6ToAdd() {
            return (day6ToAdd != null)
                   ? Optional.of(Collections.unmodifiableList(day6ToAdd))
                   : Optional.empty();
        }

        /**
         * Returns an unmodifiable Day 6recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay6ToRemove() {
            return (day6ToRemove != null)
                   ? Optional.of(Collections.unmodifiableList(day6ToRemove))
                   : Optional.empty();
        }

        /**
         * Set {@code recipes} to this object's {@code day7ToAdd}.
         * A defensive copy of {@code day7ToAdd} is used internally.
         */
        public void setAddDay7(List<RecipeName> recipes) {
            this.day7ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Adds {@code recipes} to this object's {@code day7ToAdd}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void addDay7(List<RecipeName> recipes) {
            this.day7ToAdd = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Set {@code recipes} to this object's {@code day7ToRemove}.
         * A defensive copy of {@code day7ToRemove} is used internally.
         */
        public void setRemoveDay7(List<RecipeName> recipes) {
            this.day7ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Removes {@code recipes} to this object's {@code day7ToRemove}.
         * A defensive copy of {@code recipes} is used internally.
         */
        public void removeDay7(List<RecipeName> recipes) {
            this.day7ToRemove = (recipes != null) ? new ArrayList<>(recipes) : null;
        }

        /**
         * Returns an unmodifiable Day 7 recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay7ToAdd() {
            return (day7ToAdd != null)
                   ? Optional.of(Collections.unmodifiableList(day7ToAdd))
                   : Optional.empty();
        }

        /**
         * Returns an unmodifiable Day 7recipes list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code recipes} is null.
         */
        public Optional<List<RecipeName>> getDay7ToRemove() {
            return (day7ToRemove != null)
                   ? Optional.of(Collections.unmodifiableList(day7ToRemove))
                   : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMealPlanDescriptor)) {
                return false;
            }

            // state check
            EditMealPlanDescriptor e = (EditMealPlanDescriptor) other;

            return getName().equals(e.getName());
        }
    }
}
