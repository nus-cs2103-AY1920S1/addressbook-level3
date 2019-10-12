package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class DoNotMergeCommand extends Command {

    public static final String COMMAND_WORD = "nmerge";

    public static final String MESSAGE_SUCCESS = "This profile was not updated : %1$s.";

    private final Person inputPerson;
    private Person originalPerson;
    private ArrayList<String[]> differentFields = new ArrayList<>();


    /**
     * Creates an Merge Command to update the original {@code Person} to the new {@code Person}
     */
    public DoNotMergeCommand(Person inputPerson) {
        requireNonNull(inputPerson);
        this.inputPerson = inputPerson;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePersonWithMergeException {
        requireNonNull(model);
        StringBuilder prompt = new StringBuilder();
        this.originalPerson = model.getPerson(inputPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, originalPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoNotMergeCommand // instanceof handles nulls
                && inputPerson.equals(((DoNotMergeCommand) other).inputPerson));
    }
}
