//@@author e0031374
package tagline.logic.commands.group;

import static tagline.logic.commands.CommandTestUtil.showContactAtIndex;
import static tagline.logic.commands.group.GroupCommandTestUtil.assertCommandSuccess;
import static tagline.testutil.TypicalContacts.getTypicalAddressBook;
import static tagline.testutil.TypicalGroups.getTypicalGroupBookExistingMembers;
import static tagline.testutil.TypicalGroups.getTypicalGroupBookSomeNonExistingMembers;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tagline.logic.commands.CommandResult.ViewType;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.note.NoteBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListGroupCommand.
 */
public class ListGroupCommandTest {

    private static final ViewType LIST_CONTACT_COMMAND_VIEW_TYPE = ViewType.GROUP_LIST;
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
            getTypicalGroupBookSomeNonExistingMembers(), new UserPrefs());
        // tests if contacts not in the TypicalAddressBook are removed as well
        expectedModel = new ModelManager(model.getAddressBook(), new NoteBook(),
            getTypicalGroupBookExistingMembers(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListGroupCommand(), model, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showContactAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListGroupCommand(), model, expectedModel);
    }
}
