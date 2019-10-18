package seedu.address.logic.commands.diary;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIARY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAGE_NUMBER;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.components.Diary;
import seedu.address.model.diary.components.DiaryName;
import seedu.address.model.diary.components.Page;

/**
 * Deletes a page from a specified diary, identified using it's displayed index and Diary DiaryName from Duke Cooks.
 */
public class DeletePageCommand extends Command {

    public static final String COMMAND_WORD = "deletePage";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the page identified by the index number in the specified diary.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PAGE_NUMBER + " 1 "
            + PREFIX_DIARY_NAME + "[Diary DiaryName]";

    public static final String MESSAGE_DELETE_PAGE_SUCCESS = "Deleted Page: %1$s";
    public static final String MESSAGE_NON_EXISTENT_DIARY = "This diary does not exists in DiaryRecords";

    private final Index targetIndex;
    private final DiaryName targetDiaryDiaryName;

    public DeletePageCommand(Index targetIndex, DiaryName targetDiaryDiaryName) {
        this.targetIndex = targetIndex;
        this.targetDiaryDiaryName = targetDiaryDiaryName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Diary> lastShownList = model.getFilteredDiaryList();

        Diary targetDiary = new Diary(targetDiaryDiaryName);

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

        model.setDiary(targetDiary, new Diary(targetDiary.getDiaryName(), newPageList));
        return new CommandResult(String.format(MESSAGE_DELETE_PAGE_SUCCESS, pageToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePageCommand // instanceof handles nulls
                && targetIndex.equals(((DeletePageCommand) other).targetIndex)); // state check
    }
}
