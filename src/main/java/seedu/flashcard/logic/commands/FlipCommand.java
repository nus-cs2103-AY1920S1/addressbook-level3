package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * Command to input an answer to the last viewed flashcard and see the correct answer.
 */
public class FlipCommand extends Command {

    public static final String COMMAND_WORD = "flip";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Takes in an answer as a parameter "
            + "and displays the correct answer of the flashcard that is being viewed.\n"
            + "Parameters: ANSWER\n"
            + "Example: " + COMMAND_WORD + "1";

    public static final String MESSAGE_SUCCESS = "Flashcard flipped";
    private static final String MESSAGE_NULL_VIEW_FLASHCARD = "There are no viewed flashcards";
    public final Answer answer;

    public FlipCommand(Answer answer) {
        this.answer = answer;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Flashcard lastViewedFlashcard = model.getLastViewedFlashcard();
        if (lastViewedFlashcard == null) {
            throw new CommandException(MESSAGE_NULL_VIEW_FLASHCARD);
        }
        Answer correctAnswer = lastViewedFlashcard.getAnswer();
        if (answer.equals(correctAnswer)) {
            lastViewedFlashcard.getScore().incrementCorrectAnswer();
            return new CommandResult("Your answer: " + answer.toString() + " is correct.\n");
        } else {
            lastViewedFlashcard.getScore().incrementWrongAnswer();
            return new CommandResult("Your answer: " + answer.toString() + " is incorrect.\n"
                + "The correct answer is: " + correctAnswer.toString());
        }
    }
}
