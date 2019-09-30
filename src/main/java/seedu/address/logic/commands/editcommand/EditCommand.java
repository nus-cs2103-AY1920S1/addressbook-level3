package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Name;

public abstract class EditCommand extends Command {

    /* Possible Fields */

    public static final String COMMAND_TYPE = "edit";

    protected Id id;

    EditCommand(Id id) {
        requireNonNull(id);
        this.id = id;
    }

    public static class EditEntityDescriptor {

        protected Name name;
        protected Id id;

        public EditEntityDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditEntityDescriptor(EditEntityDescriptor toCopy) {
            this.setName(toCopy.name);
            this.setId(toCopy.id);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.id);
        }

        /* ======== Getters ======== */

        public Optional<Name> getName() {
            return Optional.ofNullable(this.name);
        }

        public Optional<Id> getId() {
            return Optional.ofNullable(this.id);
        }

        /* ======== Setters ======== */

        public void setName(Name name) {
            this.name = name;
        }

        public void setId(Id id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEntityDescriptor)) {
                return false;
            }

            // state check
            EditEntityDescriptor e = (EditEntityDescriptor) other;
            return this.getName().equals(e.getName())
                    && this.getId().equals(e.getId());
        }

    }

}
