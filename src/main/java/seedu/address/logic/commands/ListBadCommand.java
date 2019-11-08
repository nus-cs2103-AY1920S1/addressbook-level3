//@@author dalsontws

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;


/**
 * Finds and lists all flashcards that are indicated as "bad".
 *
 */
public class ListBadCommand extends Command {

    public static final String COMMAND_WORD = "listbad";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all FlashCard(s) which have been "
            + "labeled as bad flashcards that to remind the users to revise for these flashcards.\n"
            + "at a later date.\n"
            + "Example: " + COMMAND_WORD;
    public static final String SUCCESS_MESSAGE = "Flashcards displayed";

//    private final String keyword;

//    public ListBadCommand() {
////        this.keyword = k;
//    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);


        return new CommandResult(String.format(SUCCESS_MESSAGE, "hello"));
    }

//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof ListBadCommand // instanceof handles nulls
//                && predicate.equals(((ListBadCommand) other).predicate)); // state check
//    }
}
