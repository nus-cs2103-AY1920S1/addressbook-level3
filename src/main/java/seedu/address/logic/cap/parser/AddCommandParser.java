package seedu.address.logic.cap.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_CREDIT;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_TITLE;

import java.lang.module.InvalidModuleDescriptorException;
import java.util.stream.Stream;

import seedu.address.logic.cap.commands.AddCommand;
import seedu.address.logic.cap.parser.exceptions.ParseException;
import seedu.address.model.cap.person.Credit;
import seedu.address.model.cap.person.Grade;
import seedu.address.model.cap.person.ModuleCode;
import seedu.address.model.cap.person.Semester;
import seedu.address.model.cap.person.Title;
import seedu.address.model.common.Module;


/**
 * Parses input arguments and creates a new AddCommand object
 */

public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException, InvalidModuleDescriptorException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_TITLE, PREFIX_SEMESTER,
                        PREFIX_CREDIT, PREFIX_GRADE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_TITLE, PREFIX_SEMESTER, PREFIX_CREDIT, PREFIX_GRADE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Module module;

        try {
            ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
            Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
            Semester semester = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get());
            Credit credit = ParserUtil.parseCredit(Integer.parseInt(argMultimap.getValue(PREFIX_CREDIT).get()));
            Grade grade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get());
            module = new Module(moduleCode, title, semester, credit, grade);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        return new AddCommand(module);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
