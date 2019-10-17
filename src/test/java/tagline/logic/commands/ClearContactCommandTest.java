package tagline.logic.commands;

import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.testutil.TypicalContacts.getTypicalAddressBook;
import static tagline.testutil.TypicalNotes.getTypicalNoteBook;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.contact.ClearContactCommand;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.contact.AddressBook;
import tagline.model.note.NoteBook;

public class ClearContactCommandTest {

    private static final ViewType CLEAR_CONTACT_COMMAND_VIEW_TYPE = ViewType.CONTACT;

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearContactCommand(), model, ClearContactCommand.MESSAGE_SUCCESS,
                CLEAR_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new NoteBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new NoteBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearContactCommand(), model, ClearContactCommand.MESSAGE_SUCCESS,
                CLEAR_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookAndNoteBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalNoteBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalNoteBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearContactCommand(), model, ClearContactCommand.MESSAGE_SUCCESS,
                CLEAR_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
    }
}
