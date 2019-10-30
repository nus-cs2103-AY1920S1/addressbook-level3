//@@author e0031374
package tagline.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.CommandTestUtil.assertCommandFailure;
import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.logic.commands.group.GroupCommandTestUtil.DESC_HYDRA;
import static tagline.logic.commands.group.GroupCommandTestUtil.DESC_SHIELD;
import static tagline.logic.commands.group.GroupCommandTestUtil.VALID_GROUPNAME_SHIELD;
import static tagline.testutil.TypicalContacts.getTypicalAddressBook;
import static tagline.testutil.TypicalGroups.MYSTIC_ARTS;
import static tagline.testutil.TypicalGroups.getTypicalGroupBookExistingMembers;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.group.EditGroupCommand.EditGroupDescriptor;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.contact.AddressBook;
import tagline.model.group.Group;
import tagline.model.group.GroupBook;
import tagline.model.group.GroupNameEqualsKeywordPredicate;
import tagline.model.note.NoteBook;
import tagline.testutil.EditGroupDescriptorBuilder;
import tagline.testutil.GroupBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and
 * unit tests for AddMemberToGroupCommand.
 */
public class AddMemberToGroupCommandTest {

    private static final ViewType ADD_MEMBER_COMMAND_VIEW_TYPE = ViewType.GROUP_SINGLE;
    private Model model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
        getTypicalGroupBookExistingMembers(), new UserPrefs());

    @Test
    public void execute_addMemberFieldsSpecified_success() {
        ObservableList<Group> groupList = model.getGroupBook().getGroupList();
        int groupListSize = groupList.size();
        Group lastGroup = groupList.get(groupListSize - 1);

        GroupBuilder groupInList = new GroupBuilder(lastGroup);
        Group editedGroup = groupInList
                .addMemberIds("1", "3")
                .build();

        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder()
                .withMemberIds("1", "3").build();
        AddMemberToGroupCommand editGroupCommand =
            new AddMemberToGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate(lastGroup), descriptor);

        String expectedMessage = String.format(AddMemberToGroupCommand.MESSAGE_ADD_MEMBER_SUCCESS, editedGroup);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new NoteBook(), new GroupBook(model.getGroupBook()), new UserPrefs());
        expectedModel.setGroup(lastGroup, editedGroup);

        // ensured expectedModel ContactDisplay is same due to setting predicate
        expectedModel.updateFilteredContactList(GroupCommand.memberIdsToContactIdPredicate(editedGroup.getMemberIds()));
        expectedModel.updateFilteredGroupList(GroupNameEqualsKeywordPredicate.generatePredicate(editedGroup));

        GroupCommandTestUtil.assertCommandSuccess(editGroupCommand, model, expectedMessage,
            ADD_MEMBER_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        Group editedGroup = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        AddMemberToGroupCommand editGroupCommand =
            new AddMemberToGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate(editedGroup),
            new EditGroupDescriptor());

        String expectedMessage = String.format(AddMemberToGroupCommand.MESSAGE_ADD_MEMBER_SUCCESS, editedGroup);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new NoteBook(),
            new GroupBook(model.getGroupBook()), new UserPrefs());

        // ensured expectedModel ContactDisplay is same due to setting predicate
        expectedModel.updateFilteredContactList(GroupCommand.memberIdsToContactIdPredicate(editedGroup.getMemberIds()));
        expectedModel.updateFilteredGroupList(GroupNameEqualsKeywordPredicate.generatePredicate(editedGroup));

        assertCommandSuccess(editGroupCommand, model, expectedMessage, ADD_MEMBER_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_invalidGroupIdUnfilteredList_failure() {
        GroupNameEqualsKeywordPredicate predicate =
            GroupNameEqualsKeywordPredicate.generatePredicate(MYSTIC_ARTS.getGroupName());

        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder().withGroupName(VALID_GROUPNAME_SHIELD).build();
        AddMemberToGroupCommand editGroupCommand = new AddMemberToGroupCommand(predicate, descriptor);

        assertCommandFailure(editGroupCommand, model, AddMemberToGroupCommand.MESSAGE_GROUP_NOT_FOUND);
    }

    @Test
    public void equals() {
        final AddMemberToGroupCommand standardCommand =
            new AddMemberToGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate("HYDRA"), DESC_HYDRA);

        // same values -> returns true
        EditGroupDescriptor copyDescriptor = new EditGroupDescriptor(DESC_HYDRA);
        AddMemberToGroupCommand commandWithSameValues =
            new AddMemberToGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate("HYDRA"), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(
            new RemoveMemberFromGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate("HYDRA"),
            copyDescriptor)));

        // different index -> returns false
        assertFalse(standardCommand.equals(
            new AddMemberToGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate("SHIELD"), DESC_HYDRA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
            new AddMemberToGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate("HYDRA"), DESC_SHIELD)));
    }
}
