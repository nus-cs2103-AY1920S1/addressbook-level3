package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the number of GOOD, HARD & EASY flashcards ";


    ////////// Should return a CommandResult with the full statistics ////////////////
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        int no_good=0, no_hard=0, no_easy=0;

        /*
        model.updateFilteredFlashCardList(predicate_good); //predicate: GOOD
        no_good = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(predicate_hard); //predicate: HARD
        no_hard = model.getFilteredFlashCardList().size();
        model.updateFilteredFlashCardList(predicate_easy); //predicate: EASY
        no_easy = model.getFilteredFlashCardList().size();
        */
        StringBuilder sb = new StringBuilder("Statistics: " + System.getProperty("line.separator"));
        sb.append("Number of flashcards labeled as good: " + no_good + System.getProperty("line.separator"));
        sb.append("Number of flashcards labeled as hard: " + no_hard + System.getProperty("line.separator"));
        sb.append("Number of flashcards labeled as easy: " + no_easy + System.getProperty("line.separator"));
        return new CommandResult(sb.toString());
    }

}
