package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.diaryfeature.logic.predicates.FindSpecificPredicate;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

public class FindSpecificCommand extends Command<DiaryModel> {

    public static final String COMMAND_WORD = "findSpecific";
    public static String MESSAGE_ENTRIES_LISTED_OVERVIEW = "%1$d entries listed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final FindSpecificPredicate predicate;

    public FindSpecificCommand(FindSpecificPredicate predicate) {

        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(DiaryModel diaryModel) {
        requireNonNull(diaryModel);
        diaryModel.updateFilteredDiaryList(predicate);
        return new CommandResult(
                String.format(MESSAGE_ENTRIES_LISTED_OVERVIEW,
                        diaryModel.getFilteredDiaryEntryList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindSpecificCommand // instanceof handles nulls
                && predicate.equals(((FindSpecificCommand) other).predicate)); // state check
    }
}
