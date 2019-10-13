package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.Name;
import seedu.address.model.diary.Page;

/**
 * Deletes a page from a specified diary, identified using it's displayed index and Diary Name from Duke Cooks.
 */
public class DeletePageCommand extends Command {

    public static final String COMMAND_WORD = "deletePage";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the page identified by the index number in the specified diary.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 + [Diary Name]";

    public static final String MESSAGE_DELETE_PAGE_SUCCESS = "Deleted Page: %1$s";
    public static final String MESSAGE_NON_EXISTENT_DIARY = "This diary does not exists in DukeCooks";

    private final Index targetIndex;
    private final Name targetDiaryName;

    public DeletePageCommand(Index targetIndex, Name targetDiaryName) {
        this.targetIndex = targetIndex;
        this.targetDiaryName = targetDiaryName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Diary> lastShownList = model.getFilteredDiaryList();

        Diary targetDiary = new Diary(targetDiaryName);

        // check if diary exists
        if (!lastShownList.contains(targetDiary)) {
            throw new CommandException(MESSAGE_NON_EXISTENT_DIARY);
        }

        // get target diary
        targetDiary = lastShownList.get(lastShownList.indexOf(targetDiary));

        // remove page from target diary's list of pages
        ArrayList<Page> newPageList = targetDiary.getPages();

        // check if index is out of bounds
        if (targetIndex.getZeroBased() >= newPageList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PAGE_DISPLAYED_INDEX);
        }

        Page pageToDelete = newPageList.get(targetIndex.getZeroBased());
        newPageList.remove(pageToDelete);

        model.setDiary(targetDiary, new Diary(targetDiary.getName(), newPageList));
        return new CommandResult(String.format(MESSAGE_DELETE_PAGE_SUCCESS, pageToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePageCommand // instanceof handles nulls
                && targetIndex.equals(((DeletePageCommand) other).targetIndex)); // state check
    }
}
