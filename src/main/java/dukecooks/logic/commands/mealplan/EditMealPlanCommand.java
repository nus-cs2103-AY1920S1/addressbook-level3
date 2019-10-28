package dukecooks.logic.commands.mealplan;

import static dukecooks.logic.parser.CliSyntax.PREFIX_CALORIES;
import static dukecooks.logic.parser.CliSyntax.PREFIX_CARBS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_FATS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

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

/**
 * Edits the details of an existing meal plan in Duke Cooks.
 */
public class EditMealPlanCommand extends EditCommand {

    public static final String VARIANT_WORD = "mealplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meal plan identified "
            + "by the index number used in the displayed meal plan list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_INGREDIENT + "INGREDIENT]... "
            + "[" + PREFIX_CALORIES + "CALORIES] "
            + "[" + PREFIX_CARBS + "CARBS] "
            + "[" + PREFIX_FATS + "FATS] "
            + "[" + PREFIX_PROTEIN + "PROTEIN] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PROTEIN + "123";

    public static final String MESSAGE_EDIT_MEALPLAN_SUCCESS = "Edited MealPlanBook: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEALPLAN = "This meal plan already exists in the Duke Cooks.";

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
        MealPlan editedMealPlan = createEditedMealPlan(mealPlanToEdit, editMealPlanDescriptor);

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
                                                 EditMealPlanDescriptor editMealPlanDescriptor) {
        assert mealPlanToEdit != null;

        MealPlanName updatedName = editMealPlanDescriptor.getName().orElse(mealPlanToEdit.getName());

        return new MealPlan(updatedName);
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

        public EditMealPlanDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code ingredients} is used internally.
         */
        public EditMealPlanDescriptor(EditMealPlanDescriptor toCopy) {
            setName(toCopy.name);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name);
        }

        public void setName(MealPlanName name) {
            this.name = name;
        }

        public Optional<MealPlanName> getName() {
            return Optional.ofNullable(name);
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
