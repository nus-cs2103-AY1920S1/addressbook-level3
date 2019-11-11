//@@author dalsontws

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.deadline.BadQuestions;
import seedu.address.model.deadline.DueDate;
import seedu.address.model.flashcard.exceptions.NoBadFlashCardException;


/**
 * Finds and lists all flashcards that are indicated as "bad".
 *
 */
public class ListBadCommand extends Command {

    public static final String COMMAND_WORD = "listbad";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all FlashCard(s) which have been "
            + "labeled as bad flashcards that to remind the users to revise for these flashcards.\n"
            + "at a later date.\n"
            + "Example: " + COMMAND_WORD + " d>12/11/2019";

    public static final String NO_BAD_FLASHCARDS = "No 'Bad' rated FlashCards for the date specified!";

    private final DueDate date;

    public ListBadCommand(DueDate d) {
        this.date = d;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        BadQuestions badQuestions = new BadQuestions();

        try {
            String bad = badQuestions.showBadQuestionsList(date);
            return new CommandResult(String.format(bad));
        } catch (NoBadFlashCardException e) {
            return new CommandResult(NO_BAD_FLASHCARDS);
        }
    }
}
