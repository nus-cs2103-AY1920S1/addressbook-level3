package seedu.deliverymans.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.deliverymans.model.Model.PREDICATE_SHOW_ALL_DELIVERYMEN;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.commons.core.index.Index;
import seedu.deliverymans.commons.util.CollectionUtil;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.deliveryman.Deliveryman;

/**
 * Edits the details of an existing deliveryman in the deliverymen database.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the deliveryman identified "
            + "by the index number used in the displayed deliveryman list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_DELIVERYMAN_SUCCESS = "Edited Deliveryman: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_DELIVERYMEN = "This deliveryman already exists in the address book.";

    private final Index index;
    private final EditDeliverymanDescriptor editDeliverymanDescriptor;

    /**
     * @param index of the deliveryman in the filtered deliverymen list to edit
     * @param editDeliverymanDescriptor details to edit the deliveryman with
     */
    public EditCommand(Index index, EditDeliverymanDescriptor editDeliverymanDescriptor) {
        requireNonNull(index);
        requireNonNull(editDeliverymanDescriptor);

        this.index = index;
        this.editDeliverymanDescriptor = new EditDeliverymanDescriptor(editDeliverymanDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Deliveryman> lastShownList = model.getFilteredDeliverymenList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
        }

        Deliveryman deliverymanToEdit = lastShownList.get(index.getZeroBased());
        Deliveryman editedDeliveryman = createEditedDeliveryman(deliverymanToEdit, editDeliverymanDescriptor);

        if (!deliverymanToEdit.isSameDeliveryman(editedDeliveryman) && model.hasDeliveryman(editedDeliveryman)) {
            throw new CommandException(MESSAGE_DUPLICATE_DELIVERYMEN);
        }

        model.setDeliveryman(deliverymanToEdit, editedDeliveryman);
        model.updateFilteredDeliverymenList(PREDICATE_SHOW_ALL_DELIVERYMEN);
        return new CommandResult(String.format(MESSAGE_EDIT_DELIVERYMAN_SUCCESS, editedDeliveryman));
    }

    /**
     * Creates and returns a {@code Deliveryman} with the details of {@code deliverymanToEdit}
     * edited with {@code editDeliverymanDescriptor}.
     */
    private static Deliveryman createEditedDeliveryman(Deliveryman deliverymanToEdit,
                                                  EditDeliverymanDescriptor editDeliverymanDescriptor) {
        assert deliverymanToEdit != null;

        Name updatedName = editDeliverymanDescriptor.getName().orElse(deliverymanToEdit.getName());
        Phone updatedPhone = editDeliverymanDescriptor.getPhone().orElse(deliverymanToEdit.getPhone());
        Set<Tag> updatedTags = editDeliverymanDescriptor.getTags().orElse(deliverymanToEdit.getTags());

        return new Deliveryman(updatedName, updatedPhone, updatedTags);
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
                && editDeliverymanDescriptor.equals(e.editDeliverymanDescriptor);
    }

    /**
     * Stores the details to edit the deliveryman with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditDeliverymanDescriptor {
        private Name name;
        private Phone phone;
        private Set<Tag> tags;

        public EditDeliverymanDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDeliverymanDescriptor(EditDeliverymanDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
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
            if (!(other instanceof EditDeliverymanDescriptor)) {
                return false;
            }

            // state check
            EditDeliverymanDescriptor e = (EditDeliverymanDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getTags().equals(e.getTags());

        }
    }
}
