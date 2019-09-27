package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MATNO;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NUSID;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.tarence.logic.commands.AddStudentCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.person.Person;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.student.Student;

/**
 * Parses input arguments and creates a new AddStudentCommand object
 */
public class AddStudentCommandParser implements Parser<AddStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                StudentArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EMAIL);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStudentCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Optional<MatricNum> matricNum;
        if (argMultimap.getValue(PREFIX_MATNO).isPresent()) {
            matricNum = Optional.of(ParserUtil.parseMatricNum(argMultimap.getValue(PREFIX_MATNO).get()));
        } else {
            matricNum = Optional.empty();
        }
        Optional<NusnetId> nusnetId;
        if (argMultimap.getValue(PREFIX_NUSID).isPresent()) {
            nusnetId = Optional.of(ParserUtil.parseNusnetId(argMultimap.getValue(PREFIX_NUSID).get()));
        } else {
            nusnetId = Optional.empty();
        }

        Person person = new Student(name, email, matricNum, nusnetId);

        return new AddStudentCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
