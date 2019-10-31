package seedu.address.logic.commands.cheatsheet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.FILTER;

import java.util.ArrayList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.CheatSheetCommandResult;
import seedu.address.model.Model;
import seedu.address.model.cheatsheet.CheatSheetContainsTagPredicate;

/**
 * Command to filter cheatsheet(s) with the related tag(s).
 */
public class FilterCheatSheetByTagCommand extends Command {

    public static final String COMMAND_WORD = FILTER;

    public static final String MESSAGE_USAGE = "filter by tags. Find all "
            + "related cheatsheets with the specified \n"
            + "tags. Example : filter tag/cheatsheet tag/cs2103t";

    public static final String FILTER_TAG_MESSAGE_SUCCESS = "Filter cheatsheets by tag(s) : ";

    public static final String NO_ITEM_FOUND = "There is no such CheatSheet with the specified tag(s).";

    private ArrayList<String> tagKeywords;

    private final CheatSheetContainsTagPredicate tagPredicate;

    /**
     * Constructor for filter by tag.
     * @param predicate to test on an note object to see if it has the tag.
     * @param tagKeywords the tags provided by user input to test on the note.
     */
    public FilterCheatSheetByTagCommand(CheatSheetContainsTagPredicate predicate, ArrayList<String> tagKeywords) {
        this.tagPredicate = predicate;
        this.tagKeywords = tagKeywords;
    }

    /**
     * To display to the user which tags he/she indicated
     * @return a string of the tags indicated
     */

    public String showTagQueries() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tagKeywords.size(); i++) {
            if (i != tagKeywords.size() - 1) {
                sb.append(tagKeywords.get(i))
                        .append(", ");
            } else {
                sb.append(tagKeywords.get(i));
            }
        }
        return sb.toString();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ArrayList<String> taggedCheatSheetResult = model.collectTaggedCheatSheets(tagPredicate);
        //model.updateFilteredCheatSheetList(tagPredicate);
        StringBuilder sb = new StringBuilder("");
        for (String s : taggedCheatSheetResult) {
            sb.append(s);
            sb.append("\n");
        }
        StringBuilder resultToDisplay = new StringBuilder();
        if (taggedCheatSheetResult.size() == 0) {
            resultToDisplay.append(NO_ITEM_FOUND);
        } else {
            resultToDisplay.append(FILTER_TAG_MESSAGE_SUCCESS)
                    .append("\n")
                    .append(showTagQueries())
                    .append(sb.toString());
        }
        return new CheatSheetCommandResult(resultToDisplay.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCheatSheetByTagCommand // instanceof handles nulls
                && tagPredicate.equals(((FilterCheatSheetByTagCommand) other).tagPredicate));
    }
}
