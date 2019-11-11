//@@author:dalsontws

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deadline.BadQuestions;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.deadline.DueDate;
import seedu.address.model.deadline.Task;
import seedu.address.model.deadline.exceptions.DeadlineNotFoundException;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.exceptions.FlashCardNotFoundException;

/**
 * Deletes a flashCard identified using it's displayed index from the address book.
 */
public class RemoveBadCommand extends Command {

    public static final String COMMAND_WORD = "removebad";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove the question identified by the index number used in the displayed 'bad' flashcard list.\n"
            + "Ensure date of the 'bad' flashcard list is specified.\n"
            + "Parameters: DATE (must be a specific date with 'bad' questions)\n "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "d>12/11/2019 i>1";

    public static final String MESSAGE_DELETE_DEADLINE_SUCCESS = "Removed 'bad' flashcard: %1$s";
    public static final String INVALID_DEADLINE = "Date specified does not have 'bad' flashcards";
    public static final String INVALID_INDEX = "No such flashcard found, check your index!";

    private final DueDate date;
    private final int targetIndex;

    public RemoveBadCommand(DueDate date, int targetIndex) {
        this.date = date;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        BadQuestions badQuestions = new BadQuestions();

        try {
            FlashCard flashCardToDelete = badQuestions.removeBadQuestion(date, targetIndex);

            if (badQuestions.getBadQuestionsList(date).size() == 0) {
                model.deleteDeadline(
                    new Deadline(
                            new Task("ToDo: Bad Questions"),
                            date
                    )
                );
            }

            badQuestions.saveAsJson(badQuestions);
            return new CommandResult(
                    String.format(MESSAGE_DELETE_DEADLINE_SUCCESS, flashCardToDelete.getQuestion().toString()));
        } catch (DeadlineNotFoundException e1) {
            return new CommandResult(INVALID_DEADLINE);
        } catch (FlashCardNotFoundException e2) {
            return new CommandResult(INVALID_INDEX);
        }
    }
}
