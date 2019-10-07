package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsTagPredicate;

public class FilterByTagCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = "filter by tags. Find all related persons with the specified \n" +
            "tags. Example : filter [neighbours] [friends]";

    public static final String FILTER_TAG_MESSAGE_SUCCESS = "Filter by tag : ";

    private final PersonContainsTagPredicate tagPredicate;

    public FilterByTagCommand(PersonContainsTagPredicate predicate) {
        this.tagPredicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(tagPredicate);
        return new CommandResult(FILTER_TAG_MESSAGE_SUCCESS);
    }
}
