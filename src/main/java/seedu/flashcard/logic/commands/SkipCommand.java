package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.logic.parser.FlashcardListParser;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.Quiz;
import seedu.flashcard.model.flashcard.Flashcard;


/**
 * Command to input an answer to the last viewed flashcard and see the correct answer.
 */
public class SkipCommand extends Command {

    public static final String COMMAND_WORD = "skip";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Skips question and automatically mark wrong \n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Flashcard skipped";
    public static final String MESSAGE_NULL_QUIZ_FLASHCARD = "There are no quiz-able flashcards";


    public SkipCommand() {

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Flashcard quizCard = model.getQuiz().quizCard();
        if (quizCard == null) {
            throw new CommandException(MESSAGE_NULL_QUIZ_FLASHCARD);
        }
        quizCard.skipAnswer();
        model.getQuiz().discardFirstCard();
        String flashcardString = buildFlashcardString(model.getQuiz());

        return new CommandResult(MESSAGE_SUCCESS, true, flashcardString);

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

}
