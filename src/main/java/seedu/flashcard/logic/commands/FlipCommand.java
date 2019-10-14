package seedu.flashcard.logic.commands;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * Command to see the answer of a flashcard, only to be used in the quiz mode
 */
public class FlipCommand extends CardRelatedCommand {

    public static final String COMMAND_WORD = "flip";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": flip the flashcard and check the answer.\n";

    public static final String MESSAGE_CHECK_ANSWER = "Here is the answer: ";

    /**
     * Return the result from executing the flip command.
     * @param flashcard the specific flashcard
     * @return the execution result
     */
    @Override
    public CommandResult execute(Flashcard flashcard) {
        String message = MESSAGE_CHECK_ANSWER.concat(flashcard.getAnswer().getAnswer());
        return new CommandResult(message, false, false);
    }

}
