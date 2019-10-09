package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.PanelType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.phone.Phone;

/**
 * Adds a phone to SML.
 */
public class AddPhoneCommand extends Command {

    public static final String COMMAND_WORD = "add-p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a phone to SML. "
            + "Parameters: "
            + PREFIX_PHONENAME + "NAME "
            + PREFIX_BRAND + "BRAND "
            + PREFIX_CAPACITY + "CAPACITY (in GB)"
            + PREFIX_COLOUR + "COLOUR"
            + PREFIX_COST + "COST"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONENAME + "iPhone 11"
            + PREFIX_BRAND + "Apple"
            + PREFIX_CAPACITY + "128"
            + PREFIX_COLOUR + "Purple"
            + PREFIX_COST + "$900"
            + PREFIX_TAG + "NEW "
            + PREFIX_TAG + "Bought Oct 2019";

    public static final String MESSAGE_SUCCESS = "New phone added: %1$s";
    public static final String MESSAGE_DUPLICATE_PHONE = "This phone already exists in SML.";

    private final Phone toAdd;

    /**
     * Creates an AddPhoneCommand to add the specified {@code Phone}
     */
    public AddPhoneCommand(Phone phone) {
        requireNonNull(phone);
        toAdd = phone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPhone(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }

        model.addPhone(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), PanelType.PHONE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPhoneCommand // instanceof handles nulls
                && toAdd.equals(((AddPhoneCommand) other).toAdd));
    }
}
