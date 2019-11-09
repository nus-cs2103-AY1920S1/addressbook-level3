package seedu.address.logic.commands.merge;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Updates a data field of a duplicate {@code Person} in the Addressbook.
 */
public class MergePersonConfirmedCommand extends MergeConfirmedCommand {

    public static final String MESSAGE_MERGE_FIELD_SUCCESS = "Successfully updated %1$s";

    private static final Logger logger = LogsCenter.getLogger(MergePersonConfirmedCommand.class);

    private MergePersonCommand previousMergeCommand;


    /**
     * Creates an {@code MergePersonConfirmedCommand} to update the original {@code Person}.
     */
    public MergePersonConfirmedCommand(MergePersonCommand previousMergeCommand) {
        requireNonNull(previousMergeCommand);
        this.previousMergeCommand = previousMergeCommand;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePersonWithMergeException {
        requireNonNull(model);
        Person originalPerson = previousMergeCommand.getOriginalPerson();
        Person inputPerson = previousMergeCommand.getInputPerson();
        String fieldType = previousMergeCommand.getNextMergeFieldType();
        EditCommand.EditPersonDescriptor editPersonDescriptor = getEditPersonDescriptor(fieldType, inputPerson);
        logger.info("Executing merge: editing " + fieldType);
        EditCommand edit = new EditCommand();
        Person editedPerson = edit.executeForMerge(originalPerson, editPersonDescriptor, model);
        previousMergeCommand.updateOriginalPerson(editedPerson);
        if (isLastMerge()) {
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

    /**
     * Creates an EditPersonDescriptor to be used to update the field.
     *
     */
    public EditCommand.EditPersonDescriptor getEditPersonDescriptor(String fieldType, Person inputPerson) {
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
        case Gender.DATA_TYPE:
            editPersonDescriptor.setGender(inputPerson.getGender());
            break;
        default:
            break;
        }
        return editPersonDescriptor;
    }

    @Override
    public boolean isLastMerge() {
        return previousMergeCommand.onlyOneMergeLeft();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergePersonConfirmedCommand // instanceof handles nulls
                && previousMergeCommand.equals(((MergePersonConfirmedCommand) other).previousMergeCommand));
    }
}
