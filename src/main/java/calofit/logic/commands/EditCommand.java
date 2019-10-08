package calofit.logic.commands;

import calofit.logic.commands.exceptions.CommandException;
import calofit.commons.core.Messages;
import calofit.commons.core.index.Index;
import calofit.commons.util.CollectionUtil;
import calofit.model.Model;
import calofit.model.dish.Dish;
import calofit.model.dish.Name;
import calofit.model.tag.Tag;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static calofit.logic.parser.CliSyntax.PREFIX_NAME;
import static calofit.logic.parser.CliSyntax.PREFIX_TAG;
import static calofit.model.Model.PREDICATE_SHOW_ALL_DISHES;

/**
 * Edits the details of an existing dish in the dish database.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the dish identified "
            + "by the index number used in the displayed dish list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_MEAL_SUCCESS = "Edited Dish: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEAL = "This dish already exists in the dish database.";

    private final Index index;
    private final EditDishDescriptor editDishDescriptor;

    /**
     * @param index of the dish in the filtered dish list to edit
     * @param editDishDescriptor details to edit the dish with
     */
    public EditCommand(Index index, EditDishDescriptor editDishDescriptor) {
        requireNonNull(index);
        requireNonNull(editDishDescriptor);

        this.index = index;
        this.editDishDescriptor = new EditDishDescriptor(editDishDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Dish> lastShownList = model.getFilteredDishList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEAL_DISPLAYED_INDEX);
        }

        Dish dishToEdit = lastShownList.get(index.getZeroBased());
        Dish editedDish = createEditedDish(dishToEdit, editDishDescriptor);

        if (!dishToEdit.isSameDish(editedDish) && model.hasDish(editedDish)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEAL);
        }

        model.setDish(dishToEdit, editedDish);
        model.updateFilteredDishList(PREDICATE_SHOW_ALL_DISHES);
        return new CommandResult(String.format(MESSAGE_EDIT_MEAL_SUCCESS, editedDish));
    }

    /**
     * Creates and returns a {@code Dish} with the details of {@code dishToEdit}
     * edited with {@code editDishDescriptor}.
     */
    private static Dish createEditedDish(Dish dishToEdit, EditDishDescriptor editDishDescriptor) {
        assert dishToEdit != null;

        Name updatedName = editDishDescriptor.getName().orElse(dishToEdit.getName());
        Set<Tag> updatedTags = editDishDescriptor.getTags().orElse(dishToEdit.getTags());

        return new Dish(updatedName, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editDishDescriptor.equals(e.editDishDescriptor);
    }

    /**
     * Stores the details to edit the dish with. Each non-empty field value will replace the
     * corresponding field value of the dish.
     */
    public static class EditDishDescriptor {
        private Name name;
        private Set<Tag> tags;

        public EditDishDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDishDescriptor(EditDishDescriptor toCopy) {
            setName(toCopy.name);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDishDescriptor)) {
                return false;
            }

            // state check
            EditDishDescriptor e = (EditDishDescriptor) other;

            return getName().equals(e.getName())
                    && getTags().equals(e.getTags());
        }
    }
}
