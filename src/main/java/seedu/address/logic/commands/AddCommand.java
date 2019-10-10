package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.answerable.Answerable;

/**
 * Adds a answerable to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a question to the test bank. "
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_DIFFICULTY + "DIFFICULTY "
            + PREFIX_CATEGORY + "CATEGORY "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "Which of the following is a valid sequence diagram? "
            + PREFIX_DIFFICULTY + "3 "
            + PREFIX_CATEGORY + "UML "
            + PREFIX_TAG + "UML "
            + PREFIX_TAG + "graphical";

    public static final String MESSAGE_SUCCESS = "New question added: %1$s";
    public static final String MESSAGE_DUPLICATE_ANSWERABLE = "This answerable already exists in the address book";

    private final Answerable toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Answerable}
     */
    public AddCommand(Answerable answerable) {
        requireNonNull(answerable);
        toAdd = answerable;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAnswerable(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ANSWERABLE);
        }

        model.addAnswerable(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
