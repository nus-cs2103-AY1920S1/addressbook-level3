package seedu.address.logic.quiz.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.Model;


/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Modulo has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressQuizBook());
        model.commitQuizBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
