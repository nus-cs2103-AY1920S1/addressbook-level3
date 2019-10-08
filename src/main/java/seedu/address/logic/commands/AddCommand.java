package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a lecture note to NUStudy.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "addnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lecture note to NUStudy. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_CONTENT + "CONTENT "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Lecture 1 "
            + PREFIX_CONTENT + "Write once debug everywhere ";

    public static final String MESSAGE_SUCCESS = "Lecture note added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This title is already used";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
