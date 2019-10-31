package seedu.address.logic.commands.global;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.LIST;
import static seedu.address.commons.core.Messages.SPECIFY_MODE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CHEATSHEETS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.GlobalCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = LIST;

    public static final String MESSAGE_SUCCESS = "Listing all ";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String returnMsg = ":\n";

        switch (LogicManager.getMode()) {
        case CHEATSHEET:
            model.updateFilteredCheatSheetList(PREDICATE_SHOW_ALL_CHEATSHEETS);
            break;

        case FLASHCARD:
            model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
            break;

        case NOTE:
            model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
            break;

        default:
            // error??
            throw new CommandException(SPECIFY_MODE);
        }

        returnMsg += model.formatOutputListString(LogicManager.getMode());
        return new GlobalCommandResult(MESSAGE_SUCCESS + LogicManager.getMode().toString() + "s"
                + returnMsg);

    }
}
