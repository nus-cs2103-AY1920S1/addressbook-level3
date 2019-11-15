package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.CommandHistoryStub;
import seedu.guilttrip.logic.commands.addcommands.AddCategoryCommand;
import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.ModelStub;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.testutil.CategoryBuilder;

public class AddCategoryCommandTest {

    @Test
    public void constructor_nullCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCategoryCommand(null));
    }

    @Test
    public void execute_categoryAcceptedByModel_addSuccessful() throws Exception {
        AddCategoryCommandTest.ModelStubAcceptingCategoryAdded modelStub =
                new AddCategoryCommandTest.ModelStubAcceptingCategoryAdded();
        CommandHistoryStub chs = new CommandHistoryStub();
        Category validCategory = new CategoryBuilder().build();

        CommandResult commandResult = new AddCategoryCommand(validCategory).execute(modelStub, chs);

        assertEquals(String.format(AddCategoryCommand.MESSAGE_SUCCESS, validCategory),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCategory), modelStub.listOfCategories);
    }

    @Test
    public void equals() {
        Category foodCategory = new CategoryBuilder().withCatName("Food").build();
        Category shoppingCategory = new CategoryBuilder().withCatName("Shopping").build();
        AddCategoryCommand addFoodCategoryCommand = new AddCategoryCommand(foodCategory);
        AddCategoryCommand addShoppingCategoryCommand = new AddCategoryCommand(shoppingCategory);

        // same object -> returns true
        assertTrue(addFoodCategoryCommand.equals(addFoodCategoryCommand));

        // same values -> returns true
        AddCategoryCommand addFoodCategoryCommandCopy = new AddCategoryCommand(foodCategory);
        assertTrue(addFoodCategoryCommand.equals(addFoodCategoryCommandCopy));

        // different types -> returns false
        assertFalse(addFoodCategoryCommandCopy.equals(1));

        // null -> returns false
        assertFalse(addFoodCategoryCommandCopy.equals(null));

        // different entry -> returns false
        assertFalse(addFoodCategoryCommandCopy.equals(addShoppingCategoryCommand));
    }

    /**
     * A Model stub that always accept the expense being added.
     */
    private class ModelStubAcceptingCategoryAdded extends ModelStub {
        final ArrayList<Category> listOfCategories = new ArrayList<>();

        @Override
        public boolean hasCategory(Category cate) {
            requireNonNull(cate);
            return listOfCategories.stream().anyMatch(cate::isSameCategory);
        }

        @Override
        public void addCategory(Category cat) {
            requireNonNull(cat);
            listOfCategories.add(cat);
        }

        //TODO
        @Override
        public void commitGuiltTrip() {
        }

        @Override
        public ReadOnlyGuiltTrip getGuiltTrip() {
            return new GuiltTrip(false);
        }
    }

}
