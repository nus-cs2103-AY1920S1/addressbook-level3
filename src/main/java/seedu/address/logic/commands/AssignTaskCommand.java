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
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.*;

public class AssignTaskCommand extends Command {
    public static final String COMMAND_WORD = "assignTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns the task identified by the index number used in the displayed task list"
            + " to the person(s) identified by the remaining indexe(s)\n"
            + "Parameters: TASK_INDEX PERSON_INDEX... (INDEX must be positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 3 5 (TASK_INDEX: 1, PERSON_INDEX:3, 5";

    public static final String MESSAGE_TASK_ASSIGNMENT_SUCCESS = "Assigned task(%1$s) to %2$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "Task is already assigned to %1$s";

    private final List<Index> targetIndexes;

    public AssignTaskCommand(List<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Project currWorkingProject = model.getWorkingProject().get(); // Gets the working project
        List<Task> taskList = currWorkingProject.getTasks(); // Gets the list of tasks in the project
        List<String> personNameList = model.getWorkingProject().get().getMemberNames(); // Gets the names of the members in the project
        List<Person> personList = new ArrayList<>(); // This is the list of Persons representing the members

        //Finding the members of the project from the address book
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
        List<Person> personsToAssign = new ArrayList<>(); //List of persons to assign the task to
        List<Index> personIndexList = targetIndexes;

        // Finds all the persons who will be assigned the task and adds them to personsToAssign list
        for (Index personIndex : personIndexList) {
            if (personIndex.getZeroBased() >= personList.size()) {
                throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + ": " + personIndex.getOneBased());
            }
            personsToAssign.add(personList.get(personIndex.getZeroBased()));
        }

        //Assigning task to all Persons
        List<Person> assignedPersons = assignTaskTo(personsToAssign, taskToAssign, currWorkingProject);
        setPersons(personsToAssign, assignedPersons, model);

        return new CommandResult(String.format(MESSAGE_TASK_ASSIGNMENT_SUCCESS,
                taskToAssign.getDescription().toString(),
                getAsStringPersons(assignedPersons)), COMMAND_WORD);
    }

    private List<Person> assignTaskTo(List<Person> personsToAssign, Task taskToAssign, Project currWorkingProject) throws CommandException {
        List<Person> assignedPersons = new ArrayList<>();
        String projectTitle = currWorkingProject.getTitle().title;

        for (Person personToAssign : personsToAssign) {
            Performance previousPerformance = personToAssign.getPerformance();
            HashMap<String, List<Task>> taskAssignment = previousPerformance.getTaskAssignment();

            //Puts the task into the list of tasks of the respective project
            if (taskAssignment.containsKey(projectTitle)) {
                for (Task task : taskAssignment.get(projectTitle)) {
                    Logger.getGlobal().warning(task.toString());
                }
                Logger.getGlobal().warning(taskToAssign.toString());
                Logger.getGlobal().warning(Boolean.toString(taskAssignment.get(projectTitle).contains(taskToAssign)));
                if (taskAssignment.get(projectTitle).contains(taskToAssign)) {
                    throw new CommandException(String.format(MESSAGE_DUPLICATE_ASSIGNMENT, personToAssign.getName().fullName));
                }
                taskAssignment.get(projectTitle).add(taskToAssign); // Add tha task to the list of tasks
            } else {
                taskAssignment.put(projectTitle, new ArrayList<>());
                taskAssignment.get(projectTitle).add(taskToAssign);
            }

            HashMap<String, List<Task>> updatedTaskAssignment = previousPerformance.getTaskAssignment();

            Performance updatedPerformance = previousPerformance.setTasksAssigned(updatedTaskAssignment);

            Person editedPerson = new Person(personToAssign.getName(), personToAssign.getPhone(), personToAssign.getEmail(),
                    personToAssign.getProfilePicture(), personToAssign.getAddress(),
                    personToAssign.getTags(), personToAssign.getTimetable(), updatedPerformance);

            editedPerson.getProjects().addAll(personToAssign.getProjects());

            assignedPersons.add(editedPerson);
        }

        return assignedPersons;
    }
}
