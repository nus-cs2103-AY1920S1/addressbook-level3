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
 * Deletes the {@code Highlight} of an {@code Annotation} from a {@code Paragraph}.
 */
public class DeleteAnnotationHighlightCommand extends DeleteAnnotationCommand {

    public static final String MESSAGE_SUCCESS = "This note has been successfully detached from its paragraph. Find "
            + "it at the bottom of this document to delete it.";
    public static final String MESSAGE_PHANTOM = "You don't have to remove highlight from a phantom paragraph;\n"
            + "It's already absent :)";


    public DeleteAnnotationHighlightCommand(Index index, ParagraphIdentifier pid) {
        super(index, pid, false, true);
    }


    /**
     * Executes the {@code DeleteAnnotationHighlightCommand} and returns an {@code OfflineCommandResult}.
     * @throws CommandException if bookmark index, cache, pid is invalid, paragraph is phantom or has nothing to delete.
     */
    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        Bookmark oldBkmark = getRequiredBookmark(model);
        OfflineDocument doc = getRequiredDocCopy(oldBkmark);


        Paragraph p;

        try {
            p = doc.getParagraph(getPid());
        } catch (IllegalValueException e) {
            throw new CommandException(DeleteAnnotationCommand.COMMAND_WORD + ": " + e.getMessage());
        }

        if (!p.isTrueParagraph()) {
            throw new CommandException(MESSAGE_PHANTOM);
        }

        if (!p.hasAnnotation()) {
            throw new CommandException(DeleteAnnotationCommand.MESSAGE_NOTHING_TO_DELETE);
        }

        Annotation an = p.removeAnnotation();
        if (an.hasNote()) {
            doc.addPhantom(an);
        }

        String savedMsg = MESSAGE_SUCCESS;
        Bookmark newBkmark = oldBkmark.copy();
        saveState(model, oldBkmark, newBkmark, doc, savedMsg);

        return new OfflineCommandResult(savedMsg);
    }
}
