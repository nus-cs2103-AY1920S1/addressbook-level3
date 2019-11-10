package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EATERIES;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.eatery.Address;
import seedu.address.model.eatery.Category;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Name;
import seedu.address.model.eatery.Tag;

/**
 * Removes tag from an existing eatery in the eatme application.
 */
public class RemoveTagCommand extends Command {

    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the TAGS of the eatery identified "
            + "by the index number used in the displayed eatery list. "
            + "The tags will be removed from the existing tags.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_TAG + " good";

    public static final String REMOVE_TAG_SUCCESS = "Removed tags from the Eatery: %1$s";

    private final Index index;
    private final EditEateryDescriptor editEateryDescriptor;

    /**
     * @param index of the eatery in the displayed list to be edited.
     * @param editEateryDescriptor details of the tags to be removed.
     */
    public RemoveTagCommand(Index index, EditEateryDescriptor editEateryDescriptor) {

        requireNonNull(index);
        requireNonNull(editEateryDescriptor);

        this.index = index;
        this.editEateryDescriptor = editEateryDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Eatery> lastShownList = model.getFilteredEateryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
        }

        Eatery eateryToEdit = lastShownList.get(index.getZeroBased());
        Eatery editedEatery = createEditedEatery(eateryToEdit, editEateryDescriptor);
        editedEatery.setReviews(eateryToEdit.getReviews());
        model.setEatery(eateryToEdit, editedEatery);
        model.updateFilteredEateryList(PREDICATE_SHOW_ALL_EATERIES);
        return new CommandResult(String.format(REMOVE_TAG_SUCCESS, editedEatery));
    }
    /**
     * Creates and returns a {@code Eatery} with the details of {@code eateryToEdit}
     * edited with {@code editEateryDescriptor}.
     */
    private static Eatery createEditedEatery(Eatery eateryToEdit, EditEateryDescriptor editEateryDescriptor) {
        Name name = eateryToEdit.getName();
        Address address = eateryToEdit.getAddress();
        Category category = eateryToEdit.getCategory();

        Set<Tag> updatedTags = editEateryDescriptor.removeTags(eateryToEdit);

        return new Eatery(name, address, category, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        // state check
        RemoveTagCommand e = (RemoveTagCommand) other;
        return index.equals(e.index)
                && editEateryDescriptor.equals(e.editEateryDescriptor);
    }

    /**
     * Contains the details of the new tags to be deleted.
     */
    public static class EditEateryDescriptor {

        private Set<Tag> tagsToRemove = new HashSet<>();

        public EditEateryDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditEateryDescriptor(EditEateryDescriptor toCopy) {
            addTags(toCopy.tagsToRemove);
        }

        /**
         * Adds {@code tags} to this object's {@code tags}.
         */
        public void addTags(Set<Tag> tags) {
            this.tagsToRemove.addAll(tags);
        }

        /**
         * @return the tags corresponding to this object.
         */
        public Set<Tag> getTags() {
            return tagsToRemove;
        }

        /**
         * Removes the tags from an eatery's list of according to user input.
         */
        public Set<Tag> removeTags(Eatery eatery) {
            Set<Tag> listAfterRemoving = new HashSet<>(eatery.getTags());
            for (Tag t : this.tagsToRemove) {
                listAfterRemoving.remove(t);
            }
            return listAfterRemoving;
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
