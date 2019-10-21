package seedu.address.logic.commands.bio;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DP_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOALS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICAL_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OTHER_BIO_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROFILE_DESC;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bio.User;
import seedu.address.ui.DisplayPaneType;

/**
 * Adds a user to the address book.
 */
public class AddBioCommand extends Command {

    public static final String COMMAND_WORD = "addbio";

    public static final String MESSAGE_USAGE = "\n" + COMMAND_WORD + ": Adds a user to the address book.\n"
            + "Note that Name, contact number(s), emergency contact(s) and medical condition(s) cannot be empty.\n\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_DP_PATH + "DP PATH] "
            + "[" + PREFIX_PROFILE_DESC + "PROFILE DESCRIPTION] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_DATE_OF_BIRTH + "DATE OF BIRTH] "
            + PREFIX_CONTACT_NUMBER + "CONTACT NUMBER... "
            + PREFIX_EMERGENCY_CONTACT + "EMERGENCY CONTACT... "
            + PREFIX_MEDICAL_CONDITION + "MEDICAL CONDITION... "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_GOALS + "GOAL]... "
            + "[" + PREFIX_OTHER_BIO_INFO + "OTHER INFO]\n\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DP_PATH + "/Users/John/Doge.jpg "
            + PREFIX_PROFILE_DESC + "Sometimes I like to pretend that I'm a carrot. "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_GENDER + "Male "
            + PREFIX_DATE_OF_BIRTH + "31/12/1900 "
            + PREFIX_CONTACT_NUMBER + "91234567 "
            + PREFIX_EMERGENCY_CONTACT + "98765432 "
            + PREFIX_EMERGENCY_CONTACT + "81234567 "
            + PREFIX_MEDICAL_CONDITION + "Type II Diabetes "
            + PREFIX_MEDICAL_CONDITION + "High Blood Pressure "
            + PREFIX_ADDRESS + "Blk 123 Example Rd #99-99 S(612345) "
            + PREFIX_GOALS + "Lose 10kg by 19/12/2019 "
            + PREFIX_OTHER_BIO_INFO + "Dislikes potatoes";

    public static final String MESSAGE_SUCCESS = "I've successfully added your biography with the following "
            + "information:\n\n%1$s";
    public static final String MESSAGE_BIO_ALREADY_EXISTS = "Oops! There is already an existing biography. "
            + "Try using the ["
            + ClearBioCommand.COMMAND_WORD + "] , ["
            + EditBioCommand.COMMAND_WORD + "] or the ["
            + BioCommand.COMMAND_WORD + "]  command to clear, edit or view existing biography respectively instead.";

    private final User toAdd;

    /**
     * Creates an AddBioCommand to add the specified {@code User}
     */
    public AddBioCommand(User user) {
        requireNonNull(user);
        toAdd = user;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.bioExists()) {
            throw new CommandException(MESSAGE_BIO_ALREADY_EXISTS);
        }

        StringBuilder addedFields = new StringBuilder();

        model.addUser(toAdd);
        toAdd.getFieldMap().forEach((key, value) -> {
            if (!value.isEmpty() && !value.equals("[]")) {
                addedFields.append("- ").append(key).append(": ")
                        .append(value).append("\n");
            }
        });

        return new CommandResult(String.format(MESSAGE_SUCCESS, addedFields.toString().trim()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBioCommand // instanceof handles nulls
                && toAdd.equals(((AddBioCommand) other).toAdd));
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.BIO;
    }

    @Override
    public boolean getnewPaneIsToBeCreated() {
        return true;
    }

}
