package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsTagPredicate;

public class FilterByTagCommand extends Command {

    private String[] tagKeywords;

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = "filter by tags. Find all related persons with the specified \n" +
            "tags. Example : filter [neighbours] [friends]";

    public static final String FILTER_TAG_MESSAGE_SUCCESS = "Filter by tag(s) : ";

    private final PersonContainsTagPredicate tagPredicate;

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
