package seedu.jarvis.logic.commands.address;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.person.Person;

/**
 * Clears the address book.
 */
public class ClearAddressCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public static final String MESSAGE_INVERSE_SUCCESS_RESTORE = "Address book has been restored!";

    public static final boolean HAS_INVERSE = true;

    private List<Person> clearedPersons;

    public ClearAddressCommand() {
        clearedPersons = new ArrayList<>();
    }

    public ClearAddressCommand(List<Person> persons) {
        this();
        clearedPersons.addAll(persons);
    }

    /**
     * Gets the command word of the command.
     *
     * @return {@code String} representation of the command word.
     */
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Gets a {@code List} of {@code Person} objects stored in this {@code ClearAddressCommand}.
     *
     * @return {@code List} of {@code Person}.
     */
    public List<Person> getClearedPersons() {
        return clearedPersons;
    }

    /**
     * Clears all {@code Person} from address book.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of a successful clear.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        clearedPersons = new ArrayList<>(model.getFilteredPersonList());
        model.setAddressBook(new AddressBook());

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Restores all {@code Person} that was cleared from address book from execution.
     * Any new {@code Person} that was added to address book after the clear execution is deleted.
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} of a successful restore.
     */
    @Override
    public CommandResult executeInverse(Model model) {
        requireNonNull(model);

        // deletes persons that were not inside address book prior to the clear execution
        model.setAddressBook(new AddressBook());

        clearedPersons.forEach(model::addPerson);
        return new CommandResult(MESSAGE_INVERSE_SUCCESS_RESTORE);
    }

    /**
     * Checks for equality with a given {@code Object}.
     * Checks that it is a {@code ClearAddressCommand} with the same {@code Person} objects in the same order.
     */
    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if it is the same object.
                || (obj instanceof ClearAddressCommand // instanceof handles nulls.
                && clearedPersons.equals(((ClearAddressCommand) obj).clearedPersons));
    }
}
