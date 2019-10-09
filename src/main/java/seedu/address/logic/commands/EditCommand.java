package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.*;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the item in the shown list "
            + "by the index number used in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TASK_DESCRIPTION + "TASK DESCRIPTION] "
            + "[" + PREFIX_EVENT_DESCRIPTION + "EVENT DESCRIPTION] "
            + "[" + PREFIX_REMINDER_DESCRIPTION + "REMINDER DESCRIPTION] "
            + "[" + PREFIX_REMINDER + "REMINDER] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TASK_DESCRIPTION + "DRINK WATER "
            + PREFIX_PRIORITY + "HIGH";

    public static final String MESSAGE_EDIT_ITEM_SUCCESS = "Edited Item: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the item list.";

    private final Index index;
    private final EditItemDescriptor editItemDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editItemDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditItemDescriptor editItemDescriptor) {
        requireNonNull(index);
        requireNonNull(editItemDescriptor);

        this.index = index;
        this.editItemDescriptor = new EditItemDescriptor(editItemDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ItemList lastShownList = model.getVisualList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item oldItem = lastShownList.get(index.getZeroBased());
        Item editedItem = createEditedItem(oldItem, editItemDescriptor, lastShownList);
//-->Stopped

        model.setItem(oldItem, editedItem);
        //model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_ITEM_SUCCESS, editedItem));
    }

    private static Item createEditedItem(Item itemToEdit, EditItemDescriptor editItemDescriptor, ItemList lastShownList) {
        assert itemToEdit != null;

        Description updatedDescription = editItemDescriptor.getDescription().orElse(itemToEdit.getDescription());
        Task updatedTask = editItemDescriptor.getTask().orElse(itemToEdit.getTask());
        Event updatedEvent = editItemDescriptor.getEvent().orElse(itemToEdit.getEvent());
        Reminder updatedReminder = editItemDescriptor.getReminder().orElse(itemToEdit.getReminder());
        Set<Tag> updatedTags = editItemDescriptor.getTags().orElse(itemToEdit.getTags());
        if (lastShownList instanceof TaskList) {
            //Priority updatedPriority = editItemDescriptor.getPriority().orElse(itemToEdit.getTask().getPriority());
            updatedTask.setPriority(editItemDescriptor.getPriority().orElse(itemToEdit.getTask().getPriority()));
        } else if (lastShownList instanceof EventList) {
            //Priority updatedPriority = editItemDescriptor.getPriority().orElse(itemToEdit.getEvent().getPriority());
            updatedEvent.setPriority(editItemDescriptor.getPriority().orElse(itemToEdit.getEvent().getPriority()));
        }

        return new Item(updatedDescription, updatedTask, updatedEvent, updatedReminder, updatedTags);
    }



    public static class EditItemDescriptor {
        private Description description;
        private Task task;
        private Event event;
        private Reminder reminder;
        private Priority priority;
        private Set<Tag> tags;

        public EditItemDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditItemDescriptor(EditItemDescriptor toCopy) {
            setDescription(toCopy.description);
            setTask(toCopy.task);
            setEvent(toCopy.event);
            setReminder(toCopy.reminder);
            setPriority(toCopy.priority);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, event, reminder, priority, tags);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setTask(Task task) {
            this.task = task;
        }

        public Optional<Task> getTask() {
            return Optional.ofNullable(task);
        }

        public void setEvent(Event event) {
            this.event = event;
        }

        public Optional<Event> getEvent() {
            return Optional.ofNullable(event);
        }

        public void setReminder(Reminder reminder) {
            this.reminder = reminder;
        }

        public Optional<Reminder> getReminder() {
            return Optional.ofNullable(reminder);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
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
