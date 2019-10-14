package seedu.billboard.logic.parser;

import seedu.billboard.logic.commands.CreateArchiveCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class CreateArchiveCommandParser implements Parser<CreateArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateArchiveCommand
     * and returns a CreateArchiveCommand object for execution.
     */
    @Override
    public CreateArchiveCommand parse(String args) {
        String archiveName = args.trim();
        return new CreateArchiveCommand(archiveName);
    }
}
