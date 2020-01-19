package seedu.mark.logic.commands;

import static seedu.mark.logic.parser.CliSyntax.PREFIX_HIGHLIGHT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_PARAGRAPH;

import seedu.mark.commons.core.index.Index;
import seedu.mark.model.annotation.ParagraphIdentifier;

/**
 * Deletes a whole or part of an {@code Annotation} from a {@code Paragraph}.
 */
public abstract class DeleteAnnotationCommand extends AnnotationCommand {


    public static final String COMMAND_WORD = "annotate-delete";

    public static final String MESSAGE_CONSTRAINTS =
            "Paragraph identifiers should coincide with the paragraph labels on the left of each paragraph.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes an annotation from the cache of the bookmark identified by "
            + "the index used in the displayed bookmark list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PARAGRAPH + "P_ID "
            + "[" + PREFIX_NOTE + "KEEP_NOTES=false] "
            + "[" + PREFIX_HIGHLIGHT + "KEEP_HIGHLIGHT=false]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PARAGRAPH + "p3 "
            + PREFIX_NOTE + "false "
            + PREFIX_HIGHLIGHT + "orange";

    public static final String MESSAGE_NO_CACHE_AVAILABLE = "No cache is available to de-annotate.\n"
            //+ "A cache has been created for you. Kindly press Enter to confirm to add the annotation to this cache."
            + "Download a cache using the " + CacheCommand.COMMAND_WORD + " command to start annotating.";
    //TODO: change msg to more informative one (what content, to which paragraph, which colour, which bkmark version
    public static final String MESSAGE_NOTHING_TO_DELETE = "There is no annotation to delete from this paragraph.";
    public static final String MESSAGE_NOTHING_TO_DO = "You have requested to keep your note and highlight! "
            + "No change shall be made.";


    private final boolean isRemoveNote;
    private final boolean isRemoveHighlight;

    public DeleteAnnotationCommand(Index index, ParagraphIdentifier pid,
                                   boolean isRemoveNote, boolean isRemoveHighlight) {
        super(index, pid);
        this.isRemoveHighlight = isRemoveHighlight;
        this.isRemoveNote = isRemoveNote;
    }

    /*
    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        model.saveMark(MESSAGE_DEFAULT);
        return new OfflineCommandResult(MESSAGE_DEFAULT);
    }
     */


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAnnotationCommand // instanceof handles nulls
                && getBookmarkIndex().equals(((DeleteAnnotationCommand) other).getBookmarkIndex())
                && getPid().equals(((DeleteAnnotationCommand) other).getPid())
                && this.isRemoveNote == ((DeleteAnnotationCommand) other).isRemoveNote
                && this.isRemoveHighlight == ((DeleteAnnotationCommand) other).isRemoveHighlight); // state check
    }

}
