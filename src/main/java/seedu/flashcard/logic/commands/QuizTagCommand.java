package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.logic.parser.FlashcardListParser;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.tag.Tag;

/**
 * Command to view the MCQ choices in a flashcard. The answer will not be shown.
 */
public class QuizTagCommand extends Command {

    public static final String COMMAND_WORD = "quiztag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Starts a series of quizzes for all the flashcards in a certain tag.\n"
            + "Each flashcard should be preceded by a flip command to answer the flashcard. \n"
            + "Quiz mode will persist until all flashcards are answered or the user inputs 'end'\n"
            + "Parameters: TAG (must be an existing tag)\n"
            + "Example: " + COMMAND_WORD + " geography";

    public static final String MESSAGE_SUCCESS = "Quiz mode has started.";
    public static final String TAG_INVALID = "There are no quizable flashcards in this tag.";
    private final Set<Tag> target;
    private final Integer duration;

    public QuizTagCommand(Set<Tag> inputTag, Integer duration) {
        requireNonNull(inputTag);
        this.target = inputTag;
        this.duration = duration;
    }

    /**
     * Showing a whole flashcard in the command bot, the answer will not be shown.
     * @param model list of flashcards
     * @return the execution result containing the flashcard without the answer.
     * @throws CommandException error encountered during execution of command.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredFlashcardList(model.getHasTagPredicate(target));
        List<Flashcard> taggedList = new ArrayList<>();
        List<Flashcard> filteredList = model.getFilteredFlashcardList();
        for (int i = 0; i < filteredList.size(); i++) {
            taggedList.add(filteredList.get(i));
        }
        if (taggedList.isEmpty()) {
            FlashcardListParser.setQuizMode(false);
            throw new CommandException(TAG_INVALID);
        }
        model.setQuizDuration(duration);
        model.setQuiz(taggedList);
        Flashcard firstCard = model.getQuiz().quizCard();
        return new CommandResult(MESSAGE_SUCCESS, true, firstCard.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof QuizTagCommand
                && target.equals(((QuizTagCommand) other).target));
    }
}
