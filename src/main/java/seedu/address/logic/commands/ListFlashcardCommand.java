package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import java.util.List;

import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.Model;

/**
 * Lists all flashcards in the StudyBuddy application to the user.
 */
public class ListFlashcardCommand extends Command {

    public static final String COMMAND_WORD = "list_flashcards";

    public static final String MESSAGE_SUCCESS = "Listed all flashcards";

    /**
     * Formats string for output
     * @param inputList list of flashcards
     * @return String formatted flashcard display
     */
    private String formatOutputListString(List<Flashcard> inputList) {
        int size = inputList.size();
        if (size == 0) {
            return "No flashcards to display!";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= size; i++) {
            Flashcard flashcard = inputList.get(i - 1);
            sb.append(i + ". ");
            sb.append(flashcard.getTitle() + " - ");
            sb.append(flashcard.getQuestion());
            if (i != size) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS); //Still needed?
        List<Flashcard> lastShownList = model.getFilteredFlashcardList();
        String outputString = formatOutputListString(lastShownList);
        return new CommandResult(outputString);
    }
}
