package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Performance;
import seedu.address.model.person.Person;
import seedu.address.model.project.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.*;

public class UnassignTaskCommand extends Command {
    public static final String COMMAND_WORD = "unassignTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the assigned task(s) specified by the index(s), from the task list of the member specified by the index\n"
            + "Parameters: PERSON_INDEX TASK_INDEX...(INDEX must be positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 3 5 (PERSON_INDEX: 1, TASK_INDEX: 3, 5)";

    public static final String MESSAGE_SUCCESS = "Unassigned task(s): %1$s \n"
            + "from %2$s.";

    private final List<Index> targetIndexList;

    public UnassignTaskCommand(List<Index> targetIndexList) {
        this.targetIndexList = targetIndexList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();
        Index personIndex = targetIndexList.remove(0);

        if (personIndex.getZeroBased() >= personList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (containsDuplicateIndexes(targetIndexList)) {
            throw new CommandException(MESSAGE_DUPLICATE_INDEX + " Duplicate index: " + getDuplicateIndex(targetIndexList));
        }

        Person personToEdit = personList.get(personIndex.getZeroBased());
        HashMap<String, List<Task>> taskAssignment = personToEdit.getPerformance().getTaskAssignment();
        List<Task> displayedTaskList = getDisplayedTaskList(personToEdit);
        List<String> displayedTaskProjectPairings = getDisplayedTaskListProjectPairing(personToEdit);

        if (containsInvalidIndexes(targetIndexList, displayedTaskList)) {
            int invalidIndex = getInvalidIndex(targetIndexList, displayedTaskList);
            throw new CommandException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX + " Invalid index: " + invalidIndex);
        }

        List<Task> removedTasks = new ArrayList<>();
        for (Index targetIndex : targetIndexList) {
            Task taskToRemove = displayedTaskList.get(targetIndex.getZeroBased());
            removedTasks.add(taskToRemove);
            String projectTitle = displayedTaskProjectPairings.get(targetIndex.getZeroBased());
            taskAssignment.get(projectTitle).remove(taskToRemove);
        }

        Performance editedPerformance = new Performance(personToEdit.getPerformance().getMeetingsAttended(), taskAssignment);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getProfilePicture(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getTimetable(), editedPerformance);

        editedPerson.getProjects().addAll(personToEdit.getProjects());
        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, getAsStringTasks(removedTasks), editedPerson.getName().fullName), COMMAND_WORD);
    }

    private List<Task> getDisplayedTaskList(Person person) {
        List<String> projectList = person.getProjects();
        List<Task> displayedTaskList = new ArrayList<>();

        for (String projectTitle : projectList) {
            if (!person.getPerformance().getTaskAssignment().containsKey(projectTitle)) {
                continue;
            }

            List<Task> projectTasks = person.getPerformance().getTaskAssignment().get(projectTitle);
            displayedTaskList.addAll(projectTasks);
        }

        return displayedTaskList;
    }

    private List<String> getDisplayedTaskListProjectPairing(Person person) {
        List<String> projectList = person.getProjects();
        final List<String> projectPairing = new ArrayList<>();

        for (String projectTitle : projectList) {
            if (!person.getPerformance().getTaskAssignment().containsKey(projectTitle)) {
                continue;
            }

            List<Task> projectTasks = person.getPerformance().getTaskAssignment().get(projectTitle);
            projectTasks.forEach(task -> projectPairing.add(projectTitle));
        }

        return projectPairing;
    }
}
