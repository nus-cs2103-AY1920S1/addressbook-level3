package seedu.guilttrip.logic.commands;

import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.guilttrip.testutil.TypicalEntries.CATEGORY_SPACE;
import static seedu.guilttrip.testutil.TypicalEntries.CATEGORY_STOCKS;
import static seedu.guilttrip.testutil.TypicalEntries.getTypicalGuiltTrip;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.CommandHistoryStub;
import seedu.guilttrip.logic.commands.addcommands.AddCategoryCommand;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.ModelManager;
import seedu.guilttrip.model.UserPrefs;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.CategoryList;

public class AddCategoryCommandIntegrationTest {
    private Model model;
    private CommandHistory chs = new CommandHistoryStub();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGuiltTrip(), new UserPrefs());
    }

    @Test
    public void execute_newCategory_success() {
        // NewCategory from Expense that doesn't Exist in current list
        Model expectedModel = new ModelManager(model.getGuiltTrip(), new UserPrefs());
        expectedModel.addCategory(CATEGORY_SPACE);
        expectedModel.commitGuiltTrip();
        assertCommandSuccess(new AddCategoryCommand(CATEGORY_SPACE), model,
                String.format(AddCategoryCommand.MESSAGE_SUCCESS, CATEGORY_SPACE), expectedModel, chs);

        //NewCategory from Income that doesn't Exist in current list
        expectedModel.addCategory(CATEGORY_STOCKS);
        expectedModel.commitGuiltTrip();

        assertCommandSuccess(new AddCategoryCommand(CATEGORY_STOCKS), model,
                String.format(AddCategoryCommand.MESSAGE_SUCCESS, CATEGORY_STOCKS), expectedModel, chs);
    }

    @Test
    public void execute_duplicateCategory_throwsCommandException() {
        //Expense Category
        Category categoryInList = model.getGuiltTrip().getCategoryList().getInternalListForOtherEntries().get(0);
        String expectedMessage = String.format(CategoryList.MESSAGE_CONSTRAINTS_IN_LIST,
                categoryInList.getCategoryType());
        assertCommandFailure(new AddCategoryCommand(categoryInList), model, expectedMessage,
                chs);

        //Income Category
        categoryInList = model.getGuiltTrip().getCategoryList().getInternalListForIncome().get(0);
        expectedMessage = String.format(CategoryList.MESSAGE_CONSTRAINTS_IN_LIST,
                categoryInList.getCategoryType());
        assertCommandFailure(new AddCategoryCommand(categoryInList), model, expectedMessage,
                chs);
    }

}
