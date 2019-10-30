package seedu.address.logic.parser.conditioncommandparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.logic.commands.conditioncommands.AddClassConditionCommand;
import seedu.address.logic.commands.conditioncommands.AddTagsConditionCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

public class AddTagsConditionCommandParser implements Parser<AddTagsConditionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddClassConditionCommand
     * and returns an AddClassConditionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagsConditionCommand parse(String args) throws ParseException {
        if (args == null || args.equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassConditionCommand.MESSAGE_USAGE));
        }
        List<Tag> tags = ParserUtil.parseTags(args.trim());
        return new AddTagsConditionCommand(tags);
    }
}
