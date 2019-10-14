package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.training.Training;

/**
 * Adds a training session of players specified by the index.
 */
public class TrainingCommand extends Command {

    public static final String COMMAND_WORD = "training";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds training session of people identified by the index numbers used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 5 7";

    public static final String TRAINING_ADD_SUCCESS = "Training successfully added.";

    public Date date;
    public List<Index> indexList;

    public TrainingCommand(List<Index> indexList) {
        this.date = new Date();
        this.indexList = indexList;
    }

    public TrainingCommand(Date date, List<Index> indexList) {
        this.date = date;
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        // Check if indexes are valid
        for(Index index: indexList) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        List<Person> allPeople = model.getAddressBook().getPersonList();

        HashMap<Person, Boolean> trainingAttendance = new HashMap<>();
        // Set all people in the address book to did not attend
        for(Person person: allPeople) {
            trainingAttendance.put(person, false);
        }

        // Filter indexes from the lastShownList
        List<Person> attendedPersons = new LinkedList<>();
        for(Index index: indexList) {
            Person personWhoAttended = lastShownList.get(index.getZeroBased());
            attendedPersons.add(personWhoAttended);
        }

        // Change the value in trainingAttendance of these people to true
        for(Person person: attendedPersons) {
            trainingAttendance.put(person, true);
        }

        Training training = new Training(date, trainingAttendance);
        model.addTraining(training);
        return new CommandResult(TRAINING_ADD_SUCCESS);
    }
}
