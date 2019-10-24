package seedu.mark.logic.commands;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.OfflineCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.annotation.Annotation;
import seedu.mark.model.annotation.AnnotationNote;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.storage.Storage;

public class DeleteAnnotationAllCommand extends DeleteAnnotationCommand {

    public static final String MESSAGE_SUCCESS = "Annotation successfully removed from paragraph %1$s:\n%2$s";
    private static final String MESSAGE_ORIG_HIGHLIGHT = "%s highlight";
    private static final String MESSAGE_ORIG_NOTE = "note \"%s\"";

    public static final String MESSAGE_PHANTOM_REMOVED = "Phantom paragraph removed.";

    public DeleteAnnotationAllCommand(Index index, ParagraphIdentifier pid) {
        super(index, pid, true, true);
    }


    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        OfflineDocument doc = getRequiredDoc(model);
        Paragraph p = doc.getParagraph(getPid());

        if (!p.hasAnnotation()) {
            throw new CommandException(DeleteAnnotationCommand.MESSAGE_NOTHING_TO_DELETE);
        }

        Annotation note = p.removeAnnotation();
        String noteRemoved = String.format(MESSAGE_ORIG_NOTE, note.getNote());

        if (!p.isTrueParagraph()) {
            doc.removePhantom(getPid());
            noteRemoved = noteRemoved + "\n" + MESSAGE_PHANTOM_REMOVED;
        } else {
            noteRemoved = String.format(MESSAGE_ORIG_HIGHLIGHT, note.getHighlight()) + " with " + noteRemoved;
        }

        model.updateDocument(doc);

        model.saveMark(String.format(MESSAGE_SUCCESS, getPid(), noteRemoved));
        return new OfflineCommandResult(String.format(MESSAGE_SUCCESS, getPid(), noteRemoved));
    }
}
