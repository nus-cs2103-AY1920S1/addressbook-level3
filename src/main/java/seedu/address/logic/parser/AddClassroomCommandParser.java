package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSROOM;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddClassroomCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.classroom.Classroom;


/**
 *
 * Parses input arguments and creates a new AddClassroomCommand object
 */
public class AddClassroomCommandParser implements Parser<AddClassroomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddClassroomCommand
     * and returns an AddClassroomCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddClassroomCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASSROOM);

        if (!arePrefixesPresent(argMultimap, PREFIX_CLASSROOM)
                    || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassroomCommand.MESSAGE_USAGE));
        }

        String classroomName = ParserUtil.parseClassroomName(argMultimap.getValue(PREFIX_CLASSROOM).get());
        if (classroomName.equals("")) {
            throw new ParseException(String.format(AddClassroomCommand.MESSAGE_BLANK_CLASSNAME));
        }
        Classroom classroom = new Classroom(classroomName);
        return new AddClassroomCommand(classroom.getClassroomName());
    }

    /**
     * Returns true if the prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
