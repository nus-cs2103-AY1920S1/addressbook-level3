package seedu.moolah.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;
import static seedu.moolah.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;
import java.util.Optional;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.commons.core.index.Index;
import seedu.moolah.commons.util.CollectionUtil;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.ui.event.EventListPanel;

/**
 * Edits the details of an existing expense in the MooLah.
 */
public class EditEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.EDIT + CommandGroup.EVENT;
    public static final String COMMAND_DESCRIPTION = "Edit event with index %1$d";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_CATEGORY + "CATEGORY]"
            + "[" + PREFIX_TIMESTAMP + "TIMESTAMP]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PRICE + "3512.123 ";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event:\n %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in MooLah!";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, index.getOneBased());
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);

        List<Event> lastShownList = model.getFilteredEventList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);
        if (!eventToEdit.equals(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);

        List<Event> lastShownList = model.getFilteredEventList();
        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        model.setEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent),
                EventListPanel.PANEL_NAME);
    }

    /**
     * Creates and returns a {@code Expense} with the details of {@code expenseToEdit}
     * edited with {@code editExpenseDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Description updatedDescription = editEventDescriptor.getDescription().orElse(eventToEdit.getDescription());
        Price updatedPrice = editEventDescriptor.getPrice().orElse(eventToEdit.getPrice());
        Category updatedCategory = editEventDescriptor.getCategory().orElse(eventToEdit.getCategory());
        Timestamp updatedTimestamp = editEventDescriptor.getTimestamp().orElse(eventToEdit.getTimestamp());

        return new Event(updatedDescription, updatedPrice, updatedCategory,
                updatedTimestamp, eventToEdit.getBudgetName());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        // state check
        EditEventCommand e = (EditEventCommand) other;
        return index.equals(e.index)
                && editEventDescriptor.equals(e.editEventDescriptor);
    }

    /**
     * Stores the details to edit the expense with. Each non-empty field value will replace the
     * corresponding field value of the expense.
     */
    public static class EditEventDescriptor {
        private Description description;
        private Price price;
        private Category category;
        private Timestamp timestamp;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setDescription(toCopy.description);
            setPrice(toCopy.price);
            setCategory(toCopy.category);
            setTimestamp(toCopy.timestamp);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, price, category, timestamp);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return Optional.ofNullable(category);
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        public Optional<Timestamp> getTimestamp() {
            return Optional.ofNullable(timestamp);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getPrice().equals(e.getPrice())
                    && getCategory().equals(e.getCategory())
                    && getTimestamp().equals(e.getTimestamp());
        }
    }
}
