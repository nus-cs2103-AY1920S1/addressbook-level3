package seedu.algobase.logic.commands.tag;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TAG_COLOR;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PROBLEMS;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_TAGS;

import java.util.List;

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
public class EditTagColorCommand extends Command {

    public static final String COMMAND_WORD = "edittagcolor";
    public static final String SHORT_COMMAND_WORD = "etc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the color of the Tag identified "
            + "by the index number used in the displayed Tag list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: \n"
            + "INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG_COLOR + "TAGCOLOR] \n"
            + "Tags colors should be one of \"RED\", \"ORANGE\", \"YELLOW\", "
            + "\"GREEN\", \"BLUE\", \"PURPLE\", \"BLACK\". \n"
            + "Example: "
            + COMMAND_WORD + " 1 "
            + PREFIX_TAG_COLOR + "BULE";
    public static final String MESSAGE_EDIT_TAG_SUCCESS = "Edited Tag: %1$s";

    private final Index index;
    private final String color;

    /**
     * @param index of the Tag in the filtered Tag list to edit
     * @param color details to edit the Tag with
     */
    public EditTagColorCommand(Index index, String color) {
        requireAllNonNull(index, color);
        this.index = index;
        this.color = color;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Tag> lastShownList = model.getFilteredTagList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        }

        if (!Tag.isValidTagColor(color)) {
            throw new CommandException(Tag.MESSAGE_COLOR_CONSTRAINTS);
        }

        Tag tagToEdit = lastShownList.get(index.getZeroBased());
        Tag editedTag = createEditedTag(tagToEdit, color);

        model.setTag(tagToEdit, editedTag);
        model.setTags(tagToEdit, editedTag);

        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        model.updateFilteredProblemList(PREDICATE_SHOW_ALL_PROBLEMS);
        return new CommandResult(String.format(MESSAGE_EDIT_TAG_SUCCESS, editedTag));
    }

    /**
     * @param tagToEdit tag that need to edit
     * @param color new tag name for tagToEdit
     * @return Tag with updated name.
     */
    private static Tag createEditedTag(Tag tagToEdit, String color) {
        requireAllNonNull(tagToEdit, color);

        Id id = tagToEdit.getId();
        return new Tag(id, tagToEdit.tagName, color);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTagColorCommand)) {
            return false;
        }

        // state check
        EditTagColorCommand e = (EditTagColorCommand) other;
        return index.equals(e.index);
    }
}
