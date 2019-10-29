package dukecooks.logic.commands.diary;

import static dukecooks.logic.parser.CliSyntax.PREFIX_DIARY_NAME;
import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.DeleteCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.model.diary.components.Page;
import javafx.collections.ObservableList;

/**
 * Deletes a page from a specified diary, identified using it's displayed index and Diary DiaryName from Duke Cooks.
 */
public class DeletePageCommand extends DeleteCommand {

    public static final String VARIANT_WORD = "page";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the page identified by the index number in the specified diary.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + ""
            + " page 1 "
            + PREFIX_DIARY_NAME + "[DiaryName]";

    public static final String MESSAGE_DELETE_PAGE_SUCCESS = "You have deleted a page with title: %1$s";
    public static final String MESSAGE_NON_EXISTENT_DIARY = "This diary does not exists!";

    private final Index targetIndex;
    private final DiaryName targetDiaryName;

    public DeletePageCommand(Index targetIndex, DiaryName targetDiaryDiaryName) {
        this.targetIndex = targetIndex;
        this.targetDiaryName = targetDiaryDiaryName;
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
        ObservableList<Page> newPageList = targetDiary.getPages();

        // check if index is out of bounds
        if (targetIndex.getZeroBased() >= newPageList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PAGE_DISPLAYED_INDEX);
        }

        Page pageToDelete = newPageList.get(targetIndex.getZeroBased());
        newPageList.remove(pageToDelete);

        model.setDiary(targetDiary, new Diary(targetDiary.getDiaryName(), newPageList));
        return new CommandResult(String.format(MESSAGE_DELETE_PAGE_SUCCESS, pageToDelete.getTitle()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePageCommand // instanceof handles nulls
                && targetIndex.equals(((DeletePageCommand) other).targetIndex)); // state check
    }
}
