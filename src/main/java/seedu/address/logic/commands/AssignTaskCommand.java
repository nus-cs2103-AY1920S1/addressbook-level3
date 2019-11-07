package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Performance;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

public class AssignTaskCommand extends Command {
    public static final String COMMAND_WORD = "assignTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns the task identified by the index number used in the displayed task list"
            + " to the person(s) identified by the remaining indexe(s)\n"
            + "Parameters: TASK_INDEX PERSON_INDEX... (INDEX must be positive integer)\n"
            + "Example: " + COMMAND_WORD + "1 3 5 (TASK_INDEX: 1, PERSON_INDEX:3, 5";

    public static final String MESSAGE_TASK_ASSIGNMENT_SUCCESS = "Assigned task(%1$s) to %2$s";

    private final List<Index> targetIndexes;

    public AssignTaskCommand(List<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Project currWorkingProject = model.getWorkingProject().get();
        List<Task> taskList = currWorkingProject.getTasks();
        List<String> personNameList = model.getWorkingProject().get().getMemberNames();
        List<Person> personList = new ArrayList<>();
       
        for (String personName : personNameList) {
            String[] nameKeywords = personName.trim().split("\\s+");
            
            //Filters the model person list one by one based on each name to find the relevant Person object 
            // since project only keeps members as strings
            personList.add(model.getFilteredPersonList()
                    .filtered(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords))).get(0));
        }

        Index taskIndex = targetIndexes.remove(0);

        if (taskIndex.getZeroBased() >= taskList.size()) {
            throw new CommandException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToAssign = taskList.get(taskIndex.getZeroBased());
        List<Person> personsToAssign = new ArrayList<>();
        List<Index> personIndexList = targetIndexes;

        for (Index personIndex : personIndexList) {
            if (personIndex.getZeroBased() >= personList.size()) {
                throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + ": " + personIndex.toString());
            }
            personsToAssign.add(personList.get(personIndex.getZeroBased()));
        }

        List<Person> assignedPersons = assignTaskTo(personsToAssign, taskToAssign, currWorkingProject);
        setPersons(personsToAssign, assignedPersons, model);

        return new CommandResult(String.format(MESSAGE_TASK_ASSIGNMENT_SUCCESS,
                taskToAssign.getDescription().toString(),
                getAsString(assignedPersons)), COMMAND_WORD);
    }

    private List<Person> assignTaskTo(List<Person> personsToAssign, Task taskToAssign, Project currWorkingProject) {
        List<Person> assignedPersons = new ArrayList<>();
        String projectTitle = currWorkingProject.getTitle().title;

        for (Person personToAssign : personsToAssign) {
            Performance previousPerformance = personToAssign.getPerformance();
            HashMap<String, List<Task>> taskAssignment = previousPerformance.getTaskAssignment();
            if (taskAssignment.containsKey(projectTitle)) {
                taskAssignment.get(projectTitle).add(taskToAssign); // Add tha task to the list of tasks
            } else {
                taskAssignment.put(projectTitle, new ArrayList<>());
                taskAssignment.get(projectTitle).add(taskToAssign);
            }

            HashMap<String, List<Task>> updatedTaskAssignment = previousPerformance.getTaskAssignment();

            Performance updatedPerformance = previousPerformance.setTasksAssigned(updatedTaskAssignment);

            Person editedPerson = new Person(personToAssign.getName(), personToAssign.getPhone(), personToAssign.getEmail(),
                    personToAssign.getProfilePicture(), personToAssign.getAddress(),
                    personToAssign.getTags(), personToAssign.getTimeTable(), updatedPerformance);

            editedPerson.getProjects().addAll(personToAssign.getProjects());

            assignedPersons.add(editedPerson);
        }

        return assignedPersons;
    }
}
