package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.diaryfeature.logic.predicates.FindPredicate;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Finds and lists all entries which match the input word
 * Keyword matching is case insensitive.
 */

public class FindCommand extends Command<DiaryModel> {


    public static final String COMMAND_WORD = "find";
    private static String MESSAGE_ENTRIES_LISTED_OVERVIEW = "%1$d entries listed. "
            + "Type list to get the original list back "
            + "Type list to get the original list back";
    private static String MESSAGE_ENTRY_LISTED_OVERVIEW = "1 entry listed. Type "
            + "list to get the original list back";
    private final FindPredicate predicate;

    /**
     * Generate a find command with the specified predicate to find the relevant entries
     *
     * @param predicate checks each entry for the specified word
     */
    public FindCommand(FindPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Execute the find command and change the list view to only show the filtered entries
     *
     * @param diaryModel model upon which to execute the command
     * @return {@code CommandResult} with the number of entries that match the predicate
     */
    @Override
    public CommandResult execute(DiaryModel diaryModel) {
        requireNonNull(diaryModel);
        diaryModel.updateFilteredDiaryList(predicate);
        int size = diaryModel.getTotalDiaryEntries();
        if (size == 1) {
            return new CommandResult(MESSAGE_ENTRY_LISTED_OVERVIEW);
        }
        return new CommandResult(
                String.format(MESSAGE_ENTRIES_LISTED_OVERVIEW,
                        diaryModel.getFilteredDiaryEntryList().size()));
    }

    /**
     * /**
     * Checks if the 2 Find commands are equal
     *
     * @param other another object to check
     * @return true if the object is the same as this command
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
