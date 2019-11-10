package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.logic.parser.FlashcardListParser;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.Quiz;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.McqFlashcard;
import seedu.flashcard.model.flashcard.exceptions.CardNotFoundException;

/**
 * Command to input an answer to the last viewed flashcard and see the correct answer.
 */
public class FlipCommand extends Command {

    public static final String COMMAND_WORD = "flip";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Takes in an answer as a parameter "
            + "and displays the correct answer of the flashcard that is being viewed.\n"
            + "Parameters: ANSWER\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Flashcard flipped";
    public static final String MESSAGE_NULL_QUIZ_FLASHCARD = "There are no quiz-able flashcards";
    public static final String MESSAGE_INVALID_CHOICE = "The index of the choice you chose is invalid.";
    public static final String MESSAGE_MCQ_INDEX = "The answer to a MCQ flashcard must be a number.";

    public final Answer answer;

    public FlipCommand(Answer answer) {
        this.answer = answer;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Flashcard quizCard;
        try {
            quizCard = model.getQuiz().quizCard();
        } catch (CardNotFoundException e) {
            throw new CommandException(MESSAGE_NULL_QUIZ_FLASHCARD);
        }
        try {
            Answer updatedAnswer = updateAnswer(quizCard);
            boolean isCorrect = quizCard.checkAnswer(updatedAnswer);
            model.getQuiz().discardFirstCard();
            String resultString = buildResultString(updatedAnswer, isCorrect, quizCard);
            String flashcardString = buildFlashcardString(model.getQuiz());
            return new CommandResult(resultString, true, flashcardString);
        } catch (NumberFormatException e) {
            throw new CommandException(MESSAGE_MCQ_INDEX);
        }
    }

    /**
     * Retrieves the content of the MCQ answer if it is a MCQ Flashcard.
     *
     * @param flashcard The last viewed flashcard.
     * @return An updated answer if the flashcard is a MCQ one, if not it returns the original answer.
     * @throws CommandException If the choice index is invalid.
     */
    private Answer updateAnswer(Flashcard flashcard) throws CommandException {
        if (!flashcard.isMcq()) {
            return answer;
        } else {
            McqFlashcard mcqCard = (McqFlashcard) flashcard;
            int index = Integer.parseInt(answer.getAnswer()) - 1;
            if (index >= mcqCard.getChoices().size() || index <= 0) {
                throw new CommandException(MESSAGE_INVALID_CHOICE);
            }
            Answer newAnswer = new Answer(mcqCard.getChoices().get(index).getChoice());
            return newAnswer;
        }
    }

    /**
     * Builds the result string based on whether the answer is correct and checks if there's a next card.
     * @param updatedAnswer Parsed answer.
     * @param isCorrect If the answer was correct.
     * @param quizCard Card currently being quizzed.
     * @return Resultant string for CommandResult
     */
    private String buildResultString(Answer updatedAnswer, boolean isCorrect, Flashcard quizCard) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Your answer: ").append(updatedAnswer.toString());
        if (isCorrect) {
            builder.append(" is correct.\n");
        } else {
            builder.append(" is incorrect.\n")
                    .append("The correct answer is: ").append(quizCard.getAnswer()).append("\n");
        }

        return builder.toString();
    }

    /**
     * Builds the flashcard string to return if there are flashcards to quiz.
     * @param quiz Quiz object containing all quizable cards.
     * @return Resultant string for Flashcards
     */
    private String buildFlashcardString(Quiz quiz) {
        final StringBuilder builder = new StringBuilder();
        if (!quiz.isEmpty()) {
            builder.append("This is your next card:\n").append(quiz.quizCard());
        } else {
            FlashcardListParser.setQuizMode(false);
            builder.append("This quiz has ended.");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof FlipCommand
                && answer.equals(((FlipCommand) other).answer));
    }

}
