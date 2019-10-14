package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;

import java.util.List;

/**
 * Lists all flashcards in the StudyBuddy application to the user.
 */
public class ListFlashcardCommand extends Command {

    public static final String COMMAND_WORD = "list_flashcards";

    public static final String MESSAGE_SUCCESS = "Listed all flashcards";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS); //Still needed?
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();

        //Builds list display of flashcard (Index. Title - Question)
        StringBuilder sb = new StringBuilder();
        int size = lastShownList.size();
        for(int i = 1; i <= size; i++) {
            Flashcard curr_flashcard = lastShownList.get(i - 1);
            sb.append(i + ". ");
            sb.append(curr_flashcard.getTitle() + " - ");
            sb.append(curr_flashcard.getQuestion());
            if(i != size) {
                sb.append("\n");
            }
        }
        return new CommandResult(sb.toString());
    }
}
