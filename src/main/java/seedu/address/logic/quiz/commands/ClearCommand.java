package seedu.address.logic.quiz.commands;

import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.Model;

import static java.util.Objects.requireNonNull;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressQuizBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
