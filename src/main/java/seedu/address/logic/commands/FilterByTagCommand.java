package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * Command to filter person(s) with the related tag(s).
 */

public class FilterByTagCommand extends Command {

    private String[] tagKeywords;

    public final static String COMMAND_WORD = "filter";

    public final static String MESSAGE_USAGE = "filter by tags. Find all related persons with the specified \n"
            + "tags. Example : filter [neighbours] [friends]";

    public final static String FILTER_TAG_MESSAGE_SUCCESS = "Filter by tag(s) : ";

    private final PersonContainsTagPredicate tagPredicate;

    /**
     * Constructor for filter by tag.
     * @param predicate to test on an person object to see if he has the tag.
     * @param tagKeywords the tags provided by user input to test on the person.
     */
    public FilterByTagCommand(PersonContainsTagPredicate predicate, String[] tagKeywords) {
        this.tagPredicate = predicate;
        this.tagKeywords = tagKeywords;
    }

    public String displayTagKeywords() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tagKeywords.length; i++) {
            if (i != tagKeywords.length - 1) {
                sb.append(tagKeywords[i] + ", ");
            } else {
                sb.append(tagKeywords[i]);
            }
        }
        return sb.toString();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(tagPredicate);
        return new CommandResult(FILTER_TAG_MESSAGE_SUCCESS + displayTagKeywords());
    }
}
