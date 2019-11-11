package seedu.guilttrip.logic.commands.editcommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_OLD_NAME;

import javafx.collections.ObservableList;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.util.CategoryType;

/**
 * Edits a category from guiltTrip;
 */
public class EditCategoryCommand extends Command {

    public static final String COMMAND_WORD = "editCategory";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Edits the details of the Category specified "
            + "by the name of the Category as well as the type of category whether it is expense or income. ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_CATEGORY + "TYPE OF CATEGORY "
            + PREFIX_OLD_NAME + "CATEGORY OLD NAME "
            + PREFIX_DESC + "NEW NAME FOR CATEGORY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "Expense "
            + PREFIX_OLD_NAME + "Food "
            + PREFIX_DESC + "Food And Drink ";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Edited Category: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CATEGORY = "This category already exists in GuiltTrip.";
    public static final String MESSAGE_NONEXISTENT_CATEGORY = "This category does not exists in the %1$s List "
            + "of GuiltTrip.";
    private final Category toEditCategory;
    private final EditCategoryDescriptor editCategoryDescriptor;

    /**
     * @param toEditCategory the category to be edited
     * @param editCategoryDescriptor details to edit the category with
     */
    public EditCategoryCommand(Category toEditCategory, EditCategoryDescriptor editCategoryDescriptor) {
        requireNonNull(toEditCategory);
        requireNonNull(editCategoryDescriptor);

        this.toEditCategory = toEditCategory;
        this.editCategoryDescriptor = new EditCategoryDescriptor(editCategoryDescriptor);
    }

    /**
     *  Edits Category toEditCategory in the list of existing categories. Model will handle the check if the category is
     *  present in the list and if the newly edited category is a duplicate of another existing category.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return CommandResult the CommandResult for guiltTrip to display to User.
     * @throws CommandException if the category is
     * present in the list and if the newly edited category is a duplicate of another existing category.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        //ensures that the category old Name specified does not exist in GuiltTrip
        if (!model.hasCategory(toEditCategory)) {
            throw new CommandException(String.format(MESSAGE_NONEXISTENT_CATEGORY, toEditCategory.getCategoryType()));
        }
        ObservableList<Category> typeOfCategoryList = model.getCategoryList()
                .determineWhichList(editCategoryDescriptor.getCategoryType());
        int indexOfEdit = typeOfCategoryList.indexOf(toEditCategory);
        Category categoryToEdit = typeOfCategoryList.get(indexOfEdit);
        Category editedCategory = createEditedCategory(categoryToEdit, editCategoryDescriptor);
        //ensures that the new category specified does not exist in guilttrip
        if (categoryToEdit.isSameCategory(editedCategory) || model.hasCategory(editedCategory)) {
            throw new CommandException(MESSAGE_DUPLICATE_CATEGORY);
        }

        model.setCategory(categoryToEdit, editedCategory);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS, editedCategory));
    }

    /**
     * Creates and returns a {@code Category} with the details of {@code categoryToEdit}
     * edited with {@code editCategoryDescriptor}.
     */
    private static Category createEditedCategory(Category categoryToEdit,
                                                 EditCategoryDescriptor editCategoryDescriptor) {
        assert categoryToEdit != null;
        String newCategoryName = editCategoryDescriptor.getCategoryName();
        CategoryType newCategoryType = editCategoryDescriptor.getCategoryType();
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
     * Stores the details to edit the Category with. Each non-empty field value will replace the
     * corresponding field value of the Category.
     */
    public static class EditCategoryDescriptor {
        private String categoryName;
        private CategoryType categoryType;

        public EditCategoryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code Category} is used internally.
         */
        public EditCategoryDescriptor(EditCategoryDescriptor toCopy) {
            setCategoryName(toCopy.categoryName);
            setCategoryType(toCopy.categoryType);
        }

        public void setCategoryName(String catName) {
            this.categoryName = catName;
        }

        public void setCategoryType(CategoryType catType) {
            this.categoryType = catType;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public CategoryType getCategoryType() {
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
