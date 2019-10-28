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
import java.net.URL;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Edits the details of an existing person in the address book.
 */
public class AddTimetableCommand extends Command {

    public static final String COMMAND_WORD = "addTimetable";

    public static final Prefix PREFIX_FILEPATH = new Prefix("f/");
    public static final Prefix PREFIX_NUSMODS_URL = new Prefix("n/");

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add timetable to the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing timetable will be overwritten by the input timetable.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_FILEPATH + "FILEPATH] "
            + "[" + PREFIX_NUSMODS_URL + "NUSMODS_URL]...\n"
            + "Example:\n"
            + COMMAND_WORD + " 1 " + PREFIX_FILEPATH + "/path/to/timetable/file\n"
            + COMMAND_WORD + " 1 " + PREFIX_NUSMODS_URL + "https://nusmods.com/timetable/sem-1/share?CS2100=LAB:05,TUT:02,LEC:1&CS2101=&CS2103T=LEC:G13&CS2105=TUT:03,LEC:1&CS3241=TUT:05,LEC:1&CS3243=TUT:01,LEC:1&GEQ1000=TUT:D27";

    public static final String MESSAGE_ADD_TIMETABLE_SUCCESS = "Added Timetable: %s to Person: %s";
    public static final String MESSAGE_INVALID_FILEPATH = "Please provide a proper absolute filepath to the timetable file";
    public static final String MESSAGE_INVALID_URL = "URL invalid. Please provide a proper URL to NUSMODs";
    public static final String MESSAGE_NO_TIMETABLE_SOURCE = "Please provide either an NUSMods URL or a filepath to a timetable time";

    private final Index index;
    private final String absoluteFilepath;
    private final URL url;

    /**
     * @param index of the person in the filtered person list to edit
     * @param absoluteFilepath absoluteFilepath to timetable file
     */
    public AddTimetableCommand(Index index, String absoluteFilepath) {
        this(index, absoluteFilepath, null);
    }

    public AddTimetableCommand(Index index, URL url) {
        this(index, null, url);
    }

    public AddTimetableCommand(Index index, String absoluteFilepath, URL url) {
        requireNonNull(index);
        this.index = index;
        this.absoluteFilepath = absoluteFilepath;
        this.url = url;
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
        if (absoluteFilepath != null) {
            try {
                timeTable = new TimeTableInput().getTabletableFromFilepath(absoluteFilepath);
            } catch (IOException e) {
                throw new IllegalValueException(MESSAGE_INVALID_FILEPATH);
            }
        } else if (url != null) {
            try {
                timeTable = new TimeTableInput().getTimetableFromUrl(url);
            } catch (IOException e) {
                throw new CommandException(MESSAGE_INVALID_URL);
            }
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
