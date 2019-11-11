package seedu.algobase.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG_COLOR;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PROBLEMS;

import java.util.List;
import java.util.Optional;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Id;
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
            + "[" + PREFIX_TAG_COLOR + "COLOR]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_TAG + "Easy " + PREFIX_TAG_COLOR + "BLUE";
    public static final String MESSAGE_EDIT_TAG_SUCCESS = "Tag [%1$s] edited.";
    public static final String MESSAGE_DUPLICATE_TAG = "Tag [%1$s] already exists in AlgoBase.";

    private final Index index;
    private final Optional<String> name;
    private final Optional<String> color;

    /**
     * @param index of the Tag in the filtered Tag list to edit
     * @param name details to edit the Tag with
     * @param color color of the tag displayed in UI
     */
    public EditTagCommand(Index index, Optional<String> name, Optional<String> color) {
        requireNonNull(index);
        this.index = index;
        this.name = name;
        this.color = color;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Tag> lastShownList = model.getFilteredTagList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        }

        if (!color.isEmpty() && !Tag.isValidTagColor(color.get())) {
            throw new CommandException(Tag.MESSAGE_COLOR_CONSTRAINTS);
        }

        Tag tagToEdit = lastShownList.get(index.getZeroBased());
        Tag editedTag = createEditedTag(tagToEdit, name, color);

        if (!tagToEdit.isSameTag(editedTag) && model.hasTag(editedTag)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TAG, name.get()));
        }

        model.setTag(tagToEdit, editedTag);
        model.setTags(tagToEdit, editedTag);

        model.updateFilteredProblemList(PREDICATE_SHOW_ALL_PROBLEMS);
        model.updateFilteredPlanList(Model.PREDICATE_SHOW_ALL_PLANS);
        model.updateFilteredTagList(Model.PREDICATE_SHOW_ALL_TAGS);
        return new CommandResult(String.format(MESSAGE_EDIT_TAG_SUCCESS, editedTag.getName()));
    }

    /**
     * @param tagToEdit tag that need to edit
     * @param name new tag name for tagToEdit
     * @return Tag with updated name.
     */
    private static Tag createEditedTag(Tag tagToEdit, Optional<String> name, Optional<String> color) {
        Id id = tagToEdit.getId();
        if (name.isEmpty()) {
            name = Optional.ofNullable(tagToEdit.getName());
        }
        if (color.isEmpty()) {
            color = Optional.ofNullable(tagToEdit.getColor());
        }
        if (color.equals(Optional.ofNullable("DEFAULT"))) {
            color = Optional.ofNullable("#3e7b91");
        }
        return new Tag(id, name.get(), color.get());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTagCommand)) {
            return false;
        }

        // state check
        EditTagCommand e = (EditTagCommand) other;
        return index.equals(e.index)
                && name.equals(e.name);
    }
}
