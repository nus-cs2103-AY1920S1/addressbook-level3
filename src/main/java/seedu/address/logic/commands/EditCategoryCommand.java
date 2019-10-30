package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Category;

/**
 * Edits a category from guiltTrip();
 */
public class EditCategoryCommand extends Command {

    public static final String COMMAND_WORD = "editCategory";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Category specified "
            + "by the name of the Category as well as the type of category whether it is expense or income. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_TYPE + "TYPE OF CATEGORY "
            + PREFIX_CATEGORY + "CATEGORY NAME "
            + PREFIX_DESC + "NEW NAME FOR CATEGORY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "Expense "
            + PREFIX_CATEGORY + "Food "
            + PREFIX_DESC + "Food And Drink ";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Category: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CATEGORY = "This category already exists in guiltTrip.";
    public static final String MESSAGE_NONEXISTENT_CATEGORY = "This category does not exists in the %1$s List "
                                                                + "of guiltTrip.";
    private final Category toEditCategory;
    private final EditCategoryDescriptor editCategoryDescriptor;

    /**
     * @param index               of the person in the filtered person list to edit
     * @param editEntryDescriptor details to edit the person with
     */
    public EditCategoryCommand(Category toEditCategory, EditCategoryDescriptor editCategoryDescriptor) {
        requireNonNull(toEditCategory);
        requireNonNull(editCategoryDescriptor);

        this.toEditCategory = toEditCategory;
        this.editCategoryDescriptor = new EditCategoryDescriptor(editCategoryDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.hasCategory(toEditCategory)) {
            throw new CommandException(String.format(MESSAGE_NONEXISTENT_CATEGORY, toEditCategory.categoryType));
        }
        ObservableList<Category> typeOfCategoryList = model.getCategoryList()
                .determineWhichList(editCategoryDescriptor.getCategoryType());
        //TODO
        int indexOfEdit = typeOfCategoryList.indexOf(toEditCategory);
        Category categoryToEdit = typeOfCategoryList.get(indexOfEdit);
        //tbh alr checks
        Category editedCategory = createEditedCategory(categoryToEdit, editCategoryDescriptor);
        //TODO possible if doesn't work properly
        if (!categoryToEdit.isSameCategory(editedCategory) || model.hasCategory(editedCategory)) {
            throw new CommandException(MESSAGE_DUPLICATE_CATEGORY);
        }

        model.setCategory(categoryToEdit, editedCategory);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedCategory));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Category createEditedCategory(Category categoryToEdit,
                                                 EditCategoryDescriptor editCategoryDescriptor) {
        assert categoryToEdit != null;
        String newCategoryName = editCategoryDescriptor.getCategoryName();
        String newCategoryType = editCategoryDescriptor.getCategoryType();
        return new Category(newCategoryName, newCategoryType);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCategoryCommand)) {
            return false;
        }

        // state check
        EditCategoryCommand e = (EditCategoryCommand) other;
        return toEditCategory.equals(e.toEditCategory)
                && editCategoryDescriptor.equals(e.editCategoryDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditCategoryDescriptor {
        private String categoryName;
        private String categoryType;

        public EditCategoryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCategoryDescriptor(EditCategoryDescriptor toCopy) {
            setCategoryName(toCopy.categoryName);
            setCategoryType(toCopy.categoryType);
        }

        /**
         * Returns true if at least one field is edited.
         */

        public void setCategoryName(String catName) {
            this.categoryName = catName;
        }

        public void setCategoryType(String catType) {
            this.categoryType = catType;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public String getCategoryType() {
            return categoryType;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCategoryDescriptor)) {
                return false;
            }

            // state check
            EditCategoryDescriptor e = (EditCategoryDescriptor) other;

            return getCategoryName().equals(e.getCategoryName())
                    && getCategoryType().equals(e.getCategoryType());
        }
    }
}
