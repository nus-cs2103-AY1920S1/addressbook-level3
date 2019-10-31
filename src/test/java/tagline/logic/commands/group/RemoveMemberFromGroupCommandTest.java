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

import java.util.Optional;

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
import tagline.model.group.MemberId;
import tagline.model.note.NoteBook;
import tagline.model.tag.TagBook;
import tagline.testutil.EditGroupDescriptorBuilder;
import tagline.testutil.GroupBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and
 * unit tests for RemoveMemberFromGroupCommand.
 */
public class RemoveMemberFromGroupCommandTest {

    private static final ViewType REMOVE_MEMBER_COMMAND_VIEW_TYPE = ViewType.GROUP_SINGLE;
    private Model model = new ModelManager(getTypicalAddressBook(), new NoteBook(),
        getTypicalGroupBookExistingMembers(), new TagBook(), new UserPrefs());

    @Test
    public void execute_removeMemberFieldsSpecified_success() {
        ObservableList<Group> groupList = model.getGroupBook().getGroupList();
        int groupListSize = groupList.size();
        Group lastGroup = groupList.get(groupListSize - 1);
        Optional<MemberId> optMember = lastGroup.getMemberIds().stream().findFirst();
        String removeThis = optMember.get().value;

        GroupBuilder groupInList = new GroupBuilder(lastGroup);
        Group editedGroup = groupInList
                .removeMemberIds(removeThis)
                .build();

        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder()
                .withMemberIds(removeThis).build();
        RemoveMemberFromGroupCommand editGroupCommand =
            new RemoveMemberFromGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate(lastGroup), descriptor);

        String expectedMessage = String.format(RemoveMemberFromGroupCommand.MESSAGE_REMOVE_MEMBER_SUCCESS, editedGroup);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new NoteBook(), new GroupBook(model.getGroupBook()), new TagBook(), new UserPrefs());
        expectedModel.setGroup(lastGroup, editedGroup);

        // ensured expectedModel ContactDisplay is same due to setting predicate
        expectedModel.updateFilteredContactList(GroupCommand.memberIdsToContactIdPredicate(editedGroup.getMemberIds()));
        expectedModel.updateFilteredGroupList(GroupNameEqualsKeywordPredicate.generatePredicate(editedGroup));

        GroupCommandTestUtil.assertCommandSuccess(editGroupCommand, model, expectedMessage,
            REMOVE_MEMBER_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        Group editedGroup = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        RemoveMemberFromGroupCommand editGroupCommand =
            new RemoveMemberFromGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate(editedGroup),
            new EditGroupDescriptor());

        String expectedMessage = String.format(RemoveMemberFromGroupCommand.MESSAGE_REMOVE_MEMBER_SUCCESS, editedGroup);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new NoteBook(),
            new GroupBook(model.getGroupBook()), new TagBook(), new UserPrefs());

        // ensured expectedModel ContactDisplay is same due to setting predicate
        expectedModel.updateFilteredContactList(GroupCommand.memberIdsToContactIdPredicate(editedGroup.getMemberIds()));
        expectedModel.updateFilteredGroupList(GroupNameEqualsKeywordPredicate.generatePredicate(editedGroup));

        assertCommandSuccess(editGroupCommand, model, expectedMessage, REMOVE_MEMBER_COMMAND_VIEW_TYPE, expectedModel);
    }

    @Test
    public void execute_invalidGroupIdUnfilteredList_failure() {
        GroupNameEqualsKeywordPredicate predicate =
            GroupNameEqualsKeywordPredicate.generatePredicate(MYSTIC_ARTS.getGroupName());

        EditGroupDescriptor descriptor = new EditGroupDescriptorBuilder().withGroupName(VALID_GROUPNAME_SHIELD).build();
        RemoveMemberFromGroupCommand editGroupCommand = new RemoveMemberFromGroupCommand(predicate, descriptor);

        assertCommandFailure(editGroupCommand, model, RemoveMemberFromGroupCommand.MESSAGE_GROUP_NOT_FOUND);
    }

    @Test
    public void equals() {
        final RemoveMemberFromGroupCommand standardCommand =
            new RemoveMemberFromGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate("HYDRA"), DESC_HYDRA);

        // same values -> returns true
        EditGroupDescriptor copyDescriptor = new EditGroupDescriptor(DESC_HYDRA);
        RemoveMemberFromGroupCommand commandWithSameValues =
            new RemoveMemberFromGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate("HYDRA"),
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(
            new AddMemberToGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate("HYDRA"),
            copyDescriptor)));

        // different index -> returns false
        assertFalse(standardCommand.equals(
            new RemoveMemberFromGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate("SHIELD"), DESC_HYDRA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(
            new RemoveMemberFromGroupCommand(GroupNameEqualsKeywordPredicate.generatePredicate("HYDRA"), DESC_SHIELD)));
    }
}
