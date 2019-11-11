package tagline.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.CommandTestUtil.CONTACT_ID_ONE;
import static tagline.logic.commands.CommandTestUtil.CONTACT_ID_TWO;
import static tagline.testutil.contact.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.CommandResult.ViewType;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.group.GroupBook;
import tagline.model.note.NoteBook;
import tagline.model.tag.TagBook;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ShowContactCommand}.
 */
public class ShowContactCommandTest {

    private static final ViewType SHOW_CONTACT_COMMAND_VIEW_TYPE = ViewType.CONTACT_PROFILE;
    private Model model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
        new GroupBook(), new TagBook(), new UserPrefs());

    @Test
    public void equals() {
        ShowContactCommand showFirstCommand = new ShowContactCommand(CONTACT_ID_ONE);
        ShowContactCommand showSecondCommand = new ShowContactCommand(CONTACT_ID_TWO);

        // same object -> returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values -> returns true
        ShowContactCommand showFirstCommandCopy = new ShowContactCommand(CONTACT_ID_ONE);
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types -> returns false
        assertFalse(showFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoContact(Model model) {
        model.updateFilteredContactList(p -> false);

        assertTrue(model.getFilteredContactList().isEmpty());
    }
}
