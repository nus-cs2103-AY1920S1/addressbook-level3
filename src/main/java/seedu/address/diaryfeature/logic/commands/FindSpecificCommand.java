package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.diaryfeature.logic.predicates.FindSpecificPredicate;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Finds and lists all entries which match the input word.
 * (For the specified section of the diary entry)
 * match
 * Keyword matching is case insensitive.
 */

public class FindSpecificCommand extends Command<DiaryModel> {

    public static final String COMMAND_WORD = "findSpecific";
    private static String MESSAGE_ENTRIES_LISTED_OVERVIEW = "%1$d entries listed";
    private static String MESSAGE_ENTRy_LISTED_OVERVIEW = "1 entry listed. Type list to get the original list back";
    private final FindSpecificPredicate predicate;

    /**
     * Creates the FindSpecific command which to find entry matches
     *
     * @param predicate Check if the specified section matches the input word
     */
    public FindSpecificCommand(FindSpecificPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the command using the predicate
     *
     * @param diaryModel to execute the command on
     * @return {@code CommandResult} a readable form of the matched entries
     */

    @Override
    public CommandResult execute(DiaryModel diaryModel) {
        requireNonNull(diaryModel);
        diaryModel.updateFilteredDiaryList(predicate);
        int size = diaryModel.getTotalDiaryEntries();
        if (size == 1) {
            return new CommandResult(MESSAGE_ENTRy_LISTED_OVERVIEW);
        }
        return new CommandResult(
                String.format(MESSAGE_ENTRIES_LISTED_OVERVIEW,
                        diaryModel.getFilteredDiaryEntryList().size()));
    }

    /**
     * Checks if the 2 findSpecific commands are equal
     *
     * @param other another object to check
     * @return true if the object is the same as this command
     */

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindSpecificCommand // instanceof handles nulls
                && predicate.equals(((FindSpecificCommand) other).predicate)); // state check
    }
}
