package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.FILTER;

import seedu.address.model.Model;
import seedu.address.model.cheatsheet.CheatSheetContainsTagPredicate;

/**
 * Command to filter cheatsheets(s) with the related tag(s).
 */

public class FilterCheatSheetByTagCommand extends Command implements FilterByTagCommand  {

    public static final String COMMAND_WORD = FILTER;

    public static final String MESSAGE_USAGE = "filter by tags. Find all related cheatsheets with the specified \n"
            + "tags. Example : filter [cheatsheet] [cs2103t]";

    public static final String FILTER_TAG_MESSAGE_SUCCESS = "Filter cheatsheets by tag(s) : ";

    private String[] tagKeywords;

    private final CheatSheetContainsTagPredicate tagPredicate;

    /**
     * Constructor for filter by tag.
     * @param predicate to test on an note object to see if it has the tag.
     * @param tagKeywords the tags provided by user input to test on the note.
     */
    public FilterCheatSheetByTagCommand(CheatSheetContainsTagPredicate predicate, String[] tagKeywords) {
        this.tagPredicate = predicate;
        this.tagKeywords = tagKeywords;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCheatSheetList(tagPredicate);
        return new CommandResult(FILTER_TAG_MESSAGE_SUCCESS + FilterByTagCommand.displayTagKeywords(tagKeywords));
    }
}
