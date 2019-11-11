package seedu.address.logic.commands.merge;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Merges the user input and the duplicate {@code Person}.
 */
public class MergePersonCommand extends MergeCommand {

    public static final String COMMAND_WORD = "merge";

    public static final String MESSAGE_SUCCESS = "Profile was successfully updated:\n%1$s";
    public static final String MERGE_COMMAND_PROMPT = "Do you wish to edit this person's %1$s?";
    public static final String MERGE_ORIGINAL_HEADER = "Original: ";
    public static final String MERGE_INPUT_HEADER = "Input: ";
    public static final String MERGE_INSTRUCTIONS = "\nPlease press enter or 'yes' to proceed or 'no' to skip.";

    private static final Logger logger = LogsCenter.getLogger(MergePersonCommand.class);

    private final Person inputPerson;
    private Person originalPerson;
    private ArrayList<String[]> differentFields = new ArrayList<>();


    /**
     * Creates an {@code MergePersonCommand} to update the original {@code Person} to the new {@code Person}.
     */
    public MergePersonCommand(Person inputPerson) {
        requireNonNull(inputPerson);
        this.inputPerson = inputPerson;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePersonWithMergeException {
        requireNonNull(model);
        logger.info("Initialising merge process...");
        this.originalPerson = model.getPerson(inputPerson);
        getDifferences();
        assert(differentFields.size() != 0);
        logger.info(differentFields.size() + " differences found.");
        return new CommandResult(getNextMergePrompt());
    }

    public void updateOriginalPerson(Person editedPerson) {
        this.originalPerson = editedPerson;
    }

    public String getNextMergePrompt() {
        String[] fieldThatIsDifferent = differentFields.get(0);
        String fieldType = fieldThatIsDifferent[0];
        String original = fieldThatIsDifferent[1];
        String input = fieldThatIsDifferent[2];
        StringBuilder mergePrompt = new StringBuilder();
        mergePrompt.append(String.format(MERGE_COMMAND_PROMPT, fieldType) + "\n")
                .append(MERGE_ORIGINAL_HEADER + original + "\n")
                .append(MERGE_INPUT_HEADER + input)
                .append(MERGE_INSTRUCTIONS);
        return mergePrompt.toString();
    }

    /**
     * Returns an array of the first different field type, original field info and the input field info.
     *
     */
    public String[] getDifferences() {
        boolean hasDifferentName = !originalPerson.getName().equals(inputPerson.getName());
        if (hasDifferentName) {
            differentFields.add(new String[]{
                Name.DATA_TYPE, originalPerson.getName().fullName, inputPerson.getName().fullName});
        }
        boolean hasDifferentPhone = !originalPerson.getPhone().equals(inputPerson.getPhone());
        if (hasDifferentPhone) {
            differentFields.add(new String[]{
                Phone.DATA_TYPE, originalPerson.getPhone().value, inputPerson.getPhone().value});
        }
        boolean hasDifferentAddress = !originalPerson.getAddress().equals(inputPerson.getAddress());
        if (hasDifferentAddress) {
            differentFields.add(new String[]{
                Address.DATA_TYPE, originalPerson.getAddress().value, inputPerson.getAddress().value});
        }
        boolean hasDifferentEmail = !originalPerson.getEmail().equals(inputPerson.getEmail());
        if (hasDifferentEmail) {
            differentFields.add(new String[]{
                Email.DATA_TYPE, originalPerson.getEmail().value, inputPerson.getEmail().value});
        }
        boolean hasDifferentDateOfBirth = !originalPerson.getDateOfBirth().equals(inputPerson.getDateOfBirth());
        if (hasDifferentDateOfBirth) {
            differentFields.add(new String[]{
                DateOfBirth.DATA_TYPE, originalPerson.getDateOfBirth().value, inputPerson.getDateOfBirth().value});
        }
        boolean hasDifferentGender = !originalPerson.getGender().equals(inputPerson.getGender());
        if (hasDifferentGender) {
            differentFields.add(new String[]{
                Gender.DATA_TYPE, originalPerson.getGender().gender, inputPerson.getGender().gender});
        }
        return null;
    }

    public void removeFirstDifferentField() {
        differentFields.remove(0);
    }

    public String getNextMergeFieldType() {
        return differentFields.get(0)[0];
    }

    public Person getInputPerson() {
        return this.inputPerson;
    }

    public Person getOriginalPerson() {
        return this.originalPerson;
    }

    public boolean onlyOneMergeLeft() {
        return differentFields.size() == 1;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergePersonCommand // instanceof handles nulls
                && inputPerson.equals(((MergePersonCommand) other).inputPerson));
    }
}
