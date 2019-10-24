package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.FILTER;

import java.util.ArrayList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.note.NoteContainsTagPredicate;

/**
 * Command to filter note(s) with the related tag(s).
 */

public class FilterNoteByTagCommand extends Command {

    public static final String COMMAND_WORD = FILTER;

    public static final String MESSAGE_USAGE = "filter by tags. "
            + "Find all related notes with the specified \n"
            + "tags. Example : filter tag/important tag/cs2100";

    public static final String FILTER_TAG_MESSAGE_SUCCESS = "Filter notes by tag(s) : ";

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
        //model.updateFilteredNoteList(tagPredicate);
        StringBuilder sb = new StringBuilder("");
        for (String s : taggedNoteResult) {
            sb.append(s);
            sb.append("\n");
        }
        return new CommandResult(FILTER_TAG_MESSAGE_SUCCESS
                + "\n" + showTagQueries()
                + "\n" + sb.toString());
    }
}
