package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Shows all entries contained in the Diary List
 */
public class ListCommand extends Command<DiaryModel> {
    public static final String COMMAND_WORD = "list";
    private static final String MESSAGE_SUCCESS = "Listed all entries";

    /**
     * Executes the command by showing unfiltered diary list
     *
     * @param diaryModel on which the command is executes
     * @return {@code CommandResult} a readable form of the entries
     */
    @Override
    public CommandResult execute(DiaryModel diaryModel) {
        requireNonNull(diaryModel);
        diaryModel.updateFilteredDiaryList(diaryModel.PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
