package seedu.address.logic.commands.global;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.CLEAR;
import static seedu.address.commons.core.Messages.SPECIFY_MODE;

import java.util.ArrayList;

import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.GlobalCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = CLEAR;
    public static final String MESSAGE_SUCCESS = "Cleared ";

    private boolean isGlobal;

    public ClearCommand() {

    }

    public ClearCommand(boolean isGlobal) {
        this.isGlobal = isGlobal;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        AddressBook newStudyBuddyBook = new AddressBook();

        if (!isGlobal) {
            switch (LogicManager.getMode()) {
            case CHEATSHEET:
                newStudyBuddyBook.setCheatSheets(new ArrayList<>());
                newStudyBuddyBook.setNotes(model.getAddressBook().getNoteList());
                newStudyBuddyBook.setFlashcards(model.getAddressBook().getFlashcardList());
                break;

            case FLASHCARD:
                newStudyBuddyBook.setCheatSheets(model.getAddressBook().getCheatSheetList());
                newStudyBuddyBook.setNotes(model.getAddressBook().getNoteList());
                newStudyBuddyBook.setFlashcards(new ArrayList<>());
                break;

            case NOTE:
                newStudyBuddyBook.setCheatSheets(model.getAddressBook().getCheatSheetList());
                newStudyBuddyBook.setNotes(new ArrayList<>());
                newStudyBuddyBook.setFlashcards(model.getAddressBook().getFlashcardList());
                break;

            default:
                // error??
                throw new CommandException(SPECIFY_MODE);
            }
        }

        model.setAddressBook(newStudyBuddyBook);

        return isGlobal
            ? new GlobalCommandResult(MESSAGE_SUCCESS + "the entire StudyBuddy book!")
            : new GlobalCommandResult(MESSAGE_SUCCESS + LogicManager.getMode().toString() + " book!");

    }
}
