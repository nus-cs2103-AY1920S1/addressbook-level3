package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;

import seedu.address.logic.commands.NusmodCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

public class NusmodCommandParser implements Parser<NusmodCommand> {
    @Override
    public NusmodCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULECODE);

        if(!arePrefixesPresent(argMultimap, PREFIX_MODULECODE) || !argMultimap.getPreamble().isEmpty()){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NusmodCommand.MESSAGE_USAGE));
        }

        String moduleCode = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULECODE).get());

        NusmodCommand nusmodCommand = new NusmodCommand(moduleCode);
        return nusmodCommand;
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
