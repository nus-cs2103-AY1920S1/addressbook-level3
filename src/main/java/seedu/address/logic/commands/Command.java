package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.project.Task;

import java.util.ArrayList;
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

    protected String getAsStringPersons(List<Person> personsAssigned) {
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

    protected String getAsStringTasks(List<Task> taskList) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if (i == 0) {
                sb.append(task.description);
            } else {
                sb.append(", " + task.description);
            }
        }

        return sb.toString();
    }

    protected boolean containsDuplicateIndexes(List<Index> targetIndexList) {
        List<Index> indexChecker = new ArrayList<>();

        for (Index targetIndex : targetIndexList) {
            if (indexChecker.contains(targetIndex)) {
                return true;
            }

            indexChecker.add(targetIndex);
        }

        return false;
    }

    protected <T> boolean containsInvalidIndexes(List<Index> targetIndexList, List<T> genericList) {
        for (Index targetIndex : targetIndexList) {
            if (targetIndex.getZeroBased() >= genericList.size()) {
                return true;
            }
        }

        return false;
    }

    protected int getDuplicateIndex(List<Index> targetIndexList) {
        List<Index> indexChecker = new ArrayList<>();

        for (Index targetIndex : targetIndexList) {
            if (indexChecker.contains(targetIndex)) {
                return targetIndex.getOneBased();
            }

            indexChecker.add(targetIndex);
        }

        return -1;
    }

    protected <T> int getInvalidIndex(List<Index> targetIndexList, List<T> genericList) {
        for (Index targetIndex : targetIndexList) {
            if (targetIndex.getZeroBased() >= genericList.size()) {
                return targetIndex.getOneBased();
            }
        }

        return -1;
    }
}
