package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.TrackableState;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Name;

/**
 * Edits an {@link Entity} in Alfred.
 */
public abstract class EditCommand extends Command implements TrackableState {

    /* Possible Fields */

    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the specified entity "
            + "with the specified ID.\n"
            + "\tExisting values will be overwritten by the input values.\n"
            + "\tEdit command must specify the entity being edited "
            + "along with its ID and the corresponding fields you wish to edit. \n"
            + "Format: edit [entity] [entity ID] [entity fields to edit] \n"
            + "Example: edit participant P-1 n/NEW_NAME e/NEW_EMAIL";

    protected Id id;

    EditCommand(Id id) {
        requireNonNull(id);
        this.id = id;
    }

    /**
     * Stores the details to edit the entity with. Each non-empty field value will replace the
     * corresponding field value of the entity.
     */
    public static class EditEntityDescriptor {

        // Do not allow editing of ID
        protected Name name;

        public EditEntityDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditEntityDescriptor(EditEntityDescriptor toCopy) {
            this.setName(toCopy.name);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return this.name != null;
        }

        /* ======== Getters ======== */

        public Optional<Name> getName() {
            return Optional.ofNullable(this.name);
        }

        /* ======== Setters ======== */

        public void setName(Name name) {
            this.name = name;
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
            return this.getName().equals(e.getName());
        }

    }

}
