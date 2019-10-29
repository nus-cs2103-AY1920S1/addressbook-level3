package seedu.address.logic.commands.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.REMIND;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.FlashcardCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = REMIND;

    public static final String MESSAGE_NO_DUE_AND_OVERDUE_FLASHCARDS = "Well done - No due or overdue flashcards!";
    public static final String MESSAGE_DUE_FLASHCARD_HELPER = "Here are the flashcards due today:";
    public static final String MESSAGE_OVERDUE_FLASHCARD_HELPER = "Here are your overdue flashcards:";

    /**
     * Formats a list of flashcards for output. Perquisites: List has at least one element in it.
     * @param dueFlashcards list of flashcards (greater than size 0)
     * @return String formatted flashcard display
     *
     */
    private String formatDueFlashcardListStringHelper(List<Flashcard> dueFlashcards) {
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
    private String formatOverdueFlashcardListStringHelper(List<Flashcard> overdueFlashcards) {
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

    /**
     *
     * @param overdueFlashcards
     * @param dueFlashcards
     * @return
     */
    public String stringOutputFormatHelper(List<Flashcard> overdueFlashcards, List<Flashcard> dueFlashcards) {
        StringBuilder sb = new StringBuilder();
        if (overdueFlashcards.isEmpty() && dueFlashcards.isEmpty()) {
            return MESSAGE_NO_DUE_AND_OVERDUE_FLASHCARDS;
        } else if (overdueFlashcards.isEmpty()) {
            sb.append(MESSAGE_DUE_FLASHCARD_HELPER + "\n");
            sb.append(formatDueFlashcardListStringHelper(dueFlashcards));
            return sb.toString();
        } else if (dueFlashcards.isEmpty()) {
            sb.append(MESSAGE_OVERDUE_FLASHCARD_HELPER + "\n");
            sb.append(formatOverdueFlashcardListStringHelper(overdueFlashcards));
            return sb.toString();
        } else {
            sb.append(MESSAGE_DUE_FLASHCARD_HELPER + "\n");
            sb.append(formatDueFlashcardListStringHelper(dueFlashcards) + "\n");
            sb.append(MESSAGE_OVERDUE_FLASHCARD_HELPER + "\n");
            sb.append(formatOverdueFlashcardListStringHelper(overdueFlashcards));
            return sb.toString();

        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Flashcard> flashcardList = model.getFilteredFlashcardList();
        List<Flashcard> overdueFlashcards = new ArrayList<>();
        List<Flashcard> dueFlashcards = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        //Gets flashcards due today
        for (Flashcard f : flashcardList) {
            if (f.getStatistics().getToViewNext().isEqual(currentDate)) {
                dueFlashcards.add(f);
            }
        }
        //Gets overdue flashcards
        for (Flashcard f : flashcardList) {
            if (f.getStatistics().getToViewNext().isBefore(currentDate)) {
                overdueFlashcards.add(f);
            }
        }

        return new FlashcardCommandResult(stringOutputFormatHelper(overdueFlashcards, dueFlashcards));
    }
}