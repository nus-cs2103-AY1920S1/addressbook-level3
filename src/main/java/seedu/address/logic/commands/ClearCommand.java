package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.entity.UniqueIdentificationNumberMaps;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        UniqueIdentificationNumberMaps.clearAllEntries();
        SelectCommand selectCommand = new SelectCommand(Integer.MAX_VALUE);
        selectCommand.execute(model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
