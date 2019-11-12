package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.FILTER;

import java.util.ArrayList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.NoteCommandResult;
import seedu.address.model.Model;
import seedu.address.model.note.NoteContainsTagPredicate;

/**
 * Command to filter note(s) with the related tag(s).
 */
public class FilterNoteByTagCommand extends Command {

    public static final String COMMAND_WORD = FILTER;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters notes by tags.\n"
            + "Find all related notes and intra-note tags with the specified tags.\n"
            + "Example : filter tag/important tag/cs2100";

    public static final String FILTER_TAG_MESSAGE_SUCCESS = "Filter notes by tag(s) : ";

    public static final String NO_ITEM_FOUND = "There is no such Note with the specified tag(s).";

    private ArrayList<String> tagKeywords;

    private final NoteContainsTagPredicate tagPredicate;

    /**
     * Constructor for filter by tag.
     * @param predicate to test on an note object to see if it has the tag.
     * @param tagKeywords the tags provided by user input to test on the note.
     */
    public FilterNoteByTagCommand(NoteContainsTagPredicate predicate, ArrayList<String> tagKeywords) {
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
        ArrayList<String> taggedNoteResult = model.collectTaggedNotes(tagPredicate);
        StringBuilder sb = new StringBuilder();
        for (String s : taggedNoteResult) {
            sb.append(s);
            sb.append("\n");
        }
        StringBuilder resultToDisplay = new StringBuilder();
        if (taggedNoteResult.size() == 0) {
            resultToDisplay.append(NO_ITEM_FOUND);
        } else {
            resultToDisplay.append(FILTER_TAG_MESSAGE_SUCCESS)
                    .append("\n")
                    .append(showTagQueries())
                    .append("\n\n")
                    .append(sb.toString());
        }
        return new NoteCommandResult(resultToDisplay.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterNoteByTagCommand // instanceof handles nulls
                && tagPredicate.equals(((FilterNoteByTagCommand) other).tagPredicate));
    }
}
