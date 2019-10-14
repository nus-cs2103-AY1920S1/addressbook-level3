package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Adds a person to the address book.
 */
public class MergeConfirmedCommand extends Command {

    public static final String COMMAND_WORD = "yes";
    public static final String DEFAULT_COMMAND_WORD = "";

    public static final String MESSAGE_MERGE_FIELD_SUCCESS = "Successfully updated %1$s";

    private MergeCommand previousMergeCommand;


    /**
     * Creates an Merge Command to update the original {@code Person} to the new {@code Person}
     */
    public MergeConfirmedCommand(MergeCommand previousMergeCommand) {
        requireNonNull(previousMergeCommand);
        this.previousMergeCommand = previousMergeCommand;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePersonWithMergeException {
        requireNonNull(model);
        Person originalPerson = previousMergeCommand.getOriginalPerson();
        Person inputPerson = previousMergeCommand.getInputPerson();
        String fieldType = previousMergeCommand.getNextMergeFieldType();
        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        switch(fieldType) {
        case Phone.DATA_TYPE:
            editPersonDescriptor.setPhone(inputPerson.getPhone());
            break;
        case Address.DATA_TYPE:
            editPersonDescriptor.setAddress(inputPerson.getAddress());
            break;
        case Email.DATA_TYPE:
            editPersonDescriptor.setEmail(inputPerson.getEmail());
            break;
        case DateOfBirth.DATA_TYPE:
            editPersonDescriptor.setDateOfBirth(inputPerson.getDateOfBirth());
            break;
        default:
            break;
        }
        EditCommand edit = new EditCommand();
        Person editedPerson = edit.executeForMerge(originalPerson, editPersonDescriptor, model);
        previousMergeCommand.updateOriginalPerson(editedPerson);
        if (isLastMerge()) {
            previousMergeCommand.removeFirstDifferentField();
            return new CommandResult(String.format(MESSAGE_MERGE_FIELD_SUCCESS, fieldType)
                    + "\n" + String.format(previousMergeCommand.MESSAGE_SUCCESS,
                    previousMergeCommand.getOriginalPerson()));
        } else {
            previousMergeCommand.removeFirstDifferentField();
            String nextMerge = previousMergeCommand.getNextMergePrompt();
            return new CommandResult(String.format(MESSAGE_MERGE_FIELD_SUCCESS, fieldType)
                    + "\n" + nextMerge);
        }
    }

    public boolean isLastMerge() {
        return previousMergeCommand.onlyOneMergeLeft();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeConfirmedCommand // instanceof handles nulls
                && previousMergeCommand.equals(((MergeConfirmedCommand) other).previousMergeCommand));
    }
}
