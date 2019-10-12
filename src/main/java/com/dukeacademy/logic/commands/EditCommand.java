package com.dukeacademy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.dukeacademy.commons.core.Messages;
import com.dukeacademy.commons.core.index.Index;
import com.dukeacademy.commons.util.CollectionUtil;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.parser.CliSyntax;
import com.dukeacademy.model.Model;
import com.dukeacademy.model.question.Address;
import com.dukeacademy.model.question.Email;
import com.dukeacademy.model.question.Phone;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.Title;
import com.dukeacademy.model.tag.Tag;

/**
 * Edits the details of an existing question in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the question identified "
            + "by the index number used in the displayed question list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_NAME + "NAME] "
            + "[" + CliSyntax.PREFIX_PHONE + "PHONE] "
            + "[" + CliSyntax.PREFIX_EMAIL + "EMAIL] "
            + "[" + CliSyntax.PREFIX_ADDRESS + "ADDRESS] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_PHONE + "91234567 "
            + CliSyntax.PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_QUESTION_SUCCESS = "Edited Question: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_QUESTION = "This question already exists in the address book.";

    private final Index index;
    private final EditQuestionDescriptor editQuestionDescriptor;

    /**
     * @param index of the question in the filtered question list to edit
     * @param editQuestionDescriptor details to edit the question with
     */
    public EditCommand(Index index, EditQuestionDescriptor editQuestionDescriptor) {
        requireNonNull(index);
        requireNonNull(editQuestionDescriptor);

        this.index = index;
        this.editQuestionDescriptor = new EditQuestionDescriptor(editQuestionDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Question> lastShownList = model.getFilteredQuestionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
        }

        Question questionToEdit = lastShownList.get(index.getZeroBased());
        Question editedQuestion = createEditedQuestion(questionToEdit, editQuestionDescriptor);

        if (!questionToEdit.isSameQuestion(editedQuestion) && model.hasQuestion(
            editedQuestion)) {
            throw new CommandException(MESSAGE_DUPLICATE_QUESTION);
        }

        model.setQuestion(questionToEdit, editedQuestion);
        model.updateFilteredQuestionList(Model.PREDICATE_SHOW_ALL_QUESTIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_QUESTION_SUCCESS,
            editedQuestion));
    }

    /**
     * Creates and returns a {@code Question} with the details of {@code questionToEdit}
     * edited with {@code editQuestionDescriptor}.
     */
    private static Question createEditedQuestion(Question questionToEdit,
                                                 EditQuestionDescriptor editQuestionDescriptor) {
        assert questionToEdit != null;

        Title updatedTitle = editQuestionDescriptor.getTitle().orElse(questionToEdit.getTitle());
        Phone updatedPhone = editQuestionDescriptor.getPhone().orElse(
            questionToEdit.getPhone());
        Email updatedEmail = editQuestionDescriptor.getEmail().orElse(
            questionToEdit.getEmail());
        Address updatedAddress = editQuestionDescriptor.getAddress().orElse(
            questionToEdit.getAddress());
        Set<Tag> updatedTags = editQuestionDescriptor.getTags().orElse(
            questionToEdit.getTags());

        return new Question(updatedTitle, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editQuestionDescriptor.equals(e.editQuestionDescriptor);
    }

    /**
     * Stores the details to edit the question with. Each non-empty field value will replace the
     * corresponding field value of the question.
     */
    public static class EditQuestionDescriptor {
        private Title title;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditQuestionDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditQuestionDescriptor(EditQuestionDescriptor toCopy) {
            setTitle(toCopy.title);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, phone, email, address, tags);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
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
            if (!(other instanceof EditQuestionDescriptor)) {
                return false;
            }

            // state check
            EditQuestionDescriptor e = (EditQuestionDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
