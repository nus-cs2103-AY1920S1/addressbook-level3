package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;
import java.util.ListIterator;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException, IllegalValueException;

    protected void setPersons(List<Person> personsToAssign, List<Person> assignedPersons, Model model) {
        ListIterator<Person> toAssignIter = personsToAssign.listIterator();
        ListIterator<Person> assignedIter = assignedPersons.listIterator();

        while (toAssignIter.hasNext() && assignedIter.hasNext()) {
            Person personToAssign = toAssignIter.next();
            Person assignedPerson = assignedIter.next();

            model.setPerson(personToAssign, assignedPerson);
        }
    }

    protected String getAsString(List<Person> personsAssigned) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < personsAssigned.size(); i++) {
            Person person = personsAssigned.get(i);
            if (i == 0) {
                sb.append(person.getName().fullName);
            } else {
                sb.append(", " + person.getName().fullName);
            }
        }

        return sb.toString();
    }
}
