package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

public class ListCommand extends Command<DiaryModel> {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all entries";


    @Override
    public CommandResult execute(DiaryModel diaryModel) {
        requireNonNull(diaryModel);
        diaryModel.updateFilteredDiaryList(diaryModel.PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
