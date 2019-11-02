package seedu.address.logic.commands.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.SHOW;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.FlashcardCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.FlashcardTabWindowController;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class ShowFlashcardAnswerCommand extends Command {

    public static final String COMMAND_WORD = SHOW;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a flashcard.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String SHOW_FLASHCARD_ANSWER_SUCCESS = "Flashcard answer loaded";

    public ShowFlashcardAnswerCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (FlashcardTabWindowController.getCurrFlashcard().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_FLASHCARD_LOADED);
        }

        if (FlashcardTabWindowController.isAnswerShown()) {
            throw new CommandException(Messages.MESSAGE_ANSWER_ALREADY_LOADED);
        }

        return new FlashcardCommandResult(SHOW_FLASHCARD_ANSWER_SUCCESS, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
