package tagline.logic.commands;

import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.logic.commands.CommandTestUtil.showContactAtIndex;
import static tagline.testutil.TypicalContacts.getTypicalAddressBook;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.ListContactCommand;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListContactCommand.
 */
public class ListContactCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListContactCommand(), model, ListContactCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);
        assertCommandSuccess(new ListContactCommand(), model, ListContactCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
