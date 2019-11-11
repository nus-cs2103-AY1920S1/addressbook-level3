package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Notes;

/**
 * Command to add note
 */
public class AddNotesCommand extends Command {

    public static final String COMMAND_WORD = "addnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a note to the address book. "
            + "Parameters: "
            + PREFIX_CLASSID + "MODULE CODE "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_CONTENT + "NOTE CONTENT"
            + "... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLASSID + "CS2103T "
            + PREFIX_TYPE + "tut "
            + PREFIX_CONTENT + "Checking for project meeting time ";

    public static final String MESSAGE_SUCCESS = "New note added: \n + %1$s";
    public static final String MESSAGE_DUPLICATE_NOTE = "This note already exists in the address book";

    private final Notes toAdd;

    public AddNotesCommand(Notes note) {
        requireNonNull(note);
        toAdd = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasNotes(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOTE);
        }

        model.addNotes(toAdd);
        model.commitTutorAid();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd),
                false, false, false, false, false,
                false, true, false);
    }

}
