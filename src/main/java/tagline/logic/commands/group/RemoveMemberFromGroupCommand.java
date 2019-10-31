//@@author e0031374
package tagline.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tagline.logic.parser.group.GroupCliSyntax.PREFIX_CONTACTID;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
public class RemoveMemberFromGroupCommand extends EditGroupCommand {

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_KEY + " " + COMMAND_WORD
            + ": Remove a contact to the group identified "
            + "by the group name and the contact ID number displayed in the contact list.\n "
            + "Parameters: GROUP_NAME (one word, cannot contain space) "
            + "[" + PREFIX_CONTACTID + " CONTACT_ID]...\n"
            + "Example: " + COMMAND_KEY + " " + COMMAND_WORD + " BTS_ARMY "
            + PREFIX_CONTACTID + " 47337 ";

    public static final String MESSAGE_REMOVE_MEMBER_SUCCESS = "Attempting to remove contact(s) from group.";
    public static final String MESSAGE_NOT_REMOVED = "At least one contactID to be removed must be provided.";

    //private final Group group;
    private final GroupNameEqualsKeywordPredicate predicate;
    private final EditGroupDescriptor editGroupDescriptor;

    /**
     * @param predicate of the group in the filtered group list to edit
     * @param editGroupDescriptor details to edit the group with
     */

    public RemoveMemberFromGroupCommand(GroupNameEqualsKeywordPredicate predicate,
        EditGroupDescriptor editGroupDescriptor) {

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

        Set<MemberId> membersNotFound = new HashSet<>();
        if (optMembers.isPresent()) {
            membersNotFound = GroupCommand.memberIdDoesntExistInContactModel(model, optMembers.get());
        }

        // removes all user-input contactIds as members of this Group checks deferred
        Group editedGroup = createRemovedMemberGroup(groupToEdit, editGroupDescriptor);

        // check to ensure Group members are ContactIds that can be found in Model
        // this Group should only have contactId of contacts found in ContactList after calling setGroup
        Group verifiedGroup = GroupCommand.verifyGroupWithModel(model, editedGroup);

        model.setGroup(groupToEdit, verifiedGroup);

        model.updateFilteredContactList(GroupCommand.groupToContactIdPredicate(verifiedGroup));
        model.updateFilteredGroupList(GroupNameEqualsKeywordPredicate.generatePredicate(verifiedGroup));

        return new CommandResult(MESSAGE_REMOVE_MEMBER_SUCCESS
                + GroupCommand.notFoundString(membersNotFound), ViewType.GROUP_PROFILE);
    }

    /**
     * Creates and returns a {@code Group} with the details of {@code groupToEdit}
     * edited with {@code editGroupDescriptor}.
     */
    private static Group createRemovedMemberGroup(Group groupToEdit, EditGroupDescriptor editGroupDescriptor) {
        assert groupToEdit != null;

        GroupName updatedGroupName = editGroupDescriptor.getGroupName().orElse(groupToEdit.getGroupName());
        GroupDescription updatedGroupDescription = editGroupDescriptor.getGroupDescription()
            .orElse(groupToEdit.getGroupDescription());
        Set<MemberId> updatedMemberIds = new HashSet<>();
        if (editGroupDescriptor.getMemberIds().isPresent()) {
            updatedMemberIds.addAll(groupToEdit.getMemberIds().stream()
                .filter(member -> !editGroupDescriptor.getMemberIds().get().contains(member))
                .collect(Collectors.toSet())
            );
        } else {
            // if no memberIds found in editGroupDescriptor, do not remove any groups
            updatedMemberIds.addAll(groupToEdit.getMemberIds());
        }

        return new Group(updatedGroupName, updatedGroupDescription, updatedMemberIds);
    }

    /**
     * Checks and returns a set of {@code MemberId} which cannot be found as members of {@code Group}
     */
    private static Set<MemberId> notFound(Group group, Collection<MemberId> toRemove) {
        return toRemove.stream()
            .filter(target -> !group.getMemberIds().stream()
                .map(members -> members.value)
                .anyMatch(members -> members.equalsIgnoreCase(target.value)))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveMemberFromGroupCommand)) {
            return false;
        }

        // state check
        RemoveMemberFromGroupCommand e = (RemoveMemberFromGroupCommand) other;
        return predicate.equals(e.predicate)
                && editGroupDescriptor.equals(e.editGroupDescriptor);
    }
}
