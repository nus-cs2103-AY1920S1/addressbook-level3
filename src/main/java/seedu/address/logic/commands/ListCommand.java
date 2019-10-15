package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.LIST;
import static seedu.address.commons.core.Messages.SPECIFY_MODE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CHEATSHEETS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = LIST;

    public static String MESSAGE_SUCCESS = "Listed all ";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

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

        return new CommandResult(MESSAGE_SUCCESS + LogicManager.getMode().toString());
    }
}
