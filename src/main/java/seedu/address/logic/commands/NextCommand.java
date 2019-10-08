package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.core.CommandResult;
import seedu.address.logic.commands.core.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;

public class NextCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_SUCCESS = "Next patient has been allocated";
    public static final String MESSAGE_UNDO_NEXT_SUCCESS = "Allocation has been undone";
    public static final String MESSAGE_UNDO_NEXT_ERROR = "Changes cannot be undone!";

    private Person personToDelete;

    public NextCommand() {
        this.personToDelete = null;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("dsadsad");
    }

    @Override
    public CommandResult undo(Model model) {
        return new CommandResult("dsadsad");
    }
}
