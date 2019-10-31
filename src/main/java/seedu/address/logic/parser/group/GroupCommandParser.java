package seedu.address.logic.parser.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_INDEX_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE_MANUAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.HashMap;
import java.util.stream.Stream;

import seedu.address.logic.commands.group.GroupAddStudentCommand;
import seedu.address.logic.commands.group.GroupCommand;
import seedu.address.logic.commands.group.GroupCreateManuallyCommand;
import seedu.address.logic.commands.group.GroupExportCommand;
import seedu.address.logic.commands.group.GroupGetStudentsCommand;
import seedu.address.logic.commands.group.GroupRemoveStudentCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser to handle commands related to Group.
 */
public class GroupCommandParser implements Parser<GroupCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the QuizCommand
     * and returns an QuizCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_GROUP, PREFIX_MODE_MANUAL, PREFIX_EXPORT,
                        PREFIX_GROUP_ID, PREFIX_STUDENT_NUMBER, PREFIX_DELETE,
                        PREFIX_GROUP_INDEX_NUMBER, PREFIX_TYPE, PREFIX_LIST);

        if (argMultimap.getValue(PREFIX_MODE_MANUAL).isPresent()) { // Create manual command
            return createManuallyCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_EXPORT).isPresent()) {
            return exportGroupCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_STUDENT_NUMBER).isPresent()) { // Add command
            return addStudentCommand(argMultimap);
        } else if (argMultimap.getValue(PREFIX_DELETE).isPresent()) { // Remove command
            return removeStudentCommand(argMultimap);
        } else { // List command
            return getStudentsCommand(argMultimap);
        }
    }

    /**
     * Add student to the group specified.
     *
     * @param argMultimap Arguments Multimap.
     * @return Group export command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private GroupExportCommand exportGroupCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP_ID, PREFIX_EXPORT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT, GroupExportCommand.MESSAGE_USAGE));
        }
        String groupId = argMultimap.getValue(PREFIX_GROUP_ID).orElse("");
        return new GroupExportCommand(groupId);
    }

    /**
     * Creates a group by specifying individual students and groupID.
     *
     * @param argMultimap Arguments Multimap.
     * @return Group create manually command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private GroupCreateManuallyCommand createManuallyCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP_ID, PREFIX_STUDENT_NUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCreateManuallyCommand.MESSAGE_USAGE));
        }

        HashMap<String, String> fields = new HashMap<>();
        fields.put("groupID", argMultimap.getValue(PREFIX_GROUP_ID).orElse(""));
        fields.put("studentNumbers", argMultimap.getValue(PREFIX_STUDENT_NUMBER).orElse(""));

        return new GroupCreateManuallyCommand(fields);
    }

    /**
     * Add student to the group specified.
     *
     * @param argMultimap Arguments Multimap.
     * @return Group add student command if the parsing was successful.
     * @throws ParseException if the input was incorrectly formatted.
     */
    private GroupAddStudentCommand addStudentCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP_ID, PREFIX_GROUP_INDEX_NUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAddStudentCommand.MESSAGE_USAGE));
        }

        String groupId = argMultimap.getValue(PREFIX_GROUP_ID).orElse("");
        int studentNumber = Integer.parseInt(argMultimap.getValue(PREFIX_STUDENT_NUMBER).orElse(""));
        int groupIndexNumber = Integer.parseInt(argMultimap.getValue(PREFIX_GROUP_INDEX_NUMBER).orElse(""));

        return new GroupAddStudentCommand(groupId, studentNumber, groupIndexNumber);
    }

    /**
     * Remove student from the group specified.
     *
     * @param argMultimap Arguments Multimap.
     * @return Group remove student command if the parsing was successful.
     * @throws ParseException if the input was in an incorrect format.
     */
    private GroupRemoveStudentCommand removeStudentCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT, GroupRemoveStudentCommand.MESSAGE_USAGE));
        }

        String groupId = argMultimap.getValue(PREFIX_GROUP_ID).orElse("");
        int groupIndexNumber = Integer.parseInt(argMultimap.getValue(PREFIX_GROUP_INDEX_NUMBER).orElse(""));

        return new GroupRemoveStudentCommand(groupId, groupIndexNumber);
    }

    /**
     * Gets students from the group specified.
     *
     * @param argMultimap Argument multimap.
     * @return Group get students command if the parsing was successful.
     * @throws ParseException if the input was in an incorrect format.
     */
    private GroupGetStudentsCommand getStudentsCommand(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    GroupGetStudentsCommand.MESSAGE_USAGE));
        }

        String groupId = argMultimap.getValue(PREFIX_GROUP_ID).orElse("");
        return new GroupGetStudentsCommand(groupId);
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
