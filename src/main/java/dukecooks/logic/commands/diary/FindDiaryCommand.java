package dukecooks.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.FindCommand;
import dukecooks.model.Model;
import dukecooks.model.diary.components.DiaryNameContainsKeywordsPredicate;

/**
 * Finds and lists all diaries in Duke Cooks whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindDiaryCommand extends FindCommand {

    public static final String VARIANT_WORD = "diary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all diaries whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " Asian Food";

    private static Event event;
    private final DiaryNameContainsKeywordsPredicate predicate;

    public FindDiaryCommand(DiaryNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDiaryList(predicate);

        // Navigate to diary tab
        event = Event.getInstance();
        event.set("diary", "all");

        return new CommandResult(
                String.format(Messages.MESSAGE_DIARY_LISTED_OVERVIEW, model.getFilteredDiaryList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindDiaryCommand // instanceof handles nulls
                && predicate.equals(((FindDiaryCommand) other).predicate)); // state check
    }
}
