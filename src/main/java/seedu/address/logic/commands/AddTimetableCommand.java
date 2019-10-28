package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.timetable.TimeTable;
import seedu.address.model.timetable.TimeTableInput;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Edits the details of an existing person in the address book.
 */
public class AddTimetableCommand extends Command {

    public static final String COMMAND_WORD = "addTimetable";

    public static final Prefix PREFIX_TIMETABLE = new Prefix("t/");

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add timetable to the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing timetable will be overwritten by the input timetable.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TIMETABLE + "FILEPATH]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TIMETABLE + "/path/to/timetable/file ";

    public static final String MESSAGE_ADD_TIMETABLE_SUCCESS = "Added Timetable: %s to Person: %s";
    public static final String MESSAGE_NO_TIMETABLE = "A filepath to the timetable file must be provided.";
    public static final String MESSAGE_INVALID_FILEPATH = "Please provide a proper absolute filepath to the timetable file";
    public static final String MESSAGE_NO_PERSON = "An index of the person must be inputted";

    private final Index index;
    private final String absoluteFilepath;

    /**
     * @param index of the person in the filtered person list to edit
     * @param absoluteFilepath absoluteFilepath to timetable file
     */
    public AddTimetableCommand(Index index, String absoluteFilepath) {
        requireNonNull(index);
        requireNonNull(absoluteFilepath);

        this.index = index;
        this.absoluteFilepath = absoluteFilepath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, IllegalValueException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        TimeTable timeTable = null;
        try {
            timeTable = new TimeTableInput().getTabletableFromFilepath(absoluteFilepath);
        } catch (IOException e) {
            throw new IllegalValueException(MESSAGE_INVALID_FILEPATH);
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getProfilePicture(),
                personToEdit.getAddress(), personToEdit.getTags(), timeTable);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_TIMETABLE_SUCCESS, timeTable, editedPerson), COMMAND_WORD);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTimetableCommand)) {
            return false;
        }

        // state check
        AddTimetableCommand a = (AddTimetableCommand) other;
        return index.equals(a.index)
                && absoluteFilepath.equals(a.absoluteFilepath);
    }
}
