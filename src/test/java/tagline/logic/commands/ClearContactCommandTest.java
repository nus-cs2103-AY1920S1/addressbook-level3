package tagline.logic.commands;

import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.ClearContactCommand;
import tagline.model.AddressBook;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;

public class ClearContactCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearContactCommand(), model, ClearContactCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearContactCommand(), model, ClearContactCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
