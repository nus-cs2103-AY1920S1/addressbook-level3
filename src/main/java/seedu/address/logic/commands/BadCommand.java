//@@author: dalsontws

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deadline.BadQuestions;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.deadline.DueDate;
import seedu.address.model.deadline.Task;
import seedu.address.model.deadline.exceptions.DuplicateDeadlineException;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;

/**
 * Set certain FlashCards as 'Bad'
 * This will then add these set of flashcards into a certain Deadline
 */
public class BadCommand extends Command {

    public static final String COMMAND_WORD = "bad";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets a specific FlashCard identified by the\n "
            + "index number used in the displayed FlashCard list.\n"
            + "as a 'Bad' FlashCard that will require re-test.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "FlashCard has been added into Deadlines!";
    public static final String DUPLICATE_DEADLINE = "FlashCard has been added into an existing deadline!";

    private final Index index;

    public BadCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<FlashCard> lastShownList = model.getFilteredFlashCardList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
        }

        FlashCard badFlashcard = lastShownList.get(index.getZeroBased());

        Task task = new Task("To Do: Bad Questions");
        DueDate d = BadQuestions.getBadDeadline();
        Deadline deadline = new Deadline(task, d);

        //TODO: add questions and due date into filtered bad flashcards list
        Question q = badFlashcard.getQuestion();

        BadQuestions badQuestions = new BadQuestions();

        badQuestions.addBadQuestion(d, q);

        badQuestions.saveAsJson(badQuestions);

        try {
            model.addDeadline(deadline);
        } catch (DuplicateDeadlineException e) {
            return new CommandResult(DUPLICATE_DEADLINE);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
