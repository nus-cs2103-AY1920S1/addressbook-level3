package seedu.billboard.logic.parser;

import seedu.billboard.logic.commands.ListArchiveCommand;

/**
 * Parses input arguments and creates a new ListArchiveCommand object
 */
public class ListArchiveCommandParser implements Parser<ListArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListArchiveCommand
     * and returns a ListArchiveCommand object for execution.
     */
    @Override
    public ListArchiveCommand parse(String args) {
        String archiveName = args.trim();
        return new ListArchiveCommand(archiveName);
    }

}
