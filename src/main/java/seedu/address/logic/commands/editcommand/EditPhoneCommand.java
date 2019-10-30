package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PHONES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.phone.Brand;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Colour;
import seedu.address.model.phone.Cost;
import seedu.address.model.phone.IdentityNumber;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.PhoneName;
import seedu.address.model.phone.SerialNumber;
import seedu.address.model.tag.Tag;

//import com.sun.scenario.effect.Identity;

/**
 * Edits the details of an existing phone in SML.
 */
public class EditPhoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit-p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the phone identified "
            + "by the index number used in the displayed phone list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_IDENTITY_NUM + "IMEI] "
            + "[" + PREFIX_SERIAL_NUM + "SERIAL NUMBER] "
            + "[" + PREFIX_PHONE_NAME + "NAME] "
            + "[" + PREFIX_BRAND + "BRAND] "
            + "[" + PREFIX_CAPACITY + "CAPACITY] "
            + "[" + PREFIX_COLOUR + "COLOUR] "
            + "[" + PREFIX_COST + "COST] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE_NAME + "iPhone 7 "
            + PREFIX_COST + "$200";

    public static final String MESSAGE_EDIT_PHONE_SUCCESS = "Edited Phone: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PHONE = "This phone already exists in the seller manager.";

    private final Index index;
    private final EditPhoneDescriptor editPhoneDescriptor;

    /**
     * @param index of the phone in the filtered phone list to edit
     * @param editPhoneDescriptor details to edit the phone with
     */
    public EditPhoneCommand(Index index, EditPhoneDescriptor editPhoneDescriptor) {
        requireNonNull(index);
        requireNonNull(editPhoneDescriptor);

        this.index = index;
        this.editPhoneDescriptor = new EditPhoneDescriptor(editPhoneDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand(Model model, CommandHistory commandHistory,
                                                UndoRedoStack undoRedoStack) throws CommandException {
        requireNonNull(model);
        List<Phone> lastShownList = model.getFilteredPhoneList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PHONE_DISPLAYED_INDEX);
        }

        Phone phoneToEdit = lastShownList.get(index.getZeroBased());
        Phone editedPhone = createEditedPhone(phoneToEdit, editPhoneDescriptor);

        if (!phoneToEdit.isSameAs(editedPhone) && model.hasPhone(editedPhone)) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }

        model.setPhone(phoneToEdit, editedPhone);
        model.updateFilteredPhoneList(PREDICATE_SHOW_ALL_PHONES);
        return new CommandResult(String.format(MESSAGE_EDIT_PHONE_SUCCESS, editedPhone), UiChange.PHONE);
    }

    /**
     * Creates and returns a {@code Phone} with the details of {@code PhoneToEdit}
     * edited with {@code editPhoneDescriptor}.
     */
    private static Phone createEditedPhone(Phone phoneToEdit,
                                                 EditPhoneDescriptor editPhoneDescriptor) {
        assert phoneToEdit != null;
        IdentityNumber updatedIdentityNumber =
                editPhoneDescriptor.getIdentityNumber().orElse(phoneToEdit.getIdentityNumber());
        SerialNumber updatedSerialNumber =
                editPhoneDescriptor.getSerialNumber().orElse(phoneToEdit.getSerialNumber());
        PhoneName updatedName = editPhoneDescriptor.getPhoneName().orElse(phoneToEdit.getPhoneName());
        Brand updatedBrand = editPhoneDescriptor.getBrand().orElse(phoneToEdit.getBrand());
        Capacity updatedCapacity = editPhoneDescriptor.getCapacity().orElse(phoneToEdit.getCapacity());
        Colour updatedColour = editPhoneDescriptor.getColour().orElse(phoneToEdit.getColour());
        Cost updatedCost = editPhoneDescriptor.getCost().orElse(phoneToEdit.getCost());
        Set<Tag> updatedTags = editPhoneDescriptor.getTags().orElse(phoneToEdit.getTags());

        return new Phone(updatedIdentityNumber, updatedSerialNumber,
                updatedName, updatedBrand, updatedCapacity, updatedColour, updatedCost, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPhoneCommand)) {
            return false;
        }

        // state check
        EditPhoneCommand e = (EditPhoneCommand) other;
        return index.equals(e.index)
                && editPhoneDescriptor.equals(e.editPhoneDescriptor);
    }

    /**
     * Stores the details to edit the phone with. Each non-empty field value will replace the
     * corresponding field value of the phone.
     */
    public static class EditPhoneDescriptor {
        private IdentityNumber identityNumber;
        private SerialNumber serialNumber;
        private PhoneName phoneName;
        private Brand brand;
        private Capacity capacity;
        private Colour colour;
        private Cost cost;
        private Set<Tag> tags;


        public EditPhoneDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPhoneDescriptor(EditPhoneDescriptor toCopy) {
            setIdentityNumber(toCopy.identityNumber);
            setSerialNumber(toCopy.serialNumber);
            setPhoneName(toCopy.phoneName);
            setBrand(toCopy.brand);
            setCapacity(toCopy.capacity);
            setColour(toCopy.colour);
            setCost(toCopy.cost);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(serialNumber, identityNumber, phoneName,
                    brand, capacity, colour, cost, tags);
        }

        public Optional<IdentityNumber> getIdentityNumber() {
            return Optional.ofNullable(identityNumber);
        }

        public void setIdentityNumber(IdentityNumber identityNumber) {
            this.identityNumber = identityNumber;
        }

        public Optional<SerialNumber> getSerialNumber() {
            return Optional.ofNullable(serialNumber);
        }

        public void setSerialNumber(SerialNumber serialNumber) {
            this.serialNumber = serialNumber;
        }

        public Optional<PhoneName> getPhoneName() {
            return Optional.ofNullable(phoneName);
        }

        public void setPhoneName(PhoneName phoneName) {
            this.phoneName = phoneName;
        }

        public Optional<Brand> getBrand() {
            return Optional.ofNullable(brand);
        }

        public void setBrand(Brand brand) {
            this.brand = brand;
        }

        public Optional<Capacity> getCapacity() {
            return Optional.ofNullable(capacity);
        }

        public void setCapacity(Capacity capacity) {
            this.capacity = capacity;
        }

        public Optional<Colour> getColour() {
            return Optional.ofNullable(colour);
        }

        public void setColour(Colour colour) {
            this.colour = colour;
        }

        public Optional<Cost> getCost() {
            return Optional.ofNullable(cost);
        }

        public void setCost(Cost cost) {
            this.cost = cost;
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
            if (!(other instanceof EditPhoneDescriptor)) {
                return false;
            }

            // state check
            EditPhoneDescriptor e = (EditPhoneDescriptor) other;

            return getIdentityNumber().equals(e.getIdentityNumber())
                    && getSerialNumber().equals(e.getSerialNumber())
                    && getPhoneName().equals(e.getPhoneName())
                    && getBrand().equals(e.getBrand())
                    && getCapacity().equals(e.getCapacity())
                    && getColour().equals(e.getColour())
                    && getCost().equals(e.getCost())
                    && getTags().equals(e.getTags());
        }
    }
}
