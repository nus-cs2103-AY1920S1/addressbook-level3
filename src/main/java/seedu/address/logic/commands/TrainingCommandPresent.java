package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEXES;
import static seedu.address.model.date.AthletickDate.DATE_FORMAT_TYPE_ONE;

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
 * Adds a training session of players specified by the indexes on the specified date.
 */
public class TrainingCommandPresent extends TrainingCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds training session of people identified by the index numbers used in the displayed person list at"
            + " the specified date.\nIf no date is specified, the current date is used.\n"
            + "Parameters: " + "#/INDEX [INDEX] [INDEX] [d/DATE]\nDate must be in the format: " + DATE_FORMAT_TYPE_ONE
            + "\n" + "Index must be a positive integer\nExample: " + COMMAND_WORD + " " + PREFIX_DATE + "20/10/2019 "
            + PREFIX_INDEXES + " 1 5 7";

    /**
     * Creates a TrainingCommandPresent to add a training session on {@code date} using the {@code indexList}.
     *
     * @param date      Date of training.
     * @param indexList List of index indicating those who were present.
     */
    public TrainingCommandPresent(AthletickDate date, List<Index> indexList) {
        super(date, indexList);
    }

    /**
     * Executes the TrainingCommandPresent which adds a training to the Attendance in model. People in indexList are
     * marked as present while everyone else is marked as absent.
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

        if (model.hasTraining(super.getDate())) {
            throw new CommandException(TrainingCommand.DUPLICATE_TRAINING);
        }

        List<Person> allPeople = model.getAddressBook().getPersonList();

        HashMap<Person, Boolean> trainingAttendance = new HashMap<>();
        // Set all people in the address book to did not attend

        for (Person person : allPeople) {
            trainingAttendance.put(person, false);
        }

        // Filter indexes from the lastShownList
        List<Person> attendedPersons = new LinkedList<>();
        for (Index index : super.getIndexList()) {
            Person personWhoAttended = lastShownList.get(index.getZeroBased());
            attendedPersons.add(personWhoAttended);
        }

        // Change the value in trainingAttendance of these people to true
        for (Person person : attendedPersons) {
            trainingAttendance.put(person, true);
        }

        Training training = new Training(super.getDate(), trainingAttendance);
        model.addTraining(training);
        AthletickDate date = super.getDate();
        date.setType(2);
        return new CommandResult(TRAINING_ADD_SUCCESS, date, model);
    }
}
