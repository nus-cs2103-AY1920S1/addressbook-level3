package seedu.address.logic.commands.flashcard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.flashcard.Flashcard;

/**
 * Utility class for remind feature that abstracts out common methods used to support the remind feature.
 */
public class RemindFeatureUtil {

    public static final String MESSAGE_DUE_FLASHCARD_HELPER = "Here are the flashcards due today:";
    public static final String MESSAGE_OVERDUE_FLASHCARD_HELPER = "Here are your overdue flashcards:";

    /**
     * Gets flashcards that are due for revision today from a given list of flashcards.
     * @param flashcardList list of flashcards to be checked
     * @return list of flashcards containing flashcards due for revision today
     */
    public List<Flashcard> getDueFlashcards(List<Flashcard> flashcardList) {
        List<Flashcard> dueFlashcards = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        //Gets flashcards due today
        for (Flashcard f : flashcardList) {
            if (f.getStatistics().getToViewNext().isEqual(currentDate)) {
                dueFlashcards.add(f);
            }
        }
        return dueFlashcards;
    }

    /**
     * Gets flashcards that are overdue for revision from a given list of flashcards.
     * @param flashcardList list of flashcards to be checked
     * @return list of flashcards containing flashcards overdue for revision
    */
    public List<Flashcard> getOverdueFlashcards(List<Flashcard> flashcardList) {
        List<Flashcard> overdueFlashcards = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        //Gets overdue flashcards
        for (Flashcard f : flashcardList) {
            if (f.getStatistics().getToViewNext().isBefore(currentDate)) {
                overdueFlashcards.add(f);
            }
        }

        return overdueFlashcards;
    }

    /**
     * Formats a list of flashcards for output. Perquisites: List has at least one element in it.
     * @param dueFlashcards list of flashcards (greater than size 0)
     * @return String formatted flashcard display
     *
     */
    public String formatDueFlashcardListStringHelper(List<Flashcard> dueFlashcards) {
        int size = dueFlashcards.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= size; i++) {
            Flashcard flashcard = dueFlashcards.get(i - 1);
            sb.append(i + ". ");
            sb.append(flashcard.getTitle() + " - ");
            sb.append(flashcard.getQuestion());
            if (i != size) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Formats a list of flashcards for output. Perquisites: List has at least one element in it.
     * @param overdueFlashcards list of flashcards (greater than size 0)
     * @return String formatted flashcard display
     *
     */
    public String formatOverdueFlashcardListStringHelper(List<Flashcard> overdueFlashcards) {
        int size = overdueFlashcards.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= size; i++) {
            Flashcard flashcard = overdueFlashcards.get(i - 1);
            sb.append(i + ". ");
            sb.append(flashcard.getTitle() + " - ");
            sb.append(flashcard.getQuestion());
            sb.append(" (Was due on ");
            sb.append(flashcard.getStatistics().getToViewNext().toString());
            sb.append(")");
            if (i != size) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
