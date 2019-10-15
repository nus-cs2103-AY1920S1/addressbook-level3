package seedu.address.logic.commands.merge;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Rejects merging a duplicate profile.
 */
public class DoNotMergePersonCommand extends Command {

    public static final String COMMAND_WORD = "nmerge";

    public static final String MESSAGE_SUCCESS = "This profile was not updated : %1$s.";

    private final Person inputPerson;
    private Person originalPerson;
    private ArrayList<String[]> differentFields = new ArrayList<>();


    /**
     * Creates an DoNotMergePersonCommand to skip the merging of a field in a merging process.
     */
    public DoNotMergePersonCommand(Person inputPerson) {
        requireNonNull(inputPerson);
        this.inputPerson = inputPerson;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePersonWithMergeException {
        requireNonNull(model);
        this.originalPerson = model.getPerson(inputPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, originalPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoNotMergePersonCommand // instanceof handles nulls
                && inputPerson.equals(((DoNotMergePersonCommand) other).inputPerson));
    }
}
