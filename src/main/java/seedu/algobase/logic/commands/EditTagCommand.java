package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_TAGS;

import java.util.List;
import java.util.Optional;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.tag.exceptions.TagNotFoundException;

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
    public static final String MESSAGE_DUPLICATE_TAG = "This Tag already exists in the algobase.";

    private final Index index;
    private final Optional<String> name;

    /**
     * @param index of the Tag in the filtered Tag list to edit
     * @param name details to edit the Tag with
     */
    public EditTagCommand(Index index, Optional<String> name) {
        requireNonNull(index);
        requireNonNull(name);

        this.index = index;
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tag> lastShownList = model.getFilteredTagList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROBLEM_DISPLAYED_INDEX);
        }

        Tag tagToEdit = lastShownList.get(index.getZeroBased());
        Tag editedTag = createEditedTag(tagToEdit, name);


        if (!tagToEdit.isSameTag(editedTag) && model.hasTag(editedTag)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        model.setTag(tagToEdit, editedTag);
        model.setTags(tagToEdit, editedTag);

        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        return new CommandResult(String.format(MESSAGE_EDIT_PROBLEM_SUCCESS, editedTag));
    }

    /**
     * Creates and returns a {@code Tag} with the details of {@code tagToEdit}
     * edited with {@code editTagDescriptor}.
     */
    private static Tag createEditedTag(Tag tagToEdit, Optional<String> name) {
        assert tagToEdit != null;
        String updatedName;
        if (name.isPresent()) {
            updatedName = name.get();
        } else {
            throw new TagNotFoundException();
        }
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
                && name.equals(e.name);
    }
}
