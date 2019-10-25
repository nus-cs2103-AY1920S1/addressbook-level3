package seedu.address.logic.commands.global;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.FILTER_ALL;

import java.util.ArrayList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.GlobalCommandResult;
import seedu.address.model.Model;
import seedu.address.model.StudyBuddyItem;
import seedu.address.model.StudyBuddyItemContainsTagPredicate;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.note.Note;

/**
 * Globally searches for any StudyBuddyItem that has tags which matches the user input of keywords.
 */

public class GlobalTagFilterCommand extends Command {

    public static final String COMMAND_WORD = FILTER_ALL;

    public static final String MESSAGE_USAGE = "lists every studyBuddy item filtered by tag(s)."
            + "\nexample usage : filterall tag/cs2100 tag/important";

    public static final String FILTER_TAG_MESSAGE_SUCCESS = "List the whole StudyBuddy by tag(s) : ";

    private ArrayList<String> tagKeywords;

    private final StudyBuddyItemContainsTagPredicate tagPredicate;

    /**
     * Constructor for filter by tag.
     * @param predicate to test on an note object to see if it has the tag.
     * @param tagKeywords the tags provided by user input to test on the note.
     */
    public GlobalTagFilterCommand(StudyBuddyItemContainsTagPredicate predicate, ArrayList<String> tagKeywords) {
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
        ArrayList<StudyBuddyItem> tagListResult = model.collectTaggedItems(tagPredicate);
        StringBuilder sb = new StringBuilder("");
        for (StudyBuddyItem sbi : tagListResult) {
            if (sbi instanceof Flashcard) {
                sb.append("Flashcard : ");
            }
            if (sbi instanceof Note) {
                sb.append("Note : ");
            }
            if (sbi instanceof CheatSheet) {
                sb.append("CheatSheet : ");
            }
            sb.append(sbi.toString());
            sb.append("\n");
        }

        return new GlobalCommandResult(FILTER_TAG_MESSAGE_SUCCESS
                + "\n" + showTagQueries()
                + "\n" + sb.toString());
    }
}
