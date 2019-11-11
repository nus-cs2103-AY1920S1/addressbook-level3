package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.eatme.model.Model.PREDICATE_SHOW_ALL_EATERIES;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.model.Model;
import seedu.eatme.model.eatery.Address;
import seedu.eatme.model.eatery.Category;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Name;
import seedu.eatme.model.eatery.Tag;

/**
 * Adds tags to an existing eatery in the eatme application.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds tags to the eatery identified "
            + "by the index number used in the displayed eatery list."
            + "The new tags will be added to the existing tags.\n"
            + "Parameters: [index] (must be a positive integer) "
            + PREFIX_TAG + " [tag]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_TAG + " good " + PREFIX_TAG + " elder-friendly";

    public static final String ADD_TAG_SUCCESS = "Tags successfully added";

    private final Index index;
    private final EditEateryDescriptor editEateryDescriptor;

    /**
     * @param index of the eatery in the displayed list to be edited.
     * @param editEateryDescriptor details of the tags to be added.
     */
    public AddTagCommand(Index index, EditEateryDescriptor editEateryDescriptor) {

        requireNonNull(index);
        requireNonNull(editEateryDescriptor);

        this.index = index;
        this.editEateryDescriptor = editEateryDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Eatery> lastShownList = model.isMainMode() ? model.getFilteredEateryList() : model.getFilteredTodoList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
        }

        Eatery eateryToEdit = lastShownList.get(index.getZeroBased());
        Eatery editedEatery = createEditedEatery(eateryToEdit, editEateryDescriptor);
        editedEatery.setReviews(eateryToEdit.getReviews());
        model.setEatery(eateryToEdit, editedEatery);
        model.updateFilteredEateryList(PREDICATE_SHOW_ALL_EATERIES);
        return new CommandResult(ADD_TAG_SUCCESS);
    }
    /**
     * Creates and returns a {@code Eatery} with the details of {@code eateryToEdit}
     * edited with {@code editEateryDescriptor}.
     */
    private static Eatery createEditedEatery(Eatery eateryToEdit, EditEateryDescriptor editEateryDescriptor) {
        Name name = eateryToEdit.getName();
        Address address = eateryToEdit.getAddress();
        Category category = eateryToEdit.getCategory();

        editEateryDescriptor.addTags(eateryToEdit.getTags());
        Set<Tag> updatedTags = editEateryDescriptor.getTags();

        return new Eatery(name, address, category, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand e = (AddTagCommand) other;
        return index.equals(e.index)
                && editEateryDescriptor.equals(e.editEateryDescriptor);
    }

    /**
     * Contains the details of the new tags to be added.
     */
    public static class EditEateryDescriptor {

        private Set<Tag> tags = new HashSet<>();

        public EditEateryDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditEateryDescriptor(EditEateryDescriptor toCopy) {
            addTags(toCopy.tags);
        }

        /**
         * Adds {@code tags} to this object's {@code tags}.
         */
        public void addTags(Set<Tag> newTag) {
            for (Tag t : newTag) {
                this.tags.add(t);
            }
        }

        /**
         * @return the tags corresponding to this object.
         */
        public Set<Tag> getTags() {
            return tags;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEateryDescriptor)) {
                return false;
            }

            // state check
            EditEateryDescriptor e = (EditEateryDescriptor) other;

            return getTags().equals(e.getTags());
        }
    }
}
