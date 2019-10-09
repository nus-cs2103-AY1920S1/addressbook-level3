package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACAD_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.stream.Stream;

import seedu.address.logic.commands.ShowNusModCommand;
import seedu.address.logic.commands.ShowNusModCommand.ShowNusModCommandOptions;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new ShowNusModCommand object.
 */
public class ShowNusModCommandParser implements Parser<ShowNusModCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public ShowNusModCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_ACAD_YEAR, PREFIX_SEMESTER);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowNusModCommand.MESSAGE_USAGE));
        }

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());

        ShowNusModCommandOptions options = new ShowNusModCommandOptions();

        if (argMultimap.getValue(PREFIX_ACAD_YEAR).isPresent()) {
            options.setAcadYear(ParserUtil.parseAcadYear(argMultimap.getValue(PREFIX_ACAD_YEAR).get()));
        }

        if (argMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            options.setSemesterNo(ParserUtil.parseSemesterNo(argMultimap.getValue(PREFIX_SEMESTER).get()));
        }

        return new ShowNusModCommand(moduleCode, options);
    }
}
