package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.phone.Phone;

/**
 * Adds a phone to SML.
 */
public class AddPhoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add-p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a phone to SML. "
            + "Parameters: "
            + PREFIX_IDENTITY_NUM + "IDENTITY NUMBER (IMEI) "
            + PREFIX_SERIAL_NUM + "SERIAL NUMBER "
            + PREFIX_PHONE_NAME + "NAME "
            + PREFIX_BRAND + "BRAND "
            + PREFIX_CAPACITY + "CAPACITY (in GB) "
            + PREFIX_COLOUR + "COLOUR "
            + PREFIX_COST + "COST "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_IDENTITY_NUM + "543407158585522 "
            + PREFIX_SERIAL_NUM + "A123bcfe29 "
            + PREFIX_PHONE_NAME + "iPhone 11 "
            + PREFIX_BRAND + "Apple "
            + PREFIX_CAPACITY + "128 "
            + PREFIX_COLOUR + "Purple "
            + PREFIX_COST + "$900 "
            + PREFIX_TAG + "NEW "
            + PREFIX_TAG + "Cool";

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
    public CommandResult executeUndoableCommand(Model model, CommandHistory commandHistory,
                                                UndoRedoStack undoRedoStack) throws CommandException {
        requireNonNull(model);

        if (model.hasPhone(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }

        model.addPhone(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), UiChange.PHONE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPhoneCommand // instanceof handles nulls
                && toAdd.equals(((AddPhoneCommand) other).toAdd));
    }
}
