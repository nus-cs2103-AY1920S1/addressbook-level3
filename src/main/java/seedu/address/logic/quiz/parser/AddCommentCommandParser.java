package seedu.address.logic.quiz.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.quiz.commands.AddCommentCommand;
import seedu.address.logic.quiz.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class AddCommentCommandParser implements Parser<AddCommentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommentCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] arrParameters = trimmedArgs.split(" <comment>");

        if (arrParameters.length != 2) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommentCommand.MESSAGE_USAGE));
        }

        Index index;
        String comment;

        try {
            index = ParserUtil.parseIndex(arrParameters[0]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommentCommand.MESSAGE_USAGE), pe);
        }

        comment = arrParameters[1];

        return new AddCommentCommand(index, comment);
    }
}
