//@@author e0031374
package tagline.logic.parser.group;

import static java.util.Objects.requireNonNull;
import static tagline.logic.parser.ParserUtil.allPrefixesPresent;
import static tagline.logic.parser.group.GroupCliSyntax.GROUP_COMMAND_MISSING_GROUP_PROMPT_STRING;
import static tagline.logic.parser.group.GroupCliSyntax.PREFIX_CONTACTID;

import java.util.ArrayList;
import java.util.List;

import tagline.logic.commands.group.AddMemberToGroupCommand;
import tagline.logic.commands.group.EditGroupCommand.EditGroupDescriptor;
import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.ArgumentTokenizer;
import tagline.logic.parser.Parser;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;
import tagline.model.group.GroupNameEqualsKeywordPredicate;

/**
 * Parses input arguments and creates a new AddMemberToGroupCommand object.
 */
public class AddMemberToGroupParser implements Parser<AddMemberToGroupCommand> {
    public static final String ADD_MEMBER_TO_GROUP_NO_CONTACTS_PROMPT_STRING =
            "Please enter a contact ID to add to the group";

    /**
     * Parses the given {@code String} of arguments in the context of the AddMemberToGroupCommand
     * and returns an AddMemberToGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMemberToGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CONTACTID);

        checkCompulsoryFields(argMultimap);

        GroupNameEqualsKeywordPredicate predicate =
            GroupParserUtil.stringsToGroupNamePredicate(argMultimap.getPreamble());

        EditGroupDescriptor editGroupDescriptor = new EditGroupDescriptor();

        // converts list of specified String memberIds to MemberIds and add to editGroupDescriptor
        GroupParserUtil.parseMemberIdsForEdit(argMultimap
            .getAllValues(PREFIX_CONTACTID)).ifPresent(editGroupDescriptor::setMemberIds);

        // checks if user input list of String memberIds is empty
        if (!editGroupDescriptor.isAnyFieldEdited()) {
            throw new ParseException(AddMemberToGroupCommand.MESSAGE_NOT_ADDED);
        }

        if (editGroupDescriptor.getMemberIds().isEmpty() || editGroupDescriptor.getMemberIds().get().size() < 1) {
            throw new ParseException(AddMemberToGroupCommand.MESSAGE_NOT_ADDED);
        }

        return new AddMemberToGroupCommand(predicate, editGroupDescriptor);
    }

    //@@author tanlk99
    /**
     * Checks the compulsory fields of the command (i.e. group name, contact ID).
     * @throws PromptRequestException if a compulsory field is missing
     */
    private static void checkCompulsoryFields(ArgumentMultimap argMultimap) throws PromptRequestException {
        List<Prompt> promptList = new ArrayList<>();
        if (argMultimap.getPreamble().isEmpty()) {
            promptList.add(new Prompt("", GROUP_COMMAND_MISSING_GROUP_PROMPT_STRING));
        }

        if (!allPrefixesPresent(argMultimap, PREFIX_CONTACTID)) {
            promptList.add(new Prompt(PREFIX_CONTACTID.getPrefix(), ADD_MEMBER_TO_GROUP_NO_CONTACTS_PROMPT_STRING));
        }

        if (!promptList.isEmpty()) {
            throw new PromptRequestException(promptList);
        }
    }

}
