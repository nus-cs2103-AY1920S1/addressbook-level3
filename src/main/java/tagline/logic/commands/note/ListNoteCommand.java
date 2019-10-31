// @@author shiweing
package tagline.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static tagline.model.note.NoteModel.PREDICATE_SHOW_ALL_NOTES;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.exceptions.CommandException;
import tagline.logic.commands.note.NoteFilter.FilterType;
import tagline.model.Model;
import tagline.model.note.NoteContainsKeywordsPredicate;
import tagline.model.note.NoteContainsTagsPredicate;
import tagline.model.tag.Tag;

/**
 * Lists all contacts in the address book to the user.
 */
public class ListNoteCommand extends NoteCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all notes";
    public static final String MESSAGE_KEYWORD_SUCCESS = "Listed notes for keyword: %1$s";
    public static final String MESSAGE_KEYWORD_EMPTYLIST = "No notes matching keywords: %1$s";
    public static final String MESSAGE_TAG_SUCCESS = "Listed notes for tags: %1$s";
    public static final String MESSAGE_TAG_EMPTYLIST = "No notes matching tags: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag cannot be found: %1$s";

    private final NoteFilter filter;

    /**
     * @param filter to list notes by
     */
    public ListNoteCommand(NoteFilter filter) {
        this.filter = filter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (filter == null) { // No filter, list all notes
            model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
            return new CommandResult(MESSAGE_SUCCESS, ViewType.NOTE);

        } else if (filter.getFilterType() == FilterType.TAG) {
            return filterAndListByTag(model);
        } else {
            return filterAndListByKeyword(model);
        }
    }

    /**
     * Filter note list by {code Tag}
     */
    private CommandResult filterAndListByTag(Model model) throws CommandException {
        List<Tag> tags = new ArrayList<>();

        for (Tag tagToFind : ((TagFilter) filter).getFilterValues()) {
            Optional<Tag> tagFound = model.findTag(tagToFind);

            if (tagFound.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagToFind));
            }

            tags.add(tagFound.get());
        }

        NoteContainsTagsPredicate predicate = new NoteContainsTagsPredicate(tags);
        model.updateFilteredNoteList(predicate);

        if (model.getFilteredNoteList().size() == 0) {
            throw new CommandException(String.format(MESSAGE_TAG_EMPTYLIST, filter));
        }

        return new CommandResult(String.format(MESSAGE_TAG_SUCCESS, filter), ViewType.NOTE);
    }

    /**
     * Filter note list by String keyword
     */
    private CommandResult filterAndListByKeyword(Model model) throws CommandException {
        NoteContainsKeywordsPredicate predicate = new NoteContainsKeywordsPredicate((
                (KeywordFilter) filter).getFilterValues());

        model.updateFilteredNoteList(predicate);

        if (model.getFilteredNoteList().size() == 0) {
            throw new CommandException(String.format(MESSAGE_KEYWORD_EMPTYLIST, filter));
        }

        return new CommandResult(String.format(MESSAGE_KEYWORD_SUCCESS, filter), ViewType.NOTE);
    }

}
