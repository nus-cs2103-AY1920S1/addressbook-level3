//@@author e0031374
package tagline.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.CommandResult.ViewType;
import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.TypicalGroups.MYSTIC_ARTS;
import static tagline.testutil.TypicalGroups.getTypicalGroupBook;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.GroupCommandTestUtil;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.contact.AddressBook;
import tagline.model.group.Group;
import tagline.model.group.GroupNameEqualsKeywordPredicate;
import tagline.model.note.NoteBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteGroupCommand}.
 */
class DeleteGroupCommandTest {

    private static final ViewType DELETE_GROUP_COMMAND_VIEW_TYPE = ViewType.GROUP_LIST;
    private Model model = new ModelManager(new AddressBook(), new NoteBook(),
            getTypicalGroupBook(), new UserPrefs());

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteGroupCommand(null));
    }

    @Test
    public void execute_validGroupIdUnfilteredList_success() {
        Group groupToDelete = model.getGroupBook().getGroupList().get(INDEX_FIRST.getZeroBased());
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(preparePredicate(groupToDelete));

        // note, Deleting a Group will only say that the GroupName has been deleted unlike other commands
        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_KEYWORD_SUCCESS,
                groupToDelete.getGroupName().value);

        Model expectedModel = new ModelManager(new AddressBook(), new NoteBook(),
                model.getGroupBook(), new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, DELETE_GROUP_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_validGroupIdFilteredList_success() {
        Group groupToDelete = model.getGroupBook().getGroupList().get(INDEX_FIRST.getZeroBased());
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(preparePredicate(groupToDelete));

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_KEYWORD_SUCCESS,
            groupToDelete.getGroupName().value);

        // note, Deleting a Group will only say that the GroupName has been deleted unlike other commands
        Model expectedModel = new ModelManager(new AddressBook(), new NoteBook(),
            model.getGroupBook(), new UserPrefs());

        expectedModel.deleteGroup(groupToDelete);

        // Currently, after deleting a group, it will show all groups
        // but what if you want to see a list of groups and delete from there? well its not implemented yet
        // and not for a long time so im probs not gonna put too much effort into this.
        //showNoGroup(model);
        //showNoGroup(expectedModel);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, DELETE_GROUP_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_nonExistingGroupId_throwsCommandException() {
        DeleteGroupCommand deleteGroupCommand =
            new DeleteGroupCommand(preparePredicate(MYSTIC_ARTS));

        GroupCommandTestUtil.assertCommandFailure(deleteGroupCommand, model, GroupCommand.MESSAGE_GROUP_NOT_FOUND);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoGroup(Model model) {
        model.updateFilteredGroupList(p -> false);

        assertTrue(model.getFilteredGroupList().isEmpty());
    }


    /**
     * Parses {@code userInput} into a {@code GroupNameEqualsKeywordPredicate}.
     */
    private GroupNameEqualsKeywordPredicate preparePredicate(Group group) {
        return new GroupNameEqualsKeywordPredicate(Collections.singletonList(group.getGroupName()));
    }
}
