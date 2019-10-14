package seedu.billboard.logic.commands;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.archive.Archive;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;

public class CreateArchiveCommand extends Command{

    public static final String COMMAND_WORD = "create-arc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates an archive with the name given.\n"
            + "Parameters: ARCHIVE NAME\n"
            + "Example: " + COMMAND_WORD + " January Groceries";

    public final String MESSAGE_SUCCESS;

    private final String archiveName;

    public CreateArchiveCommand(String archiveName) {
        this.archiveName = archiveName;
        this.MESSAGE_SUCCESS = "Successfully created [" + archiveName + "] archive" ;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if(archiveName.equals("")) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        Archive newArchive = new Archive(archiveName, new ArrayList<>());
        model.addArchive(newArchive);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
