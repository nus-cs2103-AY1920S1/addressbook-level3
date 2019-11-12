package seedu.address.logic.commands.flashcard;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.REMIND;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.FlashcardCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;

/**
 * Reminds the user about due and overdue flashcards.
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = REMIND;

    public static final String MESSAGE_NO_DUE_AND_OVERDUE_FLASHCARDS = "Well done - No due or overdue flashcards!";

    private static final Logger logger = LogsCenter.getLogger(RemindCommand.class);

    private RemindFeatureUtil remindFeatureUtil = new RemindFeatureUtil();


    /**
     * Formats output of list of due and overdue flashcards to the user.
     * @param overdueFlashcards list of overdue flashcards
     * @param dueFlashcards list of due flashcards
     * @return String output for the user.
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

        logger.info("Executing RemindCommand");

        List<Flashcard> flashcardList = model.getFilteredFlashcardList();
        List<Flashcard> overdueFlashcards = remindFeatureUtil.getOverdueFlashcards(flashcardList);
        List<Flashcard> dueFlashcards = remindFeatureUtil.getDueFlashcards(flashcardList);

        return new FlashcardCommandResult(stringOutputFormatHelper(overdueFlashcards, dueFlashcards));
    }
}
