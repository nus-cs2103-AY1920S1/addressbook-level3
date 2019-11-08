package seedu.guilttrip.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.logic.commands.CommandTestUtil.DESC_CATEGORY_INCOME_BUSINESS;
import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.guilttrip.testutil.TypicalEntries.getTypicalGuiltTrip;
import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.CommandHistoryStub;
import seedu.guilttrip.logic.commands.editcommands.EditCategoryCommand;
import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.ModelManager;
import seedu.guilttrip.model.UserPrefs;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.testutil.CategoryBuilder;
import seedu.guilttrip.testutil.EditCategoryDescriptorBuilder;


public class EditCategoryCommandTest {
    private Model model = new ModelManager(getTypicalGuiltTrip(), new UserPrefs());
    private CommandHistory chs = new CommandHistoryStub();

    @Test
    public void execute_allFieldsSpecifiedAndNotInList_success() {
        //non existent-category leisure
        Category editedCategory = new CategoryBuilder().withCatName("Leisure").build();
        EditCategoryCommand.EditCategoryDescriptor descriptor =
                new EditCategoryDescriptorBuilder(editedCategory).build();
        //get First Category in the List, food
        Category firstCategory = model.getCategoryList().getInternalListForOtherEntries().get(INDEX_FIRST_ENTRY
                .getZeroBased());
        EditCategoryCommand editCommand = new EditCategoryCommand(firstCategory, descriptor);

        String expectedMessage = String.format(EditCategoryCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedCategory);

        Model expectedModel = new ModelManager(new GuiltTrip(model.getGuiltTrip()), new UserPrefs());
        expectedModel.setCategory(model.getCategoryList().getInternalListForOtherEntries().get(0), editedCategory);
        expectedModel.commitGuiltTrip();

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, chs);
    }

    @Test
    public void execute_editedCategoryIsADuplicate_failure() {
        //duplicate of the original category with no change
        Category editedCategory = new CategoryBuilder().build();

        EditCategoryCommand.EditCategoryDescriptor descriptor =
                new EditCategoryDescriptorBuilder(editedCategory).build();
        //get First Category in the List, food
        Category firstCategory = model.getCategoryList().getInternalListForOtherEntries().get(INDEX_FIRST_ENTRY
                .getZeroBased());
        EditCategoryCommand editCommand = new EditCategoryCommand(firstCategory, descriptor);

        String expectedMessage = String.format(EditCategoryCommand.MESSAGE_DUPLICATE_CATEGORY, editedCategory);
        assertCommandFailure(editCommand, model, expectedMessage, chs);

        //duplicate of another category already existing
        Category existingCategory = model.getCategoryList().getInternalListForOtherEntries().get(INDEX_SECOND_ENTRY
                .getZeroBased());
        editedCategory = new CategoryBuilder().withCatName(existingCategory.getCategoryName()).build();

        descriptor = new EditCategoryDescriptorBuilder(editedCategory).build();
        //get First Category in the List, food
        firstCategory = model.getCategoryList().getInternalListForOtherEntries().get(INDEX_FIRST_ENTRY
                .getZeroBased());
        editCommand = new EditCategoryCommand(firstCategory, descriptor);

        expectedMessage = String.format(EditCategoryCommand.MESSAGE_DUPLICATE_CATEGORY, editedCategory);
        assertCommandFailure(editCommand, model, expectedMessage, chs);
    }

    @Test
    public void execute_editedCategoryInvalidValue_failure() {
        //editing a non existent category
        Category editedCategory = new CategoryBuilder().withCatName("Leisure").build();
        Category nonExistentCategory = new CategoryBuilder().withCatName("Make Death").build();
        EditCategoryCommand.EditCategoryDescriptor descriptor =
                new EditCategoryDescriptorBuilder(editedCategory).build();
        EditCategoryCommand editCommand = new EditCategoryCommand(nonExistentCategory, descriptor);

        String expectedMessage = String.format(EditCategoryCommand.MESSAGE_NONEXISTENT_CATEGORY,
                editedCategory.getCategoryType());
        assertCommandFailure(editCommand, model, expectedMessage, chs);
    }

    @Test
    public void equals() {
        Category firstCategory = new CategoryBuilder().build();
        EditCategoryCommand.EditCategoryDescriptor secondCategoryDescriptor = new EditCategoryDescriptorBuilder()
                .withCategoryName("DEATH").withCategoryType("Expense").build();
        EditCategoryCommand.EditCategoryDescriptor copydescriptor =
                new EditCategoryCommand.EditCategoryDescriptor(DESC_CATEGORY_INCOME_BUSINESS);
        final EditCategoryCommand standardCommand = new EditCategoryCommand(firstCategory,
                DESC_CATEGORY_INCOME_BUSINESS);
        EditCategoryCommand editCommandWithSameValues = new EditCategoryCommand(firstCategory, copydescriptor);

        assertTrue(standardCommand.equals(editCommandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCategoryCommand(firstCategory, secondCategoryDescriptor)));
    }
}
