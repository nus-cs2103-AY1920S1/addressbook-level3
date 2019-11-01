package seedu.mark.logic.commands;

import static seedu.mark.logic.parser.CliSyntax.PREFIX_HIGHLIGHT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_PARAGRAPH;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TO_NEW_PARAGRAPH;

import seedu.mark.commons.core.index.Index;
import seedu.mark.commons.exceptions.IllegalValueException;
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
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.storage.Storage;

/**
 * Edits the pre-existing annotations of a requested paragraph.
 */
public class EditAnnotationCommand extends AnnotationCommand {

    public static final String COMMAND_WORD = "annotate-edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits a pre-existing annotation of the offline copy identified by the index used "
            + "in the displayed bookmark list. You may specify a NEW_PARA_ID to move the annotation from "
            + "paragraph ORIG_PARA_ID to paragraph NEW_PARA_ID.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + PREFIX_PARAGRAPH + "ORIG_PARA_ID"
            + "[" + PREFIX_TO_NEW_PARAGRAPH + "NEW_PARA_ID]"
            + "[" + PREFIX_NOTE + "NEW_NOTE]"
            + "[" + PREFIX_HIGHLIGHT + "NEW_HIGHLIGHT]"
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PARAGRAPH + "p3 "
            + PREFIX_TO_NEW_PARAGRAPH + "p2 "
            + PREFIX_NOTE + "edited note content "
            + PREFIX_HIGHLIGHT + "orange";
    public static final String MESSAGE_TARGET_NO_PHANTOM = "You cannot move an annotation to a phantom paragraph.";
    public static final String MESSAGE_PHANTOM_CANNOT_HIGHLIGHT = "You cannot change the highlight "
            + "of a phantom paragraph.";
    public static final String MESSAGE_NOTHING_TO_EDIT = "Paragraph %1$s has no annotations to edit.";
    public static final String MESSAGE_SUCCESS = "Annotation at paragraph %1$s successfully modified:\n%2$s";
    public static final String MESSAGE_MOVED_TO = "This has been moved to paragraph %s";

    private final ParagraphIdentifier newPid;
    private final AnnotationNote newNote;
    private final Highlight newHighlight;

    public EditAnnotationCommand(Index index, ParagraphIdentifier origPid,
                                 ParagraphIdentifier newPid,
                                 AnnotationNote newNote, Highlight newColour) {
        super(index, origPid);
        this.newPid = newPid;
        this.newNote = newNote;
        this.newHighlight = newColour;
    }

    /**
     * Executes the {@code EditAnnotationCommand} and returns an {@code OfflineCommandResult}.
     *
     * @throws CommandException if bookmark index, cache, pid(s) are invalid, original paragraph has no annotation
     *          or target paragraph is phantom.
     */
    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        Bookmark oldBkmark = getRequiredBookmark(model);
        //TODO: refactor to prevent repetition
        OfflineDocument docOriginal = getRequiredDoc(model);
        OfflineDocument doc = docOriginal.copy();

        Paragraph originalP;
        Paragraph newP = null;

        if (newPid != null && newPid.isStray()) {
            throw new CommandException(MESSAGE_TARGET_NO_PHANTOM);
        }

        try {
            originalP = doc.getParagraph(getPid());
            if (newPid != null) {
                newP = doc.getParagraph(newPid);
            }
        } catch (IllegalValueException e) {
            throw new CommandException(EditAnnotationCommand.COMMAND_WORD + ": " + e.getMessage());
        }

        if (newNote == null && newPid == null && getPid().isStray()) {
            throw new CommandException(MESSAGE_PHANTOM_CANNOT_HIGHLIGHT);
        }

        if (!originalP.hasAnnotation()) {
            throw new CommandException(String.format(EditAnnotationCommand.MESSAGE_NOTHING_TO_EDIT, getPid()));
        }


        Annotation newAnnotation = getNewAnnotation(originalP);

        moveAnnotation(originalP, newP, newAnnotation, doc);

        model.updateDocument(doc);

        Bookmark newBkmark = new Bookmark(oldBkmark.getName(),
                oldBkmark.getUrl(), oldBkmark.getRemark(), oldBkmark.getFolder(),
                oldBkmark.getTags(), oldBkmark.getCachedCopies());

        newBkmark.updateCachedCopy(doc);
        model.setBookmark(oldBkmark, newBkmark);

        String returnMsg = newAnnotation.toString();
        if (newP != null) {
            returnMsg = returnMsg + "\n" + String.format(MESSAGE_MOVED_TO, newPid);
        }

        model.saveMark(String.format(MESSAGE_SUCCESS, getPid(), returnMsg));
        return new OfflineCommandResult(String.format(MESSAGE_SUCCESS, getPid(), returnMsg));
    }

    private Annotation getNewAnnotation(Paragraph originalP) {
        Annotation newAnnotation = originalP.getAnnotation();
        if (newHighlight != null) {
            newAnnotation.setHighlight(newHighlight);
        }
        if (newNote != null) {
            newAnnotation.setNote(newNote);
        }
        return newAnnotation;
    }

    private void moveAnnotation(Paragraph oldP, Paragraph newP, Annotation newAnnotation, OfflineDocument doc) throws CommandException {
        if (newP != null) {
            newP.addAnnotation(newAnnotation);
            if (oldP.isTrueParagraph()) {
                oldP.removeAnnotation();
            } else {
                try {
                    doc.removePhantom(getPid());
                } catch (IllegalValueException e) {
                    assert false : "should not get here since it is already checked";
                    throw new CommandException(e.getMessage());
                }
            }
        }
    }
}
