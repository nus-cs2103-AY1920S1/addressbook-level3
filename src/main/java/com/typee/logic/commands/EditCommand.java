package com.typee.logic.commands;

import static com.typee.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import com.typee.commons.core.Messages;
import com.typee.commons.core.index.Index;
import com.typee.commons.util.CollectionUtil;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.model.Model;
import com.typee.model.engagement.Engagement;
import com.typee.model.person.Name;

/**
 * Edits the details of an existing engagement in the list.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the engagement identified "
            + "by the index number used in the displayed engagement list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] ";

    public static final String MESSAGE_EDIT_ENGAGEMENT_SUCCESS = "Edited engagement: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENGAGEMENT = "This engagement already exists in the list.";

    private final Index index;
    private final EditEngagementDescriptor editEngagementDescriptor;

    /**
     * @param index of the engagement in the filtered engagement list to edit
     * @param editPersonDescriptor details to edit the engagement with
     */
    public EditCommand(Index index, EditEngagementDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editEngagementDescriptor = new EditEngagementDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Engagement> lastShownList = model.getFilteredEngagementList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENGAGEMENT_DISPLAYED_INDEX);
        }

        Engagement engagementToEdit = lastShownList.get(index.getZeroBased());
        Engagement editedEngagement = createEditedEngagement(engagementToEdit, editEngagementDescriptor);

        if (!engagementToEdit.isConflictingWith(editedEngagement) && model.hasEngagement(editedEngagement)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENGAGEMENT);
        }

        model.setEngagement(engagementToEdit, editedEngagement);
        model.updateFilteredEngagementList(Model.PREDICATE_SHOW_ALL_ENGAGEMENTS);
        model.saveEngagementList();
        return new CommandResult(String.format(MESSAGE_EDIT_ENGAGEMENT_SUCCESS, editedEngagement));
    }

    /**
     * Creates and returns a {@code Engagement} with the details of {@code engagementToEdit}
     * edited with {@code editEngagementDescriptor}.
     */
    private static Engagement createEditedEngagement(Engagement engagementToEdit,
                                                     EditEngagementDescriptor editEngagementDescriptor) {
        assert engagementToEdit != null;

        //Name updatedName = editEngagementDescriptor.getName().orElse(engagementToEdit.getName());
        //return new Person(updatedName);
        return engagementToEdit;
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
                && editEngagementDescriptor.equals(e.editEngagementDescriptor);
    }

    /**
     * Stores the details to edit the engagement with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditEngagementDescriptor {
        private Name name;

        public EditEngagementDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEngagementDescriptor(EditEngagementDescriptor toCopy) {
            setName(toCopy.name);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEngagementDescriptor)) {
                return false;
            }

            // state check
            EditEngagementDescriptor e = (EditEngagementDescriptor) other;

            return getName().equals(e.getName());
        }
    }
}
