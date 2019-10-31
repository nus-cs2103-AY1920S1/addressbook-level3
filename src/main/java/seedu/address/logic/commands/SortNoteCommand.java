package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SORTBY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.SortByCond;




/**
 * Edits the details of an existing person in the address book.
 */
public class SortNoteCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String DATEMODIFIED = "DateModified";
    public static final String DATECREATED = "DateCreated";
    public static final String NUMOFACCESS = "NumOfAccess";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the notes "
            + "by the condition given by the user. \n "
            + "Parameters: "
        + "[" + PREFIX_SORTBY + DATEMODIFIED + " or " + DATECREATED + " or " + NUMOFACCESS + " ] "
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORTBY + "DateModified";

    public static final String MESSAGE_SORT_NOTE_SUCCESS = "Sorted Note by: %1$s";
    public static final String MESSAGE_NOT_SORTED = "Must provide at one and only one field to sort by.";
    public static final String MESSAGE_DUPLICATE_NOTE = "This notebook is already sorted.";
    private SortByCond sortByCond;

    /**
     * @param sortByCond condition to sort NoteBook by.
     */
    public SortNoteCommand(SortByCond sortByCond) {
        requireNonNull(sortByCond);
        this.sortByCond = sortByCond;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.commitNote();
        model.editNoteSortByCond(sortByCond);
        model.sortNoteBook();
        model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
        return new CommandResult(String.format(MESSAGE_SORT_NOTE_SUCCESS, sortByCond.sortByCond));
    }



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortNoteCommand)) {
            return false;
        }

        // state check
        SortNoteCommand e = (SortNoteCommand) other;
        return sortByCond.equals(e.sortByCond);
    }

}
