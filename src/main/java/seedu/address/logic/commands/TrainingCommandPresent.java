package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEXES;
import static seedu.address.model.date.AthletickDate.DATE_FORMAT_TYPE_ONE;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
import seedu.address.model.training.Training;
import seedu.address.ui.feature.Feature;

/**
 * Adds a training session of players specified by the indexes on the specified
 * date.
 */
public class TrainingCommandPresent extends TrainingCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds training session of people identified by the index numbers used in the displayed person list at"
            + " the specified date.\nIf no date is specified, the current date is used.\n" + "Parameters: "
            + "#/INDEX [INDEX] [INDEX] [d/DATE]\nDate must be in the format: " + DATE_FORMAT_TYPE_ONE + "\n"
            + "Index must be a positive integer\nExample: " + COMMAND_WORD + " " + PREFIX_DATE + "20102019 "
            + PREFIX_INDEXES + " 1 4 5";

    private Training trainingToAdd;
    /**
     * Creates a TrainingCommandPresent to add a training session on {@code date}
     * using the {@code indexList}.
     *
     * @param date      Date of training.
     * @param indexList List of index indicating those who were present.
     */
    public TrainingCommandPresent(AthletickDate date, List<Index> indexList) {
        super(date, indexList);
    }

    /**
     * Executes the TrainingCommandPresent which adds a training to the Attendance
     * in model. People in indexList are marked as present while everyone else is
     * marked as absent.
     *
     * @param model {@code Model} where Training is saved.
     * @return Outcome of executed command.
     * @throws CommandException Thrown when specified indexes are invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        AthletickDate date = super.getDate();
        List<Index> indexes = super.getIndexList();
        // Check if indexes are valid
        checkIndexesValid(indexes, model);

        // Create training
        Training training = createTrainingByPresent(date, model, indexes);
        this.trainingToAdd = training;

        CommandResult result;
        if (model.hasTrainingOn(super.getDate())) {
            result = new CommandResult(String.format(TRAINING_REPLACE_SUCCESS, date),
                    new Feature("calendar"), date, model);
        } else {
            result = new CommandResult(String.format(TRAINING_ADD_SUCCESS, date), new Feature(
                    "calendar"), date, model);
        }
        model.addTraining(training);
        date.setType(2);

        return result;
    }

    /**
     * Creates a training session using a list of indexes and marks them as present.
     * @param date    Date of training.
     * @param indexes Indexes of those who attended.
     * @return Created training session.
     */
    private static Training createTrainingByPresent(AthletickDate date, Model model, List<Index> indexes) {
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> allPeople = model.getAthletick().getPersonList();
        HashMap<Person, Boolean> trainingAttendance = new HashMap<>();
        // Set all people in the address book to did not attend
        for (Person person : allPeople) {
            trainingAttendance.put(person, false);
        }
        // Filter indexes from the lastShownList
        List<Person> attendedPersons = new LinkedList<>();
        for (Index index : indexes) {
            Person personWhoAttended = lastShownList.get(index.getZeroBased());
            attendedPersons.add(personWhoAttended);
        }
        // Change the value in trainingAttendance of these people to true
        for (Person person : attendedPersons) {
            trainingAttendance.put(person, true);
        }
        return new Training(date, trainingAttendance);
    }
    @Override
    public String toString() {
        return "'Add " + trainingToAdd + "' Command";
    }
}
