package seedu.address.logic.commands.global;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.FILTER_ALL;

import java.util.ArrayList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.GlobalCommandResult;
import seedu.address.model.Model;
import seedu.address.model.StudyBuddyItemContainsTagPredicate;

/**
 * Globally searches for any StudyBuddyItem that has tags which matches the user input of keywords.
 */

public class FilterAllByTagCommand extends Command {

    public static final String COMMAND_WORD = FILTER_ALL;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters and displays every StudyBuddyPro item by tag"
            + "(s).\nExample usage : filterall tag/cs2100 tag/important";

    public static final String FILTER_TAG_MESSAGE_SUCCESS = "Listing the whole StudyBuddyPro after filtering by tag(s)"
            + ": ";

    public static final String NO_ITEM_FOUND = "There is no such StudyBuddyItem with the specified "
            + "tag(s) in StudyBuddy!";

    private ArrayList<String> tagKeywords;

    private final StudyBuddyItemContainsTagPredicate tagPredicate;

    /**
     * Constructor for filter by tag.
     * @param predicate to test on an note object to see if it has the tag.
     * @param tagKeywords the tags provided by user input to test on the note.
     */
    public FilterAllByTagCommand(StudyBuddyItemContainsTagPredicate predicate, ArrayList<String> tagKeywords) {
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
        ArrayList<String> tagListResult = model.collectTaggedItems(tagPredicate);
        StringBuilder sb = new StringBuilder();
        for (String s : tagListResult) {
            sb.append(s);
            sb.append("\n");
        }
        StringBuilder resultToDisplay = new StringBuilder();
        if (tagListResult.size() == 0) {
            resultToDisplay.append(NO_ITEM_FOUND);
        } else {
            resultToDisplay.append(FILTER_TAG_MESSAGE_SUCCESS)
                    .append("\n")
                    .append(showTagQueries())
                    .append("\n\n")
                    .append(sb.toString());
        }
        return new GlobalCommandResult(resultToDisplay.toString());
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterAllByTagCommand // instanceof handles nulls
                && tagPredicate.equals(((FilterAllByTagCommand) other).tagPredicate));
    }
}
