package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSROOM;

import java.util.stream.Stream;

import seedu.address.logic.commands.SetClassroomCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.classroom.Classroom;

/**
 * Parses input arguments and creates a new SetClassroomCommand object
 */
public class SetClassroomCommandParser implements Parser<SetClassroomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetClassroomCommand
     * and returns an SetClassroomCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetClassroomCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASSROOM);
        if (!arePrefixesPresent(argMultimap, PREFIX_CLASSROOM)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetClassroomCommand.MESSAGE_USAGE));
        }

        String classroomName = ParserUtil.parseClassroomName(argMultimap.getValue(PREFIX_CLASSROOM).get());
        Classroom classroom = new Classroom(classroomName);
        return new SetClassroomCommand(classroom.getClassroomName());
    }

    /**
     * Returns true if the prefix contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
