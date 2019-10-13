package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEMBERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;
import seedu.address.model.member.MemberName;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

public class EditMemberCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the member identified "
            + "by the id used in the displayed member list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: ID (must be an alphanumeric string) "
            + "[" + PREFIX_MEMBER_NAME + "NAME] "
            + "[" + PREFIX_MEMBER_ID + "ID] "
            + "[" + PREFIX_MEMBER_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_MEMBER_SUCCESS = "Edited Member: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEMBER = "This member already exists in the address book.";

    private final MemberId id;
    private final EditMemberDescriptor editMemberDescriptor;

    /**
     * @param id of the member in the filtered member list to edit
     * @param editMemberDescriptor details to edit the member with
     */
    public EditMemberCommand(MemberId id, EditMemberDescriptor editMemberDescriptor) {
        requireNonNull(id);
        requireNonNull(editMemberDescriptor);

        this.id = id;
        this.editMemberDescriptor = new EditMemberDescriptor(editMemberDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMembersList();

        boolean contains = false;
        Member memberToEdit = null;

        for(int i = 0; i < lastShownList.size(); i++) {
            if(lastShownList.get(i).getId() == id) {
                contains = true;
                memberToEdit = lastShownList.get(i);
                break;
            }
        }

        if (!contains) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_ID);
        }

        Member editedMember = createEditedMember(memberToEdit, editMemberDescriptor);

        if (!memberToEdit.isSameMember(editedMember) && model.hasMember(editedMember)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEMBER);
        }

        model.setMember(memberToEdit, editedMember);
        model.updateFilteredMembersList(PREDICATE_SHOW_ALL_MEMBERS);
        return new CommandResult(String.format(MESSAGE_EDIT_MEMBER_SUCCESS, editedMember));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Member createEditedMember(Member memberToEdit, EditMemberDescriptor editMemberDescriptor) {
        assert memberToEdit != null;

        MemberName updatedName = editMemberDescriptor.getName().orElse(memberToEdit.getName());
        MemberId updatedMemberId = editMemberDescriptor.getId().orElse(memberToEdit.getId());
        Set<Tag> updatedTags = editMemberDescriptor.getTags().orElse(memberToEdit.getTags());

        return new Member(updatedName, updatedMemberId, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMemberCommand)) {
            return false;
        }

        // state check
        EditMemberCommand e = (EditMemberCommand) other;
        return id.equals(e.id)
                && editMemberDescriptor.equals(e.editMemberDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditMemberDescriptor {
        private MemberName name;
        private MemberId id;
        private Set<Tag> tags;

        public EditMemberDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMemberDescriptor(EditMemberDescriptor toCopy) {
            setName(toCopy.name);
            setId(toCopy.id);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, id, tags);
        }

        public void setId(MemberId id) {
            this.id = id;
        }

        public Optional<MemberId> getId() {
            return Optional.ofNullable(id);
        }
        public void setName(MemberName name) {
            this.name = name;
        }

        public Optional<MemberName> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMemberDescriptor)) {
                return false;
            }

            // state check
            EditMemberDescriptor e = (EditMemberDescriptor) other;

            return getName().equals(e.getName())
                    && (getId().equals(e.getId()))
                    && getTags().equals(e.getTags());
        }
    }
}
