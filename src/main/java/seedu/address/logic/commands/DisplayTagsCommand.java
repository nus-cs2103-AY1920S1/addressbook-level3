package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

public class DisplayTagsCommand extends Command {

    public static final String COMMAND_WORD = "tags";

    public static final String LIST_TAG_MESSAGE_SUCCESS = "Listed all tags";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        //model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(LIST_TAG_MESSAGE_SUCCESS);
    }
}
