//@@author e0031374
package tagline.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tagline.logic.parser.group.GroupCliSyntax.PREFIX_CONTACTID;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.group.Group;
import tagline.model.group.GroupDescription;
import tagline.model.group.GroupName;
import tagline.model.group.GroupNameEqualsKeywordPredicate;
import tagline.model.group.MemberId;

/**
 * Edits the details of an existing group in the address book.
 */
public class AddMemberToGroupCommand extends EditGroupCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_KEY + " " + COMMAND_WORD
            + ": Add a contact to the group identified "
            + "by the group name and the contact ID number displayed in the contact list.\n "
            + "Parameters: GROUP_NAME (one word, cannot contain space) "
            + "[" + PREFIX_CONTACTID + " CONTACT_ID]...\n"
            + "Example: " + COMMAND_KEY + " " + COMMAND_WORD + " BTS_ARMY "
            + PREFIX_CONTACTID + " 47337 ";

    public static final String MESSAGE_ADD_MEMBER_SUCCESS = "Attempting to add contact(s) to group.";
    public static final String MESSAGE_NOT_ADDED = "At least one contactID to add must be provided.";

    //private final Group group;
    private final GroupNameEqualsKeywordPredicate predicate;
    private final EditGroupDescriptor editGroupDescriptor;

    /**
     * @param predicate of the group in the filtered group list to edit
     * @param editGroupDescriptor details to edit the group with
     */

    public AddMemberToGroupCommand(GroupNameEqualsKeywordPredicate predicate, EditGroupDescriptor editGroupDescriptor) {
        requireNonNull(predicate);
        requireNonNull(editGroupDescriptor);

        //this.index = index;
        this.predicate = predicate;
        this.editGroupDescriptor = new EditGroupDescriptor(editGroupDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Group groupToEdit = findOneGroup(model, predicate);

        Optional<Set<MemberId>> optMembers = editGroupDescriptor.getMemberIds();
        //assert optMembers.isPresent();

        Set<MemberId> notFound = new HashSet<>();
        if (optMembers.isPresent()) {
            notFound = GroupCommand.memberIdDoesntExistInContactModel(model, optMembers.get());
        }

        // adds all user-input contactIds as members of this Group checks deferred
        Group editedGroup = createEditedGroup(groupToEdit, editGroupDescriptor);

        // check to ensure Group members are ContactIds that can be found in Model
        // this Group should only have contactId of contacts found in ContactList after calling setGroup
        Group verifiedGroup = GroupCommand.verifyGroupWithModel(model, editedGroup);
        model.setGroup(groupToEdit, verifiedGroup);

        model.updateFilteredContactList(GroupCommand.groupToContactIdPredicate(verifiedGroup));
        model.updateFilteredGroupList(GroupNameEqualsKeywordPredicate.generatePredicate(verifiedGroup));

        return new CommandResult(MESSAGE_ADD_MEMBER_SUCCESS + GroupCommand.notFoundString(notFound),
                ViewType.GROUP_SINGLE);
    }

    /**
     * Creates and returns a {@code Group} with the details of {@code groupToEdit}
     * edited with {@code editGroupDescriptor}.
     */
    private static Group createEditedGroup(Group groupToEdit, EditGroupDescriptor editGroupDescriptor) {
        assert groupToEdit != null;

        GroupName updatedGroupName = editGroupDescriptor.getGroupName().orElse(groupToEdit.getGroupName());
        GroupDescription updatedGroupDescription = editGroupDescriptor.getGroupDescription()
            .orElse(groupToEdit.getGroupDescription());
        Set<MemberId> updatedMemberIds = new HashSet<>();
        if (editGroupDescriptor.getMemberIds().isPresent()) {
            updatedMemberIds.addAll(editGroupDescriptor.getMemberIds().get());
        }
        updatedMemberIds.addAll(groupToEdit.getMemberIds());

        return new Group(updatedGroupName, updatedGroupDescription, updatedMemberIds);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddMemberToGroupCommand)) {
            return false;
        }

        // state check
        AddMemberToGroupCommand e = (AddMemberToGroupCommand) other;
        return predicate.equals(e.predicate)
                && editGroupDescriptor.equals(e.editGroupDescriptor);
    }

}
