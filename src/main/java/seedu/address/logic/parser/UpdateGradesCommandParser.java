package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateGradesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateGradesCommand object
 */
public class UpdateGradesCommandParser implements Parser<UpdateGradesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateGradesCommand
     * and returns an UpdateGradesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateGradesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT, PREFIX_STUDENT, PREFIX_MARKS);

        Index assignment;
        List<String> marks = new ArrayList<>();
        Index student = null;
        String grade = null;
        boolean updatingIndividualGrades = false;

        if (!arePrefixesPresent(argMultimap, PREFIX_ASSIGNMENT, PREFIX_MARKS) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateGradesCommand.MESSAGE_USAGE));
        }

        assignment = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ASSIGNMENT).get());

        if (argMultimap.getValue(PREFIX_STUDENT).isPresent()) {
            student = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get());
            grade = ParserUtil.parseSingleAssignmentGrade(argMultimap.getValue(PREFIX_MARKS).get());
            updatingIndividualGrades = true;
        } else if (argMultimap.getValue(PREFIX_MARKS).isPresent()) {
            marks = ParserUtil.parseAssignmentGrades(argMultimap.getValue(PREFIX_MARKS).get());
        }

        if (updatingIndividualGrades) {
            return new UpdateGradesCommand(assignment, student, grade);
        } else {
            return new UpdateGradesCommand(assignment, marks);
        }
    }

    /**
     * Returns true if the prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
