package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalGroceryItems.getTypicalAddressBook;
import static seedu.address.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.address.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.address.testutil.TypicalWasteList.getTypicalWasteList;

import org.junit.jupiter.api.BeforeEach;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalTemplateList(),
                getTypicalWasteList(), getTypicalShoppingList());
    }

    /*@Test
    public void execute_duplicatePerson_throwsCommandException() {
        Food foodInList = model.getGroceryList().getPersonList().get(0);
        assertCommandFailure(new AddCommand(foodInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }*/

}
