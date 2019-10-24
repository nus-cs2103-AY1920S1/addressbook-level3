package seedu.mark.logic.commands;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.OfflineCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.annotation.Annotation;
import seedu.mark.model.annotation.AnnotationNote;
import seedu.mark.model.annotation.Highlight;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.storage.Storage;

public class DeleteAnnotationHighlightCommand extends DeleteAnnotationCommand {

    public static final String MESSAGE_SUCCESS =
            "Note is successfully detached from paragraph and has become stray. Find it below to delete it.";
    public static final String MESSAGE_PHANTOM = "You don't have to remove highlight from a phantom paragraph;\n"
            + "It's already absent :)";


    public DeleteAnnotationHighlightCommand(Index index, ParagraphIdentifier pid) {
        super(index, pid, false, true);
    }


    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        OfflineDocument doc = getRequiredDoc(model);
        Paragraph p = doc.getParagraph(getPid());

        if (!p.isTrueParagraph()) {
            throw new CommandException(MESSAGE_PHANTOM);
        }

        if (!p.hasAnnotation()) {
            throw new CommandException(DeleteAnnotationCommand.MESSAGE_NOTHING_TO_DELETE);
        }

        Annotation an = p.removeAnnotation();
        doc.addPhantom(an);

        model.updateDocument(doc);

        model.saveMark(MESSAGE_SUCCESS);
        return new OfflineCommandResult(MESSAGE_SUCCESS);
    }
}
