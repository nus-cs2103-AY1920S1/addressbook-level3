package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.elisa.commons.core.Messages;
import seedu.elisa.commons.core.index.Index;
import seedu.elisa.commons.core.item.Event;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.core.item.Item.ItemBuilder;
import seedu.elisa.commons.core.item.ItemDescription;
import seedu.elisa.commons.core.item.Priority;
import seedu.elisa.commons.core.item.Reminder;
import seedu.elisa.commons.core.item.Task;
import seedu.elisa.commons.core.item.tag.Tag;
import seedu.elisa.commons.util.CollectionUtil;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.AutoRescheduleManager;
import seedu.elisa.model.AutoReschedulePeriod;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.RescheduleTask;
import seedu.elisa.model.item.VisualizeList;

/**
 * Edits the details of an existing item in the item list.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the item in the shown list "
            + "by the index number used in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: \n"
            + "INDEX (must be a positive integer) \n"
            + "[" + PREFIX_DESCRIPTION + " DESCRIPTION] \n"
            + "[" + PREFIX_REMINDER + " REMINDER] \n"
            + "[" + PREFIX_PRIORITY + " PRIORITY] \n"
            + "[" + PREFIX_TAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + " DRINK WATER "
            + PREFIX_PRIORITY + " HIGH";

    public static final String MESSAGE_EDIT_ITEM_SUCCESS = "Edited Item: %1$s,"
            + " because someone couldn't make up their mind";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the item list.";

    private final Index index;
    private final EditItemDescriptor editItemDescriptor;

    private Item oldItem;
    private Item editedItem;

    /**
     * @param index              of the person in the filtered person list to edit
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
        this.oldItem = oldItem;
        Item editedItem = createEditedItem(oldItem, editItemDescriptor, lastShownList);
        this.editedItem = editedItem;

        if (model.hasItem(editedItem)) {
            throw new CommandException("Edit failed! Don't you remember that this item already exists?");
        }
        // if event has AutoReschedule, add it to the AutoRescheduleManager
        if (editedItem.hasAutoReschedule()) {
            Event event = editedItem.getEvent().get();
            RescheduleTask task = new RescheduleTask(editedItem, event.getPeriod(), model);
            AutoRescheduleManager.getInstance().add(task);
        }

        model.editItem(oldItem, editedItem);
        if (!isExecuted()) {
            model.getElisaCommandHistory().clearRedo();
            setExecuted(true);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_ITEM_SUCCESS, editedItem));
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        model.editItem(editedItem, oldItem);
    }

    /**
     * Create a new Item with the new edited details.
     *
     * @param itemToEdit         old item to edit
     * @param editItemDescriptor details to edit the item with
     * @param lastShownList      the last list shown to the user, set by the model
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
        Priority updatedPriority = editItemDescriptor.getPriority().orElse(itemToEdit.getPriority());
        Optional<AutoReschedulePeriod> updatedPeriod = Optional.ofNullable(editItemDescriptor
                .getAutoReschedulePeriod()
                .orElse(itemToEdit.hasEvent()
                        ? itemToEdit.getEvent().get().getPeriod() : null));

        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.setItemDescription(updatedDescription);
        itemBuilder.setTags(updatedTags);
        itemBuilder.setItemPriority(updatedPriority);

        if (updatedTask.isPresent()) {
            itemBuilder.setTask(updatedTask.get());
        }
        if (updatedEvent.isPresent()) {
            if (updatedPeriod.isPresent()) {
                updatedEvent = Optional.of(updatedEvent.get().setReschedulePeriod(updatedPeriod.get()));
            }
            itemBuilder.setEvent(updatedEvent.get());
        }
        if (updatedReminder.isPresent()) {
            itemBuilder.setReminder(updatedReminder.get());
        }

        if (editItemDescriptor.getHasDeleteTask()) {
            itemBuilder.setTask(null);
        }
        if (editItemDescriptor.getHasDeleteEvent()) {
            itemBuilder.setEvent(null);
        }
        if (editItemDescriptor.getHasDeleteReminder()) {
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
        private AutoReschedulePeriod period;
        private boolean hasDeleteTask = false;
        private boolean hasDeleteEvent = false;
        private boolean hasDeleteReminder = false;

        public EditItemDescriptor() {
        }

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
            setAutoReschedulePeriod(toCopy.period);
            setHasDeleteTask(toCopy.hasDeleteTask);
            setHasDeleteEvent(toCopy.hasDeleteEvent);
            setHasDeleteReminder(toCopy.hasDeleteReminder);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, task, event, reminder, priority, tags, period);
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

        public void setAutoReschedulePeriod(AutoReschedulePeriod period) {
            this.period = period;
        }

        public Optional<AutoReschedulePeriod> getAutoReschedulePeriod() {
            return Optional.ofNullable(period);
        }

        public boolean hasAnyDelete() {
            return (hasDeleteEvent || hasDeleteReminder || hasDeleteTask);
        }

        public void setHasDeleteTask(boolean bool) {
            this.hasDeleteTask = bool;
        }

        public boolean getHasDeleteTask() {
            return this.hasDeleteTask;
        }

        public void setHasDeleteEvent(boolean bool) {
            this.hasDeleteEvent = bool;
        }

        public boolean getHasDeleteEvent() {
            return this.hasDeleteEvent;
        }

        public void setHasDeleteReminder(boolean bool) {
            this.hasDeleteReminder = bool;
        }

        public boolean getHasDeleteReminder() {
            return this.hasDeleteReminder;
        }
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
