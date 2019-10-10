package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import mams.commons.util.CollectionUtil;
import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;
import mams.model.student.Address;
import mams.model.student.Email;
import mams.model.student.Name;
import mams.model.student.Phone;
import mams.model.student.Student;
import mams.model.tag.Tag;

public class AddModCommand extends Command {

    public static final String COMMAND_WORD = "addmod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to a student in MAMS"
            + "Session ID is optional"
            + "Parameters: "
            + "STUDENT_ID"
            + "MODULE_CODE"
            + "[SESSION_ID]";

    public static final String MESSAGE_EDIT_STUDENT_MODULE = "Edited Student: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Module not added(Whats the reason).";

    private final Student student;
    private final Module module;
    private final EditStudentDescriptor;




    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModule(toAdd);

    }


    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditStudentDescriptor {
        private Name name;
        private Credits credits;
        private Email email;
        private MatricId matricId;
        private Set<Tag> tags;

        public EditStudentDescriptor() {}

        public EditStudentDescriptor(EditStudentDescriptor toCopy) {
            setName(toCopy.name);
            setCredits(toCopy.credits);
            setEmail(toCopy.email);
            setAddress(toCopy.matricId);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, credits, email, matricId, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setCredits(Credits credits) {
            this.credits = credits;
        }

        public Optional<Credits> getCredits() {
            return Optional.ofNullable(credits);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setMatricId(MatricId matricId) {
            this.matricId = matricId;
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
    }



}
