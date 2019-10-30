package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEXES;
import static seedu.address.model.date.AthletickDate.DATE_FORMAT;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
import seedu.address.model.training.Training;

/**
 * Adds a training session of everyone except players specified by the indexes on the specified date.
 */
public class TrainingCommandAbsent extends TrainingCommand {
    public static final String ABSENT_FLAG = "-a";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + ABSENT_FLAG
            + ": Adds training session of everyone except people identified by the index numbers used in the "
            + "displayed person " + "list at" + " the specified date.\n"
            + "If no date is specified, the current date is used.\nParameters: " + "#/INDEX [INDEX] [INDEX] [d/DATE]\n"
            + "Date must be in the format: " + DATE_FORMAT + "\nIndex must be a positive integer\nExample: "
            + COMMAND_WORD + " " + ABSENT_FLAG + " " + PREFIX_DATE + "20/10/2019 " + PREFIX_INDEXES + " 1 5 7";

    /**
     * Creates a TrainingCommandAbsent to add a training session on {@code date} using the {@code indexList}.
     *
     * @param date      Date of training.
     * @param indexList List of index indicating absentees.
     */
    public TrainingCommandAbsent(AthletickDate date, List<Index> indexList) {
        super(date, indexList);
    }

    /**
     * Executes the TrainingCommandAbsent which adds a training to the Attendance in model. People in indexList are
     * marked as absent while everyone else is marked as present.
     *
     * @param model {@code Model} where Training is saved.
     * @return Outcome of executed command.
     * @throws CommandException Thrown when specified indexes are invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        // Check if indexes are valid

        for (Index index : super.getIndexList()) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        // Check if training exists.
        if (model.hasTraining(super.getDate())) {
            throw new CommandException(TrainingCommand.DUPLICATE_TRAINING);
        }

        List<Person> allPeople = model.getAddressBook().getPersonList();
        HashMap<Person, Boolean> trainingAttendance = new HashMap<>();

        // Set all people in the address book to true, indicating that they attended.
        for (Person person : allPeople) {
            trainingAttendance.put(person, true);
        }

        // Filter indexes from the lastShownList
        List<Person> attendedPersons = new LinkedList<>();
        for (Index index : super.getIndexList()) {
            Person personWhoAttended = lastShownList.get(index.getZeroBased());
            attendedPersons.add(personWhoAttended);
        }

        // Change the value in trainingAttendance of these people to false, indicating that they attended.
        for (Person person : attendedPersons) {
            trainingAttendance.put(person, false);
        }

        Training training = new Training(super.getDate(), trainingAttendance);
        model.addTraining(training);
        return new CommandResult(TRAINING_ADD_SUCCESS);
    }
}
