//@@author e0031374
package tagline.logic.commands.group;

import static tagline.logic.commands.CommandTestUtil.showContactAtIndex;
import static tagline.logic.commands.group.GroupCommandTestUtil.assertCommandSuccess;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST;
import static tagline.testutil.contact.TypicalContacts.getTypicalAddressBook;
import static tagline.testutil.group.TypicalGroups.getTypicalGroupBookExistingMembers;
import static tagline.testutil.group.TypicalGroups.getTypicalGroupBookSomeNonExistingMembers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tagline.logic.commands.CommandResult.ViewType;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.note.NoteBook;
import tagline.model.tag.TagBook;

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
            getTypicalGroupBookSomeNonExistingMembers(), new TagBook(), new UserPrefs());
        // tests if contacts not in the TypicalAddressBook are removed as well
        expectedModel = new ModelManager(model.getAddressBook(), new NoteBook(),
            getTypicalGroupBookExistingMembers(), new TagBook(), new UserPrefs());
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
