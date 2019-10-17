package tagline.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static tagline.model.note.NoteModel.PREDICATE_SHOW_ALL_NOTES;

import java.util.Arrays;
import java.util.List;

import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.note.NoteContainsKeywordsPredicate;

/**
 * Lists all contacts in the address book to the user.
 */
public class ListNoteCommand extends NoteCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all notes";
    public static final String MESSAGE_KEYWORD_SUCCESS = "Listed notes for keyword: %1$s";
    public static final String MESSAGE_KEYWORD_EMPTYLIST = "No notes matching keyword: %1$s";

    private final Filter filter;

    /**
     * @param filter to list notes by
     */
    public ListNoteCommand(Filter filter) {
        this.filter = filter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (filter == null) { // No filter, list all notes
            model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
            return new CommandResult(MESSAGE_SUCCESS, ViewType.NOTE);
        } else { // list if note contains keyword (filter.filterType.equals(KEYWORD))
            return filterAndListByKeyword(model);
        }

        /* TODO Implement filter by tag */
    }

    /**
     * Filter note list by String keyword
     */
    private CommandResult filterAndListByKeyword(Model model) throws CommandException {
        List<String> keywordList = Arrays.asList(filter.filterValue.split(" "));
        NoteContainsKeywordsPredicate predicate = new NoteContainsKeywordsPredicate(keywordList);

        model.updateFilteredNoteList(predicate);

        if (model.getFilteredNoteList().size() == 0) {
            throw new CommandException(String.format(MESSAGE_KEYWORD_EMPTYLIST, filter.filterValue));
        }

        return new CommandResult(String.format(MESSAGE_KEYWORD_SUCCESS, filter.filterValue), ViewType.NOTE);
    }

    /**
     * Stores filter for note listing.
     */
    public static class Filter {
        /**
         * Represents the type of filter to list notes by.
         */
        public enum FilterType {
            KEYWORD
        }

        private final String filterValue;
        private final FilterType filterType;

        public Filter(String filterValue, FilterType filterType) {
            this.filterValue = filterValue;
            this.filterType = filterType;
        }

        public String getFilterValue() {
            return filterValue;
        }

        public FilterType getFilterType() {
            return filterType;
        }
    }
}
