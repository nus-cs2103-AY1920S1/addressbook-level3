package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_CREDITS;
import static mams.logic.parser.CliSyntax.PREFIX_EMAIL;
import static mams.logic.parser.CliSyntax.PREFIX_MATRICID;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static mams.logic.parser.CliSyntax.PREFIX_NAME;
import static mams.logic.parser.CliSyntax.PREFIX_SESSIONID;
import static mams.logic.parser.CliSyntax.PREFIX_TAG;

import mams.commons.core.index.Index;
import mams.logic.commands.AddModCommand;
import mams.logic.parser.exceptions.ParseException;

public class AddModCommandParser implements Parser<AddModCommand> {

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param userInput
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public AddModCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MATRICID, PREFIX_MODULE_CODE,
                        PREFIX_SESSIONID);

        Index index;

        index
        return null;
    }



}
