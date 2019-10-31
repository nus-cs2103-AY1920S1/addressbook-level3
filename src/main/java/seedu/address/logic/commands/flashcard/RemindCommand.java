package seedu.address.logic.commands.flashcard;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.REMIND;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.FlashcardCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.note.RemindFeatureUtil;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;

/**
 *
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = REMIND;

    public static final String MESSAGE_NO_DUE_AND_OVERDUE_FLASHCARDS = "Well done - No due or overdue flashcards!";

    private RemindFeatureUtil remindFeatureUtil = new RemindFeatureUtil();

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
            sb.append(remindFeatureUtil.MESSAGE_DUE_FLASHCARD_HELPER + "\n");
            sb.append(remindFeatureUtil.formatDueFlashcardListStringHelper(dueFlashcards));
            return sb.toString();
        } else if (dueFlashcards.isEmpty()) {
            sb.append(remindFeatureUtil.MESSAGE_OVERDUE_FLASHCARD_HELPER + "\n");
            sb.append(remindFeatureUtil.formatOverdueFlashcardListStringHelper(overdueFlashcards));
            return sb.toString();
        } else {
            sb.append(remindFeatureUtil.MESSAGE_DUE_FLASHCARD_HELPER + "\n");
            sb.append(remindFeatureUtil.formatDueFlashcardListStringHelper(dueFlashcards) + "\n");
            sb.append(remindFeatureUtil.MESSAGE_OVERDUE_FLASHCARD_HELPER + "\n");
            sb.append(remindFeatureUtil.formatOverdueFlashcardListStringHelper(overdueFlashcards));
            return sb.toString();

        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Flashcard> flashcardList = model.getFilteredFlashcardList();
        List<Flashcard> overdueFlashcards = remindFeatureUtil.getOverdueFlashcards(flashcardList);
        List<Flashcard> dueFlashcards = remindFeatureUtil.getDueFlashcards(flashcardList);

        return new FlashcardCommandResult(stringOutputFormatHelper(overdueFlashcards, dueFlashcards));
    }
}
