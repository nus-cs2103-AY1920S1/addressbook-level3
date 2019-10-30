//@@author e0031374
package tagline.logic.parser.group;

import static java.util.Objects.requireNonNull;
import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.parser.group.GroupCliSyntax.PREFIX_CONTACTID;
import static tagline.model.group.GroupModel.PREDICATE_SHOW_ALL_GROUPS;

import java.util.Optional;
import java.util.Set;

import tagline.logic.commands.group.ListGroupCommand;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.group.GroupMembersContainsSearchIdsPredicate;
import tagline.model.group.MemberId;

/**
 * Parses input arguments and creates a new ListGroupCommand object
 */
public class ListGroupParser implements Parser<ListGroupCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListGroupCommand
     * and returns an EditGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CONTACTID);

        // converts list of specified String memberIds to Empty if no Strings parsed in
        Optional<Set<MemberId>> optionalMemberIds =
            GroupParserUtil.parseMemberIdsForEdit(argMultimap.getAllValues(PREFIX_CONTACTID));

        // preamble is not allowed compulsory
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListGroupCommand.MESSAGE_USAGE));
        }

        if (optionalMemberIds.isPresent()) {
            return new ListGroupCommand(new GroupMembersContainsSearchIdsPredicate(optionalMemberIds.get()));
        }
        return new ListGroupCommand(PREDICATE_SHOW_ALL_GROUPS);
    }

}
