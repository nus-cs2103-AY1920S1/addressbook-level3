//@@author e0031374
package tagline.logic.parser.group;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import tagline.commons.core.Messages;
import tagline.logic.commands.group.FindGroupCommand;
import tagline.logic.parser.Parser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.group.GroupName;
import tagline.model.group.GroupNameEqualsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindGroupCommand object
 */
public class FindGroupParser implements Parser<FindGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindGroupCommand
     * and returns a FindGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindGroupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindGroupCommand.MESSAGE_USAGE));
        }

        Optional<Set<GroupName>> optNameSet = GroupParserUtil.parseGroupNamesForSearch(Arrays.asList(trimmedArgs));
        if (optNameSet.isEmpty()) {
            throw new ParseException(Messages.MESSAGE_INVALID_GROUP_NAME + ": " + trimmedArgs);
        }
        return new FindGroupCommand(new GroupNameEqualsKeywordPredicate(optNameSet.get()));
    }

}
