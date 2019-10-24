package tagline.logic.commands.group;

import static java.util.Objects.requireNonNull;
import static tagline.logic.parser.group.GroupCliSyntax.PREFIX_CONTACTID;
import static tagline.model.group.GroupModel.PREDICATE_SHOW_ALL_GROUPS;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import tagline.commons.util.CollectionUtil;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.group.Group;
import tagline.model.group.GroupDescription;
import tagline.model.group.GroupName;
import tagline.model.group.MemberId;

/**
 * Edits the details of an existing group in the address book.
 */
public class AddMemberToGroupCommand extends GroupCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a contact to the group identified "
            + "by the group name and the contact ID number displayed in the contact list.\n "
            + "Parameters: GROUP_NAME (one word, cannot contain space) "
            + "[" + PREFIX_CONTACTID + "CONTACT_ID]...\n"
            + "Example: " + COMMAND_WORD + " BTS_ARMY "
            + PREFIX_CONTACTID + "47337 ";

    public static final String MESSAGE_ADD_MEMBER_SUCCESS = "Added contact to Group: %1$s";
    public static final String MESSAGE_NOT_ADDED = "At least one contactID to add must be provided.";

    //private final Group group;
    private final String groupName;
    private final EditGroupDescriptor editGroupDescriptor;

    /**
     * @param groupName of the group in the filtered group list to edit
     * @param editGroupDescriptor details to edit the group with
     */

    public AddMemberToGroupCommand(String groupName, EditGroupDescriptor editGroupDescriptor) {
        requireNonNull(groupName);
        requireNonNull(editGroupDescriptor);

        //this.index = index;
        this.groupName = groupName.trim();
        this.editGroupDescriptor = new EditGroupDescriptor(editGroupDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Group groupToEdit = findOneGroup(model, groupName);
        // adds all user-input contactIds as members of this Group checks deferred
        Group editedGroup = createEditedGroup(groupToEdit, editGroupDescriptor);

        // check to ensure Group members are ContactIds that can be found in Model
        // this Group should only have contactId of contacts found in ContactList after calling setGroup
        Group verifiedGroup = GroupCommand.verifyGroupWithModel(model, editedGroup);
        model.setGroup(groupToEdit, verifiedGroup);

        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(String.format(MESSAGE_ADD_MEMBER_SUCCESS, verifiedGroup));
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
        return groupName.equals(e.groupName)
                && editGroupDescriptor.equals(e.editGroupDescriptor);
    }

    /**
     * Stores the details to edit the group with. Each non-empty field value will replace the
     * corresponding field value of the group.
     */
    public static class EditGroupDescriptor {
        private GroupName groupName;
        private GroupDescription description;
        private Set<MemberId> memberIds;
        //private Set<Tag> tags;

        public EditGroupDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditGroupDescriptor(EditGroupDescriptor toCopy) {
            setGroupName(toCopy.groupName);
            setGroupDescription(toCopy.description);
            setMemberIds(toCopy.memberIds);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(groupName, description, memberIds);
        }

        public void setGroupName(GroupName groupName) {
            this.groupName = groupName;
        }

        public Optional<GroupName> getGroupName() {
            return Optional.ofNullable(groupName);
        }

        public void setGroupDescription(GroupDescription description) {
            this.description = description;
        }

        public Optional<GroupDescription> getGroupDescription() {
            return Optional.ofNullable(description);
        }

        /**
         * Sets {@code memberIds} to this object's {@code memberIds}.
         * A defensive copy of {@code memberIds} is used internally.
         */
        public void setMemberIds(Set<MemberId> memberIds) {
            this.memberIds = (memberIds != null) ? new HashSet<>(memberIds) : null;
        }

        /**
         * Adds {@code memberIds} to this object's {@code memberIds}.
         * A defensive copy of {@code memberIds} is used internally.
         */
        public void addMemberIds(Set<MemberId> memberIds) {
            this.memberIds = (memberIds != null) ? new HashSet<>(memberIds) : null;
        }
        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<MemberId>> getMemberIds() {
            return (memberIds != null) ? Optional.of(Collections.unmodifiableSet(memberIds)) : Optional.empty();
        }

        ///**
        // * Sets {@code tags} to this object's {@code tags}.
        // * A defensive copy of {@code tags} is used internally.
        // */
        //public void setTags(Set<Tag> tags) {
        //    this.tags = (tags != null) ? new HashSet<>(tags) : null;
        //}

        ///**
        // * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
        // * if modification is attempted.
        // * Returns {@code Optional#empty()} if {@code tags} is null.
        // */
        //public Optional<Set<Tag>> getTags() {
        //    return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        //}

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditGroupDescriptor)) {
                return false;
            }

            // state check
            EditGroupDescriptor e = (EditGroupDescriptor) other;

            return getGroupName().equals(e.getGroupName())
                    && getMemberIds().equals(e.getMemberIds());
        }
    }
}
