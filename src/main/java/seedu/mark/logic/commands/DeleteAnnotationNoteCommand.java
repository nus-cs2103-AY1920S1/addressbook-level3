package seedu.mark.logic.commands;

import seedu.mark.commons.core.index.Index;
import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.OfflineCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.annotation.AnnotationNote;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.storage.Storage;

/**
 * Deletes the {@code AnnotationNote} of an {@code Annotation} of a {@code Paragraph}.
 * If the {@code Paragraph} is a {@code PhantomParagraph}, the entire {@Paragraph} is removed.
 */
public class DeleteAnnotationNoteCommand extends DeleteAnnotationCommand {

    public static final String MESSAGE_SUCCESS =
            "Note successfully removed from paragraph %1$s:\nNote \"%2$s\"";

    public DeleteAnnotationNoteCommand(Index index, ParagraphIdentifier pid) {
        super(index, pid, true, false);
    }


    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        OfflineDocument doc = getRequiredDoc(model);
        Paragraph p;

        try {
            p = doc.getParagraph(getPid());
        } catch (IllegalValueException e) {
            throw new CommandException(DeleteAnnotationCommand.COMMAND_WORD + ": " + e.getMessage());
        }

        if (!p.hasAnnotation() || !p.hasNote()) {
            throw new CommandException(DeleteAnnotationCommand.MESSAGE_NOTHING_TO_DELETE);
        }

        AnnotationNote note = p.removeNote();
        if (!p.isTrueParagraph()) {
            try {
                doc.removePhantom(getPid());
            } catch (IllegalValueException e) {
                assert false : "Should never come here since paragraph already checked to exist.";
                throw new CommandException(DeleteAnnotationCommand.COMMAND_WORD + ": " + e.getMessage());
            }
        }

        model.updateDocument(doc);

        model.saveMark(String.format(MESSAGE_SUCCESS, getPid(), note));
        return new OfflineCommandResult(String.format(MESSAGE_SUCCESS, getPid(), note));
    }
}
