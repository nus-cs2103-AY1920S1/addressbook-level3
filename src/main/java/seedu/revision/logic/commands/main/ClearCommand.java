package seedu.revision.logic.commands.main;

import static java.util.Objects.requireNonNull;

import seedu.revision.logic.commands.Command;
import seedu.revision.model.AddressBook;
import seedu.revision.model.Model;

/**
 * Clears the revision tool.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Test bank has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult().withFeedBack(MESSAGE_SUCCESS).build();
    }
}
