package seedu.address.logic.parser.student;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.student.StudentAddCommand;
import seedu.address.logic.commands.student.StudentCommand;
import seedu.address.logic.commands.student.StudentDeleteCommand;
import seedu.address.logic.commands.student.StudentEditCommand;
import seedu.address.logic.commands.student.StudentEditCommand.EditStudentDescriptor;
import seedu.address.logic.commands.student.StudentListCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Represents a parser for student commands.
 */
public class StudentCommandParser implements Parser<StudentCommand> {

    private static final Logger logger = LogsCenter.getLogger(StudentCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the CreationStudentCommand
     * and returns an CreateStudentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public StudentCommand parse(String args) throws ParseException {
        String arguments = args;
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_STUDENT, PREFIX_LIST,
                        PREFIX_DELETE, PREFIX_TAG);

        boolean isEdit = false;
        Index index = Index.fromZeroBased(0);
        try {
            String preamble = argMultimap.getPreamble();

            if (!preamble.isBlank()) {
                index = ParserUtil.parseIndex(preamble);
                isEdit = true;
            }
        } catch (ParseException pe) {
            logger.info("error in student command preamble.");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StudentEditCommand.MESSAGE_USAGE),
                    pe);
        }

        if (argMultimap.getValue(PREFIX_LIST).isPresent()) { // List command
            return new StudentListCommand();
        }
        if (argMultimap.getValue(PREFIX_DELETE).isPresent()) { // Delete command
            return deleteCommand(index, argMultimap);
        }
        if (!isEdit) { // Create command
            return addCommand(argMultimap);
        }
        if (isEdit) { // Edit command
            return editCommand(index, argMultimap);
        }

        return addCommand(null);
    }

    /**
     * Deletes a student from student list.
     *
     * @param index       Index of student to be deleted.
     * @param argMultimap Arguments Multimap.
     * @return Student delete command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private StudentDeleteCommand deleteCommand(Index index, ArgumentMultimap argMultimap) throws ParseException {
        try {
            int indexToDelete = Integer
                    .parseInt(argMultimap.getValue(PREFIX_DELETE).orElse("0"));

            if (indexToDelete <= 0) {
                logger.info("student delete index must be a positive integer.");
                throw new ParseException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }
            index = index.fromOneBased(indexToDelete);
        } catch (NumberFormatException e) {
            logger.info("error in the student delete index number format");
            throw new ParseException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        return new StudentDeleteCommand(index);
    }

    /**
     * Edits student in student list.
     *
     * @param index       Index of student to be edited.
     * @param argMultimap Arguments Multimap.
     * @return Student edit command if the parsing was successful.
     * @throws ParseException -
     */
    private StudentEditCommand editCommand(Index index, ArgumentMultimap argMultimap) throws ParseException {
        EditStudentDescriptor editStudentDescriptor = new EditStudentDescriptor();
        editStudentDescriptor.setName(ParserUtil.parseStudentName(argMultimap.getValue(PREFIX_STUDENT).get()));
        return new StudentEditCommand(index, editStudentDescriptor);
    }

    /**
     * Add student to student list.
     *
     * @param argMultimap Arguments Multimap.
     * @return Student add command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private StudentAddCommand addCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.info("missing fields in the student add command.");
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT, StudentAddCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseStudentName(argMultimap.getValue(PREFIX_STUDENT).get());
        Set<Tag> tagList = parseStudentTags(argMultimap);
        return new StudentAddCommand(new Student(name, tagList));
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

    /**
     * Parses for {@code Set<Tag>} object given the {@code ArgumentMultimap} object.
     *
     * @param argMultimap the object to parse from.
     * @return {@code Set<Tag>} object based on the values in the input {@code ArgumentMultimap}.
     */
    private Set<Tag> parseStudentTags(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
    }


}
