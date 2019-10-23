package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.McqFlashcard;

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
    private static final String MESSAGE_INVALID_CHOICE = "The index of the choice you chose is invalid.";

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

        Answer updatedAnswer = updateAnswer(lastViewedFlashcard);
        boolean isCorrect = lastViewedFlashcard.checkAnswer(updatedAnswer);

        if (isCorrect) {
            return new CommandResult("Your answer: " + updatedAnswer.toString() + " is correct.\n");
        } else {
            return new CommandResult("Your answer: " + updatedAnswer.toString() + " is incorrect.\n"
                    + "The correct answer is: " + lastViewedFlashcard.getAnswer().toString());
        }
    }

    /**
     * Retrieves the content of the MCQ answer if it is a MCQ Flashcard.
     * @param flashcard The last viewed flashcard.
     * @return An updated answer if the flashcard is a MCQ one, if not it returns the original answer.
     * @throws CommandException If the choice index is invalid.
     */
    public Answer updateAnswer(Flashcard flashcard) throws CommandException {
        if (!flashcard.isMcq()) {
            return answer;
        } else {
            McqFlashcard mcqCard = (McqFlashcard) flashcard;
            int index = Integer.parseInt(answer.getAnswer()) - 1;
            if (index >= mcqCard.getChoices().size()) {
                throw new CommandException(MESSAGE_INVALID_CHOICE);
            }
            Answer newAnswer = new Answer(mcqCard.getChoices().get(index).getChoice());
            return newAnswer;
        }
    }

}
