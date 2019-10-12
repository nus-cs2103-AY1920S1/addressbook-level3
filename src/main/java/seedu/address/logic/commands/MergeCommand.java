package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

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
public class MergeCommand extends Command {

    public static final String COMMAND_WORD = "merge";

    public static final String MESSAGE_SUCCESS = "Profile was successfully updated:\n%1$s";
    public static final String MERGE_COMMAND_PROMPT = "Do you wish to edit this person's %1$s?";
    public static final String ORIGINAL_HEADER = "Original: ";
    public static final String INPUT_HEADER = "Input: ";

    private final Person inputPerson;
    private Person originalPerson;
    private ArrayList<String[]> differentFields = new ArrayList<>();


    /**
     * Creates an Merge Command to update the original {@code Person} to the new {@code Person}
     */
    public MergeCommand(Person inputPerson) {
        requireNonNull(inputPerson);
        this.inputPerson = inputPerson;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePersonWithMergeException {
        requireNonNull(model);
        StringBuilder prompt = new StringBuilder();
        this.originalPerson = model.getPerson(inputPerson);
        getDifferences();
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
                .append(ORIGINAL_HEADER + original + "\n")
                .append(INPUT_HEADER + input);
        return mergePrompt.toString();
    }

    /**
     * Returns an array of the first different field type, original field info and the input field info.
     * @return
     */
    private String[] getDifferences() {
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
                || (other instanceof MergeCommand // instanceof handles nulls
                && inputPerson.equals(((MergeCommand) other).inputPerson));
    }
}
