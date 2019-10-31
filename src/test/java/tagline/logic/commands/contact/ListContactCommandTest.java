package tagline.logic.commands.contact;

import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.logic.commands.CommandTestUtil.showContactAtIndex;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST;
import static tagline.testutil.contact.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tagline.logic.commands.CommandResult.ViewType;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.group.GroupBook;
import tagline.model.note.NoteBook;
import tagline.model.tag.TagBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListContactCommand.
 */
public class ListContactCommandTest {

    private static final ViewType LIST_CONTACT_COMMAND_VIEW_TYPE = ViewType.CONTACT;
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
            new GroupBook(), new TagBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new NoteBook(),
            new GroupBook(), new TagBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListContactCommand(), model, ListContactCommand.MESSAGE_SUCCESS,
            LIST_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showContactAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListContactCommand(), model, ListContactCommand.MESSAGE_SUCCESS,
            LIST_CONTACT_COMMAND_VIEW_TYPE, expectedModel);
    }
}
