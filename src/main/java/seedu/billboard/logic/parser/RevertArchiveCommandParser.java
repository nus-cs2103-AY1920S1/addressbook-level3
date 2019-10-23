package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_ARCHIVE;

import seedu.billboard.commons.core.index.Index;
import seedu.billboard.logic.commands.RevertArchiveCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class RevertArchiveCommandParser implements Parser<RevertArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RevertArchiveCommand
     * and returns a RevertArchiveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RevertArchiveCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ARCHIVE);

        try {
            String archiveName = ParserUtil.parseArchive(argMultimap.getValue(PREFIX_ARCHIVE).get());
            Index targetIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            return new RevertArchiveCommand(archiveName, targetIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RevertArchiveCommand.MESSAGE_USAGE), pe);
        }
    }
}
