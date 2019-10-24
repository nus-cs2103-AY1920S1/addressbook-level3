package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_HIGHLIGHT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_PARAGRAPH;

import java.util.List;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.OfflineCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.CachedCopy;
import seedu.mark.storage.Storage;

public class DeleteAnnotationCommand extends Command {


    public static final String COMMAND_WORD = "annotatedelete";

    public static final String MESSAGE_CONSTRAINTS =
            "Paragraph identifiers should coincide with the paragraph labels on the left of each paragraph.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes an annotation from the cache of the bookmark identified by "
            + "the index used in the displayed bookmark list.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + PREFIX_PARAGRAPH + "P_ID"
            + "[" + PREFIX_NOTE + "KEEP_NOTES=false]"
            + "[" + PREFIX_HIGHLIGHT + "KEEP_HIGHLIGHT=false]"
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PARAGRAPH + "p3 "
            + PREFIX_NOTE + "false "
            + PREFIX_HIGHLIGHT + "orange";

    public static final String MESSAGE_NO_CACHE_AVAILABLE = "No cache is available to de-annotate.\n"
            //+ "A cache has been created for you. Kindly press Enter to confirm to add the annotation to this cache."
            + "Download a cache using the " + CacheCommand.COMMAND_WORD + " command to start annotating.";
    //TODO: change msg to more informative one (what content, to which paragraph, which colour, which bkmark version
    public static final String MESSAGE_NOTHING_TO_DELETE = "There is no annotation is delete from this paragraph.";
    public static final String MESSAGE_DEFAULT = "Successfully kept your note and highlight.";


    private final Index index;
    private final ParagraphIdentifier pid;
    private final boolean isRemoveNote;
    private final boolean isRemoveHighlight;

    public DeleteAnnotationCommand(Index index, ParagraphIdentifier pid, boolean isRemoveNote, boolean isRemoveHighlight) {
        requireNonNull(index);
        requireNonNull(pid);

        this.index = index;
        this.pid = pid;
        this.isRemoveHighlight = isRemoveHighlight;
        this.isRemoveNote = isRemoveNote;
    }

    public OfflineDocument getRequiredDoc(Model model) throws CommandException {
        requireNonNull(model);
        List<Bookmark> lastShownList = model.getFilteredBookmarkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX);
        }

        Bookmark bookmarkOwnerOfCache = lastShownList.get(index.getZeroBased());
        List<CachedCopy> caches = bookmarkOwnerOfCache.getCachedCopies();
        if (caches.isEmpty()) {
            //TODO: maybe - create cache if it dne so they can annotate immediately. (then need to change ug and dg too)
            throw new CommandException(MESSAGE_NO_CACHE_AVAILABLE);
        }

        //TODO: pick out the appropriate cache. (change ug; default version on newwest cache; but can access others too)
        CachedCopy c = caches.get(0);

        return c.getAnnotations();
    }

    public boolean isRemoveNote() {
        return isRemoveNote;
    }

    public boolean isRemoveHighlight() {
        return isRemoveHighlight;
    }

    public ParagraphIdentifier getPid() {
        return pid;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        model.saveMark(MESSAGE_DEFAULT);
        return new OfflineCommandResult(MESSAGE_DEFAULT);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAnnotationCommand // instanceof handles nulls
                && index.equals(((DeleteAnnotationCommand) other).index)
                && pid.equals(((DeleteAnnotationCommand) other).pid)
                && this.isRemoveNote == ((DeleteAnnotationCommand) other).isRemoveNote
                && this.isRemoveHighlight == ((DeleteAnnotationCommand) other).isRemoveHighlight); // state check
    }

}
