package tagline.logic.parser.group;

import static java.util.Objects.requireNonNull;
import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.parser.group.GroupCliSyntax.PREFIX_CONTACTID;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import tagline.logic.commands.group.AddMemberToGroupCommand;
import tagline.logic.commands.group.AddMemberToGroupCommand.EditGroupDescriptor;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.Prefix;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.group.MemberId;

/**
 * Parses input arguments and creates a new AddMemberToGroupCommand object
 */
public class AddMemberToGroupParser implements Parser<AddMemberToGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMemberToGroupCommand
     * and returns an AddMemberToGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMemberToGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CONTACTID);

        // preamble and contactid is compulsory
        if (argMultimap.getPreamble().isEmpty() || !arePrefixesPresent(argMultimap, PREFIX_CONTACTID)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddMemberToGroupCommand.MESSAGE_USAGE));
        }

        String targetGroupName;
        targetGroupName = argMultimap.getPreamble();

        EditGroupDescriptor editGroupDescriptor = new EditGroupDescriptor();

        // converts list of specified String memberIds to MemberIds and add to editGroupDescriptor
        parseMemberIdsForEdit(argMultimap.getAllValues(PREFIX_CONTACTID)).ifPresent(editGroupDescriptor::setMemberIds);

        // checks if user input list of String memberIds is empty
        if (!editGroupDescriptor.isAnyFieldEdited()) {
            throw new ParseException(AddMemberToGroupCommand.MESSAGE_NOT_ADDED);
        }

        if (editGroupDescriptor.getMemberIds().isEmpty() || editGroupDescriptor.getMemberIds().get().size() < 1) {
            throw new ParseException(AddMemberToGroupCommand.MESSAGE_NOT_ADDED);
        }

        return new AddMemberToGroupCommand(targetGroupName, editGroupDescriptor);
    }

    /**
     * Parses {@code Collection<String> memberIds} into a {@code Set<MemberId>} if {@code memberIds} is non-empty.
     * If {@code memberIds} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<MemberId>} containing zero memberIds.
     */
    private Optional<Set<MemberId>> parseMemberIdsForEdit(Collection<String> memberIds) throws ParseException {
        assert memberIds != null;

        if (memberIds.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = memberIds.size() == 1 && memberIds.contains("")
            ? Collections.emptySet() : memberIds;
        return Optional.of(GroupParserUtil.parseMemberIds(tagSet));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
