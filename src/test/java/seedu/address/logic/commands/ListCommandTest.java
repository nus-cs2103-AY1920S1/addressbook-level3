package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.address.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.address.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.address.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.address.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import org.junit.jupiter.api.BeforeEach;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
                getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList());
        expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), getTypicalTemplateList(),
                model.getWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList());
    }

    /*@Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }*/

    /*@Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }*/
}
