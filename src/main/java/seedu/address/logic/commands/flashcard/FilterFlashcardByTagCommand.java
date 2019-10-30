package seedu.address.logic.commands.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.FILTER;

import java.util.ArrayList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.FlashcardCommandResult;
import seedu.address.model.Model;
import seedu.address.model.flashcard.FlashcardContainsTagPredicate;

/**
 * Command to filter flashcard(s) with the related tag(s).
 */

public class FilterFlashcardByTagCommand extends Command {

    public static final String COMMAND_WORD = FILTER;

    public static final String MESSAGE_USAGE = "filter by tags. Find all "
            + "related flashcards with the specified \n"
            + "tags. Example : filter tag/hard tag/cs2101";

    public static final String FILTER_TAG_MESSAGE_SUCCESS = "Filter flashcards by tag(s) : ";

    public static final String NO_ITEM_FOUND = "There is no such FlashCard with the specified tag(s).";

    private ArrayList<String> tagKeywords;

    private final FlashcardContainsTagPredicate tagPredicate;

    /**
     * Constructor for filter by tag.
     * @param predicate to test on an note object to see if it has the tag.
     * @param tagKeywords the tags provided by user input to test on the note.
     */
    public FilterFlashcardByTagCommand(FlashcardContainsTagPredicate predicate, ArrayList<String> tagKeywords) {
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
        ArrayList<String> taggedFlashcardResult = model.collectTaggedFlashcards(tagPredicate);
        //model.updateFilteredFlashcardList(tagPredicate);
        StringBuilder sb = new StringBuilder("");
        for (String s : taggedFlashcardResult) {
            sb.append(s);
            sb.append("\n");
        }
        StringBuilder resultToDisplay = new StringBuilder();
        if (taggedFlashcardResult.size() == 0) {
            resultToDisplay.append(NO_ITEM_FOUND);
        } else {
            resultToDisplay.append(FILTER_TAG_MESSAGE_SUCCESS)
                    .append("\n")
                    .append(showTagQueries())
                    .append(sb.toString());
        }
        return new FlashcardCommandResult(resultToDisplay.toString());
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterFlashcardByTagCommand // instanceof handles nulls
                && tagPredicate.equals(((FilterFlashcardByTagCommand) other).tagPredicate));
    }
}
