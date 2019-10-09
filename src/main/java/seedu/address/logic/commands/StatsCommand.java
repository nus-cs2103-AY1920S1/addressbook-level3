package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.flashcard.RatingContainsKeywordPredicate;

import java.util.Arrays;

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
        int no_good=0, no_hard=0, no_easy=0;

        model.updateFilteredFlashCardList(predicate_good);
        no_good = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(predicate_hard);
        no_hard = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(predicate_easy);
        no_easy = model.getFilteredFlashCardList().size();

        StringBuilder sb = new StringBuilder("STATISTICS" + System.getProperty("line.separator"));
        sb.append("Good:Hard:Easy = " + no_good + ":" + no_hard + ":" + no_easy);

        return new CommandResult(sb.toString());
    }

}
