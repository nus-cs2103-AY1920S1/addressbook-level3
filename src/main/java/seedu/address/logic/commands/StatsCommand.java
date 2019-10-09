package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.model.Model;
import seedu.address.model.flashcard.RatingContainsKeywordPredicate;

public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the number of GOOD, HARD & EASY flashcards ";

    private final RatingContainsKeywordPredicate predicateGood = new RatingContainsKeywordPredicate("good");
    private final RatingContainsKeywordPredicate predicateHard = new RatingContainsKeywordPredicate("hard");
    private final RatingContainsKeywordPredicate predicateEasy = new RatingContainsKeywordPredicate("easy");

    ////////// Should return a CommandResult with the statistics ////////////////
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        int numGood = 0, numHard = 0, numEasy = 0;

        model.updateFilteredFlashCardList(predicateGood);
        numGood = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(predicateHard);
        numHard = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(predicateEasy);
        numEasy = model.getFilteredFlashCardList().size();

        StringBuilder sb = new StringBuilder("STATISTICS" + System.getProperty("line.separator"));
        sb.append("Good:Hard:Easy = " + numGood + ":" + numHard + ":" + numEasy);

        return new CommandResult(sb.toString());
    }

}
