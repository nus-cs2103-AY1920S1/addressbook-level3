package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_HIGHLIGHT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_PARAGRAPH;

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
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.storage.Storage;

/**
 * Annotates a paragraph with an {@code Annotation}.
 */
public class AddAnnotationCommand extends AnnotationCommand {

    public static final String COMMAND_WORD = "annotate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Annotates the cache of the bookmark identified by the index used in the displayed bookmark list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PARAGRAPH + "P_ID "
            + "[" + PREFIX_NOTE + "NOTE] "
            + "[" + PREFIX_HIGHLIGHT + "HIGHLIGHT=yellow]"
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PARAGRAPH + "p3 "
            + PREFIX_NOTE + "a note tagged to paragraph 3 highlighted orange "
            + PREFIX_HIGHLIGHT + "orange";

    public static final String MESSAGE_CANNOT_CHOOSE_PHANTOM_TO_ANNOTATE = "You cannot annotate phantom paragraphs "
            + "this way. If you want to add a general note, use p/NULL instead, e.g.\n"
            + "annotate 1 p/NULL n/i can add a general note this way!";
    //TODO: change msg to more informative one (what content, to which paragraph, which colour, which bkmark version
    public static final String MESSAGE_SUCCESS = "Annotation successfully added to paragraph %1$s:\n%2$s";
    private static final String MESSAGE_HIGHLIGHT_ADDED = "%s highlight";
    private static final String MESSAGE_NOTE_ADDED = " with note \"%s\"";

    private static final ParagraphIdentifier NULL_DUMMY_PID = new DummyParagraphIdentifier();

    private final AnnotationNote note;
    private final Highlight highlight;

    private final boolean isAddGeneralNote;

    public AddAnnotationCommand(Index index, ParagraphIdentifier pid, AnnotationNote note, Highlight highlight) {
        super(index, pid);
        requireNonNull(highlight);
        //note can be null which is handled in command result.

        this.note = note;
        this.highlight = highlight;
        this.isAddGeneralNote = false;
    }

    public AddAnnotationCommand(Index index, Highlight highlight, AnnotationNote note) {
        super(index, NULL_DUMMY_PID);
        requireNonNull(highlight);  //dummy value that will come into existence when attached to true paragraph
        requireNonNull(note);

        this.note = note;
        this.highlight = highlight;
        this.isAddGeneralNote = true;
    }

    /**
     * Executes the {@code AddAnnotationCommand} and returns an {@code OfflineCommandResult}.
     * @throws CommandException if bookmark index, cache, pid is invalid or paragraph is phantom.
     */
    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        Bookmark oldBkmark = getRequiredBookmark(model);
        OfflineDocument docOriginal = getRequiredDoc(oldBkmark);

        OfflineDocument doc = docOriginal.copy();

        String returnMsg;
        Annotation an;
        returnMsg = String.format(MESSAGE_HIGHLIGHT_ADDED, highlight.toString());
        if (note == null) {
            an = new Annotation(highlight);
        } else {
            an = new Annotation(highlight, note);
            returnMsg = returnMsg + String.format(MESSAGE_NOTE_ADDED, note.toString());
        }

        if (isAddGeneralNote) {
            doc.addPhantom(an);
        } else {
            if (getPid().isStray()) {
                throw new CommandException(MESSAGE_CANNOT_CHOOSE_PHANTOM_TO_ANNOTATE);
            }

            try {
                doc.addAnnotation(getPid(), an);
            } catch (IllegalValueException e) {
                throw new CommandException(COMMAND_WORD + ": " + e.getMessage());
            }

        }

        model.updateDocument(doc);

        Bookmark newBkmark = oldBkmark.copy();
        newBkmark.updateCachedCopy(doc);
        model.setBookmark(oldBkmark, newBkmark);

        model.saveMark(String.format(MESSAGE_SUCCESS, getPid(), returnMsg));
        return new OfflineCommandResult(String.format(MESSAGE_SUCCESS, getPid(), returnMsg));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAnnotationCommand // instanceof handles nulls
                && getBookmarkIndex().equals(((AddAnnotationCommand) other).getBookmarkIndex())
                && getPid().equals(((AddAnnotationCommand) other).getPid())
                && (this.note == ((AddAnnotationCommand) other).note
                || note.equals(((AddAnnotationCommand) other).note))
                && highlight.equals(((AddAnnotationCommand) other).highlight)); // state check
    }



}

/**
 * This is a dummy pid for a non-existent paragraph, null.
 */
class DummyParagraphIdentifier extends ParagraphIdentifier {
    public DummyParagraphIdentifier() {
        super(Index.fromOneBased(1), ParagraphType.STRAY);
    }

    @Override
    public String toString() {
        return "NULL (i.e. your note has been added to the general ";
    }
}
