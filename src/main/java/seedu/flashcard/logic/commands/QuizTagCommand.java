package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;


import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.logic.parser.ParserUtil;
import seedu.flashcard.logic.parser.QuizCommandParser;
import seedu.flashcard.logic.parser.exceptions.ParseException;
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

    public static final String MESSAGE_INVALID_FLASHCARD_TAG = "The tag you entered is invalid!";
    private final Set<Tag> target;

    public QuizTagCommand(Set<Tag> inputTag) {
        requireNonNull(inputTag);
        this.target = inputTag;
    }

    /**
     * Showing a whole flashcard in the command bot, the answer will not be shown.
     * @param model list of flashcards
     * @return the execution result containing the flashcard without the answer.
     * @throws CommandException error encountered during execution of command.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredFlashcardList(model.getHasTagPredicate(target));
        List<Flashcard> taggedList = model.getFilteredFlashcardList();
        for (Flashcard flashcard : taggedList) {
            try {
                Command quizCommand = new QuizCommandParser().parse(String.format("%d", taggedList.indexOf(flashcard) + 1));
                quizCommand.execute(model);
            } catch (ParseException e) {
                throw new CommandException(e.toString());
            }
        }
        return new CommandResult("SUCCESS");
    }
}
