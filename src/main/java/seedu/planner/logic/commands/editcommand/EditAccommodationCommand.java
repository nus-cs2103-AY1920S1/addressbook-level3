package seedu.planner.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.commands.util.CommandUtil.findIndexOfAccommodation;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_ACCOMMODATIONS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.commons.util.CollectionUtil;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.contact.Phone;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Name;
import seedu.planner.model.tag.Tag;

//@@author KxxMxxx
/**
 * Edits the details of an existing accommodation in the itinerary.
 */
public class EditAccommodationCommand extends EditCommand {
    public static final String SECOND_COMMAND_WORD = "accommodation";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Edit the accommodation's details identified "
                    + "by it's index in accommodation list. ",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
                    + "INDEX(a positive integer) "
                    + "[" + PREFIX_NAME + "NAME] "
                    + "[" + PREFIX_ADDRESS + "ADDRESS] "
                    + "[" + PREFIX_PHONE + "PHONE] "
                    + "[" + PREFIX_TAG + "TAG]...",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 1 "
                    + PREFIX_NAME + "Tom "
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "<INDEX>",
            new ArrayList<>(),
            new ArrayList<>(),
            Arrays.asList(PREFIX_NAME.toString(), PREFIX_ADDRESS.toString(), PREFIX_PHONE.toString()),
            Arrays.asList(PREFIX_TAG.toString())
    );


    public static final String MESSAGE_EDIT_ACCOMMODATION_SUCCESS = "Edited Accommodation: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ACCOMMODATION = "This accommodation already exists in the itinerary.";

    private final Index index;
    private final EditAccommodationDescriptor editAccommodationDescriptor;
    private final Accommodation accommodation;
    private final boolean isUndoRedo;

    /**
     * @param index of the accommodation in the filtered accommodation list to edit
     */
    public EditAccommodationCommand(Index index, EditAccommodationDescriptor editAccommodationDescriptor,
                                    boolean isUndoRedo) {
        requireAllNonNull(index, editAccommodationDescriptor, isUndoRedo);
        this.index = index;
        this.editAccommodationDescriptor = editAccommodationDescriptor;
        accommodation = null;
        this.isUndoRedo = isUndoRedo;
    }

    // Constructor used to undo or generate EditAccommodationEvent
    public EditAccommodationCommand(Index index, EditAccommodationDescriptor editAccommodationDescriptor,
                                    Accommodation accommodation) {
        requireAllNonNull(index, accommodation);
        this.index = index;
        this.accommodation = accommodation;
        this.editAccommodationDescriptor = editAccommodationDescriptor;
        this.isUndoRedo = true;
    }

    public Index getIndex() {
        return index;
    }

    public EditAccommodationDescriptor getEditAccommodationDescriptor() {
        return editAccommodationDescriptor;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    @Override
    public String getSecondCommandWord() {
        return SECOND_COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Accommodation> lastShownList = model.getFilteredAccommodationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACCOMMODATION_DISPLAYED_INDEX);
        }

        Accommodation accommodationToEdit = lastShownList.get(index.getZeroBased());
        Index accommodationToEditIndex = findIndexOfAccommodation(model, accommodationToEdit);
        Accommodation editedAccommodation;
        editedAccommodation = (accommodation == null) ? createEditedAccommodation(accommodationToEdit,
                editAccommodationDescriptor) : accommodation;

        if (!accommodationToEdit.isSameAccommodation(editedAccommodation)
                && model.hasAccommodation(editedAccommodation)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOMMODATION);
        }

        if (accommodation == null && !isUndoRedo) {
            //Not due to undo/redo method of EditAccommodationEvent
            EditAccommodationCommand newCommand = new EditAccommodationCommand(index, editAccommodationDescriptor,
                    accommodationToEdit);
            updateEventStack(newCommand, model);
        }
        model.setAccommodation(accommodationToEdit, editedAccommodation);
        model.updateFilteredAccommodationList(PREDICATE_SHOW_ALL_ACCOMMODATIONS);
        Index editedAccommodationIndex = findIndexOfAccommodation(model, editedAccommodation);

        return new CommandResult(
            String.format(MESSAGE_EDIT_ACCOMMODATION_SUCCESS, editedAccommodation),
            new ResultInformation[]{
                new ResultInformation(
                    accommodationToEdit,
                    accommodationToEditIndex,
                    "Edited Accommodation from:"
                ),
                new ResultInformation(
                    editedAccommodation,
                    editedAccommodationIndex,
                    "To:"
                )
            },
            new UiFocus[] { UiFocus.ACCOMMODATION, UiFocus.INFO });
    }

    /**
     * Creates and returns a {@code Accommodation} with the details of {@code accommodationToEdit}
     * edited with {@code editAccommodationDescriptor}.
     */
    private static Accommodation createEditedAccommodation(Accommodation accommodationToEdit,
                                                           EditAccommodationDescriptor editAccommodationDescriptor) {
        assert accommodationToEdit != null;

        Name updatedName = editAccommodationDescriptor.getName().orElse(accommodationToEdit.getName());
        Address updatedAddress = editAccommodationDescriptor.getAddress().orElse(accommodationToEdit.getAddress());
        Contact updatedContact = editAccommodationDescriptor.getPhone().isPresent()
                ? new Contact(updatedName, editAccommodationDescriptor.getPhone().get(),
                null, updatedAddress, new HashSet<>())
                : accommodationToEdit.getContact().isPresent()
                ? new Contact(updatedName, accommodationToEdit.getContact().get().getPhone(),
                accommodationToEdit.getContact().get().getEmail().orElse(null), updatedAddress, new HashSet<>())
                : null;

        Set<Tag> updatedTags = editAccommodationDescriptor.getTags().orElse(accommodationToEdit.getTags());

        return new Accommodation(updatedName, updatedAddress, updatedContact, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditAccommodationCommand)) {
            return false;
        }

        // state check
        EditAccommodationCommand e = (EditAccommodationCommand) other;
        return other == this
                || other instanceof EditAccommodationCommand
                && index.equals(e.index)
                && editAccommodationDescriptor.equals(e.editAccommodationDescriptor);
    }

    /**
     * Stores the details to edit the accommodation with. Each non-empty field value will replace the
     * corresponding field value of the accommodation.
     */
    public static class EditAccommodationDescriptor {
        private Name name;
        private Address address;
        private Phone phone;
        private Set<Tag> tags;

        public EditAccommodationDescriptor() {}

        public EditAccommodationDescriptor(EditAccommodationDescriptor toCopy) {
            setName(toCopy.name);
            setAddress(toCopy.address);
            setPhone(toCopy.phone);
            setTags(toCopy.tags);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, address, phone, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

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
            if (!(other instanceof EditAccommodationDescriptor)) {
                return false;
            }

            // state check
            EditAccommodationDescriptor e = (EditAccommodationDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
