package seedu.mark.logic.commands;

import seedu.mark.commons.core.index.Index;
import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.OfflineCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.annotation.Annotation;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.storage.Storage;

/**
 * Deletes an {@code Annotation} from a {@code Paragraph}.
 * If the {@code Paragraph} is a {@code PhantomParagraph},
 * then the entire {@code Paragraph} is removed from the document.
 */
public class DeleteAnnotationAllCommand extends DeleteAnnotationCommand {

    public static final String MESSAGE_PHANTOM_REMOVED = "Phantom paragraph removed.";

    public static final String MESSAGE_SUCCESS = "Annotation successfully removed from paragraph %1$s:\n%2$s";
    private static final String MESSAGE_ORIG_HIGHLIGHT = "%s highlight";
    private static final String MESSAGE_ORIG_NOTE = "note \"%s\"";


    public DeleteAnnotationAllCommand(Index index, ParagraphIdentifier pid) {
        super(index, pid, true, true);
    }


    /**
     * Executes the {@code DeleteAnnotationAllCommand} and returns an {@code OfflineCommandResult}.
     * @throws CommandException if bookmark index, cache, pid is invalid or paragraph has no annotations to delete.
     */
    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        Bookmark oldBkmark = getRequiredBookmark(model);
        //TODO: refactor to prevent repetition
        OfflineDocument doc = getRequiredDocCopy(oldBkmark);

        Paragraph p;

        try {
            p = doc.getParagraph(getPid());
        } catch (IllegalValueException e) {
            throw new CommandException(DeleteAnnotationCommand.COMMAND_WORD + ": " + e.getMessage());
        }

        if (!p.hasAnnotation()) {
            throw new CommandException(DeleteAnnotationCommand.MESSAGE_NOTHING_TO_DELETE);
        }

        Annotation note = p.removeAnnotation();
        String noteRemoved = String.format(MESSAGE_ORIG_NOTE, note.getNote());

        if (!p.isTrueParagraph()) {
            try {
                doc.removePhantom(getPid());
            } catch (IllegalValueException e) {
                assert false : "Should never come here since paragraph already checked to exist.";
                throw new CommandException(DeleteAnnotationCommand.COMMAND_WORD + ": " + e.getMessage());
            }
            noteRemoved = noteRemoved + "\n" + MESSAGE_PHANTOM_REMOVED;
        } else {
            noteRemoved = String.format(MESSAGE_ORIG_HIGHLIGHT, note.getHighlight()) + " with " + noteRemoved;
        }

        String savedMsg = String.format(MESSAGE_SUCCESS, getPid(), noteRemoved);

        Bookmark newBkmark = oldBkmark.copy();
        saveState(model, oldBkmark, newBkmark, doc, savedMsg);

        return new OfflineCommandResult(savedMsg);
    }
}
