package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.flashcard.RatingContainsKeywordPredicate;
import static java.util.Objects.requireNonNull;

public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the number of GOOD, HARD & EASY flashcards ";

    private final RatingContainsKeywordPredicate predicate_good = new RatingContainsKeywordPredicate("good");
    private final RatingContainsKeywordPredicate predicate_hard = new RatingContainsKeywordPredicate("hard");
    private final RatingContainsKeywordPredicate predicate_easy = new RatingContainsKeywordPredicate("easy");

    ////////// Should return a CommandResult with the statistics ////////////////
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        int No_Good = 0, No_Hard = 0, No_Easy = 0;

        model.updateFilteredFlashCardList(predicate_good);
        No_Good = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(predicate_hard);
        No_Hard = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(predicate_easy);
        No_Easy = model.getFilteredFlashCardList().size();

        StringBuilder sb = new StringBuilder("STATISTICS" + System.getProperty("line.separator"));
        sb.append("Good:Hard:Easy = " + No_Good + ":" + No_Hard + ":" + No_Easy);

        return new CommandResult(sb.toString());
    }

}
