package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.item.Event;
import seedu.address.commons.core.item.Item;
import seedu.address.commons.core.item.Item.ItemBuilder;
import seedu.address.commons.core.item.ItemDescription;
import seedu.address.commons.core.item.Priority;
import seedu.address.commons.core.item.Reminder;
import seedu.address.commons.core.item.Task;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;
import seedu.address.model.item.EventList;
import seedu.address.model.item.TaskList;
import seedu.address.model.item.VisualizeList;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing item in the item list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the item in the shown list "
            + "by the index number used in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + " DESCRIPTION] "
            + "[" + PREFIX_REMINDER + "REMINDER] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "DRINK WATER "
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
    public CommandResult execute(ItemModel model) throws CommandException {
        requireNonNull(model);
        VisualizeList lastShownList = model.getVisualList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item oldItem = lastShownList.get(index.getZeroBased());
        Item editedItem = createEditedItem(oldItem, editItemDescriptor, lastShownList);

        model.replaceItem(oldItem, editedItem);
        return new CommandResult(String.format(MESSAGE_EDIT_ITEM_SUCCESS, editedItem));
    }

    /**
     * Create a new Item with the new edited details.
     * @param itemToEdit old item to edit
     * @param editItemDescriptor details to edit the item with
     * @param lastShownList the last list shown to the user, set by the model
     * @return a new Item with the edited details
     * @throws CommandException representing failure to create new edited item
     */
    private static Item createEditedItem(Item itemToEdit, EditItemDescriptor editItemDescriptor,
                                         VisualizeList lastShownList) throws CommandException {
        assert itemToEdit != null;

        ItemDescription updatedDescription = editItemDescriptor
                .getDescription()
                .orElse(itemToEdit.getItemDescription());
        Optional<Task> updatedTask = Optional.ofNullable(editItemDescriptor
                .getTask()
                .orElse(itemToEdit
                        .getTask()
                        .orElse(null)));
        Optional<Event> updatedEvent = Optional.ofNullable(editItemDescriptor
                .getEvent()
                .orElse(itemToEdit
                        .getEvent()
                        .orElse(null)));
        Optional<Reminder> updatedReminder = Optional.ofNullable(editItemDescriptor
                .getReminder()
                .orElse(itemToEdit
                        .getReminder()
                        .orElse(null)));
        Set<Tag> updatedTags = editItemDescriptor.getTags().orElse(itemToEdit.getTags());

        if (lastShownList instanceof TaskList) {
            // Change the Priority of this Task. If no priority is given, use the priority of the old item.
            updatedTask = Optional.of(updatedTask.get()
                    .changePriority(editItemDescriptor
                            .getPriority()
                            .orElse(itemToEdit.getTask().get().getPriority())));
        } else if (lastShownList instanceof EventList) {
            // Change the Priority of this Event. If no priority is given, use the priority of the old item.
            updatedEvent = Optional.of(updatedEvent.get()
                    .changePriority(editItemDescriptor
                            .getPriority()
                            .orElse(itemToEdit.getEvent().get().getPriority())));
        }

        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.setItemDescription(updatedDescription);
        itemBuilder.setTags(updatedTags);

        if (updatedTask.isPresent()) {
            itemBuilder.setTask(updatedTask.get());
        }
        if (updatedEvent.isPresent()) {
            itemBuilder.setEvent(updatedEvent.get());
        }
        if (updatedReminder.isPresent()) {
            itemBuilder.setReminder(updatedReminder.get());
        }
        System.out.println("Before checking boolean, hasDeleteTask=" + editItemDescriptor.hasDeleteTask);
        if (editItemDescriptor.hasDeleteTask) {
            System.out.println("entered delete task");
            itemBuilder.setTask(null);
        }
        if (editItemDescriptor.hasDeleteEvent) {
            itemBuilder.setEvent(null);
        }
        if (editItemDescriptor.hasDeleteReminder) {
            itemBuilder.setReminder(null);
        }

        Item updatedItem;
        try {
            updatedItem = itemBuilder.build();
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }

        return updatedItem;
    }

    /**
     * Create a new EditItemDescriptor that edits the details of an item.
     */
    public static class EditItemDescriptor {
        private ItemDescription description;
        private Task task;
        private Event event;
        private Reminder reminder;
        private Priority priority;
        private Set<Tag> tags;
        public boolean hasDeleteTask = false;
        public boolean hasDeleteEvent = false;
        public boolean hasDeleteReminder = false;

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
            setHasDeleteTask(toCopy.hasDeleteTask);
            setHasDeleteEvent(toCopy.hasDeleteEvent);
            setHasDeleteReminder(toCopy.hasDeleteReminder);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, task, event, reminder, priority, tags);
        }

        public void setDescription(ItemDescription description) {
            this.description = description;
        }

        public Optional<ItemDescription> getDescription() {
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

        public boolean hasAnyDelete() {
            return (hasDeleteEvent || hasDeleteReminder || hasDeleteTask);
        }

        public void setHasDeleteTask(boolean bool) {
            this.hasDeleteTask = bool;
        }

        public void setHasDeleteEvent(boolean bool) {
            this.hasDeleteEvent = bool;
        }

        public void setHasDeleteReminder(boolean bool) {
            this.hasDeleteReminder = bool;
        }
    }
}
