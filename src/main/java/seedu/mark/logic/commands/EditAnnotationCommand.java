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
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PARAGRAPH + "ORIG_PARA_ID "
            + "[" + PREFIX_TO_NEW_PARAGRAPH + "NEW_PARA_ID] "
            + "[" + PREFIX_NOTE + "NEW_NOTE] "
            + "[" + PREFIX_HIGHLIGHT + "NEW_HIGHLIGHT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PARAGRAPH + "p3 "
            + PREFIX_TO_NEW_PARAGRAPH + "p2 "
            + PREFIX_NOTE + "edited note content "
            + PREFIX_HIGHLIGHT + "orange";
    public static final String MESSAGE_TARGET_NO_PHANTOM = "You cannot move an annotation to a phantom paragraph.";
    public static final String MESSAGE_PHANTOM_CANNOT_HIGHLIGHT = "You cannot change the highlight "
            + "of a phantom paragraph.";
    public static final String MESSAGE_CANNOT_MOVE_TO_SAME_PARA = "Please specify a different paragraph to move "
            + "annotation to.";
    public static final String MESSAGE_NOTHING_TO_EDIT = "Paragraph %1$s has no annotations to edit.";
    public static final String MESSAGE_NO_EDITS_GIVEN = "Please indicate the note or highlight to "
            + "change to.";
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
        OfflineDocument doc = getRequiredDocCopy(oldBkmark);

        Paragraph originalP;
        Paragraph newP = null;

        if (newPid == null && newNote == null && newHighlight == null) {
            throw new CommandException(MESSAGE_NO_EDITS_GIVEN);
        }

        if (newPid != null && newPid.isStray()) {
            throw new CommandException(MESSAGE_TARGET_NO_PHANTOM);
        }

        if (isOnlyHighlightPhantom(newNote, newPid)) {
            throw new CommandException(MESSAGE_PHANTOM_CANNOT_HIGHLIGHT);
        }

        try {
            originalP = doc.getParagraph(getPid());

            if (newPid != null) {
                newP = doc.getParagraph(newPid);
            }
        } catch (IllegalValueException e) {
            throw new CommandException(EditAnnotationCommand.COMMAND_WORD + ": " + e.getMessage());
        }

        if (!originalP.hasAnnotation()) {
            throw new CommandException(String.format(EditAnnotationCommand.MESSAGE_NOTHING_TO_EDIT, getPid()));
        }

        if (getPid().equals(newPid)) {
            throw new CommandException(MESSAGE_CANNOT_MOVE_TO_SAME_PARA);
        }


        Annotation newAnnotation = getNewAnnotation(originalP);

        moveAnnotation(originalP, newP, newAnnotation, doc);

        String returnMsg = newAnnotation.toString();
        if (newP != null) {
            returnMsg = returnMsg + "\n" + String.format(MESSAGE_MOVED_TO, newPid);
        }
        String savedMsg = String.format(MESSAGE_SUCCESS, getPid(), returnMsg);

        Bookmark newBkmark = oldBkmark.copy();
        saveState(model, oldBkmark, newBkmark, doc, savedMsg);

        return new OfflineCommandResult(savedMsg);
    }

    private boolean isOnlyHighlightPhantom(AnnotationNote newNote, ParagraphIdentifier newPid) {
        return newNote == null && newPid == null && getPid().isStray();
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

    /**
     * Moves a specified annotation from paragraph to another paragraph if possible.
     * @param oldP The original paragraph that had the annotation attached to it
     * @param newP The new paragraph to shift annotation to
     * @param newAnnotation The specified annotation
     * @param doc The document containing these paragraphs
     * @throws CommandException if given old phantom paragraph id, if any, is invalid.
     */
    private void moveAnnotation(Paragraph oldP, Paragraph newP, Annotation newAnnotation, OfflineDocument doc)
            throws CommandException {
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EditAnnotationCommand)) {
            return false;
        }
        return getBookmarkIndex().equals(((EditAnnotationCommand) other).getBookmarkIndex())
                && getPid().equals(((EditAnnotationCommand) other).getPid())
                && newHighlight == ((EditAnnotationCommand) other).newHighlight
                && (newNote == ((EditAnnotationCommand) other).newNote
                || (newNote != null && newNote.equals(((EditAnnotationCommand) other).newNote)))
                && (newPid == ((EditAnnotationCommand) other).newPid
                || (newPid != null && newPid.equals(((EditAnnotationCommand) other).newPid)));
    }

}
