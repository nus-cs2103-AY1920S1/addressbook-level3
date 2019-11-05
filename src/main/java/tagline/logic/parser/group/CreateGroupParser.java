//@@author e0031374
package tagline.logic.parser.group;

import static tagline.logic.parser.group.GroupCliSyntax.GROUP_COMMAND_MISSING_GROUP_PROMPT_STRING;
import static tagline.logic.parser.group.GroupCliSyntax.PREFIX_CONTACTID;
import static tagline.logic.parser.group.GroupCliSyntax.PREFIX_GROUPDESCRIPTION;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import tagline.logic.commands.group.CreateGroupCommand;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;
import tagline.model.group.Group;
import tagline.model.group.GroupDescription;
import tagline.model.group.GroupName;
import tagline.model.group.MemberId;

/**
 * Parses input arguments and creates a new CreateGroupCommand object
 */
public class CreateGroupParser implements Parser<CreateGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateGroupCommand
     * and returns an CreateGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CONTACTID);

        checkCompulsoryFields(argMultimap);

        GroupName groupName = GroupParserUtil.parseGroupName(argMultimap.getPreamble());
        GroupDescription groupDescription = GroupParserUtil.parseGroupDescription(
            argMultimap.getValue(PREFIX_GROUPDESCRIPTION).orElse(""));

        // converts list of specified String memberIds to Empty if no Strings parsed in
        Optional<Set<MemberId>> optionalMemberIds =
            GroupParserUtil.parseMemberIdsForEdit(argMultimap.getAllValues(PREFIX_CONTACTID));
        Set<MemberId> memberIds = new HashSet<>();

        if (optionalMemberIds.isPresent()) {
            memberIds.addAll(optionalMemberIds.get());
        }

        Group group = new Group(groupName, groupDescription, memberIds);

        return new CreateGroupCommand(group);
    }

    //@@author tanlk99
    /**
     * Checks the compulsory fields of the command (i.e. group name).
     * @throws PromptRequestException if a compulsory field is missing
     */
    private static void checkCompulsoryFields(ArgumentMultimap argMultimap) throws PromptRequestException {
        // PREFIX_CONTACTID is optional
        if (argMultimap.getPreamble().isEmpty()) {
            throw new PromptRequestException(Collections.singletonList(
                    new Prompt("", GROUP_COMMAND_MISSING_GROUP_PROMPT_STRING)));
        }
    }

}
