package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePicture;
import seedu.address.storage.StorageManager;

import java.io.File;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

public class AddProfilePictureCommand extends Command {

    public static final String COMMAND_WORD = "addProfilePicture";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a profile picture to the person specified by the index. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_FILE_PATH + "File path to the image"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_FILE_PATH + "downloads/profile_picture";

    public static final String MESSAGE_ADD_PROFILE_PICTURE_SUCCESS = "Profile picture added for %1$s";

    private final Index index;
    private final File imgFile;

    public AddProfilePictureCommand(Index index, File imgFile) {
        this.index = index;
        this.imgFile = imgFile;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        String filePath = StorageManager.copyImageToData(imgFile, personToEdit.getName().toString());
        ProfilePicture profilePicture = new ProfilePicture(filePath);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                profilePicture, personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getTimetable(), personToEdit.getPerformance());

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_ADD_PROFILE_PICTURE_SUCCESS, editedPerson.getName().toString()), COMMAND_WORD);

    }

}
