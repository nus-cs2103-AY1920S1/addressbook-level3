package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_TAGS;

import java.util.List;
import java.util.Optional;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.commons.util.CollectionUtil;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.tag.Tag;

/**
 * Edits the details of an existing Tag in the algobase.
 */
public class EditTagCommand extends Command {

    public static final String COMMAND_WORD = "edittag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Tag identified "
            + "by the index number used in the displayed Tag list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG] "
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_TAG + "Easy";
    public static final String MESSAGE_EDIT_PROBLEM_SUCCESS = "Edited Tag: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PROBLEM = "This Tag already exists in the algobase.";

    private final Index index;
    private final EditTagDescriptor editTagDescriptor;

    /**
     * @param index of the Tag in the filtered Tag list to edit
     * @param editTagDescriptor details to edit the Tag with
     */
    public EditTagCommand(Index index, EditTagDescriptor editTagDescriptor) {
        requireNonNull(index);
        requireNonNull(editTagDescriptor);

        this.index = index;
        this.editTagDescriptor = new EditTagDescriptor(editTagDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tag> lastShownList = model.getFilteredTagList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROBLEM_DISPLAYED_INDEX);
        }

        Tag tagToEdit = lastShownList.get(index.getZeroBased());
        Tag editedTag = createEditedTag(tagToEdit, editTagDescriptor);

        if (!tagToEdit.isSameTag(editedTag) && model.hasTag(editedTag)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROBLEM);
        }

        model.setTag(tagToEdit, editedTag);
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        return new CommandResult(String.format(MESSAGE_EDIT_PROBLEM_SUCCESS, editedTag));
    }

    /**
     * Creates and returns a {@code Tag} with the details of {@code tagToEdit}
     * edited with {@code editTagDescriptor}.
     */
    private static Tag createEditedTag(Tag tagToEdit, EditTagDescriptor editTagDescriptor) {
        assert tagToEdit != null;

        String updatedName = editTagDescriptor.getName().orElse(tagToEdit.getName());


        return new Tag(updatedName);
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
        EditTagCommand e = (EditTagCommand) other;
        return index.equals(e.index)
                && editTagDescriptor.equals(e.editTagDescriptor);
    }

    /**
     * Stores the details to edit the Tag with. Each non-empty field value will replace the
     * corresponding field value of the Tag.
     */
    public static class EditTagDescriptor {
        private String name;

        public EditTagDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTagDescriptor(EditTagDescriptor toCopy) {
            setName(toCopy.name);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name);
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTagDescriptor)) {
                return false;
            }

            // state check
            EditTagDescriptor e = (EditTagDescriptor) other;

            return getName().equals(e.getName());
        }
    }
}
