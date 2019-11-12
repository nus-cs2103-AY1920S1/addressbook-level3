package seedu.address.logic.commands.global;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.GlobalCommandResult;
import seedu.address.logic.commands.flashcard.RemindFeatureUtil;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;


/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting StudyBuddy application as requested ...";
    public static final String MESSAGE_EXIT_DISAPPOINTMENT = "Exiting StudyBuddy application as requested ... try to "
            + "revise the overdue flashcards next time!";
    public static final String MESSAGE_EXIT_CHECK_UNREVISED_FLASHCARDS_START = "Are you sure you want to exit? You "
            + "still have the following flashcards overdue or left to revise for today: ";
    public static final String MESSAGE_EXIT_CHECK_UNREVISED_FLASHCARDS_END = "Type 'exit' again to exit the "
            + "application!";

    private RemindFeatureUtil remindFeatureUtil = new RemindFeatureUtil();

    /**
     * Formats list of due and overdue flashcards for output.
     * @param overdueFlashcards List of all overdue flashcards
     * @param dueFlashcards List of all due flashcards
     * @return String formatted output of due and overdue flashcards
     */
    public String stringOutputFormatHelper(List<Flashcard> overdueFlashcards, List<Flashcard> dueFlashcards) {
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_EXIT_CHECK_UNREVISED_FLASHCARDS_START + "\n");
        if (overdueFlashcards.isEmpty()) {
            sb.append(remindFeatureUtil.MESSAGE_DUE_FLASHCARD_HELPER + "\n");
            sb.append(remindFeatureUtil.formatDueFlashcardListStringHelper(dueFlashcards) + "\n");
        } else if (dueFlashcards.isEmpty()) {
            sb.append(remindFeatureUtil.MESSAGE_OVERDUE_FLASHCARD_HELPER + "\n");
            sb.append(remindFeatureUtil.formatOverdueFlashcardListStringHelper(overdueFlashcards) + "\n");
        } else {
            sb.append(remindFeatureUtil.MESSAGE_DUE_FLASHCARD_HELPER + "\n");
            sb.append(remindFeatureUtil.formatDueFlashcardListStringHelper(dueFlashcards) + "\n");
            sb.append(remindFeatureUtil.MESSAGE_OVERDUE_FLASHCARD_HELPER + "\n");
            sb.append(remindFeatureUtil.formatOverdueFlashcardListStringHelper(overdueFlashcards) + "\n");
        }
        sb.append(MESSAGE_EXIT_CHECK_UNREVISED_FLASHCARDS_END);
        return sb.toString();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Flashcard> flashcardList = model.getFilteredFlashcardList();
        List<Flashcard> overdueFlashcards = remindFeatureUtil.getOverdueFlashcards(flashcardList);
        List<Flashcard> dueFlashcards = remindFeatureUtil.getDueFlashcards(flashcardList);

        if (overdueFlashcards.isEmpty() && dueFlashcards.isEmpty()) {
            return new GlobalCommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        } else if (!CommandHistory.getLastCommand().isPresent()) {
            return new GlobalCommandResult(stringOutputFormatHelper(overdueFlashcards, dueFlashcards),
                    false, false);
        } else if (CommandHistory.getLastCommand().get() instanceof ExitCommand) {
            return new GlobalCommandResult(MESSAGE_EXIT_DISAPPOINTMENT, false, true);
        } else {
            return new GlobalCommandResult(stringOutputFormatHelper(overdueFlashcards, dueFlashcards),
                    false, false);
        }
    }

}
