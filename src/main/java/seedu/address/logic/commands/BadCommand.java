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
import seedu.address.model.deadline.Task;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;


/**
 * Set certain FlashCards as 'Bad'
 * This will then add these set of flashcards into a certain Deadline
 */
public class BadCommand extends Command {

    public static final String COMMAND_WORD = "bad";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets a specific flashCard identified by the index number used in the displayed flashCard list.\n"
            + "as a 'Bad' flashcard that will require re-test.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Flashcard has been added into Deadlines!";

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
        Question q = badFlashcard.getQuestion();
        Task task = new Task("To Do: Bad Questions");
        //TODO: add questions and due date into filtered bad flashcards list
        Deadline deadline = new Deadline(task, BadQuestions.getBadDeadline());
        model.addDeadline(deadline);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
