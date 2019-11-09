package seedu.planner.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.commands.util.CommandUtil.findIndexOfActivity;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_ACTIVITIES;

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
import seedu.planner.model.activity.Activity;
import seedu.planner.model.activity.Duration;
import seedu.planner.model.activity.Priority;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.contact.Phone;
import seedu.planner.model.day.exceptions.EndOfTimeException;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Cost;
import seedu.planner.model.field.Name;
import seedu.planner.model.tag.Tag;

/**
 * Edits the details of an existing activity in the itinerary.
 */
public class EditActivityCommand extends EditCommand {
    public static final String SECOND_COMMAND_WORD = "activity";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            ": Edits the details of an activity identified "
                    + "by it's index number in the activity list. ",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
                    + "INDEX(must be a positive integer) "
                    + "[" + PREFIX_NAME + "NAME] "
                    + "[" + PREFIX_ADDRESS + "ADDRESS] "
                    + "[" + PREFIX_PHONE + "PHONE] "
                    + "[" + PREFIX_DURATION + "DURATION] "
                    + "[" + PREFIX_PRIORITY + "PRIORITY] "
                    + "[" + PREFIX_COST + "COST] "
                    + "[" + PREFIX_TAG + "TAG]...",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 2 "
                    + PREFIX_PHONE + "91234567 "
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "INDEX",
            new ArrayList<>(),
            new ArrayList<>(),
            Arrays.asList(PREFIX_NAME.toString(), PREFIX_ADDRESS.toString(), PREFIX_PHONE.toString(),
                    PREFIX_DURATION.toString(), PREFIX_PRIORITY.toString(), PREFIX_COST.toString()),
            Arrays.asList(PREFIX_TAG.toString())
    );

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Edited Activity: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the itinerary.";

    private final Index index;
    private final EditActivityDescriptor editActivityDescriptor;
    private final Activity activity;
    private final boolean isUndoRedo;

    /**
     * @param index of the activity in the filtered activity list to edit
     */
    public EditActivityCommand(Index index, EditActivityDescriptor editActivityDescriptor, boolean isUndoRedo) {
        requireAllNonNull(index, editActivityDescriptor);
        this.index = index;
        this.editActivityDescriptor = editActivityDescriptor;
        activity = null;
        this.isUndoRedo = isUndoRedo;
    }

    // Constructor used to undo or generate EditActivityEvent
    public EditActivityCommand(Index index, EditActivityDescriptor editActivityDescriptor, Activity activity) {
        requireAllNonNull(index, activity);
        this.index = index;
        this.editActivityDescriptor = editActivityDescriptor;
        this.activity = activity;
        this.isUndoRedo = true;
    }

    public Index getIndex() {
        return index;
    }

    public EditActivityDescriptor getEditActivityDescriptor() {
        return editActivityDescriptor;
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public String getSecondCommandWord() {
        return SECOND_COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Activity> lastShownList = model.getFilteredActivityList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Activity activityToEdit = lastShownList.get(index.getZeroBased());
        Index activityToEditIndex = findIndexOfActivity(model, activityToEdit);
        Activity editedActivity;
        editedActivity = (activity == null) ? createEditedActivity(activityToEdit, editActivityDescriptor) : activity;

        if (!activityToEdit.isSameActivity(editedActivity) && model.hasActivity(editedActivity)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }
        try {
            model.setActivity(activityToEdit, editedActivity);
        } catch (EndOfTimeException e) {
            throw new CommandException(e.toString());
        }

        if (activity == null && !isUndoRedo) {
            // Not due to undo/redo method of EditActivityEvent
            EditActivityCommand newCommand = new EditActivityCommand(index, editActivityDescriptor, activityToEdit);
            updateEventStack(newCommand, model);
        }
        model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
        Index editedActivityIndex = findIndexOfActivity(model, editedActivity);

        return new CommandResult(
            String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity),
            new ResultInformation[] {
                new ResultInformation(
                    activityToEdit,
                    activityToEditIndex,
                    "Edited Activity from:"
                ),
                new ResultInformation(
                    editedActivity,
                    editedActivityIndex,
                    "To:"
                )
            },
            new UiFocus[] { UiFocus.ACTIVITY, UiFocus.INFO }
        );
    }

    /**
     * Creates and returns a {@code Activity} with the details of {@code activityToEdit}
     * edited with {@code editActivityDescriptor}.
     */
    private static Activity createEditedActivity(Activity activityToEdit,
                                                 EditActivityDescriptor editActivityDescriptor) {
        assert activityToEdit != null;

        Name updatedName = editActivityDescriptor.getName().orElse(activityToEdit.getName());
        Address updatedAddress = editActivityDescriptor.getAddress().orElse(activityToEdit.getAddress());
        Contact updatedContact = editActivityDescriptor.getPhone().isPresent()
                ? new Contact(updatedName, editActivityDescriptor.getPhone().get(),
                null, updatedAddress, new HashSet<>())
                : activityToEdit.getContact().isPresent()
                ? new Contact(updatedName, activityToEdit.getContact().get().getPhone(),
                activityToEdit.getContact().get().getEmail().orElse(null), updatedAddress, new HashSet<>())
                : null;

        Cost updatedCost = editActivityDescriptor.getCost().isPresent()
                ? editActivityDescriptor.getCost().get()
                : null;

        Set<Tag> updatedTags = editActivityDescriptor.getTags().orElse(activityToEdit.getTags());
        Duration updatedDuration = editActivityDescriptor.getDuration().orElse(activityToEdit.getDuration());
        Priority updatedPriority = editActivityDescriptor.getPriority().orElse(activityToEdit.getPriority());

        return new Activity(updatedName, updatedAddress, updatedContact, updatedCost, updatedTags, updatedDuration,
                updatedPriority);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditActivityCommand)) {
            return false;
        }

        // state check
        EditActivityCommand e = (EditActivityCommand) other;
        return other == this
                || other instanceof EditActivityCommand
                && index.equals(e.index)
                && editActivityDescriptor.equals(e.editActivityDescriptor);
    }

    /**
     * Stores the details to edit the activity with. Each non-empty field value will replace the
     * corresponding field value of the activity.
     */
    public static class EditActivityDescriptor {
        private Name name;
        private Address address;
        private Phone phone;
        private Cost cost;
        private Set<Tag> tags;
        private Duration duration;
        private Priority priority;

        public EditActivityDescriptor() {
        }

        public EditActivityDescriptor(EditActivityDescriptor toCopy) {
            setName(toCopy.name);
            setAddress(toCopy.address);
            setPhone(toCopy.phone);
            setCost(toCopy.cost);
            setTags(toCopy.tags);
            setDuration(toCopy.duration);
            setPriority(toCopy.priority);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, address, phone, tags, cost, duration, priority);
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

        public void setCost(Cost cost) {
            this.cost = cost;
        }

        public Optional<Cost> getCost() {
            return Optional.ofNullable(cost);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public Optional<Duration> getDuration() {
            return Optional.ofNullable(duration);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditActivityCommand.EditActivityDescriptor)) {
                return false;
            }

            // state check
            EditActivityCommand.EditActivityDescriptor e = (EditActivityCommand.EditActivityDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getAddress().equals(e.getAddress())
                    && getCost().equals(e.getCost())
                    && getTags().equals(e.getTags())
                    && getDuration().equals(e.getDuration())
                    && getPriority().equals(e.getPriority());
        }
    }
}

