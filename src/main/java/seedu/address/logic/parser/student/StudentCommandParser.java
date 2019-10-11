package seedu.address.logic.parser.student;

import static java.util.Objects.requireNonNull;
import seedu.address.commons.core.index.Index;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;

import java.util.HashMap;
import java.util.stream.Stream;

import seedu.address.logic.commands.student.StudentAddCommand;
import seedu.address.logic.commands.student.StudentCommand;
import seedu.address.logic.commands.student.StudentDeleteCommand;
import seedu.address.logic.commands.student.StudentEditCommand;
import seedu.address.logic.commands.student.StudentListCommand;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a parser for student commands.
 */
public class StudentCommandParser implements Parser<StudentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CreationQuestionCommand
     * and returns an CreateQuestionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public StudentCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_STUDENT, PREFIX_LIST,
                        PREFIX_DELETE);

        boolean isEdit = false;
        Index index = Index.fromZeroBased(0);
        try {
            String preamble = argMultimap.getPreamble();

            if (!preamble.isBlank()) {
                index = ParserUtil.parseIndex(preamble);
                isEdit = true;
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StudentEditCommand.MESSAGE_USAGE),
                    pe);
        }

        if (argMultimap.getValue(PREFIX_LIST).isPresent()) { // List command
            return new StudentListCommand();
        } else if (argMultimap.getValue(PREFIX_DELETE).isPresent()) { // Delete command
            try {
                int indexToDelete = Integer
                        .parseInt(argMultimap.getValue(PREFIX_DELETE).orElse("0"));

                if (indexToDelete <= 0) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    StudentDeleteCommand.MESSAGE_USAGE));
                }
                index.fromOneBased(indexToDelete);
            } catch (NumberFormatException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                StudentDeleteCommand.MESSAGE_USAGE));
            }

            return new StudentDeleteCommand(index);
        } else if (isEdit) { // Edit command
            // Add parameters to be edited. Note: the fields are optional
            HashMap<String, String> fields = new HashMap<>();
            fields.put("name", argMultimap.getValue(PREFIX_QUESTION).orElse(""));
            return new StudentEditCommand(index, fields);
        } else { // Create command
            if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(
                        String
                                .format(MESSAGE_INVALID_COMMAND_FORMAT, StudentAddCommand.MESSAGE_USAGE));
            }

            String name = argMultimap.getValue(PREFIX_STUDENT).orElse("");

            return new StudentAddCommand(name);
        }

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
