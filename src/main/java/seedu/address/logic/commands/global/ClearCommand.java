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
import seedu.address.model.Model;
import seedu.address.model.StudyBuddyPro;

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

        StudyBuddyPro newStudyBuddyBook = new StudyBuddyPro();

        if (!isGlobal) {
            switch (LogicManager.getMode()) {
            case CHEATSHEET:
                newStudyBuddyBook.setCheatSheets(new ArrayList<>());
                newStudyBuddyBook.setNotes(model.getStudyBuddyPro().getNoteList());
                newStudyBuddyBook.setFlashcards(model.getStudyBuddyPro().getFlashcardList());
                newStudyBuddyBook.addAllTags(model.getStudyBuddyPro().getTagList());
                break;

            case FLASHCARD:
                newStudyBuddyBook.setCheatSheets(model.getStudyBuddyPro().getCheatSheetList());
                newStudyBuddyBook.setNotes(model.getStudyBuddyPro().getNoteList());
                newStudyBuddyBook.setFlashcards(new ArrayList<>());
                newStudyBuddyBook.addAllTags(model.getStudyBuddyPro().getTagList());
                break;

            case NOTE:
                newStudyBuddyBook.setCheatSheets(model.getStudyBuddyPro().getCheatSheetList());
                newStudyBuddyBook.setNotes(new ArrayList<>());
                newStudyBuddyBook.setFlashcards(model.getStudyBuddyPro().getFlashcardList());
                newStudyBuddyBook.addAllTags(model.getStudyBuddyPro().getTagList());
                break;

            default:
                // error??
                throw new CommandException(SPECIFY_MODE);
            }
        }

        model.setStudyBuddyPro(newStudyBuddyBook);

        return isGlobal
            ? new GlobalCommandResult(MESSAGE_SUCCESS + "the entire StudyBuddyPro!")
            : new GlobalCommandResult(MESSAGE_SUCCESS + LogicManager.getMode().toString() + "!");

    }
}
