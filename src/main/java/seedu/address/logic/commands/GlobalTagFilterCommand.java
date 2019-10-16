package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.StudyBuddyItem;
import seedu.address.model.StudyBuddyItemContainsTagPredicate;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.GLOBAL_TAG_FILTER;

public class GlobalTagFilterCommand extends Command {

    public static final String COMMAND_WORD = GLOBAL_TAG_FILTER;

    public static final String MESSAGE_USAGE = "filters every studyBuddy item by a tag." +
            "example usage : globaltagfilter cs2100";

    public static final String FILTER_TAG_MESSAGE_SUCCESS = "Filter all by tag(s) : ";

    private String[] tagKeywords;

    private final StudyBuddyItemContainsTagPredicate tagPredicate;

    /**
     * Constructor for filter by tag.
     * @param predicate to test on an note object to see if it has the tag.
     * @param tagKeywords the tags provided by user input to test on the note.
     */
    public GlobalTagFilterCommand(StudyBuddyItemContainsTagPredicate predicate, String[] tagKeywords) {
        this.tagPredicate = predicate;
        this.tagKeywords = tagKeywords;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ArrayList<StudyBuddyItem> tagListResult = model.collectTaggedItems(tagPredicate);
        StringBuilder sb = new StringBuilder("");
        for (StudyBuddyItem sbi : tagListResult) {
            sb.append(sbi.toString());
            sb.append("\n");
        }
        return new CommandResult( FILTER_TAG_MESSAGE_SUCCESS
                + "\n" + sb.toString()
                + FilterByTagCommand.displayTagKeywords(tagKeywords));
    }
}
